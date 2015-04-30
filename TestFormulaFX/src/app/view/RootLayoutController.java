package app.view;

import javax.swing.Icon;
import javax.swing.JTextPane;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import app.MainApp;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

public class RootLayoutController {

	@FXML
	private TextFlow textFlow;

	@FXML
	private SwingNode swingNode;
	
	@FXML
	private BorderPane rootPane;
	
	@FXML
	private Label label;
	
	@FXML
	private Button buttonPlus;
	
	@FXML
	private Button buttonMinus;
	
	// global parameters for the font size
	private int fontsize = 20;

	// Reference to the main application.
	private MainApp mainApp;

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

		// show sample LaTeX
		showLatex();

	}

	public void showLatex() {
		// TeX in the center of the Border Pane
		// will be displayed in a ScrollPane

		String latex = " A_1 \\\\ " + "\\sqrt{2} \\\\ "
				+ "\\sqrt{ \\frac{-b \\pm \\sqrt {b^2-4ac}} {2a} } \\\\"
				+ "\\textrm{das ist unser Text der Erklärkomponente } \\\\"
				+ "noch \\\\" + "ein \\\\" + "paar \\\\"
				+ "Zeilen \\\\ im Mathemodus";

		TeXFormula tex = new TeXFormula(latex);

		// Icon icon = tex.createTeXIcon(TeXConstants.ALIGN_CENTER, 40);
		Icon icon = tex.createTeXIcon(TeXConstants.ALIGN_CENTER, fontsize);

		// generate a JTextPane that will be displayed in a SwingNode in JavaFX
		JTextPane pane = new JTextPane();
		pane.insertIcon(icon);
		swingNode.setContent(pane);
	}

	@FXML
	public void handleZoomIn() {
		fontsize++;

		// regenerate LaTeX image
		showLatex();
		
//		zoomTestSouthBar();
	}

	@FXML
	public void handleZoomOut() {
		fontsize--;

		// regenerate LaTeX image
		showLatex();
		
//		zoomTestSouthBar();
		
	}

	/**
	 * Sets the font size according of zoom factor on a touch based zoom event
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

		// Debugging Printouts
//		System.out.println(zoomFactorDouble);
//		System.out.println(zoomFactorInteger);

		// stop further propagation of the event
		event.consume();
	}
	
	/**
	 * Redraws the image with the LaTeX code based on the new fontsize
	 * @param event
	 */
	public void handleZoomFinished(ZoomEvent event) {

		// regenerate LaTeX image
		showLatex();

		// stop further propagation of the event
		event.consume();
		
//		zoomTestSouthBar();
	}
	
	/**
	 * Experimental Zoom Method
	 * !!!WARNING - does not do the zoom properly!!!
	 * @param content
	 */
	public void scalableZoom(Node content)
	  {
		
		double scaleValue = 4.0;
		Scale scaleTransform = new Scale(scaleValue , scaleValue, 0, 0);
		content.getTransforms().add(scaleTransform);
	  
	  }
	
	/**
	 * Test Zoom for the south bar
	 */
	public void zoomTestSouthBar() {
		buttonPlus.setFont(new Font(fontsize));
		buttonMinus.setFont(new Font(fontsize));
		label.setFont(new Font(fontsize));
		
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
