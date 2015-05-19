package de.oth.smplsp.util;

import java.text.DecimalFormat;

/**
 * To define the decimal places in the table views
 *
 */
public class Decimals {

    private int decimals = 0;
    private DecimalFormat decimalFormat = null;

    /**
     * @param decimals
     */
    public Decimals(int decimals) {
	super();
	this.decimals = decimals;
	// create the decimal format
	decimalFormat = dezimalFormater();
    }

    /**
     * @return the decimals
     */
    public int getDecimals() {
	return decimals;
    }

    /**
     * set the decimals
     * 
     * @param decimals
     */
    public void setDecimals(int decimals) {
	this.decimals = decimals;
	decimalFormat = dezimalFormater();
    }

    /**
     * @return the decimalFormat
     */
    public DecimalFormat getDecimalFormat() {
	return decimalFormat;
    }

    /**
     * define the pattern that in handles the decimal patterns
     * 
     * @return
     */
    public DecimalFormat dezimalFormater() {
	String pattern = "###,###.";
	for (int i = 0; i < decimals; i++) {
	    pattern += "#";
	}
	return new DecimalFormat(pattern);
    }

}
