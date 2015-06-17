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

	    // für zweiten Zyklus
	    double zyklusdauer = product.getQ() / product.getD();
	    processRuesten.setStart_zyklus2(new SimpleDoubleProperty(
		    processRuesten.getStart().doubleValue() + zyklusdauer));
	    processRuesten.setEnde_zyklus2(new SimpleDoubleProperty(
		    processRuesten.getEnde().doubleValue() + zyklusdauer));
	    processes.add(processRuesten);

	    ProductionProcess processProduction = new ProductionProcess();
	    processProduction.setK(null);
	    processProduction
		    .setVorgang(new SimpleStringProperty("Produktion"));
	    processProduction.setStart(processRuesten.getEnde());
	    double endeProduktion = processProduction.getStart().doubleValue()
		    + product.getT();
	    processProduction.setEnde(new SimpleDoubleProperty(endeProduktion));

	    // Für zweiten Zyklus
	    processProduction.setStart_zyklus2(new SimpleDoubleProperty(
		    processProduction.getStart().doubleValue() + zyklusdauer));
	    processProduction.setEnde_zyklus2(new SimpleDoubleProperty(
		    processProduction.getEnde().doubleValue() + zyklusdauer));
	    processes.add(processProduction);
	}
    }

    public List<ProductionProcess> getProductionProcessPlan() {
	if (processes.size() == 0) {
	    calculateProcess();
	}
	return processes;
    }

    public List<ProductionProcess> getProductionProcessPlanForTable() {
	List<ProductionProcess> processList = new ArrayList<ProductionProcess>();
	if (processes.size() != 0) {
	    processList = addTotalDurationForTableView();
	}
	return processList;
    }

    public List<ProductionProcess> addTotalDurationForTableView() {
	List<ProductionProcess> processesTable = new ArrayList<ProductionProcess>(
		processes);
	ProductionProcess totalDuration = new ProductionProcess();
	totalDuration.setK(null);
	totalDuration.setVorgang(new SimpleStringProperty("Gesamtdauer"));
	ProductionProcess lastProcess = processes.get(processes.size() - 1);
	totalDuration.setEnde(lastProcess.getEnde());
	processesTable.add(totalDuration);
	return processesTable;
    }
}
