package gui;

import database.PasswordManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the Login window at program start
 * @author      Martin Anker <ankem1 @ students.bfh.ch>
 * @version     0.9
 */
public class LoginController {


    @FXML private TextField popupLogin_id;
    @FXML private PasswordField popupLogin_pw;
    @FXML private Label popupLogin_credLabel;

    //Skips Login if true, login requires level 1, login with ID: 16, pw: admin
    private final static boolean debug = false;

    /**
     * handles the pressing of the login button
     * @throws IOException
     */
    @FXML
    private void popupLogin_login() throws IOException {
        doLogin();

    }

    /**
     * Handles pressing of the enter key
     * @param event key event
     * @throws IOException
     */
    @FXML
    private void handleKeypopupLogin_login(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER)  {
            doLogin();
        }
    }

    /**
     * Handles pressing of the abbrechen button
     */
   @FXML
   private void popupLogin_abbrechen() {
       Stage stage2 = (Stage) popupLogin_id.getScene().getWindow();
       stage2.close();

    }

    /**
     * Attempts to login with the entered credentials if both fields are not empty
     * Calls Check credentials
     * If successfull launches the main controller and UI window
     * @throws IOException
     */
    private void doLogin() throws IOException {
        popupLogin_credLabel.setText("");

        PasswordManager pwm = new PasswordManager();



        try{
            if(debug||pwm.checkCredentials(
                    Integer.parseInt(popupLogin_id.getText()),
                    popupLogin_pw.getText()

            )){
                System.out.println("Login Successful");
                Parent root  = FXMLLoader.load(getClass().getResource("sample.fxml"));
                Stage stage = new Stage();
                // stage.initModality(Modality.APPLICATION_MODAL);
                stage.setOpacity(1);
                stage.setTitle("Zeit und Leistungserfassung");
                stage.setScene(new Scene(root, 1200, 800));
                stage.setResizable(false);
                stage.show();

                Stage stage2 = (Stage) popupLogin_id.getScene().getWindow();
                stage2.close();
            }else{
                popupLogin_credLabel.setText("Ihre Login Daten sind ungültig");

            }
        }catch(NumberFormatException e){
            popupLogin_credLabel.setText("Ihre Login Daten sind ungültig");
        }

    }
    }

