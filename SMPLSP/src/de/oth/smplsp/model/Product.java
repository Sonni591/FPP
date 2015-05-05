package de.oth.smplsp.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import de.oth.smplsp.util.MathUtils;

public class Product {

    // TODO: translate getters

    // Produktnummer
    private IntegerProperty k;
    // Nachfragerate
    private DoubleProperty d;
    // Produktionsrate
    private DoubleProperty p;
    // Ruestzeit
    private DoubleProperty tau;
    // Ruestkostensatz
    private DoubleProperty s;
    // Lagerkostensatz
    private DoubleProperty h;
    // Losgroesse
    private DoubleProperty q;
    // production time (Produktionsdauer)
    private DoubleProperty t;
    // Auslastung der Anlage
    private DoubleProperty roh;

    // Constructor
    /**
     *
     * @param k
     * @param d
     * @param p
     * @param tau
     * @param s
     * @param h
     */
    public Product(int k, double d, double p, double tau, double s, double h) {
	super();
	this.k = new SimpleIntegerProperty(k); // TODO
	this.d = new SimpleDoubleProperty(d);
	this.p = new SimpleDoubleProperty(p);
	this.tau = new SimpleDoubleProperty(tau);
	this.s = new SimpleDoubleProperty(s);
	this.h = new SimpleDoubleProperty(h);
    }

    /**
     * @param k
     *            the k to set
     */
    public void setK(IntegerProperty k) {
	this.k = k;
    }

    /**
     * @param d
     *            the d to set
     */
    public void setD(DoubleProperty d) {
	this.d = d;
    }

    /**
     * @param p
     *            the p to set
     */
    public void setP(DoubleProperty p) {
	this.p = p;
    }

    /**
     * @param tau
     *            the tau to set
     */
    public void setTau(DoubleProperty tau) {
	this.tau = tau;
    }

    /**
     * @param s
     *            the s to set
     */
    public void setS(DoubleProperty s) {
	this.s = s;
    }

    /**
     * @param h
     *            the h to set
     */
    public void setH(DoubleProperty h) {
	this.h = h;
    }

    /**
     * @param q
     *            the q to set
     */
    public void setQ(DoubleProperty q) {
	this.q = q;
    }

    /**
     * @param t
     *            the t to set
     */
    public void setT(DoubleProperty t) {
	this.t = t;
    }

    /**
     * @param roh
     *            the roh to set
     */
    public void setRoh(DoubleProperty roh) {
	this.roh = roh;
    }

    /**
     * @return the lot size
     */
    public double getQ() {
	return q.get();
    }

    /**
     * @param q
     *            lot size to set
     */
    public void setQ(double q) {
	this.q = new SimpleDoubleProperty(q);
    }

    /**
     * @return the D
     */
    public double getD() {
	return d.get();
    }

    /**
     * @return the p
     */
    public double getP() {
	return p.get();
    }

    /**
     * @return the t
     */
    public double getTau() {
	return tau.get();
    }

    /**
     * @return the s
     */
    public double getS() {
	return s.get();
    }

    /**
     * @return the h
     */
    public double getH() {
	return h.get();
    }

    public double getT() {
	return t.get();
    }

    public void setT(double t) {
	this.t = new SimpleDoubleProperty(t);
    }

    public double getRoh() {
	return roh.get();
    }

    /**
     * @param roh
     *            the roh to set
     */
    public void setRoh(double roh) {
	this.roh = new SimpleDoubleProperty(roh);
    }

    /**
     * @return the k
     */
    public int getK() {
	return k.get();
    }

    /**
     * @param k
     *            the k to set
     */
    public void setK(int k) {
	this.k = new SimpleIntegerProperty(k);
    }

    public String getQToString() {
	return "Die optimale Losgröße für das Produkt mit Produktnummer " + k
		+ " beträgt: " + MathUtils.round(getQ(), 2) + "\n";
    }

    public String getTToString() {
	return "Die optimale Produktionsdauer für das Produkt mit Produktnummer "
		+ k + " beträgt: " + MathUtils.round(getT(), 2) + "\n";
    }

    public String getRohToString() {
	return "Die Auslastung der Anlage für das Produkt mit Produktnummer "
		+ k + " beträgt: " + MathUtils.round(getRoh(), 2) + "\n";
    }
}
