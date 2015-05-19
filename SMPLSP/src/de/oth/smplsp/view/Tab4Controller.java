package de.oth.smplsp.view;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javax.swing.Icon;
import javax.swing.JTextPane;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import de.oth.smplsp.Main;
import de.oth.smplsp.algorithms.IBasicLotSchedulingAlgorithm;
import de.oth.smplsp.algorithms.MoreProductLotScheduling;
import de.oth.smplsp.algorithms.ProductionProcessCalculator;
import de.oth.smplsp.formula.MehrproduktLosgroessenFormula;
import de.oth.smplsp.model.LotSchedulingResult;
import de.oth.smplsp.model.Product;
import de.oth.smplsp.model.ProductionProcess;

//import de.oth.smplsp.util.jfreechartfx.ChartViewer;

public class Tab4Controller implements Initializable {

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

    @FXML
    private SwingNode tOptNode;
    @FXML
    private SwingNode tMinNode;

    private String latexString = "";
    private int fontsize = 20;

    // Reference to the main application.
    private Main main;
    private RootLayoutController root;

    public ObservableList<Product> schedulingResult;

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

    public void addListenerForTableView() {
	losgroessenTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent event) {
		showExplanations();
	    }
	});
	losgroessenTableView.setOnKeyReleased(new EventHandler<KeyEvent>() {

	    @Override
	    public void handle(KeyEvent event) {

		if (event.getCode() == KeyCode.UP
			|| event.getCode() == KeyCode.DOWN) {
		    showExplanations();
		}
	    }

	});
    }

    public void showExplanations() {
	Product product = losgroessenTableView.getSelectionModel()
		.getSelectedItem();
	String formula = MehrproduktLosgroessenFormula.getLosgroessenFormel(
		product,
		Tab1Controller.results.get(
			MoreProductLotScheduling.class.toString()).getResult());

	root.setLatexString(formula);
	root.showLatex();

    }

    public void showTOpt() {

	latexString = "T_{opt}=\\sqrt{\\frac{2*\\sum_{k=1}^{K}s_k}{\\sum_{k=1}^{K}h_k*D_k*(1-p_k)}}";
	TeXFormula tex = new TeXFormula(latexString);

	Icon icon = tex.createTeXIcon(TeXConstants.ALIGN_CENTER, fontsize);

	// generate a JTextPane that will be displayed in a SwingNode in JavaFX
	JTextPane pane = new JTextPane();
	pane.setEditable(false);
	pane.insertIcon(icon);
	pane.repaint();

	tOptNode.setContent(pane);
    }

    public void showTMin() {

	latexString = "T_{min}=\\frac{\\sum_{k=1}^{K}T_k}{1-\\sum_{k=1}^{K}p_k}\\le{T}";
	TeXFormula tex = new TeXFormula(latexString);

	Icon icon = tex.createTeXIcon(TeXConstants.ALIGN_CENTER, fontsize);

	// generate a JTextPane that will be displayed in a SwingNode in JavaFX
	JTextPane pane = new JTextPane();
	pane.setEditable(false);
	pane.insertIcon(icon);
	pane.repaint();

	tMinNode.setContent(pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	// TODO Auto-generated method stub

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

	addListenerForTableView();
	showTMin();
	showTOpt();

    }

    public void init(RootLayoutController rootLayoutController) {
	root = rootLayoutController;
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