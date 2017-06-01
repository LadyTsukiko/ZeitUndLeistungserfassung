package gui;

import database.TableContents;
import database.dbAccess;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class Controller {

    dbAccess dba;
    public TableView mainTable;
    public CheckBox checky;

    public Controller(){
        dba = new dbAccess();
    }

    @FXML
    private void handleCreateNew(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newEntry.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(root, 450, 450));
        stage.showAndWait();
    }

    @FXML
    private void displayMitarbeiter(ActionEvent event) {

       ObservableList data = FXCollections.observableArrayList();
        TableContents tc = dba.getMitarbeiter();
        ObservableList<String> row = FXCollections.observableArrayList();
        mainTable.getColumns().clear();
        mainTable.getItems().clear();

        for(int i=0 ; i<tc.collumnCount; i++){
            //We are using non property style for making dynamic table
            final int j = i;
            TableColumn col = new TableColumn(tc.meta.get(i));
            System.out.println(tc.meta.get(i));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            mainTable.getColumns().addAll(col);

            System.out.println("Column ["+i+"] ");
        }

        mainTable.setItems(tc.data);
    }

}
