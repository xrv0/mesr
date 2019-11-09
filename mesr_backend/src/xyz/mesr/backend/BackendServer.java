package xyz.mesr.backend;

import xyz.mesr.backend.log.Logger;
import xyz.mesr.backend.user.User;
import java.util.ArrayList;

/**
 * Class representing the whole backend server
 */
public class BackendServer extends Thread{
    private final ArrayList<User> loadedUsers;
    private final int port = 4455;

    public BackendServer() {
        this.loadedUsers = new ArrayList<>();
    }

    /**
     * Is run when the server is started
     */
    @Override
    public void run() {
        Logger.INSTANCE.information("Started Backend server! Thread: {" + this.getName() + "}");
        super.run();
    }
}
