package de.oth.smplsp.view;

import javafx.fxml.FXML;
import de.oth.smplsp.Main;

//import de.oth.smplsp.util.jfreechartfx.ChartViewer;

public class Tab4Controller {

    // Reference to the main application.
    private Main main;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public Tab4Controller() {

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