package xyz.mesr.client;

import xyz.mesr.client.key.Keypair;
import xyz.mesr.client.message.Dialog;

import java.util.ArrayList;

public class Client {
    private Keypair keypair;
    private ArrayList<Dialog> dialogs;

    public Client() {
        this.dialogs = new ArrayList<>();
    }
}
