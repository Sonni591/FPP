package de.oth.smplsp.formula;

import de.oth.smplsp.model.Product;
import de.oth.smplsp.model.ProductionProcess;
import de.oth.smplsp.util.Configuration;
import de.oth.smplsp.util.Decimals;

public class ProductionProcessFormula {

    // Formatter to disable the scientific notation
    private static Decimals decimals;

    /**
     * initialize the production process formula
     */
    private static void initialize() {
	int decimal = Configuration.getInstance().getDecimalPlaces();
	decimals = new Decimals(decimal);
    }

    /**
     * Returns a String for the formula of the production process
     * 
     * @param process
     * @param product
     * @return String for the formula of the production process
     */
    public static String getProductionProcessFormula(ProductionProcess process,
	    Product product) {
	initialize();
	String formula = "";
	if (process.getProcess().get().equals("Rüsten")) {
	    formula += getAllgemeineProductionProcessFormelRuesten();
	    formula += getNewLine();
	    formula += getProductionProcessSetUpFormula(product, process
		    .getStartCycle1().doubleValue());
	} else {
	    formula += getGeneralProductionProcessProductionFormula();
	    formula += getNewLine();
	    formula += getProductionProcessProductionFormula(product, process
		    .getStartCycle1().doubleValue());
	}
	formula += getNewLine();
	formula += getTotalDuration(product);

	return formula;
    }

    /**
     * Returns a String for the formula of the production process for the set up
     * of a given product
     * 
     * @param product
     * @param endProductionTimePredecessor
     * @return String for the formula of the production process for the set up
     *         of a given product
     */
    public static String getProductionProcessSetUpFormula(Product product,
	    double endProductionTimePredecessor) {
	String formula = "\\textrm{Berechnung des Rüstvorgangs für Produkt "
		+ product.getK() + " berechnet sich wie folgt:}";
	formula += getNewLine();
	formula += decimals.getDecimalFormat().format(
		endProductionTimePredecessor)
		+ " + "
		+ decimals.getDecimalFormat().format(product.getTau())
		+ " = "
		+ decimals.getDecimalFormat().format(
			endProductionTimePredecessor + product.getTau());
	return formula;
    }

    /**
     * Returns a String for the formula of the production process for the
     * production of a given product
     * 
     * @param product
     * @param endSetUpTime
     * @return String for the formula of the production process for the
     *         production of a given product
     */
    public static String getProductionProcessProductionFormula(Product product,
	    double endSetUpTime) {
	String formula = "\\textrm{Berechnung des Produktionsvorgangs für Produkt "
		+ product.getK() + " berechnet sich wie folgt:}";
	formula += getNewLine();
	formula += decimals.getDecimalFormat().format(endSetUpTime)
		+ " + "
		+ decimals.getDecimalFormat().format(product.getT())
		+ " = "
		+ decimals.getDecimalFormat().format(
			endSetUpTime + product.getT());
	return formula;
    }

    /**
     * Returns a String for the formula of the general production process of the
     * set up
     * 
     * @return String for the formula of the general production process of the
     *         set up
     */
    public static String getAllgemeineProductionProcessFormelRuesten() {
	return "\\textrm{Allgemeine Berechnungsmöglichkeit des Rüstvorgangs für ein Produkt: }"
		+ getNewLine()
		+ "\\textrm{Ende Produktionszeit Vorgängerprodukt}"
		+ " +  {\\tau}_k";
    }

    /**
     * Returns a String for the formula of the total duration of a given product
     * 
     * @param product
     * @return String for the formula of the total duration of a given product
     */
    public static String getTotalDuration(Product product) {
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

    /**
     * Returns a String for the formula of the general production process for
     * the production
     * 
     * @return String for the formula of the general production process for the
     *         production
     */
    public static String getGeneralProductionProcessProductionFormula() {
	return "\\textrm{Allgemeine Berechnungsmöglichkeit des Produktionsvorgangs für ein Produkt: }"
		+ getNewLine()
		+ "\\textrm{Ende Rüstzeit des Produkts}"
		+ " +  t_p";
    }

    /**
     * Returns a String with the newLine contend for the explanation component
     * 
     * @return String with newLine-Content
     */
    public static String getNewLine() {
	return "\\\\";
    }
}
