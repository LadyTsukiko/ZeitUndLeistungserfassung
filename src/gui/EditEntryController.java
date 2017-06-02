package gui;

import data.RefreshData;
import database.TableContents;
import database.dbAccess;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class EditEntryController {



    @FXML private Label popupEditEntry_toplabel;
    @FXML private Button popupNewEntry_abbrechen;
    @FXML private GridPane popupNewEntry_mitarbeiter;
    @FXML private GridPane popupNewEntry_leistung;
    @FXML private GridPane popupNewEntry_zeiterfassung;
    @FXML private GridPane popupNewEntry_projekt;
    @FXML private GridPane popupNewEntry_kunde;
    @FXML private ComboBox popupNewEntry_combo_mitarbeiter;
    @FXML private ComboBox popupNewEntry_combo_projekt;
    @FXML private ComboBox popupNewEntry_combo_leistung;
    @FXML private ComboBox popupNewEntry_combo_kunde;
    @FXML private DatePicker popupNewEntry_datum;
    @FXML private TextField popupNewEntry_dauer;
    @FXML private TextField popupEditEntry_idtext;
    @FXML private Label popupEditEntry_idlabel;


    private RefreshData redo = new RefreshData();
    private TableContents tc;
    private TableContents popuptc;
    private ObservableList row;
    private dbAccess dba;

    public EditEntryController() {



    }

    public void initialize(ObservableList row, RefreshData redo){
        dba = new dbAccess();

        this.redo = redo;
       //this.tc = tc;
        this.row = row;

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

        Iterator iter = row.iterator();

        popupEditEntry_idtext.setText((String)iter.next());
        popupEditEntry_idtext.setEditable(false);
        popupEditEntry_idtext.setDisable(true);


        switch(redo.getRedo()){
            case 1:
                popupNewEntry_zeiterfassung.setVisible(true);
                popupNewEntry_zeiterfassung.setManaged(true);
                popupEditEntry_idlabel.setText("Erfassungs ID");
                popupEditEntry_toplabel.setText("Zeiterfassung bearbeiten");
                popuptc=dba.getZeiterfassungById(Integer.parseInt((String)row.iterator().next()));
                autoSelectId((String)(popuptc.data.get(0).get(1)),popupNewEntry_combo_mitarbeiter);
                autoSelectId((String)(popuptc.data.get(0).get(2)),popupNewEntry_combo_leistung);
                autoSelectId((String)(popuptc.data.get(0).get(3)),popupNewEntry_combo_projekt);
                popupNewEntry_datum.setValue(LOCAL_DATE((String)(popuptc.data.get(0).get(4))));
                popupNewEntry_dauer.setText((String)(popuptc.data.get(0).get(5)));
                break;
            case 2:
                popupNewEntry_mitarbeiter.setVisible(true);
                popupNewEntry_mitarbeiter.setManaged(true);
                popupEditEntry_idlabel.setText("Mitarbeiter ID");
                popupEditEntry_toplabel.setText("Mitarbeiter bearbeiten");
                TextField mname      =   (TextField)popupNewEntry_mitarbeiter.getChildren().get(2);
                TextField mvorname   =   (TextField)popupNewEntry_mitarbeiter.getChildren().get(3);
                mname.setText((String)iter.next());
                mvorname.setText((String)iter.next());
                break;
            case 3:
                popupNewEntry_leistung.setManaged(true);
                popupNewEntry_leistung.setVisible(true);
                popupEditEntry_idlabel.setText("Leistungs ID");
                popupEditEntry_toplabel.setText("Leistung bearbeiten");
                TextField lname    =   (TextField)popupNewEntry_leistung.getChildren().get(2);
                TextField lansatz  =   (TextField)popupNewEntry_leistung.getChildren().get(3);
                lname.setText((String)iter.next());
                lansatz.setText((String)iter.next());

                break;
            case 4:
                popupNewEntry_projekt.setVisible(true);
                popupNewEntry_projekt.setManaged(true);
                popupEditEntry_idlabel.setText("Projekt ID");
                popupEditEntry_toplabel.setText("Projekt bearbeiten");
                popuptc = dba.getProjektById(Integer.parseInt((String)row.iterator().next()));
                TextField pname    =   (TextField)popupNewEntry_projekt.getChildren().get(2);
                iter.next();
                pname.setText((String)iter.next());
                autoSelectId((String)(popuptc.data.get(0).get(0)),popupNewEntry_combo_kunde);


                break;
            case 5:
                popupNewEntry_kunde.setVisible(true);
                popupNewEntry_kunde.setManaged(true);
                popupEditEntry_idlabel.setText("Kunden ID");
                popupEditEntry_toplabel.setText("Kunde bearbeiten");
                TextField kname     =   (TextField)popupNewEntry_kunde.getChildren().get(3);
                TextField kstrasse     =   (TextField)popupNewEntry_kunde.getChildren().get(4);
                TextField kplz     =   (TextField)popupNewEntry_kunde.getChildren().get(5);
                TextField kstadt     =   (TextField)popupNewEntry_kunde.getChildren().get(6);
                kname.setText((String)iter.next());
                kstrasse.setText((String)iter.next());
                kplz.setText((String)iter.next());
                kstadt.setText((String)iter.next());


                break;


        }

    }



    @FXML
    private void handlePopupNewEntryUpdate(ActionEvent event) {


        switch(redo.getRedo()) {
            case 1:
                dba.updateZeiterfassung(
                        Integer.parseInt(popupEditEntry_idtext.getText()),
                        Integer.parseInt( popupNewEntry_combo_mitarbeiter.getSelectionModel().getSelectedItem().toString()),
                        Integer.parseInt( popupNewEntry_combo_leistung.getSelectionModel().getSelectedItem().toString()),
                        Integer.parseInt( popupNewEntry_combo_projekt.getSelectionModel().getSelectedItem().toString()),
                        popupNewEntry_datum.getValue(),
                        popupNewEntry_dauer.getText()
                );
                break;
            case 2:
                TextField mname      =   (TextField)popupNewEntry_mitarbeiter.getChildren().get(2);
                TextField mvorname   =   (TextField)popupNewEntry_mitarbeiter.getChildren().get(3);

                dba.updateMitarbeiter(
                        Integer.parseInt(popupEditEntry_idtext.getText()),
                        mname.getText(),
                        mvorname.getText()
                );

                break;
            case 3:
                TextField lname    =   (TextField)popupNewEntry_leistung.getChildren().get(2);
                TextField lansatz  =   (TextField)popupNewEntry_leistung.getChildren().get(3);

                    dba.updateLeistung(
                            Integer.parseInt(popupEditEntry_idtext.getText()),
                            lname.getText(),
                            Integer.parseInt(lansatz.getText())
                    );
                break;
            case 4:
                TextField pname     =   (TextField)popupNewEntry_projekt.getChildren().get(2);

                dba.updateProjekt(
                        Integer.parseInt(popupEditEntry_idtext.getText()),
                        pname.getText(),
                        Integer.parseInt( popupNewEntry_combo_kunde.getSelectionModel().getSelectedItem().toString())
                );
                break;
            case 5:
                TextField kname     =   (TextField)popupNewEntry_kunde.getChildren().get(3);
                TextField kstrasse     =   (TextField)popupNewEntry_kunde.getChildren().get(4);
                TextField kplz     =   (TextField)popupNewEntry_kunde.getChildren().get(5);
                TextField kstadt     =   (TextField)popupNewEntry_kunde.getChildren().get(6);

                dba.updateKunde(
                        Integer.parseInt(popupEditEntry_idtext.getText()),
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
    private void handlePopupNewEntryDelete(ActionEvent event) {
        switch(redo.getRedo()){
            case 1:
                dba.deleteZeiterfassung(Integer.parseInt(popupEditEntry_idtext.getText()));
                break;
            case 2:
                dba.deleteMitarbeiter(Integer.parseInt(popupEditEntry_idtext.getText()));
                break;
            case 3:
                dba.deleteLeistung(Integer.parseInt(popupEditEntry_idtext.getText()));
                break;
            case 4:
                dba.deleteProjekt(Integer.parseInt(popupEditEntry_idtext.getText()));
                break;
            case 5:
                dba.deleteKunde(Integer.parseInt(popupEditEntry_idtext.getText()));
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


    @FXML
    private void handlePopupNewEntryAbschliessen(ActionEvent event) throws IOException {
        popuptc = dba.getTotalTimeByProjekt(Integer.parseInt(popupEditEntry_idtext.getText()));
        TextField pname     =   (TextField)popupNewEntry_projekt.getChildren().get(2);


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("popupAbrechnung.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        stage.setTitle("Abschlussrechnung");
        stage.setScene(new Scene(root, 600, 400));
        fxmlLoader.<AbrechnungsController>getController().initialize(popupEditEntry_idtext.getText(),pname.getText(), popuptc);
        stage.showAndWait();

        Stage stage2 = (Stage) popupNewEntry_abbrechen.getScene().getWindow();
        stage2.close();

    }
    private void autoSelectId(String id, ComboBox cb) {

        Iterator iter = cb.getItems().iterator();
       while(iter.hasNext()) {
           String str = (String)iter.next();
            if(str.equals(id)) {
                cb.getSelectionModel().select(str);
                break;
            }
        }
    }
    public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }


}
