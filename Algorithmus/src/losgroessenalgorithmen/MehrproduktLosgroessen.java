package losgroessenalgorithmen;

import java.util.List;

import model.LotSchedulingResult;
import model.Product;

public class MehrproduktLosgroessen implements IBasicLotSchedulingAlgorithm {

    private List<Product> products;
    private Double tOpt;
    private Double tMin;

    public MehrproduktLosgroessen(List<Product> products) {
        super();
        this.products = products;
        tOpt = null;
        tMin = null;
    }

    @Override
    public LotSchedulingResult calculateInTotal() {

        calculateEfficiencyOfMachine();

        calculateOptProductionCycle();

        calculateMinProductionCycle();

        if (tOpt < tMin) {
            // TODO: Was passiert in diesem Fall?
            tOpt = tMin;
        }

        calculateBatchSize();

        calculateProductionTime();

        return new LotSchedulingResult(products, tOpt, tMin);
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

    @Override
    public String getDescriptionToString() {
        return "Berechnung der optimalen Losgrößen der Produkte 1-"
                + products.size()
                + " mit Hilfe der statischen Mehrproduktlosgrößenplanung"
                + "\n";
    }
}
