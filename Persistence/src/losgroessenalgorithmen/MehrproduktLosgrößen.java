package losgroessenalgorithmen;

import java.util.List;

public class MehrproduktLosgrößen {

	public List<Product> calculate(List<Product> products) {
		// optimaler gemeinsamer Produktionszyklus
		double tOpt;
		// minimaler gemeinsamer Produktionszyklus
		double tMin;

		products = calculateAuslastungAnlage(products);

		tOpt = calculateOptProduktionszyklus(products);

		tMin = calculateMinProduktionszyklus(products);

		products = calculateLosgroessen(products, tOpt);

		products = calculateProduktionsDauer(products);

		return products;
	}

	private List<Product> calculateLosgroessen(List<Product> products,
			double tOpt) {
		for (Product product : products) {
			product.setQ(product.getD() * tOpt);
		}
		return products;
	}

	private double calculateMinProduktionszyklus(List<Product> products) {
		double numerator = 0.0;
		double denominator = 0.0;

		for (Product product : products) {
			numerator += product.getTau();
		}

		for (Product product : products) {
			denominator += product.getRoh();
		}

		denominator = 1 - denominator;

		return Math.sqrt((numerator / denominator));
	}

	private double calculateOptProduktionszyklus(List<Product> products) {
		double numerator = 0.0;
		double denominator = 0.0;

		for (Product product : products) {
			numerator += product.getS();
		}

		numerator *= 2;

		for (Product product : products) {
			denominator += (product.getH() * product.getD() * (1 - product
					.getRoh()));
		}

		return Math.sqrt((numerator / denominator));
	}

	private List<Product> calculateAuslastungAnlage(List<Product> products) {
		for (Product product : products) {
			product.setRoh(product.getD() / product.getP());
		}
		return products;
	}

	private List<Product> calculateProduktionsDauer(List<Product> products) {

		for (Product product : products) {
			product.setT(product.getQ() / product.getP());
		}

		return products;
	}

}
