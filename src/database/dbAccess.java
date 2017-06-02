package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class dbAccess {

    // JDBC driver name and database URL
   // static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://147.87.116.243:3306/leistungdb";

    //  Database credentials
    private static final String USER = "leistunguser";
    private static final String PASS = "leipass223";
    private static final String connectionString = "" + DB_URL + "?user=" + USER + "&password=" + PASS + "&verifyServerCertificate=false&useSSL=true&useUnicode=true&characterEncoding=UTF-8";


    private Connection conn;
    private Statement stmt;
    private TableContents tc;

    public dbAccess() {


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public TableContents getMitarbeiter() {
        return getQuery("SELECT mitarbeiter_id, name, vorname FROM mitarbeiter where inaktiv_flag = 0");
    }

    public void createMitarbeiter(String name, String vorname, String passwort) {
        String passHash = new PasswordManager().getHash(passwort);
        updateQuery("INSERT INTO mitarbeiter (name, vorname, passwort) VALUES('" + name + "','" + vorname + "','" + passHash + "');");

    }
    public void updateMitarbeiter(int mitarbeiter_id, String name, String vorname){
        updateQuery("UPDATE mitarbeiter SET name = '"+name+"', vorname = '"+vorname+"' WHERE mitarbeiter_id = '"+mitarbeiter_id+"'");

    }

    public void deleteMitarbeiter(int mitarbeiter_id){
        updateQuery("UPDATE mitarbeiter SET inaktiv_flag = '1' WHERE mitarbeiter_id = '"+mitarbeiter_id+"'");
    }

    public TableContents getLeistungen() {
        return getQuery("SELECT leistungs_id, name, stundenansatz FROM leistung where inaktiv_flag = 0");
    }

    public void createLeistung(String name, int stundenansatz) {
        updateQuery("INSERT INTO leistung (name, stundenansatz) VALUES('" + name + "','" + stundenansatz + "');");

    }
    public void updateLeistung(int leistungs_id, String name, int stundenansatz){
        updateQuery("UPDATE leistung SET name = '"+name+"', stundenansatz = '"+stundenansatz+"' WHERE leistungs_id = '"+leistungs_id+"'");

    }

    public void deleteLeistung(int leistungs_id){
        updateQuery("UPDATE leistung SET inaktiv_flag = '1' WHERE leistungs_id = '"+leistungs_id+"'");
    }

    public TableContents getProjekt() {
        return getQuery("SELECT  projekt_id, kunden.name as kunde, projekt.name as projekt FROM kunden JOIN projekt using(kunden_id) where projekt.inaktiv_flag = 0");
    }

    public TableContents getProjektById(int projekt_id) {
        return getQuery("SELECT kunden_id FROM projekt  where projekt_id = '"+projekt_id+"'");

    }

    public void createProjekt(String name, int kunden_id) {
        updateQuery("INSERT INTO projekt (name, kunden_id) VALUES('" + name + "','" + kunden_id + "');");

    }
    public void updateProjekt(int projekt_id, String name, int kunden_id){
        updateQuery("UPDATE projekt SET name = '"+name+"', kunden_id = '"+kunden_id+"' WHERE projekt_id = '"+projekt_id+"'");

    }

    public void deleteProjekt(int projekt_id){
        updateQuery("UPDATE projekt SET inaktiv_flag = '1' WHERE projekt_id = '"+projekt_id+"'");
    }

    public TableContents getKunden() {
        return getQuery("SELECT kunden_id, name, strasse, plz, stadt FROM kunden where inaktiv_flag = 0");
    }

    public void createKunde(String name, String strasse, String plz, String stadt) {
        updateQuery("INSERT INTO kunden (name, strasse, plz, stadt) VALUES('" + name + "','" + strasse + "','" + plz + "','" + stadt + "');");

    }
    public void updateKunde(int kunden_id, String name, String strasse, String plz, String stadt){
        updateQuery("UPDATE kunden SET name = '"+name+"', strasse = '"+strasse+"', plz = '"+plz+"', stadt = '"+stadt+"' WHERE kunden_id = '"+kunden_id+"'");

    }

    public void deleteKunde(int kunden_id){
        updateQuery("UPDATE kunden SET inaktiv_flag = '1' WHERE kunden_id = '"+kunden_id+"'");
    }

    public TableContents getZeiterfassungByMitarbeiter(int mitarbeiter_id) {
        return getQuery("SELECT erfassungs_id, mitarbeiter.name as mitarbeiter, leistung.name as leistung, projekt.name as projekt, datum, dauer FROM zeiterfassung JOIN mitarbeiter using(mitarbeiter_id) JOIN leistung using(leistungs_id) JOIN projekt using(projekt_id) where mitarbeiter_id = '"+mitarbeiter_id+"'");

    }

    public TableContents getZeiterfassungByProjekt(int projekt_id) {
        return getQuery("SELECT erfassungs_id, mitarbeiter.name as mitarbeiter, leistung.name as leistung, projekt.name as projekt, datum, dauer FROM zeiterfassung JOIN mitarbeiter using(mitarbeiter_id) JOIN leistung using(leistungs_id) JOIN projekt using(projekt_id) where projekt_id = '"+projekt_id+"'");

    }

    public TableContents getZeiterfassungById(int erfassungs_id) {
        return getQuery("SELECT * FROM zeiterfassung  where erfassungs_id = '"+erfassungs_id+"'");

    }

    public void createZeiterfassung(int mitarbeiter_id, int leistungs_id, int projekt_id, LocalDate datum, String dauer) {
        updateQuery("INSERT INTO zeiterfassung (mitarbeiter_id, leistungs_id, projekt_id, datum, dauer) VALUES('" + mitarbeiter_id + "','" + leistungs_id + "','" + projekt_id + "','" + datum + "','" + dauer + "');");

    }
    public void updateZeiterfassung(int erfassungs_id, int mitarbeiter_id, int leistungs_id, int projekt_id, LocalDate datum, String dauer){
        updateQuery("UPDATE zeiterfassung SET mitarbeiter_id = '"+mitarbeiter_id+"', leistungs_id = '"+leistungs_id+"', projekt_id = '"+projekt_id+"', datum = '"+datum+"', dauer = '"+dauer+"'  WHERE erfassungs_id = '"+erfassungs_id+"'");

    }

    public void deleteZeiterfassung(int erfassungs_id){
        updateQuery("DELETE FROM zeiterfassung WHERE erfassungs_id= '"+erfassungs_id+"'");
    }


    public TableContents getTotalTimeByProjekt(int projekt_id) {
        return getQuery("SELECT leistung.name as leistung, leistung.stundenansatz as stundenansatz, leistungs_id,  sum(TIME_TO_SEC(dauer))/3600 as gesamtdauer FROM zeiterfassung JOIN leistung USING(leistungs_id) WHERE projekt_id = '"+projekt_id+"' group by leistungs_id");
    }

    String getPassHash(int mitarbeiter_id){

        TableContents tc = getQuery("SELECT passwort FROM mitarbeiter WHERE level = '1' AND mitarbeiter_id = '"+mitarbeiter_id+"'");
        if(tc.collumnCount!=0) return (String)tc.data.get(0).iterator().next();
        else return "";
    }




    private void updateQuery(String query) {

        try {
            conn = DriverManager.getConnection(connectionString);
            //conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Database Connection Established");

            stmt = conn.createStatement();

            stmt.executeUpdate(query);
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());

                se.printStackTrace();
            }//end finally try

        }
    }


       private TableContents parseToTableContents(ResultSet rs) throws SQLException {

           int collumnCount = 0;
           ObservableList data = FXCollections.observableArrayList();
           ArrayList<String> meta = new ArrayList<String>();
           TableContents tc = new TableContents();
           ResultSetMetaData metaData = rs.getMetaData();
           collumnCount = metaData.getColumnCount();

            //Get the column names
           for(int i=1; i<=collumnCount; i++){
                meta.add(metaData.getColumnLabel(i));
           }

           while(rs.next()){
               //Iterate Row
               ObservableList<String> row = FXCollections.observableArrayList();
               for(int i=1 ; i<=collumnCount; i++){
                   //Iterate Column
                   row.add(rs.getString(i));
               }
               data.add(row);
               }

           tc.collumnCount=collumnCount;
           tc.data=data;
           tc.meta=meta;

           return tc;

       }


    private TableContents getQuery(String query) {

        try {
            conn = DriverManager.getConnection(connectionString);
            //conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            tc = parseToTableContents(rs);
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                System.out.println(se.getMessage());

                se.printStackTrace();
            }//end finally try

        }
        return tc;

    }
}