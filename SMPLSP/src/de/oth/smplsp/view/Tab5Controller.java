package de.oth.smplsp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import de.oth.smplsp.Main;

public class Tab5Controller {

    // References
    @FXML
    private Label label1;

    // Reference to the main application.
    private Main main;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public Tab5Controller() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main main) {
	this.main = main;
    }

}
