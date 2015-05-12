package de.oth.smplsp.view;

import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.NumberStringConverter;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import de.oth.smplsp.Main;
import de.oth.smplsp.algorithms.ClassicLotScheduling;
import de.oth.smplsp.algorithms.IBasicLotSchedulingAlgorithm;
import de.oth.smplsp.algorithms.MehrproduktLosgroessen;
import de.oth.smplsp.error.MinimalProductionCycleError;
import de.oth.smplsp.model.LotSchedulingResult;
import de.oth.smplsp.model.Product;
import de.oth.smpslp.test.LotSchedulingAlgorithmTester;

public class Tab1Controller {

    // References for the FXML layout
    @FXML
    private Button btnAddRow;
    @FXML
    private Button btnRemoveRow;
    @FXML
    private Button btnRemoveAll;
    @FXML
    private Button btnLoad;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCalculate;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TableColumn<Product, Number> column1;
    @FXML
    private TableColumn<Product, Number> column2;
    @FXML
    private TableColumn<Product, Number> column3;
    @FXML
    private TableColumn<Product, Number> column4;
    @FXML
    private TableColumn<Product, Number> column5;
    @FXML
    private TableColumn<Product, Number> column6;

    // Reference to the main application.
    private Main main;

    ObservableList<Product> productsList = FXCollections.observableArrayList();

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
	column1.setCellValueFactory(cellData -> cellData.getValue()
		.getKProperty());
	column2.setCellValueFactory(cellData -> cellData.getValue()
		.getDProperty());
	column3.setCellValueFactory(cellData -> cellData.getValue()
		.getPProperty());
	column4.setCellValueFactory(cellData -> cellData.getValue()
		.getTauProperty());
	column5.setCellValueFactory(cellData -> cellData.getValue()
		.getSProperty());
	column6.setCellValueFactory(cellData -> cellData.getValue()
		.getHProperty());

	fillTableTestData();

	customizeTable();

    }

    /**
     * //TODO documentation!!
     */
    private void customizeUI() {
	// remove default text of the buttons
	btnAddRow.setText("");
	btnRemoveRow.setText("");
	btnRemoveAll.setText("");

	// set icon fonts to the buttons
	btnAddRow.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.PLUS));
	btnRemoveRow.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.MINUS));
	btnRemoveAll.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.TIMES));
	btnLoad.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.FOLDER_OPEN_ALT));
	btnSave.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.SAVE));
	btnCalculate.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.PLAY_CIRCLE_ALT));

	// set tooltip text
	// TODO: Strings auslagern?
	btnAddRow.setTooltip(new Tooltip("Zeile hinzufügen"));
	btnRemoveRow.setTooltip(new Tooltip("Zeile löschen"));
	btnRemoveAll.setTooltip(new Tooltip("Tabelle leeren"));
	btnLoad.setTooltip(new Tooltip("Projekt laden"));
	btnSave.setTooltip(new Tooltip("Projekt speichern"));
	btnCalculate.setTooltip(new Tooltip("Berechnung starten"));

    }

    private void fillTableTestData() {

	productsList.add(new Product(1, 10.4, 128.5714, 2.0000, 190, 0.000689));
	productsList.add(new Product(2, 0.8, 32.43243, 2.0, 210, 0.010208));

	productsTableView.setItems(productsList);

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
     * Called when the user clicks the add row button. A new row is added to the
     * table directly after the current selected one
     */
    @FXML
    private void handleAddRow() {
	Product newProductRow = new Product(productsList.size() + 1);
	int selectedIndex = productsTableView.getSelectionModel()
		.getSelectedIndex();
	productsList.add(selectedIndex + 1, newProductRow);

	refactorIndexes(productsList);
    }

    /**
     * Called when the user clicks on the delete button. The current selected
     * row will be deleted from the table
     */
    @FXML
    private void handleDeleteRow() {
	int selectedIndex = productsTableView.getSelectionModel()
		.getSelectedIndex();
	if (selectedIndex >= 0) {
	    productsTableView.getItems().remove(selectedIndex);
	    refactorIndexes(productsList);
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
     * Called when the user clicks on the delete all button. After a
     * Warning-Dialog the table will be cleared
     */
    @FXML
    private void handleDeleteAll() {
	// show dialog, with warning that all data will be deleted
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Tabelle löschen?");
	alert.setHeaderText("Wollen Sie alle Daten der Tabelle löschen?");
	alert.setContentText("OK bestätigt das Löschen, Cancel bricht den Vorgang ab.");

	Optional<ButtonType> result = alert.showAndWait();
	if (result.get() == ButtonType.OK) {
	    // ... user chose OK
	    // delete the table data
	    productsList.clear();
	} else {
	    // ... user chose CANCEL or closed the dialog
	    // do nothing
	}
    }

    @FXML
    private void handleLoad() {
	// TODO
    }

    @FXML
    private void handleSave() {
	// TODO
    }

    @FXML
    private void handleCalculate() {

	// TODO: code below is just for test!!
	// run complete algorithm and print result in console

	List<IBasicLotSchedulingAlgorithm> algorithms = new ArrayList<IBasicLotSchedulingAlgorithm>();
	List<Product> products = LotSchedulingAlgorithmTester.getTestProducts();

	algorithms.add(new ClassicLotScheduling(products));
	algorithms.add(new MehrproduktLosgroessen(products));

	String ausgabe = "";
	for (IBasicLotSchedulingAlgorithm algorithm : algorithms) {
	    ausgabe += algorithm.getDescriptionToString();
	    LotSchedulingResult result;
	    try {
		result = algorithm.calculateInTotal();
		ausgabe += result.getTotalErgebnis();
		System.out.println(ausgabe);
	    } catch (MinimalProductionCycleError e) {
		// TODO Abfangen der Exception: Öffnen eines Error-Overlays mit
		// Anzeige der Fehlermeldung
		e.printStackTrace();
	    }

	    ausgabe = "";
	}
    }

    private void refactorIndexes(ObservableList<Product> products) {
	for (Product product : products) {
	    product.setK(products.indexOf(product) + 1);
	}
    }

    private void customizeTable() {

	// TODO: set tooltip for each column header field (but not just the
	// text); by now I just found a solution to set a tooltip of the column
	// fields, not of the column header row

	// tooltip for the whole table
	productsTableView.setTooltip(new Tooltip("k: Zeilenindex\n"
		+ "D: Nachfragerate\n" + "p: Produktionsrate\n"
		+ "τ: Rüstzeit\n" + "s: Rüstkostensatz\n"
		+ "h: Lagerkostensatz"));

	// making columns 2-6 editable
	column2.setCellFactory(TextFieldTableCell
		.<Product, Number> forTableColumn(new NumberStringConverter()));
	column3.setCellFactory(TextFieldTableCell
		.<Product, Number> forTableColumn(new NumberStringConverter()));
	column4.setCellFactory(TextFieldTableCell
		.<Product, Number> forTableColumn(new NumberStringConverter()));
	column5.setCellFactory(TextFieldTableCell
		.<Product, Number> forTableColumn(new NumberStringConverter()));
	column6.setCellFactory(TextFieldTableCell
		.<Product, Number> forTableColumn(new NumberStringConverter()));

    }
}
