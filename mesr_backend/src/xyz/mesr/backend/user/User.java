package xyz.mesr.backend.user;

import xyz.mesr.backend.key.KeypairEncrypted;

/**
 * Class for representing a user
 */
public class User {
    private byte[] ID; // 8 char long user ID
    private Password password; // User login password
    private KeypairEncrypted keypairEncrypted; // User keypair

    /**
     * Create user given the user ID
     * @param id
     */
    public User(byte[] id) {
        this.ID= id;
    }

    /*
    TODO: Add sql functionlity
     */
    /**
     * Accesses the temp variable when possible, when run the first time it is loaded from the sql database
     * @return encrypted keypair
     */
    public KeypairEncrypted getEncryptedKeypair() {
        if(keypairEncrypted != null) {
            return keypairEncrypted;
        }else {
            System.err.println("The encrypted key pair is not yet loaded!" +
                    "Requesting from sql database...");
            // Request keypair from database
        }
        return null;
    }
}
