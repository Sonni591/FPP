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
import javafx.stage.Stage;
import de.oth.smplsp.Main;
import de.oth.smplsp.messages.Messages;
import de.oth.smplsp.util.Configuration;

/**
 * @author Tobias Eichinger
 *
 */
public class SettingsDialogController {

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
    private boolean hasChangedDecimals = false;

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
    }

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
     * 
     */
    private void closeDialog() {
	Stage stage = (Stage) btnClose.getScene().getWindow();
	stage.close();
    }

    @FXML
    public void onActionSave() {
	config.saveSettingsToConfigFile();
	if (hasChangedDecimals) {
	    Main.controller.setDecimalsInAllTabs();
	}
	closeDialog();
    }

    @FXML
    public void onActionTxtDecimalPlacesChanged() {
	try {
	    if (txtDecimalPlaces.textProperty().getValue().length() > 0
		    && txtDecimalPlaces.textProperty().getValue() != null) {
		config.setDecimalPlaces(Integer.parseInt(txtDecimalPlaces
			.textProperty().getValue()));
		hasChangedDecimals = true;
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

    @FXML
    public void onActionCBBlackAndWhiteChanged() {
	config.setBlackAndWhiteMode(cbBlackAndWhite.selectedProperty()
		.getValue());
    }

}
