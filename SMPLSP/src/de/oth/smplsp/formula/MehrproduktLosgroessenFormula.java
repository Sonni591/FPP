package de.oth.smplsp.formula;

import de.oth.smplsp.model.LotSchedulingResult;
import de.oth.smplsp.model.Product;
import de.oth.smplsp.util.Configuration;
import de.oth.smplsp.util.Decimals;

public class MehrproduktLosgroessenFormula {

    // Formatter to disable the scientific notation
    private static Decimals decimals;

    private static String red;
    private static String green;
    private static String blue;
    private static String rubineRed;
    private static String oliveGreen;
    private static String dandelion;

    public static void initialize() {
	int decimal = Configuration.getInstance().getDecimalPlaces();
	decimals = new Decimals(decimal);

	// Check weather the text should be with color, or just black and white
	if (Configuration.getInstance().getBlackAndWhiteMode()) {
	    red = "Black";
	    green = "Black";
	    blue = "Black";
	    rubineRed = "Black";
	    oliveGreen = "Black";
	    dandelion = "Black";
	} else {
	    green = "Green";
	    red = "Red";
	    blue = "Blue";
	    rubineRed = "RubineRed";
	    oliveGreen = "OliveGreen";
	    dandelion = "Dandelion";
	}
    }

    public static String getAllgemeineGemeinsameProduktionszyklusFormel() {
	initialize();
	String formel = "\\textcolor{"
		+ dandelion
		+ "}{T_{opt}} = \\sqrt{\\frac{2 \\cdot \\sum_{k=1}^{K} \\textcolor{"
		+ red + "}{s_k}}{\\sum_{k=1}^{K}{\\textcolor{" + green
		+ "}{h_k} \\cdot \\textcolor{" + blue
		+ "}{D_k} \\cdot (1- \\textcolor{" + rubineRed
		+ "}{{\\rho}_k})}}}";
	return formel;
    }

    public static String getGemeinsameProduktionszyklusMitParameternFormel(
	    LotSchedulingResult result) {
	initialize();
	String formel = "\\textcolor{" + dandelion
		+ "}{T_{opt}} = \\sqrt{\\frac{2 \\cdot ( ";
	for (int i = 0; i < result.getProducts().size(); i++) {
	    Product product = result.getProducts().get(i);
	    if (i == result.getProducts().size() - 1) {
		formel += " \\textcolor{" + red + "}{"
			+ decimals.getDecimalFormat().format(product.getS())
			+ "})}";
	    } else {
		formel += " \\textcolor{" + red + "}{"
			+ decimals.getDecimalFormat().format(product.getS())
			+ "} + ";
	    }

	}
	formel += "{";

	for (int i = 0; i < result.getProducts().size(); i++) {
	    Product product = result.getProducts().get(i);
	    if (i == result.getProducts().size() - 1) {
		formel += "\\textcolor{" + green + "}{"
			+ decimals.getDecimalFormat().format(product.getH())
			+ "} \\cdot \\textcolor{" + blue + "}{"
			+ decimals.getDecimalFormat().format(product.getD())
			+ "} \\cdot (1 - \\textcolor{" + rubineRed + "}{"
			+ decimals.getDecimalFormat().format(product.getRoh())
			+ "})";
	    } else {
		formel += "\\textcolor{" + green + "}{"
			+ decimals.getDecimalFormat().format(product.getH())
			+ "} \\cdot \\textcolor{" + blue + "}{"
			+ decimals.getDecimalFormat().format(product.getD())
			+ "} \\cdot (1 - \\textcolor{" + rubineRed + "}{"
			+ decimals.getDecimalFormat().format(product.getRoh())
			+ "}) + ";
	    }
	}

	formel += "}}";
	return formel;
    }

    public static String getAllgemeineMinimalenProduktionszyklusFormel() {
	initialize();

	// String formel =
	// "\\frac{\\sum_{k=1}^{K} {\\tau}_k}{1 - \\sum_{k=1}^{K} {\\textcolor{"
	// + rubineRed + "}{{\\rho}_k}}} \\leq T";
	String formel = "T_{min} \\geq\\frac{\\sum_{k=1}^{K}T_k}{1-\\sum_{k=1}^{K}\\textcolor{"
		+ rubineRed + "}{\\rho}_k}";
	return formel;
    }

    public static String getMinimalenProduktionszyklusMitParameternFormel(
	    LotSchedulingResult result) {
	initialize();
	String formel = "T_{min} = \\frac{";
	for (int i = 0; i < result.getProducts().size(); i++) {
	    Product product = result.getProducts().get(i);
	    if (i == result.getProducts().size() - 1) {
		formel += decimals.getDecimalFormat().format(product.getTau())
			+ "}";
	    } else {
		formel += decimals.getDecimalFormat().format(product.getTau())
			+ " + ";
	    }
	}
	formel += "{1 - (";
	for (int i = 0; i < result.getProducts().size(); i++) {
	    Product product = result.getProducts().get(i);
	    if (i == result.getProducts().size() - 1) {
		formel += "\\textcolor{" + rubineRed + "}{"
			+ decimals.getDecimalFormat().format(product.getRoh())
			+ "})}";
	    } else {
		formel += " \\textcolor{" + rubineRed + "}{"
			+ decimals.getDecimalFormat().format(product.getRoh())
			+ "} + ";
	    }
	}
	return formel;
    }

    public static String getAllgemeineLosgroessenFormel() {
	initialize();
	String formel = "\\textcolor{" + oliveGreen + "}{q_k} = \\textcolor{"
		+ blue + "}{D_k} \\cdot \\textcolor{" + dandelion
		+ "}{T_{opt}}";
	return formel;
    }

    public static String getLosgroessenMitParameterFormel(Product product,
	    LotSchedulingResult result) {
	String formel = "\\textcolor{" + oliveGreen + "}{q_k} = \\textcolor{"
		+ blue + "}{"
		+ decimals.getDecimalFormat().format(product.getD())
		+ "} \\cdot \\textcolor{" + dandelion + "}{"
		+ decimals.getDecimalFormat().format(result.gettOpt()) + "}";
	return formel;
    }

    public static String getNewLine() {
	return "\\\\";
    }

    public static String getGemeinsameProduktionszyklus(
	    LotSchedulingResult result) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von} T_{opt}:";
	formula += getAllgemeineGemeinsameProduktionszyklusFormel();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingestzten Werten:}";
	formula += getGemeinsameProduktionszyklusMitParameternFormel(result);
	formula += getNewLine();
	return formula;
    }

    public static String getMinimalenProduktionszyklusFormel(
	    LotSchedulingResult result) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von} T_{min}:";
	formula += getAllgemeineMinimalenProduktionszyklusFormel();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingestzten Werten:}";
	formula += getMinimalenProduktionszyklusMitParameternFormel(result);
	formula += getNewLine();
	return formula;
    }

    public static String getLosgroessenFormel(Product product,
	    LotSchedulingResult result) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von } q_{opt}:";
	formula += getNewLine();
	formula += getAllgemeineLosgroessenFormel();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingestzten Werten für Produkt }"
		+ product.getK() + ":";
	formula += getNewLine();
	formula += getLosgroessenMitParameterFormel(product, result);
	formula += getNewLine();
	return formula;
    }

}
