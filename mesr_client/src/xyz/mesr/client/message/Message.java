package xyz.mesr.client.message;

import xyz.mesr.client.log.Logger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/*
 * This class is responsible for handling an encrypted message.
 * The decryption, signature check and format
 */
public class Message {
    private final String publicKey;
    private final byte[] messageEncrypted, sha1_signed;
    private final String raw;

    /*
     * Parses a raw message and extracts the recipents public_key, the encrypted message and the signed hash
     * Format: corresponding_public_key$message_encrypted_base64$sha1_hash_signed_base64
     */
    public Message(String raw) {
        Base64.Decoder decoder = Base64.getDecoder();
        String[] args = raw.split("$");

        this.raw = raw;
        this.publicKey = args[0];
        this.messageEncrypted = decoder.decode(args[1]);
        this.sha1_signed = decoder.decode(args[2]);
    }

    /*
     * Decrypt the message given the private key
     */
    public void getDecrypt(PrivateKey privateKey) {
        Logger.INSTANCE.error("Encryption not yet implemented");
    }

    /*
     * Decrypt the signed hash using their public key
     * and compares the decrypted message sha1 hash with the decrypted signed hash
     */
    public boolean checkSignature(PublicKey senderPublicKey) {
        Logger.INSTANCE.error("Encryption not yet implemented");
        return false;
    }
}
