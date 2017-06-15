package database;


/**
 * Class which interfaces between BCrypt and the LoginController
 * @author      Martin Anker <ankem1 @ students.bfh.ch>
 * @version     0.9
 */
public class PasswordManager {
    public PasswordManager() {

    }

    /**
     * Generates a hashed String form a normal String (password)
     * @param pw unhashed password
     * @return the hashes and salted password
     */
    String getHash(String pw) {
        return BCrypt.hashpw(pw, BCrypt.gensalt());
    }



    /**
     * Checks if the entered password is valid
     * @param mitarbeiter_id ID of the login vredentials
     * @param passwort password of the Login credentials
     * @return returns true if login credentials are valid
     */
    public boolean checkCredentials(int mitarbeiter_id, String passwort) {
        dbAccess dba = new dbAccess();
        return  BCrypt.checkpw(passwort, dba.getPassHash(mitarbeiter_id));
}
}
