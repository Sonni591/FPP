package de.oth.smplsp.algorithms;

import java.util.List;

import de.oth.smplsp.error.MinimalProductionCycleError;
import de.oth.smplsp.model.LotSchedulingResult;
import de.oth.smplsp.model.Product;

public class MultiProductLotScheduling implements IBasicLotSchedulingAlgorithm {

    private List<Product> products;
    private LotSchedulingResult result;
    private Double tOpt;
    private Double tMin;

    /**
     * Initializes a newly created MultiProductLotScheduling object with the
     * products
     * 
     * @param products
     */
    public MultiProductLotScheduling(List<Product> products) {
	super();
	this.products = products;
	tOpt = null;
	tMin = null;
    }

    /**
     * @see de.oth.smplsp.algorithms.IBasicLotSchedulingAlgorithm#calculateInTotal()
     */
    @Override
    public LotSchedulingResult calculateInTotal()
	    throws MinimalProductionCycleError {

	calculateEfficiencyOfMachine();

	calculateOptProductionCycle();

	calculateMinProductionCycle();

	if (tOpt < tMin) {
	    throw new MinimalProductionCycleError();
	}

	calculateBatchSize();

	calculateProductionTime();

	calculateRange();

	this.result = new LotSchedulingResult(products, tOpt, tMin);

	return result;
    }

    /**
     * calculates the batch size for all products
     */
    private void calculateBatchSize() {
	for (Product product : products) {
	    product.setQ(product.getD() * tOpt);
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
     * calculates the minimal accepted common production cycle
     */
    private void calculateMinProductionCycle() {
	double numerator = 0.0;
	double denominator = 0.0;

	for (Product product : products) {
	    numerator += product.getTau();
	}

	for (Product product : products) {
	    denominator += product.getRoh();
	}

	denominator = 1 - denominator;

	tMin = (numerator / denominator);
    }

    /**
     * calculates the optimal common production cycle
     */
    private void calculateOptProductionCycle() {
	double numerator = 0.0;
	double denominator = 0.0;

	for (Product product : products) {
	    numerator += product.getS();
	}

	numerator *= 2;

	for (Product product : products) {
	    denominator += (product.getH() * product.getD() * (1 - product
		    .getRoh()));
	}

	tOpt = Math.sqrt(numerator / denominator);
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
     * calculates the production time for all products
     */
    private void calculateProductionTime() {
	for (Product product : products) {
	    product.setT(product.getQ() / product.getP());
	}
    }

    /**
     * @return the result
     * @throws MinimalProductionCycleError
     */
    @Override
    public LotSchedulingResult getResult() {
	return result;
    }

    /**
     * @see de.oth.smplsp.algorithms.IBasicLotSchedulingAlgorithm#getDescriptionToString()
     */
    public String getDescriptionToString() {
	return "Berechnung der optimalen Losgrößen der Produkte 1-"
		+ products.size()
		+ " mit Hilfe der statischen Mehrproduktlosgrößenplanung"
		+ "\n";
    }

}
