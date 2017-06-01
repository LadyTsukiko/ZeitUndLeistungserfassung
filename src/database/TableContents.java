package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by Martin on 31.05.2017.
 */
public class TableContents {

    public ObservableList<ObservableList> data;
    public ArrayList<String> meta = new ArrayList<String>();
    public int collumnCount = 0;
}
