package de.oth.smplsp.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InputData {

    private IntegerProperty k;
    private DoubleProperty d;
    private DoubleProperty p;
    private DoubleProperty tau;
    private IntegerProperty s;
    private DoubleProperty h;

    private static Integer indexCount = 1;

    // Constructors

    /**
     * Default constructor.
     */
    public InputData() {
	this(null, null, null, null, null, null);
    }

    /**
     * Constructor with all field
     * 
     * @param k
     * @param d
     * @param p
     * @param tau
     * @param s
     * @param h
     * @param object
     */
    public InputData(Integer k, Double d, Double p, Double tau, Integer s,
	    Double h) {
	super();
	this.k = new SimpleIntegerProperty(indexCount++);
	this.d = new SimpleDoubleProperty(d);
	this.p = new SimpleDoubleProperty(p);
	this.tau = new SimpleDoubleProperty(tau);
	this.s = new SimpleIntegerProperty(s);
	this.h = new SimpleDoubleProperty(h);
    }

    /**
     * Constructor with auto-assigned index
     * 
     * @param d
     * @param p
     * @param tau
     * @param s
     * @param h
     */
    // public InputData(Double d, Double p, Double tau, Integer s, Double h) {
    // super();
    // this.k = indexCount++;
    // this.d = d;
    // this.p = p;
    // this.tau = tau;
    // this.s = s;
    // this.h = h;
    // }

    // Getters and Setters

    /**
     * @return the k
     */

    public IntegerProperty kProperty() {
	return k;
    }

    public DoubleProperty dProperty() {
	return d;
    }

    public DoubleProperty pProperty() {
	return p;
    }

    public DoubleProperty tauProperty() {
	return tau;
    }

    public IntegerProperty sProperty() {
	return s;
    }

    public DoubleProperty hProperty() {
	return h;
    }

    public Integer getK() {
	return k.get();
    }

    /**
     * @param k
     *            the k to set
     */
    public void setK(Integer k) {
	this.k.set(k);
    }

    /**
     * @return the d
     */
    public Double getD() {
	return d.get();
    }

    /**
     * @param d
     *            the d to set
     */
    public void setD(Double d) {
	this.d.set(d);
    }

    /**
     * @return the p
     */
    public Double getP() {
	return p.get();
    }

    /**
     * @param p
     *            the p to set
     */
    public void setP(Double p) {
	this.p.set(p);
    }

    /**
     * @return the tau
     */
    public Double getTau() {
	return tau.get();
    }

    /**
     * @param tau
     *            the tau to set
     */
    public void setTau(Double tau) {
	this.tau.set(tau);
    }

    /**
     * @return the s
     */
    public Integer getS() {
	return s.get();
    }

    /**
     * @param s
     *            the s to set
     */
    public void setS(Integer s) {
	this.s.set(s);
    }

    /**
     * @return the h
     */
    public double getH() {
	return h.get();
    }

    /**
     * @param h
     *            the h to set
     */
    public void setH(Double h) {
	this.h.set(h);
    }

    /**
     * @return the indexCount
     */
    public static Integer getIndexCount() {
	return indexCount;
    }

    /**
     * @param indexCount
     *            the indexCount to set
     */
    public static void setIndexCount(Integer indexCount) {
	InputData.indexCount = indexCount;
    }

}
