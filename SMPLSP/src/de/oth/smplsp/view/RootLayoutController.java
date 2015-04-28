package de.oth.smplsp.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import de.oth.smplsp.Main;

public class RootLayoutController {

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public BorderPane borderPane;

    @FXML
    public AnchorPane pane2;

    // Reference to the main application.
    private Main main;

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
	showInput();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main main) {
	this.main = main;
    }

    public void showInput() {

	FXMLLoader loader = new FXMLLoader();
	loader.setLocation(Main.class.getResource("view/BorderInput.fxml"));
	BorderPane inputOverview;
	try {
	    inputOverview = (BorderPane) loader.load();
	    borderPane.setCenter(inputOverview);

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	FXMLLoader loader2 = new FXMLLoader();
	loader2.setLocation(Main.class.getResource("view/Input.fxml"));
	AnchorPane inputOverview2;
	try {
	    inputOverview2 = (AnchorPane) loader2.load();
	    anchorPane.setBottomAnchor(anchorPane, 100.0);

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
