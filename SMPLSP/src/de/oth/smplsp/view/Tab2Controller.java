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
    private TableView<Product> lotSchedulingTableView;
    @FXML
    private TableColumn<Product, Number> lgColumn1;
    @FXML
    private TableColumn<Product, Number> lgColumn2;
    @FXML
    private TableColumn<Product, Number> lgColumn3;
    @FXML
    private TableColumn<Product, Number> lgColumn4;

    @FXML
    private TableView<ProductionProcess> productionProcessesTableView;

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

    /**
     * Get the result of the algorithm calculation and show the data
     */
    @FXML
    public void setData() {
	IBasicLotSchedulingAlgorithm algorithm = root.getResults().get(
		ClassicLotScheduling.class.toString());
	if (algorithm != null && algorithm.getResult() != null) {
	    ObservableList<Product> productList = FXCollections
		    .observableArrayList(algorithm.getResult().getProducts());
	    setProductsListAndShowInTableProduct(productList);

	    ProductionProcessCalculator productionCalculator = new ProductionProcessCalculator(
		    algorithm.getResult());
	    List<ProductionProcess> processes = productionCalculator
		    .getProductionProcessPlan();
	    List<ProductionProcess> processesForTable = productionCalculator
		    .getProductionProcessPlanForTable();
	    ObservableList<ProductionProcess> processesList = FXCollections
		    .observableArrayList(processesForTable);
	    setProcessesListAndShowInTableProcessing(processesList);
	    root.getTab3Controller().showChart(processes);
	}
    }

    /**
     * Show the data of the Products in the UI
     * 
     * @param newProductsList
     */
    public void setProductsListAndShowInTableProduct(
	    ObservableList<Product> newProductsList) {
	refreshDecimals();
	productList.clear();
	productList = newProductsList;
	lotSchedulingTableView.setItems(productList);

    }

    /**
     * Show the data of the Processing in the UI
     * 
     * @param newProcessesList
     */
    public void setProcessesListAndShowInTableProcessing(
	    ObservableList<ProductionProcess> newProcessesList) {
	refreshDecimals();
	processesList.clear();
	processesList = newProcessesList;
	productionProcessesTableView.setItems(processesList);
    }

    /**
     * Initialize and customize the tables of the UI
     * 
     * @see javafx.fxml.Initializable#initialize(java.net.URL,
     *      java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
	int decimal = Configuration.getInstance().getDecimalPlaces();
	decimals = new Decimals(decimal);

	setCellValueFactoryLotSchedulingTableView();
	setCellValueFactoryProductionProcessesTableView();

	initializeTables();
	addListenerForProductionProcessTableView();
	addListenerForLotSchedulingTableView();
	setColumnDecimals();

	setTableTooltips();

    }

    /**
     * Set the CellValueFactory for the columns of the lotSchedulingTableView
     */
    private void setCellValueFactoryLotSchedulingTableView() {
	lgColumn1.setCellValueFactory(cellData -> cellData.getValue()
		.getKProperty());
	lgColumn2.setCellValueFactory(cellData -> cellData.getValue()
		.getQProperty());
	lgColumn3.setCellValueFactory(cellData -> cellData.getValue()
		.getTProperty());
	lgColumn4.setCellValueFactory(cellData -> cellData.getValue()
		.getRProperty());
    }

    /**
     * Set the CellValueFactory for the columns of the lotSchedulingTableView
     */
    private void setCellValueFactoryProductionProcessesTableView() {
	paColumn1.setCellValueFactory(cellData -> cellData.getValue().getK());
	paColumn2.setCellValueFactory(cellData -> cellData.getValue()
		.getProcess());
	paColumn3.setCellValueFactory(cellData -> cellData.getValue()
		.getStartCycle1());
	paColumn4
		.setCellValueFactory(cellData -> cellData.getValue().getEndCycle1());
    }

    /**
     * Set the tooltips for the tables lotSchedulingTableView and
     * productionProcessesTableView
     */
    private void setTableTooltips() {
	lotSchedulingTableView.setTooltip(new Tooltip(
		"Tabelle der optimalen Losgrößen\n" + "k: Zeilenindex\n"
			+ "q: optimale spezifische Losgröße\n"
			+ "t: Produktionsdauer\n" + "r: Reichweite\n"));

	productionProcessesTableView.setTooltip(new Tooltip(
		"Tabelle des Produktionsablaufs\n" + "k: Zeilenindex\n"
			+ "Vorgang: Beschreibung des Vorgangs\n"
			+ "Start: Start des Vorgangs\n"
			+ "Ende: Ende des Vorgangs\n"));
    }

    /**
     * Refresh the decimals of the tables
     */
    public void refreshDecimals() {
	int decimal = Configuration.getInstance().getDecimalPlaces();
	decimals.setDecimals(decimal);
	setColumnDecimals();
    }

    /**
     * check if the tables are empty
     * 
     * @return boolean
     */
    public boolean areTablesEmpty() {
	return lotSchedulingTableView.getItems().isEmpty()
		|| productionProcessesTableView.getItems().isEmpty();
    }

    /**
     * set the decimals of the tables using a NumberStringConverter and the @class
     * Decimals
     */
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
	lgColumn4.setCellFactory(TextFieldTableCell
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

    /**
     * @param rootLayoutController
     */
    public void init(RootLayoutController rootLayoutController) {
	root = rootLayoutController;
    }

    /**
     * get the result of the Scheduling
     * 
     * @return
     */
    public ObservableList<Product> getScheduling() {
	return schedulingResult;
    }

    /**
     * Add an onMouseClicked- and Key-Listener to production process table
     */
    public void addListenerForProductionProcessTableView() {
	productionProcessesTableView
		.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent event) {
			if (!productionProcessesTableView.getItems().isEmpty()) {
			    ProductionProcess process = productionProcessesTableView
				    .getSelectionModel().getSelectedItem();
			    if (process != null) {
				showExplanations(getProductionProcessFormula(process));
			    }
			}
		    }
		});
	productionProcessesTableView
		.setOnKeyReleased(new EventHandler<KeyEvent>() {

		    @Override
		    public void handle(KeyEvent event) {

			if (!productionProcessesTableView.getItems().isEmpty()) {
			    if (event.getCode() == KeyCode.UP
				    || event.getCode() == KeyCode.DOWN) {
				ProductionProcess process = productionProcessesTableView
					.getSelectionModel().getSelectedItem();
				if (process != null) {
				    showExplanations(getProductionProcessFormula(process));
				}
			    }
			}
		    }

		});
    }

    /**
     * Add an onMouseClicked- and Key-Listener to lot scheduling table
     */
    public void addListenerForLotSchedulingTableView() {
	lotSchedulingTableView
		.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent event) {

			if (!lotSchedulingTableView.getItems().isEmpty()) {
			    Product product = lotSchedulingTableView
				    .getSelectionModel().getSelectedItem();
			    if (product != null) {
				showExplanations(getLotSchedulingFormula(product));
			    }
			}
		    }
		});
	lotSchedulingTableView.setOnKeyReleased(new EventHandler<KeyEvent>() {

	    @Override
	    public void handle(KeyEvent event) {
		if (!lotSchedulingTableView.getItems().isEmpty()) {

		    if (event.getCode() == KeyCode.UP
			    || event.getCode() == KeyCode.DOWN) {
			Product product = lotSchedulingTableView
				.getSelectionModel().getSelectedItem();
			if (product != null) {
			    showExplanations(getLotSchedulingFormula(product));
			}

		    }
		}
	    }

	});
    }

    /**
     * Returns a formula String for the production process
     * 
     * @param process
     * @return
     */
    public String getProductionProcessFormula(ProductionProcess process) {
	String formula = "";
	int k;
	if (process.getK() != null) {
	    k = process.getK().intValue();
	} else if (process.getProcess().get().equals("Gesamtdauer")) {
	    return formula;
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

    /**
     * Return the Product of the index k
     * 
     * @param k
     * @return
     */
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

    /**
     * initialize the tables and show the products and the production processes
     */
    public void initializeTables() {
	ObservableList<Product> productList = FXCollections
		.observableArrayList();
	setProductsListAndShowInTableProduct(productList);
	ObservableList<ProductionProcess> processingList = FXCollections
		.observableArrayList();
	setProcessesListAndShowInTableProcessing(processingList);
    }

    /**
     * Returns a formula for the lot scheduling
     * 
     * @param product
     * @return
     */
    public String getLotSchedulingFormula(Product product) {
	String formula = ClassicLotSchedulingFormula
		.getLosgroessenFormel(product);
	formula += ProductFormula.getProduktionsdauerFormel(product);
	formula += ProductFormula.getReichweiteFormel(product);
	return formula;
    }

    public void showExplanations(String formula) {
	root.setLatexString(formula);
	root.showExplanationComponent();
    }

}
