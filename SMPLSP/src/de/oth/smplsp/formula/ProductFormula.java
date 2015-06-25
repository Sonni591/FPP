package de.oth.smplsp.formula;

import de.oth.smplsp.model.Product;
import de.oth.smplsp.util.Configuration;
import de.oth.smplsp.util.Decimals;

/**
 * @author Christiane
 *
 */
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
	String formula = "\\textcolor{" + rubineRed
		+ "}{\\rho} = \\frac{ \\textcolor{" + blue
		+ "} D}{ \\textcolor{" + plum + "}p}";
	return formula;
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
	String formula = "\\textcolor{" + rubineRed
		+ "}{\\rho} = \\frac{ \\textcolor{" + blue + "}{"
		+ decimals.getDecimalFormat().format(product.getD())
		+ "}}{ \\textcolor{" + plum + "}{"
		+ decimals.getDecimalFormat().format(product.getP()) + "}}";
	return formula;
    }

    /**
     * @return the general formula for the production duration of a product
     */
    public static String getGeneralProductionDurationFormula() {
	initialize();
	String formula = "t_p = \\frac{\\textcolor{" + oliveGreen
		+ "}q}{ \\textcolor{" + plum + "}p}";
	return formula;
    }

    /**
     * @param product
     * @return the formula for the production duration of a product with
     *         parameters
     */
    public static String getProductionDurationWithParametersFormula(
	    Product product) {
	initialize();
	String formula = "t_p = \\frac{\\textcolor{" + oliveGreen + "}{"
		+ decimals.getDecimalFormat().format(product.getQ())
		+ "}}{ \\textcolor{" + plum + "}{"
		+ decimals.getDecimalFormat().format(product.getP()) + "}}";
	return formula;
    }

    /**
     * @return the sequence for a new line in latex
     */
    public static String getNewLine() {
	return "\\\\";
    }

    /**
     * @param product
     * @return the combined String of the general utilized capacity formula and
     *         the formula with the concrete values for a product
     */
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

    /**
     * @param product
     * @return the combined String of the general production duration formula
     *         and the formula with the concrete values for a product
     */
    public static String getProductionDurationFormula(Product product) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von } t_{p}:";
	formula += getNewLine();
	formula += getGeneralProductionDurationFormula();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingesetzten Werten für Produkt }"
		+ product.getK() + ":";
	formula += getNewLine();
	formula += getProductionDurationWithParametersFormula(product);
	formula += getNewLine();
	return formula;
    }

    /**
     * @param product
     * @return the combined String of the general reach formula and the formula
     *         with the concrete values for a product
     */
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

    /**
     * @return the general formula for the reach of a product
     */
    public static String getGeneralReachFormula() {
	initialize();
	String formula = "r_p = \\frac{\\textcolor{" + oliveGreen
		+ "}{q_{k}}}{ \\textcolor{" + blue + "}{D_{k}}}";
	return formula;
    }

    /**
     * @param product
     * @return the formula for the reach of a product with concrete values for
     *         the parameters
     */
    public static String getReachWithParametersFormula(Product product) {
	initialize();
	String formula = "r_p = \\frac{\\textcolor{" + oliveGreen + "}{"
		+ decimals.getDecimalFormat().format(product.getQ())
		+ "}}{ \\textcolor{" + blue + "}{"
		+ decimals.getDecimalFormat().format(product.getD()) + "}}";
	return formula;
    }

}
