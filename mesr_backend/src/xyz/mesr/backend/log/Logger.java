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
    /**
     * Default Logger instance
     */
    public static final Logger INSTANCE = new Logger();

    private static final String WARNING_PREFIX = "[Warning] ";
    private static final String ERROR_PREFIX = "[Warning] ";
    private static final String INFORMATION_PREFIX = "[Warning] ";

    private ArrayList<String> messages;

    public Logger() {
        this.messages = new ArrayList<>();
    }

    /**
     * Prints out a error message
     * @param message Provides more information
     * @return the printed message
     */
    public String error(String message) {
        var output = LocalDateTime.now().toString() + " : " + ERROR_PREFIX + message;
        System.err.println(ERROR_PREFIX + message);
        this.messages.add(output);
        return output;
    }

    /**
     * Prints out an information message
     * @param message Provides more information
     * @return the printed message
     */
    public String information(String message) {
        var output = LocalDateTime.now().toString() + " : " + INFORMATION_PREFIX + message;
        this.messages.add(output);
        System.err.println("[Error] " + message);
        return output;
    }

    /**
     * Prints out a warning message
     * @param message Provides more information
     * @return the printed message
     */
    public String warning(String message) {
        var output = LocalDateTime.now().toString() + " : " + WARNING_PREFIX + message;
        this.messages.add(output);
        System.err.println(WARNING_PREFIX + message);
        return output;
    }

    /**
     * Saves all messages from the current session to file
     * @param log File to save the information to
     * @return Was the save successful
     */
    public boolean save(File log) {
        try {
            FileWriter logWriter = new FileWriter(log);
            for(String message : messages) {
                logWriter.append(message);
            }
            return true;
        } catch (IOException e) {
            this.error("There was an error while saving the log to file! [" + e.getMessage() + "]");
            return false;
        }
    }
}
