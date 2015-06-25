package de.oth.smplsp.formula;

import de.oth.smplsp.model.Product;
import de.oth.smplsp.util.Configuration;
import de.oth.smplsp.util.Decimals;

public class ClassicLotSchedulingFormula {

    // Formatter to disable the scientific notation
    private static Decimals decimals;

    private static String red;
    private static String green;
    private static String blue;
    private static String rubineRed;
    private static String oliveGreen;
    private static String plum;

    /**
     * initialize the classical lot scheduling formula
     */
    private static void initialize() {
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
	green = "Green";
	red = "Red";
	blue = "Blue";
	rubineRed = "RubineRed";
	oliveGreen = "OliveGreen";
	plum = "Plum";
    }

    /**
     * initialize alle colors to black
     */
    private static void initColorsBlackedOut() {
	red = "Black";
	green = "Black";
	blue = "Black";
	rubineRed = "Black";
	oliveGreen = "Black";
	plum = "Black";
    }

    /**
     * Returns the the formula for the general product specific formula
     * 
     * @return String for the formula for the general product specific formula
     */
    public static String getGeneralProductSpecificProductionCycleFormula() {
	initialize();
	String formel = "t_{opt} = \\sqrt{\\frac{2 \\cdot \\textcolor{" + red
		+ "}s}{\\textcolor{" + green + "}h \\cdot \\textcolor{" + blue
		+ "} D \\cdot (1 - \\textcolor{" + rubineRed + "}{\\rho})}}";

	return formel;
    }

    /**
     * Returns the formula for the product specific production cycle with filled
     * parameters
     * 
     * @param product
     * @return String for the formula of the product specific production cycle
     *         with filled parameters
     */
    public static String getProductSpecificProductionCycleWithParametersFormula(
	    Product product) {
	initialize();
	String formel = "t_{opt} = \\sqrt{\\frac{2 \\cdot \\textcolor{" + red
		+ "}{" + decimals.getDecimalFormat().format(product.getS())
		+ "}}{\\textcolor{" + green + "}{"
		+ decimals.getDecimalFormat().format(product.getH())
		+ "} \\cdot \\textcolor{" + blue + "}{"
		+ decimals.getDecimalFormat().format(product.getD())
		+ "} \\cdot (1 - \\textcolor{" + rubineRed + "}{"
		+ decimals.getDecimalFormat().format(product.getRoh()) + "})}}";
	return formel;
    }

    /**
     * Returns a String with the newLine contend for the explanation component
     * 
     * @return String with newLine-Content
     */
    public static String getNewLine() {
	return "\\\\";
    }

    /**
     * Returns the formula for the product specific production cycle
     * 
     * @param product
     * @return String for the formula of the product specific production cycle
     */
    public static String getProductSpecificProductionCycleFormula(
	    Product product) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von} T_{opt}:";
	formula += getNewLine();
	formula += getGeneralProductSpecificProductionCycleFormula();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingesetzten Werten für Produkt }"
		+ product.getK() + ":";
	formula += getNewLine();
	formula += getProductSpecificProductionCycleWithParametersFormula(product);
	formula += getNewLine();
	return formula;
    }

    /**
     * Returns the formula for the lot scheduling
     * 
     * @return String for the general lot scheduling formula
     */
    public static String getGeneralLotSchedulingFormula() {
	initialize();
	String formel = "\\textcolor{" + oliveGreen
		+ "}{q_{k}^{opt}} = \\sqrt{\\frac{2 \\cdot \\textcolor{" + blue
		+ "} {D_k} \\cdot \\textcolor{" + red + "}{s_k}}{\\textcolor{"
		+ green + "}{h_k} \\cdot (1 - \\frac{\\textcolor{" + blue
		+ "}  {D_k}}{ \\textcolor{" + plum + "}{p_k}})}}";
	return formel;
    }

    /**
     * Returns the formula for the lot scheduling with filled parameters
     * 
     * @param product
     * @return String for the formula of the lot scheduling with filled
     *         parameters
     */
    public static String getLotSchedulingWithParametersFormula(Product product) {
	initialize();
	String formel = "\\textcolor{" + oliveGreen + "}{q_{" + product.getK()
		+ "}^{opt}} = \\sqrt{\\frac{2 \\cdot \\textcolor{" + blue
		+ "}{" + decimals.getDecimalFormat().format(product.getD())
		+ "}\\cdot \\textcolor{" + red + "}{"
		+ decimals.getDecimalFormat().format(product.getS())
		+ "}}{\\textcolor{" + green + "}{"
		+ decimals.getDecimalFormat().format(product.getH())
		+ "}\\cdot (1 - \\frac{ \\textcolor{" + blue + "}{"
		+ decimals.getDecimalFormat().format(product.getD())
		+ "}}{ \\textcolor{" + plum + "}{"
		+ decimals.getDecimalFormat().format(product.getP()) + "}})}}";
	return formel;
    }

    /**
     * Returns the formula for the lot scheduling
     * 
     * @param product
     * @return String for the formula of the lot scheduling
     */
    public static String getLotSchedulingFormula(Product product) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von } q_{opt}:";
	formula += getNewLine();
	formula += getGeneralLotSchedulingFormula();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingesetzten Werten für Produkt }"
		+ product.getK() + ":";
	formula += getNewLine();
	formula += getLotSchedulingWithParametersFormula(product);
	formula += getNewLine();
	return formula;
    }
}
