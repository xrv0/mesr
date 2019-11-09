package xyz.mesr.backend.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Logger class for managing all warnings, errors, and information
 */
public class Logger {
    public static final Logger INSTANCE = new Logger();
    private ArrayList<String> messages;

    public Logger() {
        this.messages = new ArrayList<>();
    }

    /**
     * Prints out a error message and stores it temporarily
     */
    public void error(String message) {
        this.messages.add(LocalDateTime.now().toString() + " : [Message]" + message);
        System.err.println("[Error] " + message);
    }

    /**
     * Prints out a information message and stores it temporarily
     */
    public void information(String message) {
        this.messages.add(LocalDateTime.now().toString() + " : [Information] " + message);
        System.out.println("[Information] " + message);
    }

    /**
     * Prints out a warning message and stores it temporarily
     */
    public void warning(String message) {
        this.messages.add(LocalDateTime.now().toString() + " : [Warning] " + message);
        System.out.println("[Warning] " + message);
    }

    /**
     * Saves all logs for the current session to a file
     * THE FILE WILL BE OVERWRITTEN
     */
    public void saveToFile(File output) {
        try {
            FileWriter logWriter = new FileWriter(output);
            for(String message : messages) {
                logWriter.append(message + "\n");
            }
            logWriter.flush();
            logWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
