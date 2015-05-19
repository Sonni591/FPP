package de.oth.smplsp.formula;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import de.oth.smplsp.model.Product;
import de.oth.smplsp.util.Configuration;

public class ClassicLotSchedulingFormula {

    // Formatter to disable the scientific notation
    private static NumberFormat formatter = DecimalFormat.getInstance();
    // TODO: lade fraction digits aus settings
    // default=8
    private static int fractionDigits = 8;

    private static String red;
    private static String green;
    private static String blue;
    private static String rubineRed;
    private static String oliveGreen;
    private static String plum;

    private static void initialize() {
	formatter.setMaximumFractionDigits(fractionDigits);

	// Check weather the text should be with color, or just black and white
	if (Configuration.getInstance().getBlackAndWhiteMode()) {
	    red = "Black";
	    green = "Black";
	    blue = "Black";
	    rubineRed = "Black";
	    oliveGreen = "Black";
	    plum = "Black";
	} else {
	    green = "Green";
	    red = "Red";
	    blue = "Blue";
	    rubineRed = "RubineRed";
	    oliveGreen = "OliveGreen";
	    plum = "Plum";
	}
    }

    public static String getAllgemeineProduktspezifischeProdukzionszyklusFormel() {
	initialize();
	String formel = "t_{opt} = \\sqrt{\\frac{2 \\cdot \\textcolor{" + red
		+ "}s}{\\textcolor{" + green + "}h \\cdot \\textcolor{" + blue
		+ "} D \\cdot (1 - \\textcolor{" + rubineRed + "}{\\rho})}}";

	return formel;
    }

    public static String getProduktspezifischeProdukzionszyklusMitParameterFormel(
	    Product product) {
	initialize();
	String formel = "t_{opt} = \\sqrt{\\frac{2 \\cdot \\textcolor{" + red
		+ "}{" + formatter.format(product.getS()) + "}}{\\textcolor{"
		+ green + "}{" + formatter.format(product.getH())
		+ "} \\cdot \\textcolor{" + blue + "}{"
		+ formatter.format(product.getD())
		+ "} \\cdot (1 - \\textcolor{" + rubineRed + "}{"
		+ formatter.format(product.getRoh()) + "})}}";
	return formel;
    }

    public static String getNewLine() {
	return "\\\\";
    }

    public static String getProduktspezifischeProdukzionszyklusFormel(
	    Product product) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von} T_{opt}:";
	formula += getNewLine();
	formula += getAllgemeineProduktspezifischeProdukzionszyklusFormel();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingestzten Werten für Produkt }"
		+ product.getK() + ":";
	formula += getNewLine();
	formula += getProduktspezifischeProdukzionszyklusMitParameterFormel(product);
	formula += getNewLine();
	return formula;
    }

    public static String getAllgemeineLosgroessenFormel() {
	initialize();
	String formel = "\\textcolor{" + oliveGreen
		+ "}{q_{k}^{opt}} = \\sqrt{\\frac{2 \\cdot \\textcolor{" + blue
		+ "} {D_k} \\cdot \\textcolor{" + red + "}{s_k}}{\\textcolor{"
		+ green + "}{h_k} \\cdot (1 - \\frac{\\textcolor{" + blue
		+ "}  {D_k}}{ \\textcolor{" + plum + "}{p_k}})}}";
	return formel;
    }

    public static String getLosgroessenmitParameternFormel(Product product) {
	initialize();
	String formel = "\\textcolor{" + oliveGreen + "}{q_{" + product.getK()
		+ "}^{opt}} = \\sqrt{\\frac{2 \\cdot \\textcolor{" + blue
		+ "}{" + formatter.format(product.getD())
		+ "}\\cdot \\textcolor{" + red + "}{"
		+ formatter.format(product.getS()) + "}}{\\textcolor{" + green
		+ "}{" + formatter.format(product.getH())
		+ "}\\cdot (1 - \\frac{ \\textcolor{" + blue + "}{"
		+ formatter.format(product.getD()) + "}}{ \\textcolor{" + plum
		+ "}{" + formatter.format(product.getP()) + "}})}}";
	return formel;
    }

    public static String getLosgroessenFormel(Product product) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von } q_{opt}:";
	formula += getNewLine();
	formula += getAllgemeineLosgroessenFormel();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingestzten Werten für Produkt }"
		+ product.getK() + ":";
	formula += getNewLine();
	formula += getLosgroessenmitParameternFormel(product);
	formula += getNewLine();
	return formula;
    }
}
