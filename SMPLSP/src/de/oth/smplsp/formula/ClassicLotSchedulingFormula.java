package de.oth.smplsp.formula;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import de.oth.smplsp.model.Product;

public class ClassicLotSchedulingFormula {

    // Formatter to disable the scientific notation
    private static NumberFormat formatter = DecimalFormat.getInstance();
    // TODO: lade fraction digits aus settings
    // default=8
    private static int fractionDigits = 8;

    private static void initialize() {
	formatter.setMaximumFractionDigits(fractionDigits);
    }

    public static String getAllgemeineProduktspezifischeProdukzionszyklusFormel() {
	initialize();
	String formel = "t_{opt} = \\sqrt{\\frac{2 \\cdot \\textcolor{Red}s}{\\textcolor{Green}h \\cdot \\textcolor{Blue} D \\cdot (1 - \\textcolor{RubineRed}{\\rho})}}";
	return formel;
    }

    public static String getProduktspezifischeProdukzionszyklusMitParameterFormel(
	    Product product) {
	initialize();
	String formel = "t_{opt} = \\sqrt{\\frac{2 \\cdot \\textcolor{Red}{"
		+ formatter.format(product.getS()) + "}}{\\textcolor{Green}{"
		+ formatter.format(product.getH())
		+ "} \\cdot \\textcolor{Blue}{"
		+ formatter.format(product.getD())
		+ "} \\cdot (1 - \\textcolor{RubineRed}{"
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
	String formel = "\\textcolor{OliveGreen}{q_{k}^{opt}} = \\sqrt{\\frac{2 \\cdot \\textcolor{Blue} {D_k} \\cdot \\textcolor{Red}{s_k}}{\\textcolor{Green}{h_k} \\cdot (1 - \\frac{\\textcolor{Blue}  {D_k}}{ \\textcolor{Plum}{p_k}})}}";
	return formel;
    }

    public static String getLosgroessenmitParameternFormel(Product product) {
	initialize();
	String formel = "\\textcolor{OliveGreen}{q_{" + product.getK()
		+ "}^{opt}} = \\sqrt{\\frac{2 \\cdot \\textcolor{Blue}{"
		+ formatter.format(product.getD())
		+ "}\\cdot \\textcolor{Red}{"
		+ formatter.format(product.getS()) + "}}{\\textcolor{Green}{"
		+ formatter.format(product.getH())
		+ "}\\cdot (1 - \\frac{ \\textcolor{Blue}{"
		+ formatter.format(product.getD()) + "}}{ \\textcolor{Plum}{"
		+ formatter.format(product.getP()) + "}})}}";
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
