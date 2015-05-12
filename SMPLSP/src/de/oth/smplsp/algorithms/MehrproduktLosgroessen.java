package de.oth.smplsp.algorithms;

import java.util.List;

import de.oth.smplsp.error.MinimalProductionCycleError;
import de.oth.smplsp.model.LotSchedulingResult;
import de.oth.smplsp.model.Product;

public class MehrproduktLosgroessen implements IBasicLotSchedulingAlgorithm {

    private List<Product> products;
    private LotSchedulingResult result;
    private Double tOpt;
    private Double tMin;

    public MehrproduktLosgroessen(List<Product> products) {
	super();
	this.products = products;
	tOpt = null;
	tMin = null;
    }

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

	this.result = new LotSchedulingResult(products, tOpt, tMin);

	return result;
    }

    private void calculateBatchSize() {
	for (Product product : products) {
	    product.setQ(product.getD() * tOpt);
	}
    }

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

    private void calculateEfficiencyOfMachine() {
	for (Product product : products) {
	    product.setRoh(product.getD() / product.getP());
	}
    }

    private void calculateProductionTime() {
	for (Product product : products) {
	    product.setT(product.getQ() / product.getP());
	}
    }

    /**
     * @return the result
     * @throws MinimalProductionCycleError
     */
    public LotSchedulingResult getResult() throws MinimalProductionCycleError {
	if (result == null) {
	    result = calculateInTotal();
	}
	return result;
    }

    @Override
    public String getDescriptionToString() {
	return "Berechnung der optimalen Losgrößen der Produkte 1-"
		+ result.getProducts().size()
		+ " mit Hilfe der statischen Mehrproduktlosgrößenplanung"
		+ "\n";
    }

}
