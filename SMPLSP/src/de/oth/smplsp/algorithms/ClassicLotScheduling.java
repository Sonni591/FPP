package de.oth.smplsp.algorithms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.oth.smplsp.model.LotSchedulingResult;
import de.oth.smplsp.model.Product;

public class ClassicLotScheduling implements IBasicLotSchedulingAlgorithm {

    private List<Product> products;
    private Map<Integer, Double> tOptSingle;
    private LotSchedulingResult result;

    /**
     * Initializes a newly created ClassicLotScheduling object with the products
     * on which the algorithm works
     * 
     * @param products
     */
    public ClassicLotScheduling(List<Product> products) {
	super();
	this.products = products;
	tOptSingle = new HashMap<Integer, Double>();
    }

    /**
     * @see de.oth.smplsp.algorithms.IBasicLotSchedulingAlgorithm#calculateInTotal()
     */
    @Override
    public LotSchedulingResult calculateInTotal() {

	calculateBatchSize();
	calculateProductionTime();
	calculateEfficiencyOfMachine();
	calculateOptProductionCycle();
	calculateRange();
	result = new LotSchedulingResult(products, tOptSingle);

	return result;
    }

    /**
     * calculates the efficiency of machine for all products
     */
    private void calculateEfficiencyOfMachine() {
	for (Product product : products) {
	    product.setRoh(product.getD() / product.getP());
	}
    }

    /**
     * calculates the optimal production cycle for all products
     */
    private void calculateOptProductionCycle() {

	for (Product product : products) {
	    tOptSingle.put(
		    product.getK(),
		    Math.sqrt((2 * product.getS() / (product.getH()
			    * product.getD() * (1 - product.getRoh())))));
	}
    }

    /**
     * calculates the production time for all products
     */
    private void calculateProductionTime() {

	for (Product product : products) {
	    product.setT(product.getQ() / product.getP());
	}
    }

    /**
     * calculates the batch size for all products
     */
    private void calculateBatchSize() {

	for (Product product : products) {
	    product.setQ(Math.sqrt((2 * product.getD() * product.getS())
		    / (product.getH() * (1 - (product.getD() / product.getP())))));
	}
    }

    /**
     * calculates the range for all products
     */
    private void calculateRange() {

	for (Product product : products) {
	    product.setR(product.getQ() / product.getD());
	}
    }

    /**
     * @return the result
     */
    @Override
    public LotSchedulingResult getResult() {
	return result;
    }

    /**
     * @see de.oth.smplsp.algorithms.IBasicLotSchedulingAlgorithm#getDescriptionToString()
     */
    @Override
    public String getDescriptionToString() {
	return "Berechnung der optimalen Losgrößen der Produkte 1-"
		+ products.size()
		+ " mit Hilfe des klassischen Losgrößenverfahrens" + "\n";
    }
}
