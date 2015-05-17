package de.oth.smplsp.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.NumberStringConverter;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import de.oth.smplsp.Main;
import de.oth.smplsp.algorithms.ClassicLotScheduling;
import de.oth.smplsp.algorithms.IBasicLotSchedulingAlgorithm;
import de.oth.smplsp.algorithms.MoreProductLotScheduling;
import de.oth.smplsp.error.CSVFileWrongNumberOfValuesInFileError;
import de.oth.smplsp.error.MinimalProductionCycleError;
import de.oth.smplsp.model.Product;
import de.oth.smplsp.persistence.CSVFile;
import de.oth.smplsp.test.LotSchedulingAlgorithmTester;
import de.oth.smplsp.util.Decimals;

public class Tab1Controller {
    // implements Initializable {

    public static Map<String, IBasicLotSchedulingAlgorithm> results = new HashMap();
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
    private RootLayoutController root;

    private Tab2Controller controller2;
    private Tab4Controller controller4;

    private ObservableList<Product> productsList = FXCollections
	    .observableArrayList();

    private Decimals decimals;

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

    public void init(RootLayoutController rootLayoutController) {
	root = rootLayoutController;
    }

    // @Override
    // public void initialize(URL arg0, ResourceBundle arg1) {
    // customizeUI();
    // column1.setCellValueFactory(cellData -> cellData.getValue()
    // .getKProperty());
    // column2.setCellValueFactory(cellData -> cellData.getValue()
    // .getDProperty());
    // column3.setCellValueFactory(cellData -> cellData.getValue()
    // .getPProperty());
    // column4.setCellValueFactory(cellData -> cellData.getValue()
    // .getTauProperty());
    // column5.setCellValueFactory(cellData -> cellData.getValue()
    // .getSProperty());
    // column6.setCellValueFactory(cellData -> cellData.getValue()
    // .getHProperty());
    //
    // btnLoad.setOnAction(new EventHandler<ActionEvent>() {
    //
    // @Override
    // public void handle(ActionEvent event) {
    //
    // Tab2Controller controller;
    // try {
    // controller = Tab2Controller.class.newInstance();
    // } catch (InstantiationException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // } catch (IllegalAccessException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // // controller.setData();
    // // main.setButtonText();
    // }
    // });
    // ;
    //
    // fillTableTestData();
    //
    // customizeTable();
    //
    // }

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

	List<Product> products = LotSchedulingAlgorithmTester.getTestProducts();
	productsList.addAll(products);

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

    public void setController2(Tab2Controller controller2) {
	this.controller2 = controller2;
    }

    public void setController4(Tab4Controller controller4) {
	this.controller4 = controller4;
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
	    showErrorDialogNoRowSelected();

	}
    }

    /**
     * Show an error dialog, that no row was is selected
     */
    private void showErrorDialogNoRowSelected() {
	Alert alert = new Alert(AlertType.INFORMATION);
	// alert.initOwner(main.getPrimaryStage());
	alert.setTitle("Keine Auswahl");
	alert.setHeaderText("Es wurde keine Zeile markiert");
	alert.setContentText("Bitte markieren Sie zum Löschen eine Zeile.");
	alert.showAndWait();
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

    /**
     * First ask the user if he wants so save the old data. Then show a dialog
     * to select an input file and parse the data into the table. If there is a
     * problem, an error alert will be shown.
     * 
     * @param e
     * @throws IOException
     */
    @FXML
    private void handleLoad(ActionEvent e) throws IOException {
	// show dialog and ask the user if he wants to save the data first
	showSaveDataBeforeLoadingNewFileAlert();

	FileChooser fileChooser = new FileChooser();
	fileChooser.setTitle("Eingabedaten laden");
	fileChooser.getExtensionFilters().addAll(
		new ExtensionFilter("CSV-Dateien", "*.csv"));
	File selectedFile = fileChooser.showOpenDialog(null);

	if (selectedFile != null) {
	    // temperary save old values of the view table
	    ObservableList<Product> productsListTmp = FXCollections
		    .observableArrayList();
	    productsListTmp.addAll(productsList);

	    CSVFile csvFile = new CSVFile(selectedFile);
	    try {
		// clear table and load new values from the file
		productsList.clear();
		productsList = (ObservableList<Product>) csvFile
			.loadValuesAsProduct();

		// show loaded values in the view
		productsTableView.setItems(productsList);
	    } catch (IOException e1) {
		// show error dialog
		showErrorDialogFileNotImported(csvFile.getName(), "");
		// undo changes and show old values
		productsList.clear();
		productsList.addAll(productsListTmp);
		// show loaded values in the view
		productsTableView.setItems(productsList);
	    } catch (CSVFileWrongNumberOfValuesInFileError e2) {
		// show error dialog with the correct errorLine
		showErrorDialogFileNotImported(csvFile.getName(),
			e2.getMessage());
		// undo changes and show old values
		productsList.clear();
		productsList.addAll(productsListTmp);
		// show loaded values in the view
		productsTableView.setItems(productsList);
	    }
	}
    }

    /**
     * show dialog with Yes/No option to save the Data before loading a new file
     */
    private void showSaveDataBeforeLoadingNewFileAlert() {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Daten speichern?");
	alert.setHeaderText("Wollen Sie alle Daten der Tabelle vorher sichern?");
	alert.setContentText("Das Laden einer Datei überschreibt alle bisherigen Daten. Wollen Sie die aktuellen Daten nun sichern?");
	alert.getButtonTypes().remove(ButtonType.OK);
	alert.getButtonTypes().remove(ButtonType.CANCEL);
	ButtonType buttonTypeJa = new ButtonType("Ja");
	ButtonType buttonTypeNein = new ButtonType("Nein");
	alert.getButtonTypes().add(buttonTypeJa);
	alert.getButtonTypes().add(buttonTypeNein);
	Optional<ButtonType> result = alert.showAndWait();
	if (result.get() == buttonTypeJa) {
	    // ... user chose Yes - save the actual data
	    handleSave();
	} else if (result.get() == buttonTypeNein) {
	    // ... user chose No - do not save the data
	    // do nothing
	} else {
	    // unwanted returncode
	}
    }

    /**
     * Show a dialog that the file @pathname could not be parsed into the table
     * 
     * @param pathname
     */
    private void showErrorDialogFileNotImported(String pathname,
	    String errorLine) {
	// show error dialog
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Fehler");
	alert.setHeaderText("Fehler beim Laden der Datei");
	if (errorLine != "") {
	    alert.setContentText("Die Datei "
		    + pathname
		    + " konnte nicht geladen werden. \nÜberprüfen Sie die Zeile "
		    + errorLine
		    + ". \nDie vorhergehenden Daten werden wieder hergestellt.");
	} else {
	    alert.setContentText("Die Datei "
		    + pathname
		    + " konnte nicht geladen werden. \nDie vorhergehenden Daten werden wieder hergestellt.");
	}
	alert.showAndWait();
    }

    /**
     * save the actual data of the table into a new *.csv file
     */
    @FXML
    private void handleSave() {
	FileChooser fileChooser = new FileChooser();
	fileChooser.setTitle("Eingabedaten speichern");
	String defaultFileName = "smplsp_data.csv";
	fileChooser.setInitialFileName(defaultFileName);
	fileChooser.getExtensionFilters().addAll(
		new ExtensionFilter("CSV-Dateien", "*.csv"));
	File savedFile = fileChooser.showSaveDialog(null);

	if (savedFile != null) {
	    try {
		CSVFile csvFile = new CSVFile(savedFile);
		csvFile.saveValuesFromProduct(productsList);
	    } catch (FileNotFoundException e) {
		// File not found
		System.out.println(getClass().toString() + "handleSave()"
			+ "selected File not found");
		e.printStackTrace();
	    } catch (IOException e) {
		// couldn't save products to file
		System.out.println(getClass().toString() + "handleSave()"
			+ "couldn't save products to file");
		e.printStackTrace();
	    }
	}
    }

    /**
     * Execute the calculation of the algorithms First Algorithm: Classic Lot
     * Scheduling Second Algorithm: More Product Scheduling
     */
    @FXML
    private void handleCalculate() {

	List<IBasicLotSchedulingAlgorithm> algorithms = new ArrayList<IBasicLotSchedulingAlgorithm>();

	// get real products
	ObservableList<Product> productsClassic = productsTableView.getItems();
	ObservableList<Product> productsMehrprodukt = FXCollections
		.observableArrayList();
	cloneProductValues(productsClassic, productsMehrprodukt);

	boolean hasEmptyFields = false;
	for (Product product : productsClassic) {
	    if (hasEmptyFields(product)) {
		hasEmptyFields = true;
	    }
	}
	if (hasEmptyFields) {
	    showProductHasEmptyValuesAlert();
	} else {
	    algorithms.add(new ClassicLotScheduling(productsClassic));
	    algorithms.add(new MoreProductLotScheduling(productsMehrprodukt));
	    for (IBasicLotSchedulingAlgorithm algorithm : algorithms) {
		try {
		    algorithm.calculateInTotal();
		} catch (MinimalProductionCycleError e) {
		    showErrorMinimalProductionCycleAlert();
		    e.printStackTrace();
		}
		results.put(algorithm.getClass().toString(), algorithm);
	    }
	    controller2.setData();
	    controller4.setData();
	}
    }

    private void showErrorMinimalProductionCycleAlert() {
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Fehler");
	alert.setHeaderText("Fehler in den Eingabedaten");
	alert.setContentText("Optimaler gemeinsamer Produktionszyklus ist kleiner als minimaler zulässiger Produktionszyklus! Bitte korrigieren Sie die Eingabedaten");
	alert.showAndWait();
    }

    public void cloneProductValues(ObservableList<Product> listFrom,
	    ObservableList<Product> listTo) {
	for (Product product : listFrom) {
	    Product productClone = product.clone();
	    listTo.add(productClone);
	}
    }

    private void showProductHasEmptyValuesAlert() {
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Ungültige Eingabe");
	alert.setHeaderText("Ihre Eingabe enthält ungültige Werte!");
	alert.setContentText("Ihre Eingabe enthält Parameter mit Wert 0. Um den Algorithmus auszuführen ergänzen Sie hierfür Werte > 0!");
	alert.showAndWait();
    }

    private boolean hasEmptyFields(Product product) {
	if (product.getD() == 0 || product.getH() == 0 || product.getP() == 0
		|| product.getS() == 0 || product.getTau() == 0) {
	    return true;
	}
	return false;
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

	// create the decimal formatter
	decimals = new Decimals(5);

	// making columns 2-6 editable
	column2.setCellFactory(TextFieldTableCell
		.<Product, Number> forTableColumn(new NumberStringConverter(
			decimals.getDecimalFormat())));
	column3.setCellFactory(TextFieldTableCell
		.<Product, Number> forTableColumn(new NumberStringConverter(
			decimals.getDecimalFormat())));
	column4.setCellFactory(TextFieldTableCell
		.<Product, Number> forTableColumn(new NumberStringConverter(
			decimals.getDecimalFormat())));
	column5.setCellFactory(TextFieldTableCell
		.<Product, Number> forTableColumn(new NumberStringConverter(
			decimals.getDecimalFormat())));
	column6.setCellFactory(TextFieldTableCell
		.<Product, Number> forTableColumn(new NumberStringConverter(
			decimals.getDecimalFormat())));

    }

}
