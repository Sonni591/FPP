package model;

import util.MathUtils;

public class Product {

    // TODO: translate getters

    // Produktnummer
    private int k;
    // Nachfragerate
    private double D;
    // Produktionsrate
    private double p;
    // Ruestzeit
    private double tau;
    // Ruestkostensatz
    private double s;
    // Lagerkostensatz
    private double h;
    // Losgroesse
    private double q;
    // production time (Produktionsdauer)
    private double t;
    // Auslastung der Anlage
    private double roh;

    public Product(double d, double p, double t, double s, double h, int k) {
        this.D = d;
        this.p = p;
        this.tau = t;
        this.s = s;
        this.h = h;
        this.k = k;
    }

    /**
     * @return the lot size
     */
    public double getQ() {
        return q;
    }

    /**
     * @param q
     *            lot size to set
     */
    public void setQ(double q) {
        this.q = q;
    }

    /**
     * @return the D
     */
    public double getD() {
        return D;
    }

    /**
     * @return the p
     */
    public double getP() {
        return p;
    }

    /**
     * @return the t
     */
    public double getTau() {
        return tau;
    }

    /**
     * @return the s
     */
    public double getS() {
        return s;
    }

    /**
     * @return the h
     */
    public double getH() {
        return h;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getRoh() {
        return roh;
    }

    /**
     * @param roh
     *            the roh to set
     */
    public void setRoh(double roh) {
        this.roh = roh;
    }

    /**
     * @return the k
     */
    public int getK() {
        return k;
    }

    /**
     * @param k
     *            the k to set
     */
    public void setK(int k) {
        this.k = k;
    }

    public String getQToString() {
        return "Die optimale Losgröße für das Produkt mit Produktnummer " + k
                + " beträgt: " + MathUtils.round(q, 2) + "\n";
    }

    public String getTToString() {
        return "Die optimale Produktionsdauer für das Produkt mit Produktnummer "
                + k + " beträgt: " + MathUtils.round(t, 2) + "\n";
    }

    public String getRohToString() {
        return "Die Auslastung der Anlage für das Produkt mit Produktnummer "
                + k + " beträgt: " + MathUtils.round(roh, 2) + "\n";
    }
}
