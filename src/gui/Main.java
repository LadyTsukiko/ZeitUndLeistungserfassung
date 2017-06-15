package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Class only used to launch the JavaFX gui
 * @author      Martin Anker <ankem1 @ students.bfh.ch>
 * @version     0.9
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //This setting is to prevent the app from freezing when a combobox is opened under windows 10 with a touchscreen (Blame Microsoft)
        System.setProperty("glass.accessible.force", "false");

        //Set up and start the login window
        Parent root = FXMLLoader.load(getClass().getResource("popupLogin.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();


    }

    /**
     * Launches the application
     * @param args not used
     */
    public static void main(String[] args) {
        launch(args);
    }
}