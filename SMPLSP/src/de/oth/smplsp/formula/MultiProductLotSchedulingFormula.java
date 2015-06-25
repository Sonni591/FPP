package de.oth.smplsp.formula;

import de.oth.smplsp.model.LotSchedulingResult;
import de.oth.smplsp.model.Product;
import de.oth.smplsp.util.Configuration;
import de.oth.smplsp.util.Decimals;

public class MultiProductLotSchedulingFormula {

    // Formatter to disable the scientific notation
    private static Decimals decimals;

    private static String red;
    private static String green;
    private static String blue;
    private static String rubineRed;
    private static String oliveGreen;
    private static String dandelion;

    /**
     * initialize the multi-product lot scheduling formula
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
	green = "Green";
	red = "Red";
	blue = "Blue";
	rubineRed = "RubineRed";
	oliveGreen = "OliveGreen";
	dandelion = "Dandelion";
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
	dandelion = "Black";
    }

    /**
     * @return the general formula for the common production cycle of all
     *         products
     */
    public static String getGeneralCommonProductionCycleFormula() {
	initialize();
	String formula = "T_{opt} = \\sqrt{\\frac{2 \\cdot \\sum_{k=1}^{K} \\textcolor{"
		+ red
		+ "}{s_k}}{\\sum_{k=1}^{K}{\\textcolor{"
		+ green
		+ "}{h_k} \\cdot \\textcolor{"
		+ blue
		+ "}{D_k} \\cdot (1- \\textcolor{"
		+ rubineRed
		+ "}{{\\rho}_k})}}}";
	return formula;
    }

    /**
     * @param result
     * @return the common production cycle formula with values for the result of
     *         the algorithm
     */
    public static String getCommonProductionCycleWithParameterFormula(
	    LotSchedulingResult result) {
	initialize();
	String formula = "\\textrm{Eingesetzte Formel für den optimalen gemeinsamen Produktionszyklus:";
	formula += "\\\\";
	formula = getNumeratorForCommonProductionCycleWithParameter(formula,
		result);
	formula = concatenateDenominatorForCommonProductionCycleWithParameter(
		formula, result);
	formula = concatenateResultForCommonProductionCycleWithParameter(
		formula, result);
	return formula;
    }

    /**
     * concatenates the numerator for the common production cycle formula with
     * concrete values for the parameters with a given String
     * 
     * @param formula
     * @param result
     * @return the concatenated formula
     */
    public static String getNumeratorForCommonProductionCycleWithParameter(
	    String formula, LotSchedulingResult result) {

	formula += "\\mathrm{{T_{opt}} = \\sqrt{\\frac{2 \\cdot ( ";
	for (int i = 0; i < result.getProducts().size(); i++) {
	    Product product = result.getProducts().get(i);
	    if (i == result.getProducts().size() - 1) {
		formula += " \\textcolor{" + red + "}{"
			+ decimals.getDecimalFormat().format(product.getS())
			+ "})}";
	    } else {
		formula += " \\textcolor{" + red + "}{"
			+ decimals.getDecimalFormat().format(product.getS())
			+ "} + ";
	    }
	}
	return formula;
    }

    /**
     * concatenates the denominator for the common production cycle formula with
     * concrete values for the parameters with a given String
     * 
     * @param formula
     * @param result
     * @return the concatenated formula
     */
    public static String concatenateDenominatorForCommonProductionCycleWithParameter(
	    String formula, LotSchedulingResult result) {
	formula += "{";
	for (int i = 0; i < result.getProducts().size(); i++) {
	    Product product = result.getProducts().get(i);
	    if (i == result.getProducts().size() - 1) {
		formula += "\\textcolor{" + green + "}{"
			+ decimals.getDecimalFormat().format(product.getH())
			+ "} \\cdot \\textcolor{" + blue + "}{"
			+ decimals.getDecimalFormat().format(product.getD())
			+ "} \\cdot (1 - \\textcolor{" + rubineRed + "}{"
			+ decimals.getDecimalFormat().format(product.getRoh())
			+ "})";
	    } else {
		formula += "\\textcolor{" + green + "}{"
			+ decimals.getDecimalFormat().format(product.getH())
			+ "} \\cdot \\textcolor{" + blue + "}{"
			+ decimals.getDecimalFormat().format(product.getD())
			+ "} \\cdot (1 - \\textcolor{" + rubineRed + "}{"
			+ decimals.getDecimalFormat().format(product.getRoh())
			+ "}) + ";
	    }
	}

	formula += "}}";
	return formula;
    }

    /**
     * concatenates the result of the common production cycle formula with a
     * given String
     * 
     * @param formula
     * @param result
     * @return the concatenated formula
     */
    public static String concatenateResultForCommonProductionCycleWithParameter(
	    String formula, LotSchedulingResult result) {
	formula += " = " + decimals.getDecimalFormat().format(result.gettOpt());
	formula += "}"; // close mathrm
	return formula;
    }

    /**
     * @return the general formula for the minimal production cycle of all
     *         products
     */
    public static String getGeneralMinimalProductionCycleFormula() {
	initialize();
	String formula = "T_{min} \\geq\\frac{\\sum_{k=1}^{K}T_k}{1-\\sum_{k=1}^{K}\\textcolor{"
		+ rubineRed + "}{\\rho_k}}";
	return formula;
    }

    /**
     * @param result
     * @return the minimal production cycle formula with values for the result
     *         of the algorithm
     */
    public static String getMinimalProductionCycleWithParameterFormula(
	    LotSchedulingResult result) {
	initialize();
	String formula = "\\textrm{Eingesetzte Formel für den minimalen gemeinsamen Produktionszyklus:";
	formula += "\\\\";
	formula = concatenateNumeratorForMinimalProductionCycleWithParameter(
		formula, result);
	formula = concatenateDenominatorForMinimalProductionCycleWithParameter(
		formula, result);
	formula = concatenateResultForMinimalProductionCycleWithParameter(
		formula, result);

	return formula;
    }

    /**
     * concatenates the numerator for the minimal production cycle formula with
     * concrete values for the parameters with a given String
     * 
     * @param formula
     * @param result
     * @return the concatenated formula
     */
    public static String concatenateNumeratorForMinimalProductionCycleWithParameter(
	    String formula, LotSchedulingResult result) {
	formula += "\\mathrm{T_{min} = \\frac{";
	for (int i = 0; i < result.getProducts().size(); i++) {
	    Product product = result.getProducts().get(i);
	    if (i == result.getProducts().size() - 1) {
		formula += decimals.getDecimalFormat().format(product.getTau())
			+ "}";
	    } else {
		formula += decimals.getDecimalFormat().format(product.getTau())
			+ " + ";
	    }
	}
	return formula;
    }

    /**
     * concatenates the denominator for the minimal production cycle formula
     * with concrete values for the parameters with a given String
     * 
     * @param formula
     * @param result
     * @return the concatenated formula
     */
    public static String concatenateDenominatorForMinimalProductionCycleWithParameter(
	    String formula, LotSchedulingResult result) {
	formula += "{1 - (";
	for (int i = 0; i < result.getProducts().size(); i++) {
	    Product product = result.getProducts().get(i);
	    if (i == result.getProducts().size() - 1) {
		formula += "\\textcolor{" + rubineRed + "}{"
			+ decimals.getDecimalFormat().format(product.getRoh())
			+ "})}";
	    } else {
		formula += " \\textcolor{" + rubineRed + "}{"
			+ decimals.getDecimalFormat().format(product.getRoh())
			+ "} + ";
	    }
	}
	return formula;
    }

    /**
     * concatenates the result of the minimal production cycle formula with a
     * given String
     * 
     * @param formula
     * @param result
     * @return the concatenated formula
     */
    public static String concatenateResultForMinimalProductionCycleWithParameter(
	    String formula, LotSchedulingResult result) {
	formula += " = " + decimals.getDecimalFormat().format(result.gettMin());
	formula += "}"; // close mathrm
	return formula;
    }

    /**
     * @return the general formula for the batch size of a product
     */
    public static String getGeneralBatchSizeFormula() {
	initialize();
	String formula = "\\textcolor{" + oliveGreen + "}{q_k} = \\textcolor{"
		+ blue + "}{D_k} \\cdot \\textcolor{" + dandelion
		+ "}{T_{opt}}";
	return formula;
    }

    /**
     * @param product
     * @param result
     * @return the formula for the batch size of a product with concrete values
     *         for the parameters
     */
    public static String getBatchSizeWithParameterFormula(Product product,
	    LotSchedulingResult result) {
	String formula = "\\textcolor{" + oliveGreen + "}{q_k} = \\textcolor{"
		+ blue + "}{"
		+ decimals.getDecimalFormat().format(product.getD())
		+ "} \\cdot \\textcolor{" + dandelion + "}{"
		+ decimals.getDecimalFormat().format(result.gettOpt()) + "}";
	return formula;
    }

    /**
     * @return the sequence for a new line in latex
     */
    public static String getNewLine() {
	return "\\\\";
    }

    /**
     * @param result
     * @return the combined String of the general common production cycle
     *         formula and the formula with the concrete values
     */
    public static String getCommonProductionCycle(LotSchedulingResult result) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von} T_{opt}:";
	formula += getGeneralCommonProductionCycleFormula();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingesetzten Werten:}";
	formula += getCommonProductionCycleWithParameterFormula(result);
	formula += getNewLine();
	return formula;
    }

    /**
     * @param result
     * @return the combined String of the general minimal production cycle
     *         formula and the formula with the concrete values
     */
    public static String getMinimalProductionCycleFormula(
	    LotSchedulingResult result) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von} \\mathrm{T_{min}}:";
	formula += getGeneralMinimalProductionCycleFormula();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingesetzten Werten:}";
	formula += getMinimalProductionCycleWithParameterFormula(result);
	formula += getNewLine();
	return formula;
    }

    /**
     * @param product
     * @param result
     * @return the combined String of the batch size formula and the formula
     *         with the concrete values for a product
     */
    public static String getBatchSizeFormula(Product product,
	    LotSchedulingResult result) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von } \\mathrm{q_{opt}}:";
	formula += getNewLine();
	formula += getGeneralBatchSizeFormula();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingesetzten Werten für Produkt }"
		+ product.getK() + ":";
	formula += getNewLine();
	formula += getBatchSizeWithParameterFormula(product, result);
	formula += getNewLine();
	return formula;
    }

}
