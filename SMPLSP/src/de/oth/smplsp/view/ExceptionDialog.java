/**
 *
 */
package de.oth.smplsp.view;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import de.oth.smplsp.messages.Messages;

/**
 * @author Tobias Eichinger
 *
 */
public class ExceptionDialog extends Alert {

    private RootLayoutController root;

    /**
     * @param alertType
     */
    public ExceptionDialog(Exception ex) {
	super(AlertType.ERROR);

	this.getDialogPane().setStyle(root.getZoomer().getStyleFXFontSize());

	this.setTitle(Messages.ExceptionDialog_Title);
	this.setHeaderText(Messages.ExceptionDialog_Header);
	this.setContentText(ex.getMessage());

	// Create expandable Exception.
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	ex.printStackTrace(pw);
	String exceptionText = sw.toString();

	Label label = new Label(Messages.ExceptionDialog_LabelOfTextArea);

	TextArea textArea = new TextArea(exceptionText);
	textArea.setEditable(false);
	textArea.setWrapText(true);

	textArea.setMaxWidth(Double.MAX_VALUE);
	textArea.setMaxHeight(Double.MAX_VALUE);
	GridPane.setVgrow(textArea, Priority.ALWAYS);
	GridPane.setHgrow(textArea, Priority.ALWAYS);

	GridPane expContent = new GridPane();
	expContent.setMaxWidth(Double.MAX_VALUE);
	expContent.add(label, 0, 0);
	expContent.add(textArea, 0, 1);

	// Set expandable Exception into the dialog pane.
	this.getDialogPane().setExpandableContent(expContent);

    }

    public void init(RootLayoutController rootLayoutController) {
	root = rootLayoutController;
    }
}
