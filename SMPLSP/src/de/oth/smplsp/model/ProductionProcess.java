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
    private DoubleProperty start_zyklus1;
    // Ende
    private DoubleProperty ende_zyklus1;

    private DoubleProperty start_zyklus2;

    private DoubleProperty ende_zyklus2;

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
	return start_zyklus1;
    }

    /**
     * @param start
     *            the start to set
     */
    public void setStart(DoubleProperty start) {
	this.start_zyklus1 = start;
    }

    /**
     * @return the ende
     */
    public DoubleProperty getEnde() {
	return ende_zyklus1;
    }

    /**
     * @param ende
     *            the ende to set
     */
    public void setEnde(DoubleProperty ende) {
	this.ende_zyklus1 = ende;
    }

    public DoubleProperty getStart_zyklus2() {
	return start_zyklus2;
    }

    public void setStart_zyklus2(DoubleProperty start_zyklus2) {
	this.start_zyklus2 = start_zyklus2;
    }

    public DoubleProperty getEnde_zyklus2() {
	return ende_zyklus2;
    }

    public void setEnde_zyklus2(DoubleProperty ende_zyklus2) {
	this.ende_zyklus2 = ende_zyklus2;
    }

}
