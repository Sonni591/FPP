package de.oth.smplsp.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import de.oth.smplsp.Main;
import de.oth.smplsp.messages.Messages;

public class SettingsDialog extends Stage implements Initializable {

    public SettingsDialog() {
	setTitle(Messages.SettingsDialog_Title);

	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
		"SettingsDialog.fxml")); //$NON-NLS-1$

	try {
	    InputStream inputStream = Main.class.getResource(
		    Messages.BUNDLE_NAME_FXML).openStream(); //$NON-NLS-1$
	    ResourceBundle bundle = new PropertyResourceBundle(inputStream);
	    fxmlLoader.setResources(bundle);
	    setScene(new Scene((Parent) fxmlLoader.load()));
	} catch (IOException e1) {
	    e1.printStackTrace();
	}
    }

    /*
     * Called when FXML file is load()ed (via FXMLLoader.load()). It will
     * execute before the form is shown.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
	// we don't need this, because we use the initialize method in the
	// controller class
    }
}
