package de.oth.smplsp.formula;

import de.oth.smplsp.model.Product;
import de.oth.smplsp.util.Configuration;
import de.oth.smplsp.util.Decimals;

public class ProductFormula {

    // Formatter to disable the scientific notation
    private static Decimals decimals;

    private static String blue;
    private static String rubineRed;
    private static String oliveGreen;
    private static String plum;

    /**
     * initialize the classical product formula formula
     */
    public static void initialize() {
	int decimal = Configuration.getInstance().getDecimalPlaces();
	decimals = new Decimals(decimal);

	// Check weather the text should be with color, or just black and white
	if (Configuration.getInstance().getBlackAndWhiteMode()) {
	    initColorsBlackedOut();
	} else {
	    initColors();
	}
    }

    /**
     * initialize the used colors
     */
    private static void initColors() {
	blue = "Blue";
	rubineRed = "RubineRed";
	oliveGreen = "OliveGreen";
	plum = "Plum";
    }

    /**
     * initialize alle colors to black
     */
    private static void initColorsBlackedOut() {
	blue = "Black";
	rubineRed = "Black";
	oliveGreen = "Black";
	plum = "Black";
    }

    /**
     * Returns a String for the formula of the general utilized capacity
     * 
     * @return String for the formula of the general utilized capacity
     */
    public static String getGeneralUtilizedCapacityFormula() {
	initialize();
	String formel = "\\textcolor{" + rubineRed
		+ "}{\\rho} = \\frac{ \\textcolor{" + blue
		+ "} D}{ \\textcolor{" + plum + "}p}";
	return formel;
    }

    /**
     *
     * Returns a String for the formula of the general utilized capacity filled
     * with parameters
     * 
     * @param product
     * @return String for the formula of the general utilized capacity filled
     *         with parameters
     */
    public static String getUtilizedCapacityWithParametersFormula(
	    Product product) {
	initialize();
	String formel = "\\textcolor{" + rubineRed
		+ "}{\\rho} = \\frac{ \\textcolor{" + blue + "}{"
		+ decimals.getDecimalFormat().format(product.getD())
		+ "}}{ \\textcolor{" + plum + "}{"
		+ decimals.getDecimalFormat().format(product.getP()) + "}}";
	return formel;
    }

    /**
     * @return
     */
    public static String getGeneralProductionDurationFormula() {
	initialize();
	String formel = "t_p = \\frac{\\textcolor{" + oliveGreen
		+ "}q}{ \\textcolor{" + plum + "}p}";
	return formel;
    }

    public static String getGeneralProductionDurationWithParametersFormula(
	    Product product) {
	initialize();
	String formel = "t_p = \\frac{\\textcolor{" + oliveGreen + "}{"
		+ decimals.getDecimalFormat().format(product.getQ())
		+ "}}{ \\textcolor{" + plum + "}{"
		+ decimals.getDecimalFormat().format(product.getP()) + "}}";
	return formel;
    }

    public static String getNewLine() {
	return "\\\\";
    }

    public static String getUtilizedCapacityFormula(Product product) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von } \\rho :";
	formula += getNewLine();
	formula += getGeneralUtilizedCapacityFormula();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingesetzten Werten für Produkt }"
		+ product.getK() + ":";
	formula += getNewLine();
	formula += getUtilizedCapacityWithParametersFormula(product);
	formula += getNewLine();
	return formula;
    }

    public static String getProductionDurationFormula(Product product) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von } t_{p}:";
	formula += getNewLine();
	formula += getGeneralProductionDurationFormula();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingesetzten Werten für Produkt }"
		+ product.getK() + ":";
	formula += getNewLine();
	formula += getGeneralProductionDurationWithParametersFormula(product);
	formula += getNewLine();
	return formula;
    }

    public static String getReachFormula(Product product) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von } r_{p}:";
	formula += getNewLine();
	formula += getGeneralReachFormula();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingesetzten Werten für Produkt }"
		+ product.getK() + ":";
	formula += getNewLine();
	formula += getReachWithParametersFormula(product);
	formula += getNewLine();
	return formula;
    }

    public static String getGeneralReachFormula() {
	initialize();
	String formel = "r_p = \\frac{\\textcolor{" + oliveGreen
		+ "}{q_{k}}}{ \\textcolor{" + blue + "}{D_{k}}}";
	return formel;
    }

    public static String getReachWithParametersFormula(Product product) {
	initialize();
	String formel = "r_p = \\frac{\\textcolor{" + oliveGreen + "}{"
		+ decimals.getDecimalFormat().format(product.getQ())
		+ "}}{ \\textcolor{" + blue + "}{"
		+ decimals.getDecimalFormat().format(product.getD()) + "}}";
	return formel;
    }

}
