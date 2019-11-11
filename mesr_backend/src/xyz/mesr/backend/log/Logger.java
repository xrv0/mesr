package xyz.mesr.backend.log;

import xyz.mesr.StringUtilties;

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
     * Prints out an error message
     * @param message Provides more information
     * @return the printed message
     */
    public String error(String message) {
        var output = ERROR_PREFIX + message;
        output = output.replace("\n", "\n" + StringUtilties.repeat(ERROR_PREFIX.length(), " "));
        System.err.println(output);
        this.messages.add(LocalDateTime.now().toString() + " : " + output);
        return output;
    }

    /**
     * Prints out an error message and provides more information on the stacktrace
     * @param message Provides more information
     * @param exception Caught exception
     * @return the printed message
     */
    public String error(String message, Exception exception) {
        var output = ERROR_PREFIX + message + "\nMore infos on the printed stacktrace: " + exception.getMessage();
        output = output.replace("\n", "\n" + StringUtilties.repeat(ERROR_PREFIX.length(), " "));
        System.err.println(output);
        this.messages.add(LocalDateTime.now().toString() + " : " + output);
        return output;
    }

    /**
     * Prints out an information message
     * @param message Provides more information
     * @return the printed message
     */
    public String information(String message) {
        var output = INFORMATION_PREFIX + message;
        output = output.replace("\n", "\n" + StringUtilties.repeat(INFORMATION_PREFIX.length(), " "));
        System.out.println(output);
        this.messages.add(LocalDateTime.now().toString() + " : " + output);
        return output;
    }

    /**
     * Prints out a warning message
     * @param message Provides more information
     * @return the printed message
     */
    public String warning(String message) {
        var output = WARNING_PREFIX + message;
        System.out.println(output);
        this.messages.add(LocalDateTime.now().toString() + " : " + output);
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

    /**
     * Getter for messages
     * @return all temporary stored messages
     */
    public ArrayList<String> getMessages() {
        return messages;
    }
}
