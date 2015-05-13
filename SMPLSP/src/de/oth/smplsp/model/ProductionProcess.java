package de.oth.smplsp.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class ProductionProcess {

    // Produktnummer
    private IntegerProperty k;
    // Vorgang
    private StringProperty vorgang;
    // Start
    private DoubleProperty start;
    // Ende
    private DoubleProperty ende;

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
     * @return the vorgang
     */
    public StringProperty getVorgang() {
	return vorgang;
    }

    /**
     * @param vorgang
     *            the vorgang to set
     */
    public void setVorgang(StringProperty vorgang) {
	this.vorgang = vorgang;
    }

    /**
     * @return the start
     */
    public DoubleProperty getStart() {
	return start;
    }

    /**
     * @param start
     *            the start to set
     */
    public void setStart(DoubleProperty start) {
	this.start = start;
    }

    /**
     * @return the ende
     */
    public DoubleProperty getEnde() {
	return ende;
    }

    /**
     * @param ende
     *            the ende to set
     */
    public void setEnde(DoubleProperty ende) {
	this.ende = ende;
    }

}
