package xyz.mesr.backend.user;

import org.mindrot.jbcrypt.BCrypt;
import xyz.mesr.backend.log.Logger;

/**
 * Class for storing a bcrypt encrypted users password
 */
public class Password {
    private String hash; // Stores the bcrypt hash of the password

    /**
     * Constructor for creating a password at sign up or password change
     * @param Password provided by user in plaintext
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
     * @param id Users id
     */
    public Password(byte[] id) {
        Logger.INSTANCE.error("SQL not yet implemented");
    }

    /**
     * bcrypt_hash(input, salt) equals password_hash
     * @param input Plaintext password to check
     * @return If the given password matches the saved hash
     */
    public boolean check(String input) {
        return BCrypt.checkpw(input, hash);
    }
}
