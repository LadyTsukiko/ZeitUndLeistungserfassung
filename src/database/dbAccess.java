package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class dbAccess {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://147.87.116.243/leistungdb";

    //  Database credentials
    static final String USER = "leistunguser";
    static final String PASS = "leipass223";

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    public dbAccess() {


        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public ResultSet getMitarbeiter() {

        return makeQuery("SELECT mitarbeiter_id, name, vorname FROM Mitarbeiter where inaktiv_flag = 0");

    }

    public void createMitarbeiter(String name, String vorname, String passwort) {
        System.out.println("Before jq");

    String passHash= new PasswordManager().getHash(passwort);
    makeQuery("INSERT into MITARBEITER (name, vorname, passwort) VALUES('"+name+"','"+vorname+"','"+passHash+"');");
        System.out.println("after jq");

    }




    private ResultSet makeQuery(String query) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;
            sql = query;
            ResultSet rs = stmt.executeQuery(sql);
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

            return rs;
        }
    }



}
