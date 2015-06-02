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
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;
import de.oth.smplsp.algorithms.IBasicLotSchedulingAlgorithm;
import de.oth.smplsp.algorithms.MoreProductLotScheduling;
import de.oth.smplsp.algorithms.ProductionProcessCalculator;
import de.oth.smplsp.formula.MehrproduktLosgroessenFormula;
import de.oth.smplsp.model.LotSchedulingResult;
import de.oth.smplsp.model.Product;
import de.oth.smplsp.model.ProductionProcess;
import de.oth.smplsp.util.Configuration;
import de.oth.smplsp.util.Decimals;

//import de.oth.smplsp.util.jfreechartfx.ChartViewer;

public class Tab4Controller implements Initializable {

    @FXML
    private HBox box;
    @FXML
    private TableView<Product> losgroessenTableView;
    @FXML
    private TableColumn<Product, Number> lgColumn1;
    @FXML
    private TableColumn<Product, Number> lgColumn2;

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

    // private String latexString = "";
    private int fontsize = 20;

    private RootLayoutController root;

    public ObservableList<Product> schedulingResult;

    private ObservableList productList = FXCollections.observableArrayList();

    private ObservableList processesList = FXCollections.observableArrayList();

    private Configuration config = Configuration.getInstance();
    private Decimals decimals;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public Tab4Controller() {

    }

    @FXML
    public void setData() {

	IBasicLotSchedulingAlgorithm algorithm = Tab1Controller.results
		.get(MoreProductLotScheduling.class.toString());
	if (algorithm != null && algorithm.getResult() != null) {
	    LotSchedulingResult result = algorithm.getResult();
	    ObservableList productList = FXCollections
		    .observableArrayList(algorithm.getResult().getProducts());
	    setProductsListAndShowInTableProduct(productList);

	    ProductionProcessCalculator productionCalculator = new ProductionProcessCalculator(
		    algorithm.getResult());
	    List<ProductionProcess> processes = productionCalculator
		    .getProductionProcessPlan();
	    ObservableList processesList = FXCollections
		    .observableArrayList(processes);
	    setProductsListAndShowInTableProcessing(processesList);

	}
    }

    public void setProductsListAndShowInTableProduct(
	    ObservableList<Product> newProductsList) {
	refreshDecimals();
	productList.clear();
	productList = newProductsList;
	losgroessenTableView.setItems(productList);

    }

    public void setProductsListAndShowInTableProcessing(
	    ObservableList<Product> newProcessesList) {
	refreshDecimals();
	processesList.clear();
	processesList = newProcessesList;
	prodablaufTableView.setItems(processesList);

    }

    public void addListenerForTableView() {
	losgroessenTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent event) {
		if (!losgroessenTableView.getItems().isEmpty()) {
		    showExplanations();
		}
	    }
	});
	losgroessenTableView.setOnKeyReleased(new EventHandler<KeyEvent>() {

	    @Override
	    public void handle(KeyEvent event) {

		if (!losgroessenTableView.getItems().isEmpty()) {
		    if (event.getCode() == KeyCode.UP
			    || event.getCode() == KeyCode.DOWN) {
			showExplanations();
		    }
		}
	    }

	});
    }

    public void showExplanations() {
	Product product = losgroessenTableView.getSelectionModel()
		.getSelectedItem();
	String formula = root.getDefaultLatexStringTab4();
	formula += MehrproduktLosgroessenFormula.getLosgroessenFormel(
		product,
		Tab1Controller.results.get(
			MoreProductLotScheduling.class.toString()).getResult());

	root.setLatexString(formula);
	root.showExplanationComponent();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

	int decimal = config.getDecimalPlaces();
	decimals = new Decimals(decimal);

	lgColumn1.setCellValueFactory(cellData -> cellData.getValue()
		.getKProperty());
	lgColumn2.setCellValueFactory(cellData -> cellData.getValue()
		.getQProperty());

	paColumn1.setCellValueFactory(cellData -> cellData.getValue().getK());
	paColumn2.setCellValueFactory(cellData -> cellData.getValue()
		.getVorgang());
	paColumn3.setCellValueFactory(cellData -> cellData.getValue()
		.getStart());
	paColumn4
		.setCellValueFactory(cellData -> cellData.getValue().getEnde());

	ObservableList productList = FXCollections.observableArrayList();
	setProductsListAndShowInTableProduct(productList);
	ObservableList processingList = FXCollections.observableArrayList();
	setProductsListAndShowInTableProcessing(processingList);
	addListenerForTableView();
	setColumnDecimals();
	// showTMin();
	// showTOpt();

	// 2x tooltip for the whole table
	losgroessenTableView.setTooltip(new Tooltip(
		"Tabelle der optimalen Losgrößen\n" + "k: Zeilenindex\n"
			+ "q: optimale spezifische Losgröße\n"));

	prodablaufTableView.setTooltip(new Tooltip(
		"Tabelle des Produktionsablaufs\n" + "k: Zeilenindex\n"
			+ "Vorgang: Beschreibung des Vorgangs\n"
			+ "Start: Start des Vorgangs\n"
			+ "Ende: Ende des Vorgangs\n"));

    }

    public void refreshDecimals() {
	int decimal = config.getDecimalPlaces();
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

	paColumn3
		.setCellFactory(TextFieldTableCell
			.<ProductionProcess, Number> forTableColumn(new NumberStringConverter(
				decimals.getDecimalFormat())));
	paColumn4
		.setCellFactory(TextFieldTableCell
			.<ProductionProcess, Number> forTableColumn(new NumberStringConverter(
				decimals.getDecimalFormat())));

    }

    public void scale() {

	double scalefactor = root.getScalefactor();

	box.setScaleX(scalefactor);
	box.setScaleY(scalefactor);
    }

    public void init(RootLayoutController rootLayoutController) {
	root = rootLayoutController;
    }

}