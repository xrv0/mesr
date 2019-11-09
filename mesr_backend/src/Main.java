import xyz.mesr.backend.BackendServer;
import xyz.mesr.backend.log.Logger;

public class Main {
    public static void main(String args[]) {
        Logger.INSTANCE.error("Starting...");

        /*
         * Start the backend server
         */
        BackendServer server = new BackendServer();
        server.run();

        while(server.isAlive()){}
    }
}