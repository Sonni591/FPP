package de.oth.smplsp.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import de.oth.smplsp.model.Product;

/**
 * Custom implementation of an EditingCell. There an TextFeld is used to display
 * Number values that are formated to the given decimals format. This class has
 * also an implementation to handle key-events (ESC, TAB, ENTER) performed on
 * the cell. The EditingCell is used in a Callback and therfor used in the input
 * table in Tab1.
 */
public class EditingCell extends TableCell<Product, Number> {

    private TextField textField;
    private Decimals decimals;

    /**
     * Constructor of the EditingCell
     * 
     * @param decimals
     */
    public EditingCell(Decimals decimals) {
	this.decimals = decimals;
    }

    /**
     * Method called when editing of a cell starts
     * 
     * @see javafx.scene.control.TableCell#startEdit()
     */
    @Override
    public void startEdit() {
	if (!isEmpty()) {
	    super.startEdit();
	    if (textField == null) {
		createTextField();
	    }

	    setGraphic(textField);
	    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	    // textField.selectAll();
	    Platform.runLater(new Runnable() {
		@Override
		public void run() {
		    textField.requestFocus();
		    textField.selectAll();
		}
	    });
	}
    }

    /**
     * Method is called when editing of s cell is canceled
     * 
     * @see javafx.scene.control.TableCell#cancelEdit()
     */
    @Override
    public void cancelEdit() {
	super.cancelEdit();
	setText((String) getItem().toString());
	setGraphic(null);
	setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    /**
     * Method is called when the item of the cell is updated
     * 
     * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
     */
    @Override
    public void updateItem(Number item, boolean empty) {
	super.updateItem(item, empty);

	if (empty) {
	    setText(null);
	    setGraphic(null);
	} else {
	    if (isEditing()) {
		if (textField != null) {
		    textField.setText(getString());
		}
		setGraphic(textField);
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	    } else {
		setText(getString());
		setContentDisplay(ContentDisplay.TEXT_ONLY);
	    }
	}
    }

    /**
     * creates a new TextField that can show Numbers formated in the given
     * decimal format
     */
    private void createTextField() {
	textField = new TextField(getString());
	textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

	textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	    @Override
	    public void changed(ObservableValue<? extends Boolean> arg0,
		    Boolean arg1, Boolean arg2) {
		if (!arg2) {
		    try {
			commitEdit(decimals.getDecimalFormat().parse(
				textField.getText()));
		    } catch (ParseException e) {
			// ignore parse exceptions
		    }
		}
	    }
	});

	textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
	    /**
	     * A custom implementation called with the onKeyPressed-Event that
	     * handles several key events. ENTER does set the current text (when
	     * the content is a number); ESC does cancel the edit; TAB or SHIFT
	     * + TAB commits the value to the cell and jumps between the cells
	     * 
	     * @see javafx.event.EventHandler#handle(javafx.event.Event)
	     */
	    @Override
	    public void handle(KeyEvent t) {
		if (t.getCode() == KeyCode.ENTER) {
		    try {
			commitEdit(decimals.getDecimalFormat().parse(
				textField.getText()));
		    } catch (ParseException e) {
			// ignore parse exceptions
		    }
		} else if (t.getCode() == KeyCode.ESCAPE) {
		    cancelEdit();
		} else if (t.getCode() == KeyCode.TAB) {
		    try {
			commitEdit(decimals.getDecimalFormat().parse(
				textField.getText()));
		    } catch (ParseException e) {
			// ignore parse exceptions
		    }
		    TableColumn nextColumn = getNextColumn(!t.isShiftDown());
		    if (nextColumn != null) {
			getTableView().edit(getTableRow().getIndex(),
				nextColumn);
		    }

		}
	    }

	});
    }

    /**
     * @return String of the cell
     */
    private String getString() {
	return getItem() == null ? "0" : decimals.getDecimalFormat()
		.format((Object) getItem()).toString();
    }

    /**
     * Returns the next supported (editing-enabled) column
     * 
     * @param forward
     * @return
     */
    private TableColumn<Product, ?> getNextColumn(boolean forward) {
	List<TableColumn<Product, ?>> columns = new ArrayList<>();
	for (TableColumn<Product, ?> column : getTableView().getColumns()) {
	    columns.addAll(getLeaves(column));
	}
	// There is no other column that supports editing.
	if (columns.size() < 2) {
	    return null;
	}
	int currentIndex = columns.indexOf(getTableColumn());
	int nextIndex = currentIndex;
	if (forward) {
	    nextIndex++;
	    if (nextIndex > columns.size() - 1) {
		nextIndex = 0;
	    }
	} else {
	    nextIndex--;
	    if (nextIndex < 0) {
		nextIndex = columns.size() - 1;
	    }
	}
	return columns.get(nextIndex);
    }

    /**
     * Returns a list with the editable Leaves of the table
     * 
     * @param root
     * @return
     */
    private List<TableColumn<Product, ?>> getLeaves(TableColumn<Product, ?> root) {
	List<TableColumn<Product, ?>> columns = new ArrayList<>();
	if (root.getColumns().isEmpty()) {
	    // We only want the leaves that are editable.
	    if (root.isEditable()) {
		columns.add(root);
	    }
	    return columns;
	} else {
	    for (TableColumn<Product, ?> column : root.getColumns()) {
		columns.addAll(getLeaves(column));
	    }
	    return columns;
	}
    }

}
