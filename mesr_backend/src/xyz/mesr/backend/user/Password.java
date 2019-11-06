package xyz.mesr.backend.user;

import org.mindrot.jbcrypt.BCrypt;

public class Password {
    private String hash;
    private String salt;

    /*
    Constructor for creating a password at sign up or password change
     */
    public Password(String plainText) {
        this.salt = BCrypt.gensalt(12); // Generate salt
        this.hash = BCrypt.hashpw(plainText, this.salt);
    }

    /*
    Constructor for loading password from database
     */
    public Password(long ID) {
        //Load pw from database
    }

    public boolean compare(String input) {
        return BCrypt.checkpw(input, hash);
    }
}
