package de.oth.smplsp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.oth.smplsp.util.MathUtils;

public class LotSchedulingResult {

    private List<Product> products;
    // ideal common production cycle
    private Double tOptCommon;
    // minimal common production cycle
    private Double tMin;
    // ideal production cycle for single products
    private Map<Integer, Double> tOptSingle;

    public LotSchedulingResult(List<Product> products, double tOpt, double tMin) {
	super();
	this.products = products;
	this.tOptCommon = tOpt;
	this.tMin = tMin;
	this.tOptSingle = new HashMap<Integer, Double>();
    }

    public LotSchedulingResult(List<Product> products,
	    Map<Integer, Double> tOptSingle) {
	super();
	this.products = products;
	this.tOptCommon = null;
	this.tMin = null;
	this.tOptSingle = tOptSingle;
    }

    public List<Product> getProducts() {
	return products;
    }

    public double gettOpt() {
	return tOptCommon;
    }

    public double gettMin() {
	return tMin;
    }

    public Map<Integer, Double> gettOptSingle() {
	return tOptSingle;
    }

    public String getOptProductionCycleToString() {
	if (tOptCommon != null) {
	    return "Der optimale gemeinsame Produktionszyklus beträgt: "
		    + MathUtils.round(tOptCommon, 2) + "\n";
	}
	return "";
    }

    public String getMinProductionCycleToString() {
	if (tMin != null) {
	    return "Der minimale gemeinsame Produktionszyklus beträgt: "
		    + MathUtils.round(tMin, 2) + "\n";
	}
	return "";
    }

    public String getProductsToString() {
	String s = "";
	for (Product product : products) {
	    s += product.getRohToString();
	    s += product.getQToString();
	    s += product.getTToString();
	    if (tOptSingle.containsKey(product.getK())) {
		s += "Der optimale, produktspezifische Produkionszyklus für das Produkt mit der Produktnummer "
			+ product.getK()
			+ " beträgt "
			+ MathUtils.round(tOptSingle.get(product.getK()), 2)
			+ "\n";
	    }
	}
	return s;
    }

    public String getTotalErgebnis() {
	String s = "";
	s += getOptProductionCycleToString();
	s += getMinProductionCycleToString();
	s += getProductsToString();
	return s;
    }

}
