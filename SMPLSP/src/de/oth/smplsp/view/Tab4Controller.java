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

public class Tab4Controller implements Initializable {

    @FXML
    private HBox box;
    @FXML
    private VBox vbox;
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

    @FXML
    private SwingNode tOptNode;
    @FXML
    private SwingNode tMinNode;

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

    /**
     * Get the result of the algorithm calculation and show the data
     */
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

    // TODO: CONTINUE HERE
    public void addListenerForProdAblaufTableView() {
	productionProcessesTableView
		.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent event) {
			if (!productionProcessesTableView.getItems().isEmpty()) {
			    ProductionProcess process = productionProcessesTableView
				    .getSelectionModel().getSelectedItem();
			    if (process != null) {
				showExplanations(getProdAblaufFormula(process));
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
				    showExplanations(getProdAblaufFormula(process));
				}
			    }
			}
		    }

		});
    }

    public void addListenerForLosgroessenTableView() {
	lotSchedulingTableView
		.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent event) {
			if (!lotSchedulingTableView.getItems().isEmpty()) {
			    Product product = lotSchedulingTableView
				    .getSelectionModel().getSelectedItem();
			    if (product != null) {
				showExplanations(getLosgroessenFormula(product));
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
		if (!productionProcessesTableView.getItems().isEmpty()) {
		    showExplanations(getTOptFormulaWithParameters());
		}
	    }
	});
    }

    public void addListenerForTMin() {
	tMinNode.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent event) {
		if (!productionProcessesTableView.getItems().isEmpty()) {
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

	// tooltips for the both tables
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
	return lotSchedulingTableView.getItems().isEmpty()
		|| productionProcessesTableView.getItems().isEmpty();
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

    public void init(RootLayoutController rootLayoutController) {
	root = rootLayoutController;
    }

}