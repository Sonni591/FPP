package de.oth.smplsp.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class ProductionProcess {

    // Produktnummer
    private IntegerProperty k;
    // Vorgang
    private StringProperty process;
    // Start of cycle 1
    private DoubleProperty startCycle1;
    // End of cycle 1
    private DoubleProperty endCycle1;
    // Start of cycle 1
    private DoubleProperty startCycle2;
    // End of cycle 1
    private DoubleProperty endCycle2;

    /**
     * @return the k
     */
    public IntegerProperty getK() {
	return k;
    }

    /**
     * @param k
     *            the k to set
     */
    public void setK(IntegerProperty k) {
	this.k = k;
    }

    /**
     * @return the process
     */
    public StringProperty getProcess() {
	return process;
    }

    /**
     * @param process
     *            the process to set
     */
    public void setProcess(StringProperty process) {
	this.process = process;
    }

    /**
     * @return the startCycle1
     */
    public DoubleProperty getStartCycle1() {
	return startCycle1;
    }

    /**
     * @param startCycle1
     *            the startCycle1 to set
     */
    public void setStartCycle1(DoubleProperty start) {
	this.startCycle1 = start;
    }

    /**
     * @return the endCycle1
     */
    public DoubleProperty getEndCycle1() {
	return endCycle1;
    }

    /**
     * @param endCycle1
     *            the endCycle1 to set
     */
    public void setEndCycle1(DoubleProperty end) {
	this.endCycle1 = end;
    }

    /**
     * @return the startCycle2
     */
    public DoubleProperty getStartCycle2() {
	return startCycle2;
    }

    /**
     * @param startCycle2
     *            the startCycle2 to set
     */
    public void setStartCycle2(DoubleProperty startCycle2) {
	this.startCycle2 = startCycle2;
    }

    /**
     * @return the endCycle2
     */
    public DoubleProperty getEndCycle2() {
	return endCycle2;
    }

    /**
     * @param endCycle2
     *            * the endCycle2 to set
     */
    public void setEndCycle2(DoubleProperty endCycle2) {
	this.endCycle2 = endCycle2;
    }

}
