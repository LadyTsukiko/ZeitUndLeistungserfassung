package gui;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import database.TableContents;
import database.dbAccess;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class handles the finalization of a project and displays the costs for it
 * <p>
 *    Displays the costs in a textField as well as generating a PDF
 * @author      Martin Anker <ankem1 @ students.bfh.ch>
 * @version     0.9
 */
public class AbrechnungsController {

    @FXML private TextArea popupAbrechnung_textarea;

    /**
     * Does the heavy lifting as well as allowing the program to pass data between controllers
     * @param projekt_id ID of the to finishing project
     * @param projekt_name name of the Project
     * @param tc TableContents with the Project informations
     * @throws IOException
     */
    public void initialize(String projekt_id, String projekt_name, TableContents tc) throws IOException {

        //Initialize pdf
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter("Abrechnug.pdf"));
        Document document = new Document(pdfDocument, PageSize.A4);
        //PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN); //creates font

        document.add(new Paragraph("Abschluss des Projektes: "+projekt_name+" (id = "+projekt_id+")").setFontSize(20));//Use setFontAndSize incase u want 2 switch font
        dbAccess dba = new dbAccess();
        TableContents address = dba.getAddresseByProjektID(projekt_id);
        for (int i = 0; i <4 ; i++) {
            document.add(new Paragraph(address.data.get(0).get(i).toString()).setTextAlignment(TextAlignment.RIGHT));
        }

        Table table = new Table(new float[]{4, 2, 1, 2, 2}); //1col has relative width 4, 2 has 2, etc
        table.setWidthPercent(100);
        table.addHeaderCell(new Cell().add(new Paragraph("Leistung")));
        table.addHeaderCell(new Cell().add(new Paragraph("Stundenansatz")));
        table.addHeaderCell(new Cell().add(new Paragraph("LeistungsID")));
        table.addHeaderCell(new Cell().add(new Paragraph("Geleistete Zeit")));
        table.addHeaderCell(new Cell().add(new Paragraph("Kosten")));

        popupAbrechnung_textarea.appendText("Abschluss des Projektes: "+projekt_name+" (id = "+projekt_id+")\n");


        popupAbrechnung_textarea.appendText("Leistung | Stundenansatz | LeistungsID | Geleistete Zeit | Kosten \n");
        int size  = tc.data.size();
        int i = 0;
        ArrayList<Float> result = new ArrayList<>();
        Float total = 0.0f;
        while (i<size){
            result.add(Float.parseFloat((String)tc.data.get(i).get(1))*Float.parseFloat((String)tc.data.get(i).get(3)));
            total+= result.get(i);
            popupAbrechnung_textarea.appendText(tc.data.get(i).get(0)+"        "+tc.data.get(i).get(1)+"      "+tc.data.get(i).get(2)+"      "+tc.data.get(i).get(3)+"       "+result.get(i)+"\n");

            for (int j = 0; j < 4; j++) {
                table.addCell(new Cell().add(new Paragraph("" +(String) tc.data.get(i).get(j)))); //.toString()
            }
            table.addCell(new Cell().add(new Paragraph("" + result.get(i))));
            i++;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        popupAbrechnung_textarea.appendText("   Totale Kosten:     "+df.format(total));


        document.add(table);
        document.add(new Paragraph("Total: " + total));
        document.close(); //closes document

    }




    }

