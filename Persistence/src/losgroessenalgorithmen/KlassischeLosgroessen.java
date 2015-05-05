package losgroessenalgorithmen;

import java.util.List;

public class KlassischeLosgroessen {

	public List<Product> calculate(List<Product> products) {

		products = calculateLosgroessen(products);
		products = calculateProduktionsDauer(products);
		products = calculateAuslastungAnlage(products);
		products = calculateOptProduktionsZyklus(products);

		return products;
	}

	private List<Product> calculateAuslastungAnlage(List<Product> products) {
		for (Product product : products) {
			product.setRoh(product.getD() / product.getP());
		}
		return products;
	}

	private List<Product> calculateOptProduktionsZyklus(List<Product> products) {

		for (Product product : products) {
			product.setTopt(Math.sqrt(((2 * product.getS()) / (product.getH()
					* product.getD() * (1 - product.getRoh())))));
		}

		return products;
	}

	private List<Product> calculateProduktionsDauer(List<Product> products) {

		for (Product product : products) {
			product.setT(product.getQ() / product.getP());
		}

		return products;
	}

	private List<Product> calculateLosgroessen(List<Product> products) {

		for (Product product : products) {
			product.setQ(Math.sqrt((2 * product.getD() * product.getS())
					/ (product.getH() * (1 - (product.getD() / product.getP())))));
		}

		return products;
	}
}
