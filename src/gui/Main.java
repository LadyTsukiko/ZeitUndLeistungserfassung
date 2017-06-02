package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


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




    public static void main(String[] args) {
        launch(args);
    }
}