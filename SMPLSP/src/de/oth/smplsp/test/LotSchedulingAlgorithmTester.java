package de.oth.smplsp.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.oth.smplsp.algorithms.ClassicLotScheduling;
import de.oth.smplsp.algorithms.MoreProductLotScheduling;
import de.oth.smplsp.error.MinimalProductionCycleError;
import de.oth.smplsp.model.LotSchedulingResult;
import de.oth.smplsp.model.Product;

public class LotSchedulingAlgorithmTester {

    private List<Product> products;

    @Before
    public void initialize() {
	products = getTestProducts();
    }

    @Test
    public void testKlassischeLosgroessen() {
	ClassicLotScheduling tester = new ClassicLotScheduling(products);

	LotSchedulingResult result = tester.calculateInTotal();

	Double[] expectedLotSizes = { 2498.02, 183.06, 225.02, 792.56, 376.78,
		453.0, 1245.81 };
	Double[] expectedEfficiencyOfMachine = { 0.08089, 0.02467, 0.089,
		0.2377, 0.04815, 0.0978, 0.2713 };
	Double[] expectedProductionTime = { 19.429, 5.6443, 5.56299, 15.4109,
		6.803, 11.073, 27.339 };
	Double[] expectedOptProductionCycle = { 240.205, 228.827, 62.505,
		64.8454, 141.2964, 113.25, 100.1066 };

	// Test if calculation for batch sizes works correct
	for (Product product : result.getProducts()) {
	    assertEquals(expectedLotSizes[product.getK() - 1], product.getQ(),
		    0.2);
	}
	// Test if calculation for efficiency of machine works correct
	for (Product product : result.getProducts()) {
	    assertEquals(expectedEfficiencyOfMachine[product.getK() - 1],
		    product.getRoh(), 0.1);
	}
	// Test if calculation for production time works correct
	for (Product product : result.getProducts()) {
	    assertEquals(expectedProductionTime[product.getK() - 1],
		    product.getT(), 0.1);
	}
	// Test if calculation for optimal production cycle works correct
	for (Product product : products) {
	    assertEquals(expectedOptProductionCycle[product.getK() - 1], result
		    .gettOptSingle().get(product.getK()), 0.1);
	}
    }

    @Test
    public void testMehrproduktLosgroessen() {

	MoreProductLotScheduling tester = new MoreProductLotScheduling(products);

	LotSchedulingResult result;
	try {
	    result = tester.calculateInTotal();

	    Double[] expectedLotSizes = { 1076.28, 82.79, 372.56, 1264.85,
		    275.97, 413.95, 1287.85 };
	    Double[] expectedEfficiencyOfMachine = { 0.08089, 0.02467, 0.089,
		    0.2377, 0.04815, 0.0978, 0.2713 };
	    Double[] expectedProductionTime = { 8.37, 2.55, 9.21, 24.59, 4.98,
		    10.12, 28.26 };
	    Double expectedMinProductionCycle = 94.16;
	    Double expectedOptProductionCycle = 103.488;

	    // Test if calculation for ideal common production cycle works
	    // correct
	    assertEquals(expectedOptProductionCycle, result.gettOpt(), 0.3);
	    // Test if calculation for ideal common production cycle works
	    // correct
	    assertEquals(expectedMinProductionCycle, result.gettMin(), 0.3);
	    // Test if calculation for batch sizes works correct
	    for (Product product : result.getProducts()) {
		assertEquals(expectedLotSizes[product.getK() - 1],
			product.getQ(), 0.2);
	    }
	    // Test if calculation for efficiency of machine works correct
	    for (Product product : result.getProducts()) {
		assertEquals(expectedEfficiencyOfMachine[product.getK() - 1],
			product.getRoh(), 0.1);
	    }
	    // Test if calculation for production time works correct
	    for (Product product : result.getProducts()) {
		assertEquals(expectedProductionTime[product.getK() - 1],
			product.getT(), 0.1);
	    }

	} catch (MinimalProductionCycleError e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public static List<Product> getTestProducts() {
	List<Product> products = new ArrayList<Product>();
	Product product1 = new Product(1, 10.4, 128.5714, 2.0, 190.0, 0.000689);
	products.add(product1);
	Product product2 = new Product(2, 0.8, 32.43243, 2.0, 210.0, 0.010280);
	products.add(product2);
	Product product3 = new Product(3, 3.6, 40.44944, 2.0, 140.0, 0.021853);
	products.add(product3);
	Product product4 = new Product(4, 12.22222, 51.42857, 2.0, 100.0,
		0.005105);
	products.add(product4);
	Product product5 = new Product(5, 2.666667, 55.38462, 2.0, 150.0,
		0.005920);
	products.add(product5);
	Product product6 = new Product(6, 4, 40.90909, 2.0, 110.0, 0.004753);
	products.add(product6);
	Product product7 = new Product(7, 12.44444, 45.56962, 2.0, 160.0,
		0.003530);
	products.add(product7);
	return products;
    }

}
