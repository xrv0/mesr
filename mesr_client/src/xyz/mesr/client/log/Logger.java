package xyz.mesr.client.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Logger {
    public static final Logger INSTANCE = new Logger();
    private ArrayList<String> messages;

    public Logger() {
        this.messages = new ArrayList<>();
    }

    public void error(String message) {
        this.messages.add(LocalDateTime.now().toString() + " : [Message]" + message);
        System.err.println("[Error] " + message);
    }

    public void information(String message) {
        this.messages.add(LocalDateTime.now().toString() + " : [Information] " + message);
        System.out.println("[Information] " + message);
    }

    public void warning(String message) {
        this.messages.add(LocalDateTime.now().toString() + " : [Warning] " + message);
        System.out.println("[Warning] " + message);
    }

    public void save(File log) {
        try {
            FileWriter logWriter = new FileWriter(log);
            for(String message : messages) {
                logWriter.append(message);
            }
        } catch (IOException e) {
            this.error("There was an error while saving the log to file!");
            e.printStackTrace();
        }
    }
}
