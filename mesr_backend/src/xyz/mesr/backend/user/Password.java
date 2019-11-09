package xyz.mesr.backend.user;

import jdk.jshell.spi.ExecutionControl;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Class for storing a bcrypt encrypted users password
 */
public class Password {
    private String hash; // Stores the bcrypt hash of the password

    /**
     * Constructor for creating a password at sign up or password change
     * @param plainText
     */
    public Password(String plainText) {
        var salt = BCrypt.gensalt(12); // Generate salt
        this.hash = BCrypt.hashpw(plainText, salt);
    }

    /*
    TODO: Add sql functionlity
     */
    /**
     * Load a password from database given the users ID
     * @param id
     */
    public Password(byte[] id) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Sql functionility not implemented yet");
    }

    /**
     * Check if the password hashed matches a given plaintext password
     * bcrypt_hash(input, salt) equals password_hash
     * @param input
     * @return
     */
    public boolean check(String input) {
        return BCrypt.checkpw(input, hash);
    }
}
