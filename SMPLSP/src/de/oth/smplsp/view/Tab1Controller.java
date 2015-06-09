package de.oth.smplsp.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import de.oth.smplsp.algorithms.ClassicLotScheduling;
import de.oth.smplsp.algorithms.IBasicLotSchedulingAlgorithm;
import de.oth.smplsp.algorithms.MoreProductLotScheduling;
import de.oth.smplsp.error.CSVFileWrongNumberOfValuesInFileError;
import de.oth.smplsp.error.MinimalProductionCycleError;
import de.oth.smplsp.model.Product;
import de.oth.smplsp.persistence.CSVFile;
import de.oth.smplsp.util.Configuration;
import de.oth.smplsp.util.Decimals;

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

    private RootLayoutController root;

    private ObservableList<Product> productsList = FXCollections
	    .observableArrayList();

    private Configuration config = Configuration.getInstance();
    private Decimals decimals;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public Tab1Controller() {

    }

    /**
     * @return the productsList
     */
    public ObservableList<Product> getProductsList() {
	return productsList;
    }

    /**
     * @param productsList
     *            the productsList to set
     */
    public void setProductsList(ObservableList<Product> productsList) {
	this.productsList = productsList;
    }

    public boolean areProductsInTable() {
	return !productsTableView.getItems().isEmpty();
    }

    public void refreshDecimals() {
	int decimal = config.getDecimalPlaces();
	decimals.setDecimals(decimal);
	setColumnDecimals();
    }

    /**
     * Set the productList and show it in the table
     *
     * @param set
     *            theproductsList
     */
    public void setProductsListAndShowInTable(
	    ObservableList<Product> newProductsList) {
	refreshDecimals();
	// clear table and load new values from the file
	productsList.clear();
	productsList = newProductsList;

	// show loaded values in the view
	productsTableView.setItems(productsList);

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
	// customize the look of the panel
	customizeButtonBar();
	int decimal = config.getDecimalPlaces();
	decimals = new Decimals(decimal);
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

	customizeTable();

	// initially combine the observable product list with the table view
	productsTableView.setItems(productsList);
    }

    public void init(RootLayoutController rootLayoutController) {
	root = rootLayoutController;
    }

    /**
     * //TODO documentation!!
     */
    private void customizeButtonBar() {
	// remove default text of the buttons
	btnAddRow.setText("");
	btnRemoveRow.setText("");
	btnRemoveAll.setText("");
	btnLoad.setText("");
	btnSave.setText("");
	btnCalculate.setText("");

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
	btnAddRow.setTooltip(new Tooltip("Zeile hinzufügen"));
	btnRemoveRow.setTooltip(new Tooltip("Zeile löschen"));
	btnRemoveAll.setTooltip(new Tooltip("Tabelle leeren"));
	btnLoad.setTooltip(new Tooltip("Projekt laden"));
	btnSave.setTooltip(new Tooltip("Projekt speichern"));
	btnCalculate.setTooltip(new Tooltip("Berechnung starten"));

    }

    // private void fillTableTestData() {
    //
    // List<Product> products = LotSchedulingAlgorithmTester.getTestProducts();
    // productsList.addAll(products);
    //
    // productsTableView.setItems(productsList);
    //
    // }

    /**
     * Called when the user clicks the add row button. A new row is added to the
     * table directly after the current selected one
     */
    @FXML
    public void handleAddRow() {
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
    public void handleDeleteRow() {
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
	alert.getDialogPane().setStyle(root.getZoomer().getStyleFXFontSize());
	alert.showAndWait();
    }

    /**
     * Called when the user clicks on the delete all button. After a
     * Warning-Dialog the table will be cleared
     */
    @FXML
    public void handleDeleteAll() {
	// show dialog, with warning that all data will be deleted
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Tabelle löschen?");
	alert.setHeaderText("Wollen Sie alle Daten der Tabelle löschen?");
	alert.setContentText("OK bestätigt das Löschen, Cancel bricht den Vorgang ab.");
	alert.getDialogPane().setStyle(root.getZoomer().getStyleFXFontSize());
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
    public void handleLoad(ActionEvent e) throws IOException {
	if (!productsList.isEmpty()) {
	    // show dialog and ask the user if he wants to save the data first
	    showSaveDataBeforeLoadingNewFileAlert();
	}
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
		// setProductsListAndShowInTable(productsList);

		// set the text in the correct fontsize
		root.getZoomer().rescaleMainApplication();

	    } catch (IOException e1) {
		// show error dialog
		showErrorDialogFileNotImported(csvFile.getName(), "");
		// undo changes and show old values

	    } catch (CSVFileWrongNumberOfValuesInFileError e2) {
		// show error dialog with the correct errorLine
		showErrorDialogFileNotImported(csvFile.getName(),
			e2.getMessage());
		// undo changes and show old values
		// setProductsListAndShowInTable(productsListTmp);
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
	alert.getDialogPane().setStyle(root.getZoomer().getStyleFXFontSize());
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
	alert.getDialogPane().setStyle(root.getZoomer().getStyleFXFontSize());
	alert.showAndWait();
    }

    /**
     * save the actual data of the table into a new *.csv file
     */
    @FXML
    public void handleSave() {
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
    public void handleCalculate() {

	List<IBasicLotSchedulingAlgorithm> algorithms = new ArrayList<IBasicLotSchedulingAlgorithm>();

	if (productsList.isEmpty()) {
	    showProductListIsEmptyAlert();
	    // exit the calculation
	    return;
	} else if (productsList.size() == 1) {
	    showProductListIsToShortAlert();
	    // exit the calculation
	    return;
	}

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
	    Map<String, IBasicLotSchedulingAlgorithm> results = new HashMap();
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
	    root.setResults(results);
	    root.getTab2Controller().setData();
	    root.getTab4Controller().setData();
	    if (!RootLayoutController.DEBUG_MODE) {
		showInfoDialogCalculationFinished();
	    }
	    root.getZoomer().rescaleMainApplication();

	}
    }

    /**
     * Show an information dialog, that the calculation is finished
     */
    private void showInfoDialogCalculationFinished() {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle("Berechnung durchgeführt");
	alert.setHeaderText("Die Berechnung wurde erfolgreich durchgeführt.");
	alert.getDialogPane().setStyle(root.getZoomer().getStyleFXFontSize());
	alert.showAndWait();
    }

    private void showErrorMinimalProductionCycleAlert() {
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Fehler");
	alert.setHeaderText("Fehler in den Eingabedaten");
	alert.setContentText("Optimaler gemeinsamer Produktionszyklus ist kleiner als minimaler zulässiger Produktionszyklus! Bitte korrigieren Sie die Eingabedaten");
	alert.getDialogPane().setStyle(root.getZoomer().getStyleFXFontSize());
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
	alert.getDialogPane().setStyle(root.getZoomer().getStyleFXFontSize());
	alert.showAndWait();
    }

    private void showProductListIsEmptyAlert() {
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Ungültige Eingabe");
	alert.setHeaderText("Ihre Eingabe enthält keine Werte!");
	alert.setContentText("Geben Sie Werte in die Eingabetabelle ein, damit die Berechnung durchgeführt werden kann!");
	alert.getDialogPane().setStyle(root.getZoomer().getStyleFXFontSize());
	alert.showAndWait();
    }

    private void showProductListIsToShortAlert() {
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Ungültige Eingabe");
	alert.setHeaderText("Ihre Eingabe enthält zu wenige Werte!");
	alert.setContentText("Geben Sie weitere Datensätze in die Eingabetabelle ein, damit die Berechnung durchgeführt werden kann!");
	alert.getDialogPane().setStyle(root.getZoomer().getStyleFXFontSize());
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

	// making columns 2-6 editable
	setColumnDecimals();
    }

    private void setColumnDecimals() {
	// column2.setCellFactory(TextFieldTableCell
	// .<Product, Number> forTableColumn(new NumberStringConverter(
	// decimals.getDecimalFormat())));
	// column3.setCellFactory(TextFieldTableCell
	// .<Product, Number> forTableColumn(new NumberStringConverter(
	// decimals.getDecimalFormat())));
	// column4.setCellFactory(TextFieldTableCell
	// .<Product, Number> forTableColumn(new NumberStringConverter(
	// decimals.getDecimalFormat())));
	// column5.setCellFactory(TextFieldTableCell
	// .<Product, Number> forTableColumn(new NumberStringConverter(
	// decimals.getDecimalFormat())));
	// column6.setCellFactory(TextFieldTableCell
	// .<Product, Number> forTableColumn(new NumberStringConverter(
	// decimals.getDecimalFormat())));

	Callback<TableColumn<Product, Number>, TableCell<Product, Number>> cellFactory = new Callback<TableColumn<Product, Number>, TableCell<Product, Number>>() {
	    public TableCell call(TableColumn p) {
		return new EditingCell();
	    }
	};

	column1.setCellFactory(cellFactory);
	column2.setCellFactory(cellFactory);
	column3.setCellFactory(cellFactory);
	column4.setCellFactory(cellFactory);
	column5.setCellFactory(cellFactory);
	column6.setCellFactory(cellFactory);

    }

    /**
     * Zoom the table of to the actual fontsize. TableColumns will be zoomed and
     * also the table content is zoomed using an custom cell factory
     * 
     * @param fontsize
     */
    // TODO: WARNING: DO NOT DELETE, maybe we need this, if the zoom is not as
    // wished
    // public void handleZoomTable() {
    //
    // // fontsize for table header
    // column1.setStyle(root.getZoomer().getStyleFXFontSize());
    // column2.setStyle(root.getZoomer().getStyleFXFontSize());
    // column3.setStyle(root.getZoomer().getStyleFXFontSize());
    // column4.setStyle(root.getZoomer().getStyleFXFontSize());
    // column5.setStyle(root.getZoomer().getStyleFXFontSize());
    // column6.setStyle(root.getZoomer().getStyleFXFontSize());
    //
    // // fontsize for table content
    // column1.setCellFactory(getCustomCellFactory());
    // column2.setCellFactory(getCustomCellFactory());
    // column3.setCellFactory(getCustomCellFactory());
    // column4.setCellFactory(getCustomCellFactory());
    // column5.setCellFactory(getCustomCellFactory());
    // column6.setCellFactory(getCustomCellFactory());
    //
    // }

    /**
     * Resize the Iconfont of the toolbar buttons using the @fontsize
     * 
     * @param fontsize
     */
    public void handleZoomButtons(int fontsize) {
	// set icon fonts to the buttons
	btnAddRow.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.PLUS)
		.size((double) fontsize));
	btnRemoveRow.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.MINUS).size((double) fontsize));
	btnRemoveAll.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.TIMES).size((double) fontsize));
	btnLoad.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.FOLDER_OPEN_ALT).size((double) fontsize));
	btnSave.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.SAVE)
		.size((double) fontsize));
	btnCalculate.setGraphic(new Glyph("FontAwesome",
		FontAwesome.Glyph.PLAY_CIRCLE_ALT).size((double) fontsize));
    }

    /**
     * Defines a custom callback to set the content of each table value and
     * update its fontsize using the param @fontsize
     * 
     * @param fontsize
     * @return
     */
    // TODO: WARNING: DO NOT DELETE, maybe we need this, if the zoom is not as
    // wished
    // private Callback<TableColumn<Product, Number>, TableCell<Product,
    // Number>> getCustomCellFactory() {
    // return new Callback<TableColumn<Product, Number>, TableCell<Product,
    // Number>>() {
    //
    // @Override
    // public TableCell<Product, Number> call(
    // TableColumn<Product, Number> tableColumn) {
    // TableCell<Product, Number> cell = new TableCell<Product, Number>() {
    //
    // @Override
    // protected void updateItem(Number item, boolean empty) {
    // if (item != null) {
    //
    // if (item instanceof Integer) {
    // setText(Integer.toString((Integer) item));
    // } else if (item instanceof Double) {
    // setText(Double.toString((Double) item));
    // } else {
    // setText("N/A");
    // }
    //
    // setStyle(root.getZoomer().getStyleFXFontSize());
    //
    // }
    // }
    // };
    // return cell;
    // }
    //
    // };
    // }

    // // TODO: Nachkommastellen berechnung muss irgendwie in den
    // // Callback integriert werden!!
    // // TextFieldTableCell
    // // .<Product, Number> forTableColumn(new NumberStringConverter(
    // // decimals.getDecimalFormat()))
    class EditingCell extends TableCell<Product, Number> {

	private TextField textField;

	public EditingCell() {
	    setDecimals();
	}

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

	@Override
	public void cancelEdit() {
	    super.cancelEdit();

	    setGraphic(null);
	    setText((String) getItem().toString());
	}

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

	private void createTextField() {
	    textField = new TextField(getString());
	    textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()
		    * 2);

	    textField.focusedProperty().addListener(
		    new ChangeListener<Boolean>() {
			@Override
			public void changed(
				ObservableValue<? extends Boolean> arg0,
				Boolean arg1, Boolean arg2) {
			    if (!arg2) {
				commitEdit(Float.valueOf(textField.getText())
					.floatValue());
			    }
			}
		    });

	    textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent t) {
		    if (t.getCode() == KeyCode.ENTER) {
			commitEdit(Float.valueOf(textField.getText())
				.floatValue());
		    } else if (t.getCode() == KeyCode.ESCAPE) {
			cancelEdit();
		    } else if (t.getCode() == KeyCode.TAB) {
			commitEdit(Float.valueOf(textField.getText())
				.floatValue());
			TableColumn nextColumn = getNextColumn(!t.isShiftDown());
			if (nextColumn != null) {
			    getTableView().edit(getTableRow().getIndex(),
				    nextColumn);
			}

		    }
		}

	    });
	}

	private String getString() {
	    return getItem() == null ? "" : getItem().toString();
	}

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

	private List<TableColumn<Product, ?>> getLeaves(
		TableColumn<Product, ?> root) {
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

	// WARNING: DOES NOT WORK LIKE THIS!
	private void setDecimals() {
	    TableColumn<Product, Number> column = getTableColumn();
	    if (column != null) {
		column.setCellFactory(TextFieldTableCell
			.<Product, Number> forTableColumn(new NumberStringConverter(
				decimals.getDecimalFormat())));
	    }

	}

    }

}
