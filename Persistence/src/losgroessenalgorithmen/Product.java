package losgroessenalgorithmen;

public class Product {

	// ToDo: translate getters

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
	// Produktionsdauer
	private double t;
	// optimaler Produktionszyklus
	private double topt;
	// Auslastung der Anlage
	private double roh;

	public Product(float d, float p, float t, float s, float h) {
		this.D = d;
		this.p = p;
		this.tau = t;
		this.s = s;
		this.h = h;
	}

	/**
	 * @return the q
	 */
	public double getQ() {
		return q;
	}

	/**
	 * @param q
	 *            the q to set
	 */
	public void setQ(double q) {
		this.q = q;
	}

	/**
	 * @return the d
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

	public double getTopt() {
		return topt;
	}

	public void setTopt(double topt) {
		this.topt = topt;
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
	 * @param k the k to set
	 */
	public void setK(int k) {
		this.k = k;
	}

}
