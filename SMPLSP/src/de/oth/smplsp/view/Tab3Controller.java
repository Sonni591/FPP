package de.oth.smplsp.view;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import de.oth.smplsp.Main;

public class Tab3Controller {

    // References
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private ObservableList<String> categories = FXCollections
	    .observableArrayList();

    // Reference to the main application.
    private Main main;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public Tab3Controller() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
	String[] cities = { "Regensburg", "Hamburg", "München" };
	categories.addAll(Arrays.asList(cities));
	xAxis.setCategories(categories);
	yAxis.setLabel("Einwohner");
	barChart.setTitle("Einwohner Vergleich");
	setCiticenData();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main main) {
	this.main = main;
    }

    public void setCiticenData() {

	XYChart.Series<String, Integer> series = new XYChart.Series<>();

	// Create a XYChart.Data object for each month. Add it to the series.
	for (int i = 0; i < categories.size(); i++) {
	    series.getData().add(
		    new XYChart.Data<>(categories.get(i), (i * 1000) + 250));
	}

	barChart.getData().add(series);
    }

}
