package de.oth.smplsp;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import de.oth.smplsp.view.RootLayoutController;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
	this.primaryStage = primaryStage;
	this.primaryStage
		.setTitle("statische Mehrproduktlosgrößenplanung, gemeinsamer Produktionszyklus");

	initRootLayout();

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
	try {
	    // Load root layout from fxml file
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
	    rootLayout = (BorderPane) loader.load();

	    // Show the scene containing the root layout
	    Scene scene = new Scene(rootLayout);
	    primaryStage.setScene(scene);

	    // Give the controller access to the main app
	    RootLayoutController controller = loader.getController();
	    controller.setMainApp(this);

	    primaryStage.show();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Returns the main stage.
     * 
     * @return
     */
    public Stage getPrimaryStage() {
	return primaryStage;
    }

    public static void main(String[] args) {
	launch(args);
    }
}