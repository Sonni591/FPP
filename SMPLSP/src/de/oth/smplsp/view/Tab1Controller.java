package de.oth.smplsp.view;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import de.oth.smplsp.Main;
import de.oth.smplsp.model.InputData;

public class Tab1Controller {

    // References
    @FXML
    private Button btnAddLine;
    @FXML
    private Button btnRemoveLine;
    @FXML
    private Button btnRemoveAll;
    @FXML
    private TableView<InputData> tableInputData;

    @FXML
    private TableColumn<InputData, Number> column1;
    @FXML
    private TableColumn<InputData, Number> column2;
    @FXML
    private TableColumn<InputData, Number> column3;
    @FXML
    private TableColumn<InputData, Number> column4;
    @FXML
    private TableColumn<InputData, Number> column5;
    @FXML
    private TableColumn<InputData, Number> column6;

    // Reference to the main application.
    private Main main;

    // Test Liste mit Daten
    ObservableList<InputData> testDataList = FXCollections
	    .observableArrayList();

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public Tab1Controller() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
	// customize the look of the panel
	customizeUI();
	column1.setCellValueFactory(cellData -> cellData.getValue().kProperty());
	column2.setCellValueFactory(cellData -> cellData.getValue().dProperty());
	column3.setCellValueFactory(cellData -> cellData.getValue().pProperty());
	column4.setCellValueFactory(cellData -> cellData.getValue()
		.tauProperty());
	column5.setCellValueFactory(cellData -> cellData.getValue().sProperty());
	column6.setCellValueFactory(cellData -> cellData.getValue().hProperty());

	fillTableTestData();

    }

    /**
     * //TODO documentation!!
     */
    private void customizeUI() {
	// remove default text of the buttons
	btnAddLine.setText("");
	btnRemoveLine.setText("");
	btnRemoveAll.setText("");

	// set icon fonts to the buttons
	btnAddLine.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.PLUS));
	btnRemoveLine.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.MINUS));
	btnRemoveAll.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.TIMES));

	// set tooltip text
	// TODO: Strings auslagern?
	btnAddLine.setTooltip(new Tooltip("Zeile hinzufügen"));
	btnRemoveLine.setTooltip(new Tooltip("Zeile löschen"));
	btnRemoveAll.setTooltip(new Tooltip("Tabelle leeren"));

    }

    private void fillTableTestData() {

	testDataList
		.add(new InputData(1, 10.4, 128.5714, 2.0000, 190, 0.000689));
	testDataList.add(new InputData(2, 0.8, 32.43243, 2.0, 210, 0.010208));

	// TODO: BUG - Elemente sind da, werden aber nicht angezeigt ?!?
	tableInputData.setItems(testDataList);

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
     * Called when the user clicks the add line button
     */
    @FXML
    private void handleAddLine() {
	InputData newLineData = new InputData();
	testDataList.add(newLineData);
    }

    /**
     * Called when the user clicks on the delete button
     */
    @FXML
    private void handleDeleteLine() {
	int selectedIndex = tableInputData.getSelectionModel()
		.getSelectedIndex();
	if (selectedIndex >= 0) {
	    tableInputData.getItems().remove(selectedIndex);
	} else {
	    // Nothing selected.
	    Alert alert = new Alert(AlertType.INFORMATION);
	    // alert.initOwner(main.getPrimaryStage());
	    alert.setTitle("Keine Auswahl");
	    alert.setHeaderText("Es wurde keine Zeile markiert");
	    alert.setContentText("Bitte markieren Sie zum Löschen eine Zeile.");

	    alert.showAndWait();
	}
    }

    /**
     * Called when the user clicks on the delete all button
     */
    @FXML
    private void handleDeleteAll() {
	// TODO
	// show dialog, with warning that all data will be deleted
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Tabelle löschen?");
	alert.setHeaderText("Wollen Sie alle Daten der Tabelle löschen?");
	alert.setContentText("OK bestätigt das Löschen, Cancel bricht den Vorgang ab.");

	Optional<ButtonType> result = alert.showAndWait();
	if (result.get() == ButtonType.OK) {
	    // ... user chose OK
	    // delete the table data
	    testDataList.clear();
	} else {
	    // ... user chose CANCEL or closed the dialog
	    // do nothing
	}
    }

}
