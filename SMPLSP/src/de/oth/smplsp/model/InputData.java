package de.oth.smplsp.model;

public class InputData {

    private Integer k;
    private Double d;
    private Double p;
    private Double tau;
    private Integer s;
    private Double h;

    private static Integer indexCount = 1;

    // Constructors

    /**
     * Default constructor.
     */
    public InputData() {
	this(null, null, null, null, null, null, null);
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
    private InputData(Integer k, Double d, Double p, Double tau, Integer s,
	    Double h, Object object) {
	super();
	this.k = indexCount++;
	this.d = d;
	this.p = p;
	this.tau = tau;
	this.s = s;
	this.h = h;
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
    public InputData(Double d, Double p, Double tau, Integer s, Double h) {
	super();
	this.k = indexCount++;
	this.d = d;
	this.p = p;
	this.tau = tau;
	this.s = s;
	this.h = h;
    }

    // Getters and Setters

    /**
     * @return the k
     */
    public Integer getK() {
	return k;
    }

    /**
     * @param k
     *            the k to set
     */
    public void setK(Integer k) {
	this.k = k;
    }

    /**
     * @return the d
     */
    public Double getD() {
	return d;
    }

    /**
     * @param d
     *            the d to set
     */
    public void setD(Double d) {
	this.d = d;
    }

    /**
     * @return the p
     */
    public Double getP() {
	return p;
    }

    /**
     * @param p
     *            the p to set
     */
    public void setP(Double p) {
	this.p = p;
    }

    /**
     * @return the tau
     */
    public Double getTau() {
	return tau;
    }

    /**
     * @param tau
     *            the tau to set
     */
    public void setTau(Double tau) {
	this.tau = tau;
    }

    /**
     * @return the s
     */
    public Integer getS() {
	return s;
    }

    /**
     * @param s
     *            the s to set
     */
    public void setS(Integer s) {
	this.s = s;
    }

    /**
     * @return the h
     */
    public Double getH() {
	return h;
    }

    /**
     * @param h
     *            the h to set
     */
    public void setH(Double h) {
	this.h = h;
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
