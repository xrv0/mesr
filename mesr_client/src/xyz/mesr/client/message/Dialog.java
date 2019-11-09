package xyz.mesr.client.message;

import xyz.mesr.client.user.ChatUser;
import java.util.ArrayList;

/*
 * Class for representing a dialog with a single other chat partner
 */
public class Dialog {
    private ArrayList<Message> messages;
    private ChatUser recicepent;
    private byte[] ID; // 8 char unique dialog ID

    public Dialog(ChatUser recipient, byte[] id) {
        this.recicepent = recipient;
        this.ID = id;
    }
}
