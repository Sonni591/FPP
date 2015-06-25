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
     * Initializes a newly created ProductionProcessCalculator object with the
     * result of the algorithm
     * 
     * @param result
     */
    public ProductionProcessCalculator(LotSchedulingResult result) {
	super();
	this.result = result;
    }

    /**
     * calculates the process plan for the result of the algorithm
     */
    private void calculateProcess() {
	for (Product product : result.getProducts()) {
	    setProcessSetUp(product);
	    setProductionProcess(product);
	}
    }

    /**
     * sets the setup process for the specific product in the process plan
     * 
     * @param product
     */
    private void setProcessSetUp(Product product) {
	ProductionProcess processSetUp = new ProductionProcess();
	processSetUp.setK(product.getKProperty());
	processSetUp.setProcess(new SimpleStringProperty("Rüsten"));
	if (processes.size() == 0) {
	    processSetUp.setStartCycle1(new SimpleDoubleProperty(0.0));
	} else {
	    ProductionProcess predecessor = processes.get(processes.size() - 1);
	    double startTime = predecessor.getEndCycle1().doubleValue();
	    processSetUp.setStartCycle1(new SimpleDoubleProperty(startTime));
	}
	double endTime = processSetUp.getStartCycle1().doubleValue()
		+ product.getTau();
	processSetUp.setEndCycle1(new SimpleDoubleProperty(endTime));
	prepareSetUpProcessForSecondCycle(product, processSetUp);
    }

    /**
     * prepares the setup process for the second cycle
     * 
     * @param product
     * @param processSetUp
     */
    private void prepareSetUpProcessForSecondCycle(Product product,
	    ProductionProcess processSetUp) {
	// for second cycle
	double cycleTime = product.getQ() / product.getD();
	processSetUp.setStartCycle2(new SimpleDoubleProperty(processSetUp
		.getStartCycle1().doubleValue() + cycleTime));
	processSetUp.setEndCycle2(new SimpleDoubleProperty(processSetUp
		.getEndCycle1().doubleValue() + cycleTime));
	processes.add(processSetUp);
    }

    /**
     * sets the production process for the specific product in the process plan
     * 
     * @param product
     */
    private void setProductionProcess(Product product) {
	ProductionProcess processProduction = new ProductionProcess();
	processProduction.setK(null);
	processProduction.setProcess(new SimpleStringProperty("Produktion"));
	ProductionProcess processSetUp = processes.get(processes.size() - 1);
	processProduction.setStartCycle1(processSetUp.getEndCycle1());
	double endProduction = processProduction.getStartCycle1().doubleValue()
		+ product.getT();
	processProduction.setEndCycle1(new SimpleDoubleProperty(endProduction));

	prepareProductionProcessForSecondCycle(product, processProduction);
    }

    /**
     * prepares the production process for the second cycle
     * 
     * @param product
     * @param processProduction
     */
    private void prepareProductionProcessForSecondCycle(Product product,
	    ProductionProcess processProduction) {
	// for second cycle
	double cycleTime = product.getQ() / product.getD();
	processProduction.setStartCycle2(new SimpleDoubleProperty(
		processProduction.getStartCycle1().doubleValue() + cycleTime));
	processProduction.setEndCycle2(new SimpleDoubleProperty(
		processProduction.getEndCycle1().doubleValue() + cycleTime));
	processes.add(processProduction);
    }

    /**
     * generates the process plan for the given result of the algorithm
     * 
     * @return the generated process plan
     */
    public List<ProductionProcess> getProductionProcessPlan() {
	if (processes.size() == 0) {
	    calculateProcess();
	}
	return processes;
    }

    /**
     * @return the generated process plan modified for presentation in chart
     */
    public List<ProductionProcess> getProductionProcessPlanForTable() {
	List<ProductionProcess> processList = new ArrayList<ProductionProcess>();
	if (processes.size() != 0) {
	    processList = addTotalDurationForTableView();
	}
	return processList;
    }

    /**
     * adds the total duration (cycle time) to the process plan
     * 
     * @return the generated process plan with cycle time
     */
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
