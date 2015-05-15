package de.oth.smplsp.view;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.Icon;
import javax.swing.JTextPane;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import de.oth.smplsp.Main;

public class RootLayoutController {

    // References to all panes of the tab bar
    @FXML
    private AnchorPane tab1;

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
    private Label lblZoom;
    @FXML
    private Button btnZoomPlus;
    @FXML
    private Button btnZoomMinus;
    @FXML
    private SwingNode swingNode;

    private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

    private String latexString = "";

    // global parameters for the font size
    private int fontsize = 20;

    // Reference to the main application.
    private Main main;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public RootLayoutController() {

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

	tab1Controller.setController2(tab2Controller);

	// customize the look of the Zoom area
	customizeUIZoom();
    }

    /**
     * decorates the buttons of the zoom area with an corresponding icon font
     */
    private void customizeUIZoom() {
	btnZoomMinus.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.SEARCH_MINUS));
	btnZoomPlus.setGraphic(fontAwesome
		.create(FontAwesome.Glyph.SEARCH_PLUS));
    }

    public void showLatex() {

	TeXFormula tex = new TeXFormula(latexString);

	Icon icon = tex.createTeXIcon(TeXConstants.ALIGN_CENTER, fontsize);

	// generate a JTextPane that will be displayed in a SwingNode in JavaFX
	JTextPane pane = new JTextPane();
	pane.setEditable(false);
	pane.insertIcon(icon);
	pane.repaint();

	swingNode.setContent(pane);

    }

    public void setLatexString(String latexString) {
	this.latexString = latexString;
    }

    @FXML
    public void handleZoomIn() {
	fontsize++;

	// regenerate LaTeX image
	showLatex();
    }

    @FXML
    public void handleZoomOut() {
	fontsize--;

	// regenerate LaTeX image
	showLatex();
    }

    /**
     * Sets the font size according of zoom factor on a touch based zoom event
     * 
     * @param event
     */
    public void handleZoom(ZoomEvent event) {

	Double zoomFactorDouble = event.getZoomFactor();
	Integer zoomFactorInteger = zoomFactorDouble.intValue();

	if (zoomFactorInteger >= 1) { // zoom in
	    fontsize += zoomFactorInteger;
	} else { // zoom out
	    fontsize -= 1;
	}

	// stop further propagation of the event
	event.consume();
    }

    /**
     * Redraws the image with the LaTeX code based on the new fontsize
     * 
     * @param event
     */
    public void handleZoomFinished(ZoomEvent event) {

	// regenerate LaTeX image
	showLatex();

	// stop further propagation of the event
	event.consume();

    }

    @FXML
    private void onActionFileOpen() {
	System.out.println("Datei->Öffnen");
    }

    @FXML
    private void onActionFileSave() {
	System.out.println("Datei->Speichern");
    }

    @FXML
    private void onActionFileSettings() {
	System.out.println("Datei->Einstellungen");
    }

    @FXML
    private void onActionFileExit() {
	Platform.exit();
    }

    @FXML
    private void onActionEditAdd() {
	System.out.println("Bearbeiten->Hinzufügen");
    }

    @FXML
    private void onActionEditDelete() {
	System.out.println("Bearbeiten->Entfernen");
    }

    @FXML
    private void onActionHelpAbout() {
	System.out.println("Hilfe->Über");
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

}
