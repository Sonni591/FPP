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

    public ClassicLotScheduling(List<Product> products) {
	super();
	this.products = products;
	tOptSingle = new HashMap<Integer, Double>();
    }

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

    private void calculateEfficiencyOfMachine() {
	for (Product product : products) {
	    product.setRoh(product.getD() / product.getP());
	}
    }

    private void calculateOptProductionCycle() {

	for (Product product : products) {
	    tOptSingle.put(
		    product.getK(),
		    Math.sqrt((2 * product.getS() / (product.getH()
			    * product.getD() * (1 - product.getRoh())))));
	}
    }

    private void calculateProductionTime() {

	for (Product product : products) {
	    product.setT(product.getQ() / product.getP());
	}
    }

    private void calculateBatchSize() {

	for (Product product : products) {
	    product.setQ(Math.sqrt((2 * product.getD() * product.getS())
		    / (product.getH() * (1 - (product.getD() / product.getP())))));
	}
    }

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

    @Override
    public String getDescriptionToString() {
	return "Berechnung der optimalen Losgrößen der Produkte 1-"
		+ products.size()
		+ " mit Hilfe des klassischen Losgrößenverfahrens" + "\n";
    }
}
