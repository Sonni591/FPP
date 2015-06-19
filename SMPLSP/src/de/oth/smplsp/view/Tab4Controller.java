package de.oth.smplsp.view;

import java.awt.Color;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
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
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

import javax.swing.Icon;
import javax.swing.JTextPane;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import de.oth.smplsp.algorithms.IBasicLotSchedulingAlgorithm;
import de.oth.smplsp.algorithms.MoreProductLotScheduling;
import de.oth.smplsp.algorithms.ProductionProcessCalculator;
import de.oth.smplsp.formula.MehrproduktLosgroessenFormula;
import de.oth.smplsp.formula.ProductFormula;
import de.oth.smplsp.formula.ProductionProcessFormula;
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
    private VBox vbox;
    @FXML
    private TableView<Product> losgroessenTableView;
    @FXML
    private TableColumn<Product, Number> lgColumn1;
    @FXML
    private TableColumn<Product, Number> lgColumn2;
    @FXML
    private TableColumn<Product, Number> lgColumn3;
    @FXML
    private TableColumn<Product, Number> lgColumn4;

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

    @FXML
    private SwingNode tOptNode;
    @FXML
    private SwingNode tMinNode;

    // private String latexString = "";
    private int fontsize = 20;

    private RootLayoutController root;

    private ObservableList<Product> productList = FXCollections
	    .observableArrayList();

    private ObservableList<ProductionProcess> processesList = FXCollections
	    .observableArrayList();

    private Decimals decimals;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public Tab4Controller() {

    }

    @FXML
    public void setData() {

	IBasicLotSchedulingAlgorithm algorithm = root.getResults().get(
		MoreProductLotScheduling.class.toString());
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
	    root.getTab5Controller().showChart(processes);

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

    public void addListenerForTOpt() {
	tOptNode.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent event) {
		if (!prodablaufTableView.getItems().isEmpty()) {
		    showExplanations(getTOptFormulaWithParameters());
		}
	    }
	});
    }

    public void addListenerForTMin() {
	tMinNode.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent event) {
		if (!prodablaufTableView.getItems().isEmpty()) {
		    showExplanations(getTMinFormulaWithParameters());
		}
	    }
	});
    }

    public void showExplanations(String formula) {
	root.setLatexString(formula);
	root.showExplanationComponent();
    }

    public String getLosgroessenFormula(Product product) {

	String formula = MehrproduktLosgroessenFormula.getLosgroessenFormel(
		product,
		root.getResults()
			.get(MoreProductLotScheduling.class.toString())
			.getResult());
	formula += ProductFormula.getProduktionsdauerFormel(product);
	formula += ProductFormula.getReichweiteFormel(product);
	return formula;
    }

    public String getTOptFormulaWithParameters() {
	return MehrproduktLosgroessenFormula
		.getGemeinsameProduktionszyklusMitParameternFormel(root
			.getResults()
			.get(MoreProductLotScheduling.class.toString())
			.getResult());
    }

    public String getTMinFormulaWithParameters() {
	return MehrproduktLosgroessenFormula
		.getMinimalenProduktionszyklusMitParameternFormel(root
			.getResults()
			.get(MoreProductLotScheduling.class.toString())
			.getResult());
    }

    public String getProdAblaufFormula(ProductionProcess process) {
	String formula = "";
	int k;
	if (process.getK() != null) {
	    k = process.getK().intValue();
	} else if (process.getVorgang().get().equals("Gesamtdauer")) {
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

    public Product getProductByK(int k) {
	IBasicLotSchedulingAlgorithm algorithm = root.getResults().get(
		MoreProductLotScheduling.class.toString());
	LotSchedulingResult result = algorithm.getResult();
	List<Product> products = result.getProducts();
	for (Product product : products) {
	    if (product.getK() == k) {
		return product;
	    }
	}
	return null;
    }

    public void showTOpt() {

	String latexString = MehrproduktLosgroessenFormula
		.getAllgemeineGemeinsameProduktionszyklusFormel();
	TeXFormula tex = new TeXFormula(latexString);

	Icon icon = tex.createTeXIcon(TeXConstants.ALIGN_CENTER, root
		.getZoomer().getLatexFontSize());

	// generate a JTextPane that will be displayed in a SwingNode
	// in JavaFX
	JTextPane pane = new JTextPane();
	pane.setEditable(false);
	pane.insertIcon(icon);
	pane.repaint();
	pane.setBackground(new Color(0, 0, 0, 0));

	tOptNode.setContent(pane);
    }

    public void showTMin() {

	String latexString = MehrproduktLosgroessenFormula
		.getAllgemeineMinimalenProduktionszyklusFormel();
	TeXFormula tex = new TeXFormula(latexString);

	Icon icon = tex.createTeXIcon(TeXConstants.ALIGN_CENTER, root
		.getZoomer().getLatexFontSize());

	// generate a JTextPane that will be displayed in a SwingNode
	// in JavaFX
	JTextPane pane = new JTextPane();
	pane.setEditable(false);
	pane.insertIcon(icon);
	pane.repaint();
	pane.setBackground(new Color(0, 0, 0, 0));

	tMinNode.setContent(pane);

    }

    public void showTOptAndTMinFormulas() {
	showTMin();
	showTOpt();
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
	lgColumn4.setCellValueFactory(cellData -> cellData.getValue()
		.getRProperty());

	paColumn1.setCellValueFactory(cellData -> cellData.getValue().getK());
	paColumn2.setCellValueFactory(cellData -> cellData.getValue()
		.getVorgang());
	paColumn3.setCellValueFactory(cellData -> cellData.getValue()
		.getStart());
	paColumn4
		.setCellValueFactory(cellData -> cellData.getValue().getEnde());

	initializeTables();
	addListeners();
	setColumnDecimals();

	// 2x tooltip for the whole table
	losgroessenTableView.setTooltip(new Tooltip(
		"Tabelle der optimalen Losgrößen\n" + "k: Zeilenindex\n"
			+ "q: optimale spezifische Losgröße\n"
			+ "t: Produktionsdauer\n" + "r: Reichweite\n"));

	prodablaufTableView.setTooltip(new Tooltip(
		"Tabelle des Produktionsablaufs\n" + "k: Zeilenindex\n"
			+ "Vorgang: Beschreibung des Vorgangs\n"
			+ "Start: Start des Vorgangs\n"
			+ "Ende: Ende des Vorgangs\n"));

    }

    public void addListeners() {
	addListenerForTOpt();
	addListenerForTMin();
	addListenerForLosgroessenTableView();
	addListenerForProdAblaufTableView();
    }

    public void initializeTables() {
	ObservableList<Product> productList = FXCollections
		.observableArrayList();
	setProductsListAndShowInTableProduct(productList);
	ObservableList<ProductionProcess> processingList = FXCollections
		.observableArrayList();
	setProcessesListAndShowInTableProcessing(processingList);
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

    public void scale() {

	double scalefactor = root.getScalefactor();

	box.setScaleX(scalefactor);
	box.setScaleY(scalefactor);
    }

    public void init(RootLayoutController rootLayoutController) {
	root = rootLayoutController;
    }

}