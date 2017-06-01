package gui;

import data.RefreshData;
import database.TableContents;
import database.dbAccess;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Iterator;

public class Controller {

    dbAccess dba;
    @FXML
    public TableView mainTable;
    public ComboBox popupZeiterfassung_combobox;
    public RadioButton popupZeiterfassung_mitarbeiter;
    public RadioButton popupZeiterfassung_projekt;
    public ToggleGroup popupZeiterfassung_toggleGroup;
    public Button popupZeiterfassung_abbrechen;
    public Button popupZeiterfassung_ok;

    public RefreshData redo = new RefreshData();
    //private TableContents tc;
    private TableContents popuptc;




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

        fillTableView(dba.getMitarbeiter());
        redo.setRedo(2);

    }

    @FXML
    private void displayLeistungen(ActionEvent event) {

        fillTableView(dba.getLeistungen());
        redo.setRedo(3);
    }

    @FXML
    private void displayKunden(ActionEvent event) {

        fillTableView(dba.getKunden());
        redo.setRedo(5);

    }

    @FXML
    private void displayProjekte(ActionEvent event) {

        fillTableView(dba.getProjekt());
        redo.setRedo(4);

    }
    @FXML
    private void displayZeiterfassungen(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("popupZeiterfassung.fxml"));

        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        stage.setTitle("Wähle ID");
        stage.setScene(new Scene(root, 400, 200));
        stage.setResizable(false);
        fxmlLoader.<Controller>getController().passTableView(mainTable,redo);
        stage.show();

    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        switch(redo.getRedo()) {
            case 1:
                fillTableView(dba.getZeiterfassungByMitarbeiter(12));
                break;
            case 2:
                fillTableView(dba.getMitarbeiter());
                break;
            case 3:
                fillTableView(dba.getLeistungen());
                break;
            case 4:
                fillTableView(dba.getProjekt());
                break;
            case 5:
                fillTableView(dba.getKunden());
                break;
        }
    }



    @FXML
    private void handlePopupZeiterfassungOk(ActionEvent event) {
        if(popupZeiterfassung_mitarbeiter.isSelected()){
            int id = Integer.parseInt(popupZeiterfassung_combobox.getSelectionModel().getSelectedItem().toString());
            fillTableView(dba.getZeiterfassungByMitarbeiter(id));
            redo.setRedo(1);
            redo.setBy_project(false);
            redo.setId_for_zeiterfassung_redo(id);
            Stage stage = (Stage) popupZeiterfassung_ok.getScene().getWindow();
            stage.close();

        }
        if(popupZeiterfassung_projekt.isSelected()) {
            int id = Integer.parseInt(popupZeiterfassung_combobox.getSelectionModel().getSelectedItem().toString());
            fillTableView(dba.getZeiterfassungByProjekt(id));
            redo.setRedo(1);
            redo.setBy_project(false);
            redo.setId_for_zeiterfassung_redo(id);
            Stage stage = (Stage) popupZeiterfassung_ok.getScene().getWindow();
            stage.close();
        }

    }
    public void passTableView(TableView tv, RefreshData redo){
        mainTable = tv;
        this.redo = redo;
    }

    @FXML
    private void handlePopupZeiterfassungAbbrechen(ActionEvent event) {

        Stage stage = (Stage) popupZeiterfassung_abbrechen.getScene().getWindow();
        stage.close();


    }

    @FXML
    private void handlePopupZeiterfassungRadioButtons(ActionEvent event) {
        popupZeiterfassung_combobox.getItems().clear();
        if(popupZeiterfassung_mitarbeiter.isSelected()){
            popuptc = dba.getMitarbeiter();
            Iterator<ObservableList> iter = popuptc.data.iterator();
            while(iter.hasNext())
                popupZeiterfassung_combobox.getItems().add(iter.next().get(0));


        }else if (popupZeiterfassung_projekt.isSelected()){
            popuptc = dba.getProjekt();
            Iterator<ObservableList> iter = popuptc.data.iterator();
            while(iter.hasNext())
                popupZeiterfassung_combobox.getItems().add(iter.next().get(0));
        }


    }

    private void fillTableView(TableContents tc){

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
