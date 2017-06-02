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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Iterator;

public class EntryController {

    dbAccess dba;
    @FXML

    public Label popupNewEntry_toplabel;
    public Button popupNewEntry_abbrechen;
    public GridPane popupNewEntry_mitarbeiter;
    public GridPane popupNewEntry_leistung;
    public GridPane popupNewEntry_zeiterfassung;
    public GridPane popupNewEntry_projekt;
    public GridPane popupNewEntry_kunde;
    public ComboBox popupNewEntry_combo_mitarbeiter;
    public ComboBox popupNewEntry_combo_projekt;
    public ComboBox popupNewEntry_combo_leistung;
    public ComboBox popupNewEntry_combo_kunde;
    public DatePicker popupNewEntry_datum;
    public TextField popupNewEntry_dauer;



    public RefreshData redo = new RefreshData();
    private TableContents tc;
    private TableContents popuptc;

    public EntryController() {



    }

    public void initialize(TableContents tc, RefreshData redo){
        dba = new dbAccess();

        this.redo = redo;
        this.tc = tc;

        popuptc = dba.getMitarbeiter();
        for (ObservableList aData : popuptc.data) popupNewEntry_combo_mitarbeiter.getItems().add(aData.get(0));

        popuptc = dba.getProjekt();
        for (ObservableList aData : popuptc.data) popupNewEntry_combo_projekt.getItems().add(aData.get(0));

        popuptc = dba.getLeistungen();
        for (ObservableList aData : popuptc.data) popupNewEntry_combo_leistung.getItems().add(aData.get(0));

        popuptc = dba.getKunden();
        for (ObservableList aData : popuptc.data) popupNewEntry_combo_kunde.getItems().add(aData.get(0));

        popupNewEntry_leistung.setManaged(false);
        popupNewEntry_leistung.setVisible(false);

        popupNewEntry_mitarbeiter.setVisible(false);
        popupNewEntry_mitarbeiter.setManaged(false);

        popupNewEntry_zeiterfassung.setVisible(false);
        popupNewEntry_zeiterfassung.setManaged(false);

        popupNewEntry_projekt.setVisible(false);
        popupNewEntry_projekt.setManaged(false);

        popupNewEntry_kunde.setVisible(false);
        popupNewEntry_kunde.setManaged(false);

      /*  for(int i=1; i< this.tc.collumnCount; i++) {
            HBox hb = new HBox(new Label(this.tc.meta.get(i)), new TextField());
            hb.setAlignment(Pos.CENTER);
            popupNewEntry_fieldcontainer.getChildren().add(hb);
        }*/
        switch(redo.getRedo()){
            case 1:
                popupNewEntry_zeiterfassung.setVisible(true);
                popupNewEntry_zeiterfassung.setManaged(true);
                popupNewEntry_toplabel.setText("Zeiterfassung");
                break;
            case 2:
                popupNewEntry_mitarbeiter.setVisible(true);
                popupNewEntry_mitarbeiter.setManaged(true);
                popupNewEntry_toplabel.setText("Mitarbeiter");
                break;
            case 3:
                popupNewEntry_leistung.setManaged(true);
                popupNewEntry_leistung.setVisible(true);
                popupNewEntry_toplabel.setText("Leistung");
                break;
            case 4:
                popupNewEntry_projekt.setVisible(true);
                popupNewEntry_projekt.setManaged(true);
                popupNewEntry_toplabel.setText("Projekt");
                break;
            case 5:
                popupNewEntry_kunde.setVisible(true);
                popupNewEntry_kunde.setManaged(true);
                popupNewEntry_toplabel.setText("Kunde");
                break;
        }

    }



    @FXML
    private void handlePopupNewEntryOk(ActionEvent event) {
        switch(redo.getRedo()) {
            case 1:
                dba.createZeiterfassung(
                        Integer.parseInt( popupNewEntry_combo_mitarbeiter.getSelectionModel().getSelectedItem().toString()),
                        Integer.parseInt( popupNewEntry_combo_leistung.getSelectionModel().getSelectedItem().toString()),
                        Integer.parseInt( popupNewEntry_combo_projekt.getSelectionModel().getSelectedItem().toString()),
                        popupNewEntry_datum.getValue(),
                        popupNewEntry_dauer.getText()
                );
                break;
            case 2:
                TextField mname      =   (TextField)popupNewEntry_mitarbeiter.getChildren().get(3);
                TextField mvorname   =   (TextField)popupNewEntry_mitarbeiter.getChildren().get(4);
                TextField mpasswort  =   (TextField)popupNewEntry_mitarbeiter.getChildren().get(5);

                dba.createMitarbeiter(
                        mname.getText(),
                        mvorname.getText(),
                        mpasswort.getText()
                );

                break;
            case 3:
                TextField lname    =   (TextField)popupNewEntry_leistung.getChildren().get(2);
                TextField lansatz  =   (TextField)popupNewEntry_leistung.getChildren().get(3);

                    dba.createLeistung(
                            lname.getText(),
                            Integer.parseInt(lansatz.getText())
                    );
                break;
            case 4:
                TextField pname     =   (TextField)popupNewEntry_projekt.getChildren().get(2);

                dba.createProjekt(
                        pname.getText(),
                        Integer.parseInt( popupNewEntry_combo_kunde.getSelectionModel().getSelectedItem().toString())
                );
                break;
            case 5:
                TextField kname     =   (TextField)popupNewEntry_kunde.getChildren().get(3);
                TextField kstrasse     =   (TextField)popupNewEntry_kunde.getChildren().get(4);
                TextField kplz     =   (TextField)popupNewEntry_kunde.getChildren().get(5);
                TextField kstadt     =   (TextField)popupNewEntry_kunde.getChildren().get(6);

                dba.createKunde(
                        kname.getText(),
                        kstrasse.getText(),
                        kplz.getText(),
                        kstadt.getText()
                );
                break;
        }
        Stage stage = (Stage) popupNewEntry_abbrechen.getScene().getWindow();
        stage.close();

    }


    @FXML
    private void handlePopupNewEntryAbbrechen(ActionEvent event) {
        Stage stage = (Stage) popupNewEntry_abbrechen.getScene().getWindow();
        stage.close();

    }
}
