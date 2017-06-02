package gui;

import database.TableContents;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

/**
 * Created by Martin on 02.06.2017.
 */
public class AbrechnungsController {

    @FXML private TextArea popupAbrechnung_textarea;

    public void initialize(String projekt_id, String projekt_name, TableContents tc){
        popupAbrechnung_textarea.appendText("Abschluss des Projektes: "+projekt_name+" (id = "+projekt_id+")\n");
        popupAbrechnung_textarea.appendText("Leistung | Stundenansatz | LeistungsID | Geleistete Zeit | Total Kosten \n");
        int size  = tc.data.size();
        int i = 0;
        ArrayList<Float> result = new ArrayList<Float>();
        Float total = 0.0f;
        while (i<size){
            result.add(Float.parseFloat((String)tc.data.get(i).get(1))*Float.parseFloat((String)tc.data.get(i).get(3)));
            total+= result.get(i);
            popupAbrechnung_textarea.appendText(tc.data.get(i).get(0)+"        "+tc.data.get(i).get(1)+"      "+tc.data.get(i).get(2)+"      "+tc.data.get(i).get(3)+"       "+result.get(i)+"\n");

            i++;
        }
        popupAbrechnung_textarea.appendText("   Total Costs:      "+total );

    }




    }

