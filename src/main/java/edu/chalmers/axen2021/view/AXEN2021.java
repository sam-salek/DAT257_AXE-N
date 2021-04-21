package edu.chalmers.axen2021.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main view class for the application.
 * @author Sam Salek
 * @author Oscar Arvidson
 */
public class AXEN2021 extends Application {

    /**
     * Runs the view configured in the 'start' method.
     * @param args
     */
    public static void run(String[] args) {
        launch(args);
    }

    /**
     * Applies and configures the main root view.
     * @param stage The main window.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent rootFXML = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/root.fxml"));
        Scene scene = new Scene(rootFXML, 1280, 720);

        stage.setScene(scene);
        stage.setFullScreen(true);
        String title = "AXE-N 2021";
        stage.setTitle(title);
        stage.show();
    }
}