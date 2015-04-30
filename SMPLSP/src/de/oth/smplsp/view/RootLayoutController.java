package de.oth.smplsp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

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

    private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

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

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main main) {
	this.main = main;
    }

}
