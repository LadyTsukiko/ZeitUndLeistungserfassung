package database;

/**
 * Created by Martin on 23.05.2017.
 */
public class PasswordManager {
    public PasswordManager(){

    }

    public String getHash(String pw){
        return BCrypt.hashpw(pw, BCrypt.gensalt());
    }
}
