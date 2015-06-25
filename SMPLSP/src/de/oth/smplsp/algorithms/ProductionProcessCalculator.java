package de.oth.smplsp.algorithms;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import de.oth.smplsp.model.LotSchedulingResult;
import de.oth.smplsp.model.Product;
import de.oth.smplsp.model.ProductionProcess;

public class ProductionProcessCalculator {

    // TODO: translate

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
	    processRuesten.setProcess(new SimpleStringProperty("Rüsten"));
	    if (processes.size() == 0) {
		processRuesten.setStartCycle1(new SimpleDoubleProperty(0.0));
	    } else {
		ProductionProcess vorgaenger = processes
			.get(processes.size() - 1);
		double startzeit = vorgaenger.getEndCycle1().doubleValue();
		processRuesten.setStartCycle1(new SimpleDoubleProperty(startzeit));
	    }
	    double ende = processRuesten.getStartCycle1().doubleValue()
		    + product.getTau();
	    processRuesten.setEndCycle1(new SimpleDoubleProperty(ende));

	    // for second cycle
	    double zyklusdauer = product.getQ() / product.getD();
	    processRuesten.setStartCycle2(new SimpleDoubleProperty(
		    processRuesten.getStartCycle1().doubleValue() + zyklusdauer));
	    processRuesten.setEndCycle2(new SimpleDoubleProperty(
		    processRuesten.getEndCycle1().doubleValue() + zyklusdauer));
	    processes.add(processRuesten);

	    ProductionProcess processProduction = new ProductionProcess();
	    processProduction.setK(null);
	    processProduction
		    .setProcess(new SimpleStringProperty("Produktion"));
	    processProduction.setStartCycle1(processRuesten.getEndCycle1());
	    double endeProduktion = processProduction.getStartCycle1().doubleValue()
		    + product.getT();
	    processProduction.setEndCycle1(new SimpleDoubleProperty(endeProduktion));

	    // for second cycle
	    processProduction.setStartCycle2(new SimpleDoubleProperty(
		    processProduction.getStartCycle1().doubleValue() + zyklusdauer));
	    processProduction.setEndCycle2(new SimpleDoubleProperty(
		    processProduction.getEndCycle1().doubleValue() + zyklusdauer));
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
	totalDuration.setProcess(new SimpleStringProperty("Gesamtdauer"));
	ProductionProcess lastProcess = processes.get(processes.size() - 1);
	totalDuration.setEndCycle1(lastProcess.getEndCycle1());
	processesTable.add(totalDuration);
	return processesTable;
    }
}
