package gui;

import data.RefreshData;
import database.TableContents;
import database.dbAccess;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
/**
 * Main Controller class for the UI
 * <p>
 *     Handles the Main window, especially the table view and it's data
 *     It also handles the popup for the Zeiterfassungs options
 * @author      Martin Anker <ankem1 @ students.bfh.ch>
 * @version     0.9
 */
public class Controller {

    @FXML private TableView mainTable;
    @FXML private ComboBox popupZeiterfassung_combobox;
    @FXML private RadioButton popupZeiterfassung_mitarbeiter;
    @FXML private RadioButton popupZeiterfassung_projekt;
    @FXML private Button popupZeiterfassung_abbrechen;
    @FXML private Button popupZeiterfassung_ok;

    private RefreshData redo = new RefreshData();
    private TableContents tc = new TableContents();
    private     dbAccess dba;


    /**
     * Constructor, sets up the Tableview and its handler
     */
    public Controller(){
        dba = new dbAccess();

        mainTable = new TableView();
        mainTable.setRowFactory(tv -> {
            TableRow<ObservableList> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY ){

                    ObservableList clickedRow = row.getItem();
                    System.out.println(clickedRow);
                  //  handleClickedRow(clickedRow);
                }
            });
            return row ;
        });


    }

    /**
     * Handles the activation of the Neuer Eintrag button
     * Starts a popup window to create a new entry
     * @throws IOException
     */
    @FXML
    private void handleCreateNew() throws IOException {

        if(redo.getRedo()!=0) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("popupnewEntry.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Neuen Eintrag erstellen");
            stage.setScene(new Scene(root, 500, 400));
            fxmlLoader.<EntryController>getController().initialize(tc,redo);
            stage.showAndWait();
            refresh();
        }
    }

    /**
     * Changes the display of the TableView to Mitarbeiter
     */
    @FXML
    private void displayMitarbeiter() {

        fillTableView(dba.getMitarbeiter());
        redo.setRedo(2);

    }

    /**
     * Changes the display of the TableView to Leistungen
     */
    @FXML
    private void displayLeistungen() {

        fillTableView(dba.getLeistungen());
        redo.setRedo(3);
    }

    /**
     * Changes the display of the TableView to Kunden
     */
    @FXML
    private void displayKunden() {

        fillTableView(dba.getKunden());
        redo.setRedo(5);

    }

    /**
     * Changes the display of the TableView to Projekte
     */
    @FXML
    private void displayProjekte() {

        fillTableView(dba.getProjekt());
        redo.setRedo(4);

    }
    /**
     * Changes the display of the TableView to Zeiterfassungen
     * Opens a popup window that allows the user to choose to display the Zeiterfassung by either Mitarbeiter or Projekt
     */
    @FXML
    private void displayZeiterfassungen() throws IOException {

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
    /**
     * Handles the pressing of the refresh button
     */
    @FXML
    private void handleRefresh() {
        refresh();
    }


    /**
     * Handles the pressing of the OK button in the popup window
     */
    @FXML
    private void handlePopupZeiterfassungOk() {
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
            redo.setBy_project(true);
            redo.setId_for_zeiterfassung_redo(id);
            Stage stage = (Stage) popupZeiterfassung_ok.getScene().getWindow();
            stage.close();
        }

    }
    /**
     * Allows the passing of table view to the popup window
     * javafx requires this to pass data
     */
    private void passTableView(TableView tv, RefreshData redo){
        mainTable = tv;
        this.redo = redo;
    }

    /**
     * Handles the pressing of the Abbrechen button in the popup window
     */
    @FXML
    private void handlePopupZeiterfassungAbbrechen() {
        Stage stage = (Stage) popupZeiterfassung_abbrechen.getScene().getWindow();
        stage.close();

    }

    /**
     * Handles the radio buttons in the popup window
     * changes the entries in the combo box
     */
    @FXML
    private void handlePopupZeiterfassungRadioButtons() {
        popupZeiterfassung_combobox.getItems().clear();
        TableContents popuptc;
        if(popupZeiterfassung_mitarbeiter.isSelected()){
            popuptc = dba.getMitarbeiter();
            for (ObservableList aData : popuptc.data) popupZeiterfassung_combobox.getItems().add(aData.get(0));


        }else if (popupZeiterfassung_projekt.isSelected()){
            popuptc = dba.getProjekt();
            for (ObservableList aData : popuptc.data) popupZeiterfassung_combobox.getItems().add(aData.get(0));
        }


    }

    /**
     * Fills in the Table view
     * @param tc TableContents with the informations to fill it with
     */
    private void fillTableView(TableContents tc){

        mainTable.getColumns().clear();
        mainTable.getItems().clear();

        for(int i=0 ; i<tc.collumnCount; i++){
            //Creates a dynamic table based on query
            final int j = i;
            TableColumn col = new TableColumn(tc.meta.get(i));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            mainTable.getColumns().addAll(col);

        }

        mainTable.setItems(tc.data);
        this.tc=tc;

    }

    /**
     * Handler that handles clicks on the TableView rows
     * Opens the edit Entry popup window on a doubleclick
     * @param me Mouse event
     * @throws IOException
     */
    @FXML
    private void handleClickedRow(MouseEvent me) throws IOException {
        if(me.getClickCount() == 2) {

            ObservableList row = (ObservableList) mainTable.getSelectionModel().getSelectedItem();
            if (row != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("popupEditEntry.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setOpacity(1);
                stage.setTitle("Eintrag Bearbeiten");
                stage.setScene(new Scene(root, 500, 400));
                fxmlLoader.<EditEntryController>getController().initialize(row, redo);
                stage.showAndWait();
                refresh();


            }

        }
    }

    /**
     * Refreshes the information in the TableView
     */
    private void refresh() {
        switch (redo.getRedo()) {
            case 1:
                if (redo.isBy_project())
                    fillTableView(dba.getZeiterfassungByProjekt(redo.getId_for_zeiterfassung_redo()));
                else if (!redo.isBy_project())
                    fillTableView(dba.getZeiterfassungByMitarbeiter(redo.getId_for_zeiterfassung_redo()));
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
}
