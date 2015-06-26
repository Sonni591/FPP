package de.oth.smplsp.view;

import java.awt.Color;
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

    // Examine OS version
    public static final String OS_NAME = System.getProperty("os.name");

    // reference to the BorderPane of the application
    @FXML
    private BorderPane rootBorderPane;

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

    // References all GUI elements
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
    private Menu menuZoom;
    @FXML
    private TabPane tabPane;
    @FXML
    private MenuBar menuBar;

    private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome"); //$NON-NLS-1$
    private Map<String, IBasicLotSchedulingAlgorithm> results;
    private int selectedTab = 0;
    private Zoomer zoomer;
    private Main main;

    public String latexString = ""; //$NON-NLS-1$

    /**
     * The constructor. The constructor is called before the initialize()
     * method. Creates a new instance of the @class Zoomer
     */
    public RootLayoutController() {
	zoomer = new Zoomer();
    }

    /**
     * @return the zoomer
     */
    public Zoomer getZoomer() {
	return zoomer;
    }

    /**
     * @return the swingNode (Explanation Component)
     */
    public SwingNode getSwingNode() {
	return swingNode;
    }

    /**
     * @param swingNode
     *            set the SwingNode (Explanation Component)
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
	// WARNING: Does not work properly on Mac OS X
	// and leads to an not responding-application
	if (getOSNameNotMacOSX()) {
	    initExplanationTabTextTab1();
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

    /**
     * initialize the explanation component and show a default Text in it
     */
    private void initExplanationTabTextTab1() {
	latexString = getDefaultLatexStringTab1();
	showExplanationComponent();
    }

    /**
     * Show content in the explanation component. Therefor the @param
     * latexString is used and using an TeXFormula is generated using the
     * JLatexMath this Texformula will be converted into in image and displayed
     * in a SwingNode
     */
    public void showExplanationComponent() {
	if (!getLatexString().equals("")) {
	    try {
		TeXFormula tex = new TeXFormula(latexString);

		Icon icon = tex.createTeXIcon(TeXConstants.ALIGN_CENTER,
			zoomer.getLatexFontSize());

		// generate a JTextPane that will be displayed in
		// a SwingNode in JavaFX
		JTextPane pane = new JTextPane();
		pane.setEditable(false);
		pane.insertIcon(icon);
		pane.setBackground(new Color(0, 0, 0, 0));
		pane.repaint();
		this.swingNode.setContent(pane);

	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * Set the latexString used in the explanation component
     * 
     * @param latexString
     */
    public void setLatexString(String latexString) {
	this.latexString = latexString;
    }

    /**
     * Call the zoom in method of the Zoomer
     */
    @FXML
    public void handleZoomIn() {
	zoomer.handleZoomIn();
    }

    /**
     * Call the zoom out method of the Zoomer
     */
    @FXML
    public void handleZoomOut() {
	zoomer.handleZoomOut();
    }

    /**
     * Sets the font size according of zoom factor on a touch based zoom event.
     * Method is called during the touch based zoom.
     * 
     * @param event
     */
    public void handleZoom(ZoomEvent event) {

	Double zoomFactorDouble = event.getZoomFactor();
	Integer zoomFactorInteger = zoomFactorDouble.intValue();

	Integer fontsize = zoomer.getFontsize();
	Integer latexFontsize = zoomer.getLatexFontSize();
	Integer chartFontsize = zoomer.getChartFontSize();

	if (zoomFactorInteger >= 1) { // zoom in
	    fontsize += zoomFactorInteger;
	    latexFontsize += zoomFactorInteger * 2;
	    chartFontsize += zoomFactorInteger * 2;
	} else { // zoom out
	    fontsize -= 1;
	    latexFontsize -= 2;
	    chartFontsize -= 2;
	}

	zoomer.setFontsize(fontsize);
	zoomer.setLatexFontSize(latexFontsize);
	zoomer.setChartFontSize(chartFontsize);

	// stop further propagation of the event
	event.consume();
    }

    /**
     * Redraws the image with the LaTeX code based on the new fontsize. Method
     * will be called when the touch based zoom is finished.
     * 
     * @param event
     */
    public void handleZoomFinished(ZoomEvent event) {

	zoomer.rescaleMainApplication();

	// stop further propagation of the event
	event.consume();

    }

    /**
     * Calls a method to open and load a file with input values and displays its
     * content in the Tab1. Method is called in the menu bar.
     */
    @FXML
    private void onActionFileOpen() {
	try {
	    tab1Controller.handleLoad(null);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Calls a method to open and save the current data in a file. Method is
     * called in the menu bar.
     */
    @FXML
    private void onActionFileSave() {
	tab1Controller.handleSave();
    }

    /**
     * Calls a method to run the calculation of the algorithms. Method is called
     * in the menu bar.
     */
    @FXML
    private void onActionCalculate() {
	tab1Controller.handleCalculate();
    }

    /**
     * Calls a method to import and show the available test data. Method is
     * called in the menu bar.
     */
    @FXML
    private void onActionTestData() {
	// when the input table is empty load the testdata and rescale the
	// application to ensure a correct zoom factor
	if (tab1Controller.getProductsList().isEmpty()) {
	    loadAndShowTestData();
	    zoomer.rescaleMainApplication();
	} else {
	    // when the table is not empty, show an confirmation dialog to
	    // overwrite the actual table data
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("Daten überschreiben?");
	    alert.setHeaderText("Wollen Sie alle Daten der Tabelle mit den Testdaten überschreiben?");
	    alert.setContentText("Das Laden der Testdaten überschreibt alle bisherigen Daten. Wollen Sie fortfahren?");
	    alert.getDialogPane().setStyle(zoomer.getStyleFXFontSize());
	    Optional<ButtonType> result = alert.showAndWait();
	    if (result.get() == ButtonType.OK) {
		// ... user chose OK - delete table and load test data
		loadAndShowTestData();
		zoomer.rescaleMainApplication();
	    } else {
		// ... user chose Cancel
		// do nothing
	    }
	}
    }

    /**
     * Calls a method to zoom into the application. Method is called in the menu
     * bar.
     */
    @FXML
    private void onActionZoomIn() {
	zoomer.handleZoomIn();
    }

    /**
     * Calls a method to zoom out of the application. Method is called in the
     * menu bar.
     */
    @FXML
    private void onActionEditZoomOut() {
	zoomer.handleZoomOut();
    }

    /**
     * Calls a method to reset the zoom of the application. Method is called in
     * the menu bar.
     */
    @FXML
    private void onActionEditResetZoom() {
	zoomer.resetZoomLevel();
    }

    /**
     * Shows the settings dialog of the application. Method is called in the
     * menu bar.
     */
    @FXML
    private void onActionFileSettings() {
	SettingsDialog dia = new SettingsDialog(this);
	dia.showAndWait();
    }

    /**
     * Exit the application. Method is called in the menu bar.
     */
    @FXML
    private void onActionFileExit() {
	Platform.exit();
    }

    /**
     * Add a row to the input table in Tab1. Method is called in the menu bar.
     */
    @FXML
    private void onActionEditAdd() {
	tab1Controller.handleAddRow();
    }

    /**
     * Remove a row to the input table in Tab1. Method is called in the menu
     * bar.
     */
    @FXML
    private void onActionEditDelete() {
	tab1Controller.handleDeleteRow();
    }

    /**
     * Clear the input table in Tab1. Method is called in the menu bar.
     */
    @FXML
    private void onActionEditDeleteAll() {
	tab1Controller.handleDeleteAll();
    }

    /**
     * Show a popup with an about information of the application. Method is
     * called in the menu bar.
     */
    @FXML
    private void onActionHelpAbout() {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle("");
	alert.setHeaderText(Messages.RootLayoutController_AboutDialog_NameAndVersion);
	alert.setContentText(Messages.RootLayoutController_AboutDialog_License);
	alert.getDialogPane().setStyle(zoomer.getStyleFXFontSize());
	alert.showAndWait();
    }

    /**
     * Method to examine if the selection of a tab from the tab bar has changed.
     * When that is the case (1) the "edit"-Menu is enabled/disabled; (2) the
     * status lable in the south-left corner is changed and also the content of
     * the explanation component is updated
     */
    @FXML
    private void onTabSelectionChanged() {
	setMenuEditDisable();
	setBottomLeftStatusLabel();
	// WARNING: Does not work properly on Mac OS X
	// and leads to an not responding-application
	if (getOSNameNotMacOSX()) {
	    setExplanationComponentDefaultText();
	}
    }

    /**
     * loads the test data, saves it in an Observable List and shows the content
     * in the input table in Tab1
     */
    private void loadAndShowTestData() {
	List<Product> productsList = LotSchedulingAlgorithmTester
		.getTestProducts();
	ObservableList<Product> ObservableProductsList = FXCollections
		.observableArrayList();
	ObservableProductsList.addAll(productsList);
	tab1Controller.setProductsListAndShowInTable(ObservableProductsList);
    }

    /**
     * Enable/Disable the "edit"-menu
     */
    private void setMenuEditDisable() {
	if (tabPane.getSelectionModel().getSelectedIndex() == 0) {
	    menuEdit.setDisable(false);
	} else {
	    menuEdit.setDisable(true);
	}
    }

    /**
     * set the south-left label in the application depending on the selected tab
     * in the tab bar
     */
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

    /**
     * Reset the content of the explanation component the default depending on
     * the selected tab
     */
    private void setExplanationComponentDefaultText() {
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
		break;
	    default:
		latexString = getLatexNewLine();
		break;
	    }
	    showExplanationComponent();
	}
    }

    /**
     * Returns a default String for the explanation component
     * 
     * @return String
     */
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

    /**
     * Returns a default String for the explanation component for Tab2
     * 
     * @return String
     */
    public String getDefaultLatexStringTab2() {
	return getExplanationTextClickOnALine();
    }

    /**
     * Returns a default String for the explanation component for Tab3
     * 
     * @return String
     */
    public String getDefaultLatexStringTab3() {
	return getLatexNewLine();
    }

    /**
     * Returns a default String for the explanation component for Tab4
     * 
     * @return String
     */
    public String getDefaultLatexStringTab4() {
	return getExplanationTextClickOnALine();
    }

    /**
     * Returns a default String for the explanation component for Tab5
     * 
     * @return String
     */
    public String getDefaultLatexStringTab5() {
	return getLatexNewLine();
    }

    /**
     * Returns a new line for the latex String
     * 
     * @return String
     */
    public static String getLatexNewLine() {
	return "\\\\";
    }

    /**
     * Returns a "click on a line for a detailed explanation" String
     * 
     * @return String
     */
    public static String getExplanationTextClickOnALine() {
	return "\\textrm{Klicken Sie auf eine Zeile, um die detaillierte Berechnung anzuzeigen";
    }

    /**
     * Set the decimals to the used setting in all tabs
     */
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

    /**
     * Returns a reference of the Tab1Controller
     * 
     * @return Tab1Controller
     */
    public Tab1Controller getTab1Controller() {
	return tab1Controller;
    }

    /**
     * Returns a reference of the Tab2Controller
     * 
     * @return Tab2Controller
     */
    public Tab2Controller getTab2Controller() {
	return tab2Controller;
    }

    /**
     * Returns a reference of the Tab3Controller
     * 
     * @return Tab3Controller
     */
    public Tab3Controller getTab3Controller() {
	return tab3Controller;
    }

    /**
     * Returns a reference of the Tab4Controller
     * 
     * @return Tab4Controller
     */
    public Tab4Controller getTab4Controller() {
	return tab4Controller;
    }

    /**
     * Returns a reference of the Tab5Controller
     * 
     * @return Tab5Controller
     */
    public Tab5Controller getTab5Controller() {
	return tab5Controller;
    }

    /**
     * Returns a Map with the results of the LotScheduling Algorithm
     * 
     * @return Map<String, IBasicLotSchedulingAlgorithm>
     */
    public Map<String, IBasicLotSchedulingAlgorithm> getResults() {
	return results;
    }

    /**
     * @param results
     *            Set a Map with the results of the LotScheduling Algorithm
     */
    public void setResults(Map<String, IBasicLotSchedulingAlgorithm> results) {
	this.results = results;
    }

    /**
     * @return tabPane
     */
    public TabPane getTabPane() {
	return tabPane;

    }

    /**
     * Modifies the CSS Style of the application. Used for changing the zoom
     * factor.
     */
    public void handleZoomEveryCSSStyle() {
	rootBorderPane.setStyle(zoomer.getStyleFXFontSize());
    }

    /**
     * Set the Graphic-Font of the zoom buttons with a new fontsize
     * 
     * @param fontsize
     */
    public void handleZoomSouthBarButtons(int fontsize) {
	btnZoomMinus.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.SEARCH_MINUS).size((double) fontsize));
	btnZoomPlus.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.SEARCH_PLUS).size((double) fontsize));
    }

    /**
     * Returns, if the Operating System is not Mac OS X Explanation: In the
     * current combination of OSX 10.10 and Java8_45 there is a bug in the used
     * formula components of the application. The formula component does use a
     * Icon to display the TeXFormula. The icon itself is displayed in a
     * JTextPane(). Working with the above combination of OSX and Java8 a second
     * call of 'new JTextPane()' causes an non-responding application. This
     * seems to be a non-fixed bug in the Java JRE, so this fix is implemented
     * for now.
     * 
     * @return false if operating system is Mac OS X; else true
     */
    public boolean getOSNameNotMacOSX() {
	if (OS_NAME.equals("Mac OS X")) {
	    return false;
	}
	return true;
    }

}
