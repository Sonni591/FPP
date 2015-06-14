package de.oth.smplsp;

import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import de.oth.smplsp.messages.Messages;
import de.oth.smplsp.view.RootLayoutController;
import de.oth.smplsp.view.Tab1Controller;
import de.oth.smplsp.view.Tab2Controller;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    public static RootLayoutController controller;

    // TODO: Kommunikation mit Tabs über RootLayoutController
    public Tab1Controller controller1;
    public Tab2Controller controller2;

    @Override
    public void start(Stage primaryStage) {
	this.primaryStage = primaryStage;
	this.primaryStage
		.setTitle("Statische Mehrproduktlosgrößenplanung, gemeinsamer Produktionszyklus");
	this.primaryStage
		.getIcons()
		.add(new Image(
			"/de/oth/smplsp/resources/images/Logo-OTH-128x128.png"));
	initRootLayout();

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
	try {

	    // Load root layout from fxml file
	    FXMLLoader loader = new FXMLLoader();

	    // Set properties for internationalization.
	    InputStream inputStream = Main.class.getResource(
		    Messages.BUNDLE_NAME_FXML).openStream();
	    ResourceBundle bundle = new PropertyResourceBundle(inputStream);
	    loader.setResources(bundle);

	    loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
	    rootLayout = (BorderPane) loader.load();

	    // Show the scene containing the root layout
	    Scene scene = new Scene(rootLayout);
	    primaryStage.setScene(scene);

	    // Give the controller access to the main app
	    // RootLayoutController controller = loader.getController();
	    controller = loader.getController();
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

    public void setButtonText() {
	controller2.setData();
    }
}