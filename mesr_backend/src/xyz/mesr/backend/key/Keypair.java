package xyz.mesr.backend.key;

/*
 * Keypair containing the private and public key
 */
public class Keypair {
    private final String key_private;
    private final String key_public;

    public Keypair(String key_private, String key_public) {
        this.key_private = key_private;
        this.key_public = key_public;
    }

    public String getPrivate() {
        return this.key_private;
    }

    public String getPublic() {
        return this.key_public;
    }
}
