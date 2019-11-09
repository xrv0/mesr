package xyz.mesr.client.key;

import java.security.*;

/**
 * Usually represents the local users keypair
 */
public class Keypair {
    /*
     * Static variables which are only assigned on the first access
     */
    private static final String KEY_PAIR_ALGORITHM = "RSA";
    private static KeyPairGenerator KEY_PAIR_GENERATOR;
    private static SecureRandom KEY_PAIR_RANDOM;

    private final PublicKey keyPublic;
    private final PrivateKey keyPrivate;

    /**
     * Create keypair instance given the public and private key
     * @param keyPublic
     * @param keyPrivate
     */
    public Keypair(PublicKey keyPublic, PrivateKey keyPrivate) {
        this.keyPrivate = keyPrivate;
        this.keyPublic = keyPublic;
    }

    /**
     * Generate a new RSA 512 Bit Keypair using the standard java.security library
     */
    public Keypair() {
        this.createGenerator();
        var keyPair = KEY_PAIR_GENERATOR.generateKeyPair();
        this.keyPrivate = keyPair.getPrivate();
        this.keyPublic = keyPair.getPublic();
    }

    /**
     * Makes sure KEY_PAIR_GENERATOR and KEY_PAIR_RANDOM are properly assigned
     */
    private void createGenerator() {
        try {
            if(KEY_PAIR_GENERATOR == null) {
                KEY_PAIR_GENERATOR = KeyPairGenerator.getInstance(KEY_PAIR_ALGORITHM);
                KEY_PAIR_GENERATOR.initialize(512, KEY_PAIR_RANDOM);
            }
            if(KEY_PAIR_RANDOM == null) KEY_PAIR_RANDOM = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
