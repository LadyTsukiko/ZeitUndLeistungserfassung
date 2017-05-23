package gui;

import database.dbAccess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        dbAccess dba = new dbAccess();
        dba.createMitarbeiter("Sebastian", "Lange", "123");
 //       ResultSet res = dba.getMitarbeiter();



        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();




    }


    public static void main(String[] args) {
        launch(args);
    }
}
