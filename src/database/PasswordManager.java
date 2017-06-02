package database;

/**
 * Created by Martin on 23.05.2017.
 */
public class PasswordManager {
    public PasswordManager() {

    }

    String getHash(String pw) {
        return BCrypt.hashpw(pw, BCrypt.gensalt());
    }

    public boolean checkCredentials(int mitarbeiter_id, String passwort) {
        dbAccess dba = new dbAccess();
        return  BCrypt.checkpw(passwort, dba.getPassHash(mitarbeiter_id));
}
}
