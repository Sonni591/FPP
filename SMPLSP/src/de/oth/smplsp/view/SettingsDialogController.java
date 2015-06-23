/**
 * 
 */
package de.oth.smplsp.view;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import de.oth.smplsp.messages.Messages;
import de.oth.smplsp.util.Configuration;
import de.oth.smplsp.zoom.Zoomer;

/**
 * @author Tobias Eichinger
 *
 */
public class SettingsDialogController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnSave;

    @FXML
    private Label lblDecimalPlaces;

    @FXML
    private TextField txtDecimalPlaces;

    @FXML
    private CheckBox cbBlackAndWhite;

    private Configuration config;

    private Zoomer zoomer;

    public SettingsDialogController() {
	config = Configuration.getInstance();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
	txtDecimalPlaces.textProperty().setValue(
		Integer.toString(config.getDecimalPlaces()));
	cbBlackAndWhite.setSelected(config.getBlackAndWhiteMode());
	zoomer = new Zoomer();
	zoomer.init(this);
	zoomer.rescaleSettingsDialog();
    }

    /**
     * Method called when the settings dialog is closed. When the configuration
     * has changed show a popup to save the changes
     */
    @FXML
    public void onActionClose() {
	if (config.hasChanged()) {
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle(Messages.SettingsDialogController_CloseDialog_Title);
	    alert.setHeaderText(Messages.SettingsDialogController_CloseDialog_Header);
	    alert.setContentText(Messages.SettingsDialogController_CloseDialog_Content);

	    ButtonType btnTSave = new ButtonType(
		    Messages.SettingsDialogController_SaveButton);
	    ButtonType btnTClose = new ButtonType(
		    Messages.SettingsDialogController_CloseButton);

	    alert.getButtonTypes().setAll(btnTSave, btnTClose);

	    Optional<ButtonType> result = alert.showAndWait();
	    if (result.get() == btnTSave) {
		config.saveSettingsToConfigFile();
	    } else {
		config.loadSettings();
	    }
	}
	closeDialog();
    }

    /**
     * close the settings dialog
     */
    private void closeDialog() {
	Stage stage = (Stage) btnClose.getScene().getWindow();
	stage.close();
    }

    /**
     * save the settings
     */
    @FXML
    public void onActionSave() {
	config.saveSettingsToConfigFile();
	closeDialog();
    }

    /**
     * save the decimals to the config file
     */
    @FXML
    public void onActionTxtDecimalPlacesChanged() {
	try {
	    if (txtDecimalPlaces.textProperty().getValue().length() > 0
		    && txtDecimalPlaces.textProperty().getValue() != null) {
		config.setDecimalPlaces(Integer.parseInt(txtDecimalPlaces
			.textProperty().getValue()));
	    }
	} catch (NumberFormatException e) {
	    txtDecimalPlaces.textProperty().setValue(
		    Integer.toString(config.getDecimalPlaces()));
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle(Messages.SettingsDialogController_DialogNoNumber_Title);
	    alert.setHeaderText(Messages.SettingsDialogController_DialogNoNumber_Header);
	    alert.setContentText(Messages.SettingsDialogController_DialogNoNumber_Content);

	    alert.showAndWait();

	}

    }

    /**
     * save the color mode to the config file
     */
    @FXML
    public void onActionCBBlackAndWhiteChanged() {
	config.setBlackAndWhiteMode(cbBlackAndWhite.selectedProperty()
		.getValue());
    }

    /**
     * handle the zoom factor of the settings dialog
     */
    public void handleZoomCSSStyle() {
	borderPane.setStyle(zoomer.getStyleFXFontSize());
    }

}
