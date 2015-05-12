package de.oth.smplsp.formula;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import de.oth.smplsp.model.Product;

public class ProductFormula {

    // Formatter to disable the scientific notation
    private static NumberFormat formatter = DecimalFormat.getInstance();
    // TODO: lade fraction digits aus settings
    // default=8
    private static int fractionDigits = 8;

    public static void initialize() {
	formatter.setMaximumFractionDigits(fractionDigits);
    }

    public static String getAllgemeineAuslastungFormel() {
	initialize();
	String formel = "\\textcolor{RubineRed}{\\rho} = \\frac{ \\textcolor{Blue} D}{ \\textcolor{Plum}p}";
	return formel;
    }

    public static String getAuslastungMitParameterFormel(Product product) {
	initialize();
	String formel = "\\textcolor{RubineRed}{\\rho} = \\frac{ \\textcolor{Blue}{"
		+ formatter.format(product.getD())
		+ "}}{ \\textcolor{Plum}{"
		+ formatter.format(product.getP()) + "}}";
	return formel;
    }

    public static String getAllgemeineProduktionsdauerFormel() {
	initialize();
	String formel = "t_p = \\frac{\\textcolor{OliveGreen}q}{ \\textcolor{Plum}p}";
	return formel;
    }

    public static String getProduktionsdauerMitParameterFormel(Product product) {
	initialize();
	String formel = "t_p = \\frac{\\textcolor{OliveGreen}{"
		+ formatter.format(product.getQ()) + "}}{ \\textcolor{Plum}{"
		+ formatter.format(product.getP()) + "}}";
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
	String formula = "\\textrm{Allgemeine Formel zur Berechnung von} t_{opt}:";
	formula += getAllgemeineProduktionsdauerFormel();
	formula += getNewLine();
	formula += getNewLine();
	formula += "\\textrm{Formel mit eingestzten Werten für Produkt }"
		+ product.getK() + ":";
	formula += getProduktionsdauerMitParameterFormel(product);
	formula += getNewLine();
	return formula;
    }

}
