package xyz.mesr.client;

import com.sun.tools.javac.Main;
import xyz.mesr.client.key.Keypair;
import xyz.mesr.client.message.Dialog;
import xyz.mesr.client.ui.MainUI;
import xyz.mesr.client.ui.components.GradientPanel;
import xyz.mesr.client.user.ChatUser;
import xyz.mesr.client.user.Config;

import java.util.ArrayList;

public class Client {
    private Keypair keypair;
    private ArrayList<Dialog> dialogs;
    private boolean newUser = false;
    public Config config;
    public Client() {
        this.dialogs = new ArrayList<>();
        this.config = new Config(new ChatUser("NJOOO".toCharArray()), this);
        MainUI mainUI = new MainUI(this);

    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public Config getConfig() {
        return config;
    }
}
