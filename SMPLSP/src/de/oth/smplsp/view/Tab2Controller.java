package de.oth.smplsp.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import de.oth.smplsp.Main;
import de.oth.smplsp.algorithms.ClassicLotScheduling;
import de.oth.smplsp.algorithms.IBasicLotSchedulingAlgorithm;
import de.oth.smplsp.algorithms.ProductionProcessCalculator;
import de.oth.smplsp.formula.ClassicLotSchedulingFormula;
import de.oth.smplsp.formula.ProductFormula;
import de.oth.smplsp.model.LotSchedulingResult;
import de.oth.smplsp.model.Product;
import de.oth.smplsp.model.ProductionProcess;

public class Tab2Controller implements Initializable {

    @FXML
    private TableView<Product> losgroessenTableView;
    @FXML
    private TableColumn<Product, Number> lgColumn1;
    @FXML
    private TableColumn<Product, Number> lgColumn2;
    @FXML
    private TableColumn<Product, Number> lgColumn3;

    @FXML
    private TableView<ProductionProcess> prodablaufTableView;

    @FXML
    private TableColumn<ProductionProcess, Number> paColumn1;
    @FXML
    private TableColumn<ProductionProcess, String> paColumn2;
    @FXML
    private TableColumn<ProductionProcess, Number> paColumn3;
    @FXML
    private TableColumn<ProductionProcess, Number> paColumn4;

    // Reference to the main application.
    private Main main;
    private RootLayoutController root;

    public ObservableList<Product> schedulingResult;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public Tab2Controller() {

    }

    @FXML
    public void setData() {
	IBasicLotSchedulingAlgorithm algorithm = Tab1Controller.results
		.get(ClassicLotScheduling.class.toString());
	if (algorithm != null && algorithm.getResult() != null) {
	    LotSchedulingResult result = algorithm.getResult();
	    ObservableList productList = FXCollections
		    .observableArrayList(algorithm.getResult().getProducts());
	    losgroessenTableView.setItems(productList);

	    ProductionProcessCalculator productionCalculator = new ProductionProcessCalculator(
		    algorithm.getResult());
	    List<ProductionProcess> processes = productionCalculator
		    .getProductionProcessPlan();
	    ObservableList processesList = FXCollections
		    .observableArrayList(processes);
	    prodablaufTableView.setItems(processesList);

	}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	lgColumn1.setCellValueFactory(cellData -> cellData.getValue()
		.getKProperty());
	lgColumn2.setCellValueFactory(cellData -> cellData.getValue()
		.getQProperty());
	lgColumn3.setCellValueFactory(cellData -> cellData.getValue()
		.getTProperty());

	paColumn1.setCellValueFactory(cellData -> cellData.getValue().getK());
	paColumn2.setCellValueFactory(cellData -> cellData.getValue()
		.getVorgang());
	paColumn3.setCellValueFactory(cellData -> cellData.getValue()
		.getStart());
	paColumn4
		.setCellValueFactory(cellData -> cellData.getValue().getEnde());

	addListenerForTableView();

    }

    public void init(RootLayoutController rootLayoutController) {
	root = rootLayoutController;
    }

    public ObservableList<Product> getScheduling() {
	return schedulingResult;
    }

    public void addListenerForTableView() {
	losgroessenTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent event) {
		Product product = losgroessenTableView.getSelectionModel()
			.getSelectedItem();
		String formula = ClassicLotSchedulingFormula
			.getLosgroessenFormel(product);
		formula += ProductFormula.getProduktionsdauerFormel(product);
		root.setLatexString(formula);
		root.showLatex();

	    }
	});
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main main) {
	this.main = main;
    }

}
