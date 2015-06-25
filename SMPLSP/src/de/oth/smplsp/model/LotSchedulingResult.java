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

    /**
     * Constructor for a lot scheduling result
     * 
     * @param products
     *            - a list of products
     * @param tOpt
     *            - the optimal lot scheduling cycle
     * @param tMin
     *            - the minimal lot scheduling cycle
     */
    public LotSchedulingResult(List<Product> products, double tOpt, double tMin) {
	super();
	this.products = products;
	this.tOptCommon = tOpt;
	this.tMin = tMin;
	this.tOptSingle = new HashMap<Integer, Double>();
    }

    /**
     * Constructor for a lot scheduling result
     * 
     * @param products
     *            - a list of products
     * @param tOptSingle
     *            - a map with optimal cycles for each product item
     */
    public LotSchedulingResult(List<Product> products,
	    Map<Integer, Double> tOptSingle) {
	super();
	this.products = products;
	this.tOptCommon = null;
	this.tMin = null;
	this.tOptSingle = tOptSingle;
    }

    /**
     * @return List<Product> - a list of @class Product
     */
    public List<Product> getProducts() {
	return products;
    }

    /**
     * Returns the ideal common production cycle
     * 
     * @return tOptCommon
     */
    public double gettOpt() {
	return tOptCommon;
    }

    /**
     * Return the minimal common production cycle
     * 
     * @return tMin
     */
    public double gettMin() {
	return tMin;
    }

    /**
     * Return a map with ideal production cycle for each product
     * 
     * @return Map<Integer, Double> tOptSingle
     */
    public Map<Integer, Double> gettOptSingle() {
	return tOptSingle;
    }

}
