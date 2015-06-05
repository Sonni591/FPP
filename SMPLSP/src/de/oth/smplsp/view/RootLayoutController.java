package de.oth.smplsp.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javax.swing.Icon;
import javax.swing.JTextPane;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import de.oth.smplsp.Main;
import de.oth.smplsp.algorithms.IBasicLotSchedulingAlgorithm;
import de.oth.smplsp.messages.Messages;
import de.oth.smplsp.model.Product;
import de.oth.smplsp.test.LotSchedulingAlgorithmTester;
import de.oth.smplsp.zoom.Zoomer;

public class RootLayoutController {

    // Examine debug mode
    public static final boolean DEBUG_MODE = java.lang.management.ManagementFactory
	    .getRuntimeMXBean().getInputArguments().toString().indexOf("jdwp") >= 0;

    @FXML
    private BorderPane rootBorderPane;

    // References to all panes of the tab bar
    @FXML
    private AnchorPane tab1;

    private Map<String, IBasicLotSchedulingAlgorithm> results;
    // References to all Controllers
    @FXML
    private Tab1Controller tab1Controller;
    @FXML
    private Tab2Controller tab2Controller;
    @FXML
    private Tab3Controller tab3Controller;
    @FXML
    private Tab4Controller tab4Controller;
    @FXML
    private Tab5Controller tab5Controller;

    // References all Buttons
    @FXML
    private Label lblLeftStatus;
    @FXML
    private Label lblZoom;
    @FXML
    private Button btnZoomPlus;
    @FXML
    private Button btnZoomMinus;
    @FXML
    private SwingNode swingNode;
    @FXML
    private Menu menuEdit;
    @FXML
    private TabPane tabPane;

    @FXML
    private MenuBar menuBar;

    private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome"); //$NON-NLS-1$

    public String latexString = ""; //$NON-NLS-1$

    // global parameters for the font size
    // private int fontsize = 20;
    private double scalefactor = 1;

    private int selectedTab = 0;

    // Reference to the main application.
    private Main main;

    private Zoomer zoomer;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public RootLayoutController() {
	zoomer = new Zoomer();
    }

    // /**
    // * @return the fontsize
    // */
    // public int getFontsize() {
    // return fontsize;
    // }
    //
    // /**
    // * @param fontsize
    // * the fontsize to set
    // */
    // public void setFontsize(int fontsize) {
    // this.fontsize = fontsize;
    // }

    /**
     * @return the zoomer
     */
    public Zoomer getZoomer() {
	return zoomer;
    }

    /**
     * @return the swingNode
     */
    public SwingNode getSwingNode() {
	return swingNode;
    }

    /**
     * @param swingNode
     *            the swingNode to set
     */
    public void setSwingNode(SwingNode swingNode) {
	this.swingNode = swingNode;
    }

    /**
     * @return the latexString
     */
    public String getLatexString() {
	return latexString;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

	// Init all controller first
	tab1Controller.init(this);
	tab2Controller.init(this);
	tab3Controller.init(this);
	tab4Controller.init(this);
	tab5Controller.init(this);
	zoomer.init(this);

	tab4Controller.showTOptAndTMinFormulas();

	// customize the look of the Zoom area
	customizeUIZoom();

	// show an initial decription text of the input table in the explanation
	// component
	// WARNING: Does not work properly on Mac OS X and leads to an not
	// responding-application
	if (!System.getProperty("os.name").equals("Mac OS X")) {
	    initExplanationTabTextTab1();
	}

	// in debug mode: load data and calculate
	if (DEBUG_MODE) {
	    loadAndShowTestData();
	    tab1Controller.handleCalculate();
	}
    }

    /**
     * decorates the buttons of the zoom area with an corresponding icon font
     */
    private void customizeUIZoom() {
	// remove default text of the buttons
	btnZoomMinus.setText("");
	btnZoomPlus.setText("");
	// set icon fonts to the buttons
	btnZoomMinus.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.SEARCH_MINUS));
	btnZoomPlus.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.SEARCH_PLUS));
	// set tooltip text
	btnZoomMinus.setTooltip(new Tooltip("verkleinern"));
	btnZoomPlus.setTooltip(new Tooltip("vergrößern"));

    }

    private void initExplanationTabTextTab1() {
	latexString = getDefaultLatexStringTab1();
	showExplanationComponent();
    }

    public void showExplanationComponent() {
	if (!getLatexString().equals("")) {
	    try {
		TeXFormula tex = new TeXFormula(latexString);

		Icon icon = tex.createTeXIcon(TeXConstants.ALIGN_CENTER,
			zoomer.getLatexFontSize());

		// generate a JTextPane that will be displayed in a SwingNode in
		// JavaFX
		JTextPane pane = new JTextPane();
		pane.setEditable(false);
		pane.insertIcon(icon);
		pane.repaint();

		this.swingNode.setContent(pane);
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    public void setLatexString(String latexString) {
	this.latexString = latexString;
    }

    @FXML
    public void handleZoomIn() {

	zoomer.handleZoomIn();

	// fontsize++;
	// setScalefactor(0.2);
	//
	// // regenerate LaTeX image
	// showExplanationComponent();
	// tab4Controller.scale();
    }

    @FXML
    public void handleZoomOut() {

	zoomer.handleZoomOut();

	// fontsize--;
	// setScalefactor(-0.2);
	//
	// // regenerate LaTeX image
	// showExplanationComponent();
	// tab4Controller.scale();
    }

    /**
     * Sets the font size according of zoom factor on a touch based zoom event
     * 
     * @param event
     */
    public void handleZoom(ZoomEvent event) {

	Double zoomFactorDouble = event.getZoomFactor();
	Integer zoomFactorInteger = zoomFactorDouble.intValue();

	Integer fontsize = zoomer.getFontsize();

	if (zoomFactorInteger >= 1) { // zoom in
	    fontsize += zoomFactorInteger;
	} else { // zoom out
	    fontsize -= 1;
	}

	zoomer.setFontsize(fontsize);

	// stop further propagation of the event
	event.consume();
    }

    /**
     * Redraws the image with the LaTeX code based on the new fontsize
     * 
     * @param event
     */
    public void handleZoomFinished(ZoomEvent event) {

	zoomer.rescaleEverything();

	// stop further propagation of the event
	event.consume();

    }

    // Menu methods
    @FXML
    private void onActionFileOpen() {
	try {
	    tab1Controller.handleLoad(null);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @FXML
    private void onActionFileSave() {
	tab1Controller.handleSave();
    }

    @FXML
    private void onActionTestData() {
	if (tab1Controller.getProductsList().isEmpty()) {
	    loadAndShowTestData();
	    zoomer.rescaleEverything();
	} else {
	    // when the table is not empty, show an confirmation dialog to
	    // overwrite
	    // the actual table data
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("Daten überschreiben?");
	    alert.setHeaderText("Wollen Sie alle Daten der Tabelle mit den Testdaten überschreiben?");
	    alert.setContentText("Das Laden der Testdaten überschreibt alle bisherigen Daten. Wollen Sie fortfahren?");
	    alert.getDialogPane().setStyle(zoomer.getStyleFXFontSize());
	    Optional<ButtonType> result = alert.showAndWait();
	    if (result.get() == ButtonType.OK) {
		// ... user chose OK - delete table and load test data
		loadAndShowTestData();
		zoomer.rescaleEverything();
	    } else {
		// ... user chose Cancel
		// do nothing
	    }
	}
    }

    private void loadAndShowTestData() {
	List<Product> productsList = LotSchedulingAlgorithmTester
		.getTestProducts();
	ObservableList<Product> ObservableProductsList = FXCollections
		.observableArrayList();
	ObservableProductsList.addAll(productsList);
	tab1Controller.setProductsListAndShowInTable(ObservableProductsList);
    }

    @FXML
    private void onActionFileSettings() {
	SettingsDialog dia = new SettingsDialog(this);
	dia.showAndWait();
    }

    @FXML
    private void onActionFileExit() {
	Platform.exit();
    }

    @FXML
    private void onActionEditAdd() {
	tab1Controller.handleAddRow();
    }

    @FXML
    private void onActionEditDelete() {
	tab1Controller.handleDeleteRow();
    }

    @FXML
    private void onActionEditDeleteAll() {
	tab1Controller.handleDeleteAll();
    }

    @FXML
    private void onActionHelpAbout() {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle(Messages.RootLayoutController_AboutDialog_Title);
	alert.setHeaderText(Messages.RootLayoutController_AboutDialog_NameAndVersion);
	alert.setContentText(Messages.RootLayoutController_AboutDialog_License);
	alert.getDialogPane().setStyle(zoomer.getStyleFXFontSize());
	alert.showAndWait();
    }

    @FXML
    private void onTabSelectionChanged() {
	setMenuEditDisable();
	setBottomLeftStatusLabel();
	// WARNING: Does not work properly on Mac OS X and leads to an not
	// responding-application
	if (!System.getProperty("os.name").equals("Mac OS X")) {
	    clearExplanationComponent();
	}
    }

    private void setMenuEditDisable() {
	if (tabPane.getSelectionModel().getSelectedIndex() == 0) {
	    menuEdit.setDisable(false);
	} else {
	    menuEdit.setDisable(true);
	}
    }

    private void setBottomLeftStatusLabel() {
	if (tabPane.getSelectionModel().getSelectedIndex() == 0) {
	    lblLeftStatus.setText("");
	} else if ((tabPane.getSelectionModel().getSelectedIndex() == 1)
		|| (tabPane.getSelectionModel().getSelectedIndex() == 2)) {
	    lblLeftStatus.setText("Algorithmus: klassische Losgrößenplanung");
	} else if ((tabPane.getSelectionModel().getSelectedIndex() == 3)
		|| (tabPane.getSelectionModel().getSelectedIndex() == 4)) {
	    lblLeftStatus.setText("Algorithmus: Mehrproduktlosgrößenplanung");
	}

    }

    private void clearExplanationComponent() {

	if (tabPane.getSelectionModel().getSelectedIndex() != selectedTab) {
	    selectedTab = tabPane.getSelectionModel().getSelectedIndex();

	    switch (tabPane.getSelectionModel().getSelectedIndex()) {
	    case 0:
		latexString = getDefaultLatexStringTab1();
		break;
	    case 1:
		latexString = getDefaultLatexStringTab2();
		break;
	    case 2:
		latexString = getDefaultLatexStringTab3();
		break;
	    case 3:
		latexString = getDefaultLatexStringTab4();
		break;
	    case 4:
		latexString = getDefaultLatexStringTab5();
		latexString += getExplanationTextClickOnALine();
		break;
	    default:
		latexString = getLatexNewLine();
		break;
	    }

	    showExplanationComponent();

	}

    }

    public String getDefaultLatexStringTab1() {
	String s = "\\textrm{Hinweise zur Dateneingabe:";
	s += getLatexNewLine();
	s += "k: Zeilenindex";
	s += getLatexNewLine();
	s += "D: Nachfragerate";
	s += getLatexNewLine();
	s += getLatexNewLine();
	s += "p: Produktionsrate";
	s += getLatexNewLine();
	s += "τ: Rüstzeit";
	s += getLatexNewLine();
	s += "s: Rüstkostensatz";
	s += getLatexNewLine();
	s += "h: Lagerkostensatz}";
	return s;
    }

    public String getDefaultLatexStringTab2() {
	return getExplanationTextClickOnALine();
    }

    public String getDefaultLatexStringTab3() {
	return getLatexNewLine();
    }

    public String getDefaultLatexStringTab4() {
	String s = "\\textrm{Formel für den optimalen gemeinsamen Produktionszyklus:";
	s += getLatexNewLine();
	s += "\\mathrm{T_{opt}=\\sqrt{\\frac{2*\\sum_{k=1}^{K}s_k}{\\sum_{k=1}^{K}h_k*D_k*(1-p_k)}}}";
	s += getLatexNewLine();
	s += getLatexNewLine();

	s += "\\textrm{Formel für den minimalen gemeinsamen Produktionszyklus:";
	s += getLatexNewLine();
	s += "\\mathrm{T_{min}=\\frac{\\sum_{k=1}^{K}T_k}{1-\\sum_{k=1}^{K}p_k}\\le{T}}";
	s += getLatexNewLine();
	return s;
    }

    public String getDefaultLatexStringTab5() {
	return getLatexNewLine();
    }

    public static String getLatexNewLine() {
	return "\\\\";
    }

    public static String getExplanationTextClickOnALine() {
	return "\\textrm{Klicken Sie auf eine Zeile, um die detaillierte Berechnung anzuzeigen";
    }

    public void setDecimalsInAllTabs() {
	if (tab1Controller.areProductsInTable()) {
	    tab1Controller.refreshDecimals();
	}
	if (!tab2Controller.areTablesEmpty()) {
	    tab2Controller.refreshDecimals();
	}
	if (!tab4Controller.areTablesEmpty()) {
	    tab4Controller.refreshDecimals();
	}

    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main main) {
	this.main = main;
    }

    public Tab1Controller getTab1Controller() {
	return tab1Controller;
    }

    public Tab2Controller getTab2Controller() {
	return tab2Controller;
    }

    public Tab3Controller getTab3Controller() {
	return tab3Controller;
    }

    public Tab4Controller getTab4Controller() {
	return tab4Controller;
    }

    public Tab5Controller getTab5Controller() {
	return tab5Controller;
    }

    private void setScalefactor(double scale) {
	scalefactor += scale;
    }

    public double getScalefactor() {
	return scalefactor;
    }

    /**
     * @return the results
     */
    public Map<String, IBasicLotSchedulingAlgorithm> getResults() {
	return results;
    }

    /**
     * @param results
     *            the results to set
     */
    public void setResults(Map<String, IBasicLotSchedulingAlgorithm> results) {
	this.results = results;
    }

    public TabPane getTabPane() {
	return tabPane;

    }

    // public void handleZoomMenuBar() {
    // menuBar.setStyle(zoomer.getStyleFXFontSize());
    // }

    public void handleZoomEveryCSSStyle() {
	rootBorderPane.setStyle(zoomer.getStyleFXFontSize());
    }

    public void handleZoomSouthBarButtons(int fontsize) {
	btnZoomMinus.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.SEARCH_MINUS).size((double) fontsize));
	btnZoomPlus.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.SEARCH_PLUS).size((double) fontsize));
    }

}
