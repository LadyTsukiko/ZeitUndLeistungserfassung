package gui;

import database.PasswordManager;
import database.dbAccess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        dbAccess dba = new dbAccess();
        //dba.createMitarbeiter("Muster", "Hans", "123");
        //dba.updateMitarbeiter(14, "Meier", "Fritz");
        //  List rl = dba.getMitarbeiter();

        //dba.createProjekt("125-A-20",2);
        //dba.updateProjekt(2,"125-A-21",2);
        //dba.createZeiterfassung(14,1,1,"20170528", "150000");
        //List rl1 = dba.getZeiterfassungByMitarbeiter(12);
        //List rl2 = dba.getTotalTimeByProjekt(1);

        //System.out.println(new PasswordManager().checkCredentials(15,"123"));




    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();




    }




    public static void main(String[] args) {
        launch(args);
    }
}