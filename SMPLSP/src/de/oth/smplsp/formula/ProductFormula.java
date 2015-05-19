package de.oth.smplsp.formula;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import de.oth.smplsp.model.Product;
import de.oth.smplsp.util.Configuration;

public class ProductFormula {

    // Formatter to disable the scientific notation
    private static NumberFormat formatter = DecimalFormat.getInstance();
    // TODO: lade fraction digits aus settings
    // default=8
    private static int fractionDigits = 8;

    private static String blue;
    private static String rubineRed;
    private static String oliveGreen;
    private static String plum;

    public static void initialize() {
	formatter.setMaximumFractionDigits(fractionDigits);

	// Check weather the text should be with color, or just black and white
	if (Configuration.getInstance().getBlackAndWhiteMode()) {
	    blue = "Black";
	    rubineRed = "Black";
	    oliveGreen = "Black";
	    plum = "Black";
	} else {
	    blue = "Blue";
	    rubineRed = "RubineRed";
	    oliveGreen = "OliveGreen";
	    plum = "Plum";
	}
    }

    public static String getAllgemeineAuslastungFormel() {
	initialize();
	String formel = "\\textcolor{" + rubineRed
		+ "}{\\rho} = \\frac{ \\textcolor{" + blue
		+ "} D}{ \\textcolor{" + plum + "}p}";
	return formel;
    }

    public static String getAuslastungMitParameterFormel(Product product) {
	initialize();
	String formel = "\\textcolor{" + rubineRed
		+ "}{\\rho} = \\frac{ \\textcolor{" + blue + "}{"
		+ formatter.format(product.getD()) + "}}{ \\textcolor{" + plum
		+ "}{" + formatter.format(product.getP()) + "}}";
	return formel;
    }

    public static String getAllgemeineProduktionsdauerFormel() {
	initialize();
	String formel = "t_p = \\frac{\\textcolor{" + oliveGreen
		+ "}q}{ \\textcolor{" + plum + "}p}";
	return formel;
    }

    public static String getProduktionsdauerMitParameterFormel(Product product) {
	initialize();
	String formel = "t_p = \\frac{\\textcolor{" + oliveGreen + "}{"
		+ formatter.format(product.getQ()) + "}}{ \\textcolor{" + plum
		+ "}{" + formatter.format(product.getP()) + "}}";
	return formel;
    }

    public static String getNewLine() {
	return "\\\\";
    }

    public static String getAuslastungFormel(Product product) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von} \\rho :";
	formula += getAllgemeineAuslastungFormel();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingestzten Werten für Produkt }"
		+ product.getK() + ":";
	formula += getAuslastungFormel(product);
	formula += getNewLine();
	return formula;
    }

    public static String getProduktionsdauerFormel(Product product) {
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von } t_{p}:";
	formula += getNewLine();
	formula += getAllgemeineProduktionsdauerFormel();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingestzten Werten für Produkt }"
		+ product.getK() + ":";
	formula += getNewLine();
	formula += getProduktionsdauerMitParameterFormel(product);
	formula += getNewLine();
	return formula;
    }
}
