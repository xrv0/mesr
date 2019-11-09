package xyz.mesr.backend.key;

/**
 * Class for representing an encrypted keypair of an user
 */
public class KeypairEncrypted {
    private final String keyPrivateEncrypted, keyPublic;

    /**
     * @param keyPrivateEncrypted
     * @param keyPublic
     */
    public KeypairEncrypted(String keyPrivateEncrypted, String keyPublic) {
        this.keyPrivateEncrypted = keyPrivateEncrypted;
        this.keyPublic = keyPublic;
    }

    /**
     * @return private encrypted key
     */
    public String getPrivateEncrypted() {
        return this.keyPrivateEncrypted;
    }

    /**
     * @return public key
     */
    public String getPublic() {
        return this.keyPublic;
    }
}
