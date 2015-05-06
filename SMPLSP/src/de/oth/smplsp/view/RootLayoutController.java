package de.oth.smplsp.view;

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

    @FXML
    private AnchorPane tab2;

    @FXML
    private AnchorPane tab3;

    @FXML
    private AnchorPane tab4;

    @FXML
    private AnchorPane tab5;

    @FXML
    private Label lblZoom;

    @FXML
    private Button btnZoomPlus;

    @FXML
    private Button btnZoomMinus;

    @FXML
    private SwingNode swingNode;

    private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

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
	// customize the look of the Zoom area
	customizeUIZoom();

	showLatex();
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

	String latex = "\\sqrt{ \\frac{-b \\pm \\sqrt {b^2-4ac}} {2a} } \\\\"
		+ "\\textrm{Text im Schriftmodus } \\\\" + "Text im Mathemodus";

	TeXFormula tex = new TeXFormula(latex);

	Icon icon = tex.createTeXIcon(TeXConstants.ALIGN_CENTER, fontsize);

	// generate a JTextPane that will be displayed in a SwingNode in JavaFX
	JTextPane pane = new JTextPane();
	pane.setEditable(false);
	pane.insertIcon(icon);
	swingNode.setContent(pane);

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

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main main) {
	this.main = main;
    }

}
