package app.view;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JTextPane;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;

/**
 * The class is set as a controller class of the RootLayout.fxml. It handles all
 * events of the GUI and also contains the logic to display a formula in the GUI
 * and scale the formula component.
 *
 * @author Daniel Sonnleitner
 * @author Pilar Wagner
 */
public class RootLayoutController {

	/** global parameters for the font size */
	private int fontsize = 20;

	/** SwingNode in which the Formula will be displayed */
	@FXML
	private SwingNode swingNode;

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
		// show sample LaTeX Formula
		showLatexFormula();

	}

	/**
	 * Show a sample Latex Formula in a SwingNode
	 */
	public void showLatexFormula() {

		// get a sample String with Latex Code for the Formula
		String latexString = getSampleLatexFormula();

		// generate a TexFormula using the latexString
		TeXFormula tex = new TeXFormula(latexString);

		// generate a new Icon using the TexFormula and setting a fontsize
		Icon icon = tex.createTeXIcon(TeXConstants.ALIGN_CENTER, fontsize);

		// generate a JTextPane that will be displayed in a SwingNode in JavaFX
		JTextPane pane = getJTextPaneContainingFormula(icon);
		swingNode.setContent(pane); // set Formula in the SwingNode on the the GUI
									
	}

	/**
	 * Returns a String with a Sample Latex Formula
	 * 
	 * @return String
	 */
	private String getSampleLatexFormula() {
		String latex = "\\textrm{" // begin text mode
				+ "Dies ist ein Text in der Erklärkomponente im Latex-Textmodus"
				+ getLatexNewLine()
				+ "Mitternachtsformel schwarz-weiß:"
				+ getLatexNewLine()
				+ getQuadraticFormulaBlack()
				+ getLatexNewLine()
				+ "Mitternachtsformel farbig:"
				+ getLatexNewLine() + getQuadraticFormulaColored() +
				"}"; // end text mode
		return latex;
	}

	/**
	 * Returns the quadratic formula ("Mitternachtsformel") in Black
	 * 
	 * @return String
	 */
	private String getQuadraticFormulaBlack() {
		return "\\sqrt{ \\frac{-b \\pm \\sqrt {b^2-4ac}} {2a} }";
	}

	/**
	 * Returns the quadratic formula ("Mitternachtsformel") in Colors
	 * 
	 * @return String
	 */
	private String getQuadraticFormulaColored() {
		return "\\sqrt{ "
				+ "\\frac{" // Wurzel + Bruch
				+ "-"
				+ "\\textcolor{ blue } b "
				+ "\\pm" // plusminus
				+ " \\sqrt {" + "\\textcolor{ blue } b^2" + "-4"
				+ "\\textcolor{ red } a" + "\\textcolor{ oliveGreen } c"
				+ "}} " + "{2" + "\\textcolor{ red }a" + "} }";
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
	 * Returns a JTextPane that contains the generated Icon with the Formula
	 * 
	 * @param icon
	 * @return JTextPane
	 */
	private JTextPane getJTextPaneContainingFormula(Icon icon) {
		JTextPane pane = new JTextPane();
		pane.setEditable(false); // set pane non editable
		pane.insertIcon(icon); // insert the formula
		pane.setBackground(new Color(0, 0, 0, 0)); // set the background color
													// transparent
		// pane.repaint(); // if needed: repaint the icon 
		return pane;
	}

	/**
	 * Handle the zoom in; Method is called on a ButtonClick-Event defined in
	 * the Rootlayout.fxml
	 */
	@FXML
	public void handleZoomIn() {
		// increase the fontsize
		fontsize++;
		// regenerate LaTeX image
		showLatexFormula();
	}

	/**
	 * Handle the zoom out; Method is called on a ButtonClick-Event defined in
	 * the Rootlayout.fxml
	 */
	@FXML
	public void handleZoomOut() {
		// decrease the fontsize
		fontsize--;
		// regenerate LaTeX image
		showLatexFormula();
	}

}
