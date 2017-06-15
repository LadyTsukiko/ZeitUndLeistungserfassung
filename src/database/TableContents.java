package database;

import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Data structure which keeps the relevant informations of a ResultSet to pass betweeen dbAccess and the GUI
 * <p>
 *     Allows the passing around of ResultSet data without keeping the Database connection open
 * @author      Martin Anker <ankem1 @ students.bfh.ch>
 * @version     0.9
 */

public class TableContents {

    //Column Data
    public ObservableList<ObservableList> data;
    //Column Names
    public ArrayList<String> meta = new ArrayList<>();
    //NUmber of Collumns
    public int collumnCount = 0;
}
