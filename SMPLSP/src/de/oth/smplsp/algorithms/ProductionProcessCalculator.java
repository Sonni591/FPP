package de.oth.smplsp.algorithms;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import de.oth.smplsp.model.LotSchedulingResult;
import de.oth.smplsp.model.Product;
import de.oth.smplsp.model.ProductionProcess;

public class ProductionProcessCalculator {

    private LotSchedulingResult result;
    private List<ProductionProcess> processes = new ArrayList<ProductionProcess>();

    /**
     * @param result
     */
    public ProductionProcessCalculator(LotSchedulingResult result) {
	super();
	this.result = result;
    }

    private void calculateProcess() {
	for (Product product : result.getProducts()) {
	    ProductionProcess processRuesten = new ProductionProcess();
	    processRuesten.setK(product.getKProperty());
	    processRuesten.setVorgang(new SimpleStringProperty("Rüsten"));
	    if (processes.size() == 0) {
		processRuesten.setStart(new SimpleDoubleProperty(0.0));
	    } else {
		ProductionProcess vorgaenger = processes
			.get(processes.size() - 1);
		double startzeit = vorgaenger.getEnde().doubleValue();
		processRuesten.setStart(new SimpleDoubleProperty(startzeit));
	    }
	    double ende = processRuesten.getStart().doubleValue()
		    + product.getTau();
	    processRuesten.setEnde(new SimpleDoubleProperty(ende));
	    processes.add(processRuesten);

	    ProductionProcess processProduction = new ProductionProcess();
	    processProduction.setK(null);
	    processProduction
		    .setVorgang(new SimpleStringProperty("Produktion"));
	    processProduction.setStart(processRuesten.getEnde());
	    double endeProduktion = processProduction.getStart().doubleValue()
		    + product.getT();
	    processProduction.setEnde(new SimpleDoubleProperty(endeProduktion));
	    processes.add(processProduction);
	}
    }

    public List<ProductionProcess> getProductionProcessPlan() {
	if (processes.size() == 0) {
	    calculateProcess();
	}
	return processes;
    }
}
