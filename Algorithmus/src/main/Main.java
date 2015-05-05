package main;

import java.util.ArrayList;
import java.util.List;

import test.LotSchedulingAlgorithmTester;
import losgroessenalgorithmen.ClassicLotScheduling;
import losgroessenalgorithmen.IBasicLotSchedulingAlgorithm;
import losgroessenalgorithmen.MehrproduktLosgroessen;
import model.LotSchedulingResult;
import model.Product;

public class Main {

    public static void main(String[] args) {

        List<IBasicLotSchedulingAlgorithm> algorithms = new ArrayList<IBasicLotSchedulingAlgorithm>();
        List<Product> products = LotSchedulingAlgorithmTester.getTestProducts();

        algorithms.add(new ClassicLotScheduling(products));
        algorithms.add(new MehrproduktLosgroessen(products));

        String ausgabe = "";
        for (IBasicLotSchedulingAlgorithm algorithm : algorithms) {
            ausgabe += algorithm.getDescriptionToString();
            LotSchedulingResult result = algorithm.calculateInTotal();
            ausgabe += result.getTotalErgebnis();
            System.out.println(ausgabe);
            ausgabe = "";
        }

    }
}
