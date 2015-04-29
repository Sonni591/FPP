package de.oth.smplsp.view;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import de.oth.smplsp.Main;

public class RootLayoutController {

    // References to all panes of the tab bar
    @FXML
    private AnchorPane tab1;
    @FXML
    private AnchorPane tab2;
    @FXML
    private AnchorPane tab3;
    @FXML
    private AnchorPane tab4;
    @FXML
    private AnchorPane tab5;

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
