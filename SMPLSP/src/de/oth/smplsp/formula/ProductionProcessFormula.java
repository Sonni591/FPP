package de.oth.smplsp.formula;

import de.oth.smplsp.model.Product;
import de.oth.smplsp.model.ProductionProcess;
import de.oth.smplsp.util.Configuration;
import de.oth.smplsp.util.Decimals;

public class ProductionProcessFormula {

    // Formatter to disable the scientific notation
    private static Decimals decimals;

    private static void initialize() {
	int decimal = Configuration.getInstance().getDecimalPlaces();
	decimals = new Decimals(decimal);
    }

    public static String getProductionProcessFormel(ProductionProcess process,
	    Product product) {
	initialize();
	String formula = "";
	if (process.getVorgang().get().equals("Rüsten")) {
	    formula += getAllgemeineProductionProcessFormelRuesten();
	    formula += getNewLine();
	    formula += getProductionProcessFormelRuesten(product, process
		    .getStart().doubleValue());
	} else {
	    formula += getAllgemeineProductionProcessFormelProduction();
	    formula += getNewLine();
	    formula += getProductionProcessFormelProduction(product, process
		    .getStart().doubleValue());
	}
	formula += getNewLine();
	formula += getGesamtdauer(product);

	return formula;
    }

    public static String getProductionProcessFormelRuesten(Product product,
	    double endeProduktionszeitVorgaenger) {
	String formula = "\\textrm{Berechnung des Rüstvorgangs für Produkt "
		+ product.getK() + " berechnet sich wie folgt:}";
	formula += getNewLine();
	formula += decimals.getDecimalFormat().format(
		endeProduktionszeitVorgaenger)
		+ " + "
		+ decimals.getDecimalFormat().format(product.getTau())
		+ " = "
		+ decimals.getDecimalFormat().format(
			endeProduktionszeitVorgaenger + product.getTau());
	return formula;
    }

    public static String getProductionProcessFormelProduction(Product product,
	    double endeRuestzeit) {
	String formula = "\\textrm{Berechnung des Produktionsvorgangs für Produkt "
		+ product.getK() + " berechnet sich wie folgt:}";
	formula += getNewLine();
	formula +=
	// formatter.format(
	decimals.getDecimalFormat().format(endeRuestzeit)
		+ " + "
		+
		// formatter.format(
		decimals.getDecimalFormat().format(product.getT())
		+ " = "
		+ decimals.getDecimalFormat().format(
			endeRuestzeit + product.getT());
	return formula;
    }

    public static String getAllgemeineProductionProcessFormelRuesten() {
	return "\\textrm{Allgemeine Berechnungsmöglichkeit des Rüstvorgangs für ein Produkt: }"
		+ getNewLine()
		+ "\\textrm{Ende Produktionszeit Vorgängerprodukt}"
		+ " +  {\\tau}_k";
    }

    public static String getGesamtdauer(Product product) {
	return "\\textrm{Die Gesamtdauer für das Produkt "
		+ product.getK()
		+ " beträgt:}"
		+ getNewLine()
		+ "{\\tau}_k + t_p = "
		+ decimals.getDecimalFormat().format(product.getTau())
		+ " + "
		+ decimals.getDecimalFormat().format(product.getT())
		+ " = "
		+ decimals.getDecimalFormat().format(
			product.getTau() + product.getT());
    }

    public static String getAllgemeineProductionProcessFormelProduction() {
	return "\\textrm{Allgemeine Berechnungsmöglichkeit des Produktionsvorgangs für ein Produkt: }"
		+ getNewLine()
		+ "\\textrm{Ende Rüstzeit des Produkts}"
		+ " +  t_p";
    }

    public static String getNewLine() {
	return "\\\\";
    }
}
