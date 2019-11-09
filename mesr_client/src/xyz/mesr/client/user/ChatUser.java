package xyz.mesr.client.user;

import jdk.jshell.spi.ExecutionControl;

import java.net.URL;
import java.security.PublicKey;

/*
 * Object for representing a user who is not the local_user
 */
public class ChatUser {
    private PublicKey publicKey;
    private String name;
    private URL profileImage;
    private char[] ID; // 8 char unique user ID

    /*
     * Constructor for initzializing a ChatPartner Object given the user ID
     * public_key, name and profile_image will be loaded from the database when required
     */
    public ChatUser(char[] ID) {
        this.ID = ID;
    }

    //TODO: Add sql functionality
    /*
     * Returns the name of the given user
     * Sends an request to the server when needed and updates the temporary variable
     */
    public String getName() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Sql not yet implemented");
    }

    //TODO: Add sql functionality
    /*
     * Returns url to jpg image of given users profile image
     * Sends to the server when needed and updates the temporary variable
     */
    public URL getProfileImage() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Sql not yet implemented");
    }
}
