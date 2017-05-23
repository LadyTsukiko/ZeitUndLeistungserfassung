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
        //   dba.createMitarbeiter("Sebastian", "Lange", "123");
        ResultSet rs = dba.getMitarbeiter();
        System.out.println("Got a Set back");


        while(rs.next()){
            System.out.println("RS is empty");
            //Retrieve by column name
            int id  = rs.getInt("mitarbeiter_id");

            String first = rs.getString("name");
            String last = rs.getString("vorname");

            //Display values
            System.out.print("ID: " + id);
            System.out.print(", First: " + first);
            System.out.println(", Last: " + last);
        }



        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();




    }


    public static void main(String[] args) {
        launch(args);
    }
}