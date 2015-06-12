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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.NumberStringConverter;
import de.oth.smplsp.algorithms.ClassicLotScheduling;
import de.oth.smplsp.algorithms.IBasicLotSchedulingAlgorithm;
import de.oth.smplsp.algorithms.ProductionProcessCalculator;
import de.oth.smplsp.formula.ClassicLotSchedulingFormula;
import de.oth.smplsp.formula.ProductFormula;
import de.oth.smplsp.formula.ProductionProcessFormula;
import de.oth.smplsp.model.LotSchedulingResult;
import de.oth.smplsp.model.Product;
import de.oth.smplsp.model.ProductionProcess;
import de.oth.smplsp.util.Configuration;
import de.oth.smplsp.util.Decimals;

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

    private RootLayoutController root;

    public ObservableList<Product> schedulingResult;

    private ObservableList<Product> productList = FXCollections
	    .observableArrayList();

    private ObservableList<ProductionProcess> processesList = FXCollections
	    .observableArrayList();

    private Decimals decimals;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public Tab2Controller() {

    }

    @FXML
    public void setData() {
	IBasicLotSchedulingAlgorithm algorithm = root.getResults().get(
		ClassicLotScheduling.class.toString());
	if (algorithm != null && algorithm.getResult() != null) {
	    LotSchedulingResult result = algorithm.getResult();
	    ObservableList productList = FXCollections
		    .observableArrayList(algorithm.getResult().getProducts());
	    setProductsListAndShowInTableProduct(productList);

	    ProductionProcessCalculator productionCalculator = new ProductionProcessCalculator(
		    algorithm.getResult());
	    List<ProductionProcess> processes = productionCalculator
		    .getProductionProcessPlan();
	    ObservableList<ProductionProcess> processesList = FXCollections
		    .observableArrayList(processes);
	    setProcessesListAndShowInTableProcessing(processesList);
	    root.getTab3Controller().showChart(processes);
	}
    }

    public void setProductsListAndShowInTableProduct(
	    ObservableList<Product> newProductsList) {
	refreshDecimals();
	productList.clear();
	productList = newProductsList;
	losgroessenTableView.setItems(productList);

    }

    public void setProcessesListAndShowInTableProcessing(
	    ObservableList<ProductionProcess> newProcessesList) {
	refreshDecimals();
	processesList.clear();
	processesList = newProcessesList;
	prodablaufTableView.setItems(processesList);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	int decimal = Configuration.getInstance().getDecimalPlaces();
	decimals = new Decimals(decimal);
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
	initializeTables();
	addListenerForProdAblaufTableView();
	addListenerForLosgroessenTableView();
	setColumnDecimals();

	// 2x tooltip for the whole table
	losgroessenTableView.setTooltip(new Tooltip(
		"Tabelle der optimalen Losgrößen\n" + "k: Zeilenindex\n"
			+ "q: optimale spezifische Losgröße\n"
			+ "t: Produktionsdauer\n"));

	prodablaufTableView.setTooltip(new Tooltip(
		"Tabelle des Produktionsablaufs\n" + "k: Zeilenindex\n"
			+ "Vorgang: Beschreibung des Vorgangs\n"
			+ "Start: Start des Vorgangs\n"
			+ "Ende: Ende des Vorgangs\n"));

    }

    public void refreshDecimals() {
	int decimal = Configuration.getInstance().getDecimalPlaces();
	decimals.setDecimals(decimal);
	setColumnDecimals();
    }

    public boolean areTablesEmpty() {
	return losgroessenTableView.getItems().isEmpty()
		|| prodablaufTableView.getItems().isEmpty();
    }

    public void setColumnDecimals() {
	lgColumn1.setCellFactory(TextFieldTableCell
		.<Product, Number> forTableColumn(new NumberStringConverter(
			decimals.getDecimalFormat())));
	lgColumn2.setCellFactory(TextFieldTableCell
		.<Product, Number> forTableColumn(new NumberStringConverter(
			decimals.getDecimalFormat())));
	lgColumn3.setCellFactory(TextFieldTableCell
		.<Product, Number> forTableColumn(new NumberStringConverter(
			decimals.getDecimalFormat())));

	paColumn3
		.setCellFactory(TextFieldTableCell
			.<ProductionProcess, Number> forTableColumn(new NumberStringConverter(
				decimals.getDecimalFormat())));
	paColumn4
		.setCellFactory(TextFieldTableCell
			.<ProductionProcess, Number> forTableColumn(new NumberStringConverter(
				decimals.getDecimalFormat())));
    }

    public void init(RootLayoutController rootLayoutController) {
	root = rootLayoutController;
    }

    public ObservableList<Product> getScheduling() {
	return schedulingResult;
    }

    public void addListenerForProdAblaufTableView() {
	prodablaufTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent event) {
		if (!prodablaufTableView.getItems().isEmpty()) {
		    ProductionProcess process = prodablaufTableView
			    .getSelectionModel().getSelectedItem();
		    if (process != null) {
			showExplanations(getProdAblaufFormula(process));
		    }
		}
	    }
	});
	prodablaufTableView.setOnKeyReleased(new EventHandler<KeyEvent>() {

	    @Override
	    public void handle(KeyEvent event) {

		if (!prodablaufTableView.getItems().isEmpty()) {
		    if (event.getCode() == KeyCode.UP
			    || event.getCode() == KeyCode.DOWN) {
			ProductionProcess process = prodablaufTableView
				.getSelectionModel().getSelectedItem();
			if (process != null) {
			    showExplanations(getProdAblaufFormula(process));
			}
		    }
		}
	    }

	});
    }

    public void addListenerForLosgroessenTableView() {
	losgroessenTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent event) {

		if (!losgroessenTableView.getItems().isEmpty()) {
		    Product product = losgroessenTableView.getSelectionModel()
			    .getSelectedItem();
		    if (product != null) {
			showExplanations(getLosgroessenFormula(product));
		    }
		}
	    }
	});
	losgroessenTableView.setOnKeyReleased(new EventHandler<KeyEvent>() {

	    @Override
	    public void handle(KeyEvent event) {
		if (!losgroessenTableView.getItems().isEmpty()) {

		    if (event.getCode() == KeyCode.UP
			    || event.getCode() == KeyCode.DOWN) {
			Product product = losgroessenTableView
				.getSelectionModel().getSelectedItem();
			if (product != null) {
			    showExplanations(getLosgroessenFormula(product));
			}

		    }
		}
	    }

	});
    }

    public String getProdAblaufFormula(ProductionProcess process) {
	String formula = "";
	int k;
	if (process.getK() != null) {
	    k = process.getK().intValue();
	} else {
	    int index = processesList.indexOf(process);
	    ProductionProcess parent = processesList.get(index - 1);
	    k = parent.getK().intValue();
	}
	Product product = getProductByK(k);
	formula += ProductionProcessFormula.getProductionProcessFormel(process,
		product);
	return formula;
    }

    public Product getProductByK(int k) {
	IBasicLotSchedulingAlgorithm algorithm = root.getResults().get(
		ClassicLotScheduling.class.toString());
	LotSchedulingResult result = algorithm.getResult();
	List<Product> products = result.getProducts();
	for (Product product : products) {
	    if (product.getK() == k) {
		return product;
	    }
	}
	return null;
    }

    public void initializeTables() {
	ObservableList<Product> productList = FXCollections
		.observableArrayList();
	setProductsListAndShowInTableProduct(productList);
	ObservableList<ProductionProcess> processingList = FXCollections
		.observableArrayList();
	setProcessesListAndShowInTableProcessing(processingList);
    }

    public String getLosgroessenFormula(Product product) {
	String formula = ClassicLotSchedulingFormula
		.getLosgroessenFormel(product);
	formula += ProductFormula.getProduktionsdauerFormel(product);
	return formula;
    }

    public void showExplanations(String formula) {
	root.setLatexString(formula);
	root.showExplanationComponent();
    }

}
