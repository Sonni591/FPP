package de.oth.smplsp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
