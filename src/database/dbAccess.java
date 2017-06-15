package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * This class handles all communication with the Database
 * <p>
 *     It contains a number of premade SQL statements relevant to this applications functions
 *     upon a function call it creates a connection with the database, executes the SQL query
 *     and if applicable returns the result as an instance of TableContents
 * @author      Martin Anker <ankem1 @ students.bfh.ch>
 * @version     0.9
 */

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

    /**
     * Constructor loads driver
     */
    public dbAccess() {


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns a TableContent with all non-deleted Mitarbeiter
     *  @return TableContents with all Mitarbeiter
     */
    public TableContents getMitarbeiter() {
        return getQuery("SELECT mitarbeiter_id, name, vorname FROM mitarbeiter where inaktiv_flag = 0");
    }


    /**
     * Creates a new Mitarbeiter with the given informations
     * @param name name of the Mitarbeiter
     * @param vorname Vorname of the Mitarbeiter
     * @param passwort the Password of the new Mitarbeiter
     */
    public void createMitarbeiter(String name, String vorname, String passwort) {
        String passHash = new PasswordManager().getHash(passwort);
        updateQuery("INSERT INTO mitarbeiter (name, vorname, passwort) VALUES('" + name + "','" + vorname + "','" + passHash + "');");

    }

    /**
     * Changes an existing Mitarbeiter with the given informations

     * @param mitarbeiter_id the ID of the Mitarbeiter that needs to be changed
     * @param name new name
     * @param vorname new vorname
     */
    public void updateMitarbeiter(int mitarbeiter_id, String name, String vorname){
        updateQuery("UPDATE mitarbeiter SET name = '"+name+"', vorname = '"+vorname+"' WHERE mitarbeiter_id = '"+mitarbeiter_id+"'");

    }


    /**
     * Sets the Delete flag of the chosen Mitarbeiter to 1
     * @param mitarbeiter_id the ID of the Mitarbeiter that will be deleted
     */
    public void deleteMitarbeiter(int mitarbeiter_id){
        updateQuery("UPDATE mitarbeiter SET inaktiv_flag = '1' WHERE mitarbeiter_id = '"+mitarbeiter_id+"'");
    }

    /**
     * Returns a TableContent with all non-deleted Leistungen
     */
    public TableContents getLeistungen() {
        return getQuery("SELECT leistungs_id, name, stundenansatz FROM leistung where inaktiv_flag = 0");
    }

    /**
     * Creates a new Leistung with the given informations
     * @param name the name of the new Leistung
     * @param stundenansatz the hourly cost of the new Leistung
     */
    public void createLeistung(String name, int stundenansatz) {
        updateQuery("INSERT INTO leistung (name, stundenansatz) VALUES('" + name + "','" + stundenansatz + "');");

    }

    /**
     * Changes an existing Leistung with the given informations
     * @param leistungs_id the ID of the Leistung that is to be updated
     * @param name new name
     * @param stundenansatz new hourly cost
     */
    public void updateLeistung(int leistungs_id, String name, int stundenansatz){
        updateQuery("UPDATE leistung SET name = '"+name+"', stundenansatz = '"+stundenansatz+"' WHERE leistungs_id = '"+leistungs_id+"'");

    }


    /**
     * Sets the Delete flag of the chosen Leistung to 1
     * @param leistungs_id ID of the Leistung
     */
    public void deleteLeistung(int leistungs_id){
        updateQuery("UPDATE leistung SET inaktiv_flag = '1' WHERE leistungs_id = '"+leistungs_id+"'");
    }


    /**
     * Returns a TableContent with all non-deleted Projekt
     * @return TableContent with all Projekt
     */
    public TableContents getProjekt() {
        return getQuery("SELECT  projekt_id, kunden.name as kunde, projekt.name as projekt FROM kunden JOIN projekt using(kunden_id) where projekt.inaktiv_flag = 0");
    }



    /**
     * Returns a TableContent with the slected Projekt
     * @param projekt_id the ID of the wanted Projekt
     * @return TableContent with the wanted Projekt
     */
    public TableContents getProjektById(int projekt_id) {
        return getQuery("SELECT kunden_id FROM projekt  where projekt_id = '"+projekt_id+"'");

    }



    /**
     * Creates a new Projekt with the given informations
     * @param name name of the new Projekt
     * @param kunden_id ID of the projekt owner
     */
    public void createProjekt(String name, int kunden_id) {
        updateQuery("INSERT INTO projekt (name, kunden_id) VALUES('" + name + "','" + kunden_id + "');");

    }


    /**
     * Changes an existing Projekt with the given informations
     * @param projekt_id the ID of the PRojekt that is to be updated
     * @param name new name
     * @param kunden_id new owner ID
     */
    public void updateProjekt(int projekt_id, String name, int kunden_id){
        updateQuery("UPDATE projekt SET name = '"+name+"', kunden_id = '"+kunden_id+"' WHERE projekt_id = '"+projekt_id+"'");

    }


    /**
     * Sets the Delete flag of the chosen Projekt to 1
     * @param projekt_id the ID of the Projekt
     */
    public void deleteProjekt(int projekt_id){
        updateQuery("UPDATE projekt SET inaktiv_flag = '1' WHERE projekt_id = '"+projekt_id+"'");
    }

    /**
     * Returns a TableContent with all non-deleted Kunden
     * @return TableContent with all Kunden
     */
    public TableContents getKunden() {
        return getQuery("SELECT kunden_id, name, strasse, plz, stadt FROM kunden where inaktiv_flag = 0");
    }

    /**
     * Returns a TableContent with the address associated with a given projekt_id
     * @param projekt_id ID of the Projekt
     * @return TableContents with the Address of the Owner
     */
    public TableContents getAddresseByProjektID(String projekt_id){
        return getQuery("SELECT kunden.name, kunden.strasse, kunden.plz, kunden.stadt FROM kunden join projekt using(kunden_id) where projekt_id = '"+projekt_id+"'");
    }


    /**
     * Creates a new Kunde with the given informations
     * @param name name of the Kunde
     * @param strasse street
     * @param plz zip code
     * @param stadt city
     */
    public void createKunde(String name, String strasse, String plz, String stadt) {
        updateQuery("INSERT INTO kunden (name, strasse, plz, stadt) VALUES('" + name + "','" + strasse + "','" + plz + "','" + stadt + "');");

    }


    /**
     * Changes an existing Kunde with the given informations
     * @param kunden_id ID of the updated customer
     * @param name name
     * @param strasse street name
     * @param plz zip code
     * @param stadt city
     */
    public void updateKunde(int kunden_id, String name, String strasse, String plz, String stadt){
        updateQuery("UPDATE kunden SET name = '"+name+"', strasse = '"+strasse+"', plz = '"+plz+"', stadt = '"+stadt+"' WHERE kunden_id = '"+kunden_id+"'");

    }


    /**
     * Sets the Delete flag of the chosen Kunde to 1
     * @param kunden_id ID of to delete customer
     */
    public void deleteKunde(int kunden_id){
        updateQuery("UPDATE kunden SET inaktiv_flag = '1' WHERE kunden_id = '"+kunden_id+"'");
    }

    /**
     * Returns a TableContent with all Zeiterfassungen accossiated with a given mitarbeiter_id
     * @param mitarbeiter_id ID of the employee
     * @return TableContents with all Zeiterfassungen registered to the employee
     */
    public TableContents getZeiterfassungByMitarbeiter(int mitarbeiter_id) {
        return getQuery("SELECT erfassungs_id, mitarbeiter.name as mitarbeiter, leistung.name as leistung, projekt.name as projekt, datum, dauer FROM zeiterfassung JOIN mitarbeiter using(mitarbeiter_id) JOIN leistung using(leistungs_id) JOIN projekt using(projekt_id) where mitarbeiter_id = '"+mitarbeiter_id+"'");

    }


    /**
     * Returns a TableContent with all Zeiterfassungen accossiated with a given projekt_id
     * @param projekt_id ID of the project
     * @return TableContent with all Zeiterfassungen registered to the Project
     */
    public TableContents getZeiterfassungByProjekt(int projekt_id) {
        return getQuery("SELECT erfassungs_id, mitarbeiter.name as mitarbeiter, leistung.name as leistung, projekt.name as projekt, datum, dauer FROM zeiterfassung JOIN mitarbeiter using(mitarbeiter_id) JOIN leistung using(leistungs_id) JOIN projekt using(projekt_id) where projekt_id = '"+projekt_id+"'");

    }


    /**
     * Returns a TableContent with the Zeiterfassung accossiated with a given erfassungs_id
     * @param erfassungs_id the ID of the Zeiterfassung
     * @return TableContent with the Zeiterfassung
     */
    public TableContents getZeiterfassungById(int erfassungs_id) {
        return getQuery("SELECT * FROM zeiterfassung  where erfassungs_id = '"+erfassungs_id+"'");

    }


    /**
     * Creates a new Zeiterfassung with the given informations
     * @param mitarbeiter_id the ID of the employee
     * @param leistungs_id the ID of the Job
     * @param projekt_id the ID of the Project
     * @param datum the date in LocalDate
     * @param dauer the duration in String format as hhmmss
     */
    public void createZeiterfassung(int mitarbeiter_id, int leistungs_id, int projekt_id, LocalDate datum, String dauer) {
        updateQuery("INSERT INTO zeiterfassung (mitarbeiter_id, leistungs_id, projekt_id, datum, dauer) VALUES('" + mitarbeiter_id + "','" + leistungs_id + "','" + projekt_id + "','" + datum + "','" + dauer + "');");

    }

    /**
     * Changes an existing Zeiterfassung with the given informations
     * @param erfassungs_id ID of the Zeiterfassung that is to be changed
     * @param mitarbeiter_id new mitarbeiter_id
     * @param leistungs_id new leistungs_id
     * @param projekt_id new projekt_id
     * @param datum new date in LocalDate
     * @param dauer new duration in String format as hhmmss
     */
    public void updateZeiterfassung(int erfassungs_id, int mitarbeiter_id, int leistungs_id, int projekt_id, LocalDate datum, String dauer){
        updateQuery("UPDATE zeiterfassung SET mitarbeiter_id = '"+mitarbeiter_id+"', leistungs_id = '"+leistungs_id+"', projekt_id = '"+projekt_id+"', datum = '"+datum+"', dauer = '"+dauer+"'  WHERE erfassungs_id = '"+erfassungs_id+"'");

    }

    /**
     * Deletes the chosen Zeiterfassung
     * @param erfassungs_id ID of the Zeiterfassung
     */
    public void deleteZeiterfassung(int erfassungs_id){
        updateQuery("DELETE FROM zeiterfassung WHERE erfassungs_id= '"+erfassungs_id+"'");
    }


    /**
     * Returns a TableContent with a list of all used Leistungen and their accumulated time as well as their price
     * @param projekt_id ID of the Project
     * @return TableContent TableContent with a list of all used Leistungen and their accumulated time as well as their price
     */
    public TableContents getTotalTimeByProjekt(int projekt_id) {
        return getQuery("SELECT leistung.name as leistung, leistung.stundenansatz as stundenansatz, leistungs_id,  sum(TIME_TO_SEC(dauer))/3600 as gesamtdauer FROM zeiterfassung JOIN leistung USING(leistungs_id) WHERE projekt_id = '"+projekt_id+"' group by leistungs_id");
    }

    /**
     * Returns the hashed Password of the given mitarbeiter_id
     * @param mitarbeiter_id ID of the mitarbeiter
     * @return String of the hashed password
     */
    String getPassHash(int mitarbeiter_id){

        TableContents tc = getQuery("SELECT passwort FROM mitarbeiter WHERE level = '1' AND mitarbeiter_id = '"+mitarbeiter_id+"'");
        if(tc.collumnCount!=0) return (String)tc.data.get(0).iterator().next();
        else return "";
    }


    /**
     * Accepts a SQL query that doesn't produce any output, connects to the database and executes that query
     * This function is called by the various query methods in this class
     * @param query a valid SQL query
     */
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


    /**
     * Extracts the relevant informationm from a ResultSet and Fills it into a TableConstruct.
     * <p>
     * This method allows this class to close the Database Coneections shortly after opening it
     * is called by the getQuery method
     * @param rs the
     * @return TableContent with the information form the ResultSet
     * @throws SQLException
     */
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



    /**
     * Accepts a SQL query that produces an output, connects to the database and executes that query then returns a TableContent
     * <p>
     *     gets called by the various query methods in this class
     * @param query SQL Query
     * @return TableContent containing the Results form the SQL query
     */
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