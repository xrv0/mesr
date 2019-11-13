package xyz.mesr.backend.conf;

import xyz.mesr.backend.log.Logger;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

/**
 * Java properties wrapper
 */
public class Configuration extends Properties {
    private File file;
    private String description;
    private Hashtable<String, String> comments;

    /**
     * @param file Config file
     * @param description Describes the use of the config and is appended to the beggining of the configuration file
     */
    public Configuration(File file, String description) {
        this.file = file;
        this.description = description;
        this.comments = new Hashtable<>();

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Logger.INSTANCE.error("There was an error when creating a configuration file", e);
            }
        }
        try {
            this.load(new FileInputStream(this.file));
        } catch (IOException e) {
            Logger.INSTANCE.error("There was an exception while reading a configuration file", e);
        }
    }

    /**
     * @param file Config file
     */
    public Configuration(File file) {
        this(file, null);
    }

    /**
     * @param path Path to the config file as String
     */
    public Configuration(String path) {
        this(new File(path));
    }

    /**
     * Goes through all fields from object, loads their values and saves them to the configuration file
     * @param object Object to load the values from
     */
    public void saveValues(Object object) {
        for(Field field : object.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(ConfigurationValue.class)) { // Only access the field when ConfigurationValue annotation is present
                if(Modifier.isPrivate(field.getModifiers())) field.setAccessible(true); // Make the field accessible
                if(Modifier.isFinal(field.getModifiers())) continue; // Skip final fields

                ConfigurationValue annotation = field.getAnnotation(ConfigurationValue.class);
                var key = annotation.name();
                try {
                    this.setProperty(key, field.get(object).toString());
                    if(!annotation.description().equals("[unassigned]")) {
                        comments.put(key, annotation.description());
                    }
                } catch (IllegalAccessException e) {
                    Logger.INSTANCE.error("Weird this should not happen :/\n" +
                            "There was an error when accessing a field while loading from configuration Field: " + field.getName(), e);                }
            }
        }

        try {
            this.storeToFile();
        } catch (IOException e) {
            Logger.INSTANCE.error("An error occurred while storing a configuration file", e);
        }
    }

    /**
     * Saves all keys, values and comments to file
     * @throws IOException
     */
    private void storeToFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));
        if (this.description != null) {
            writer.write("# " + (this.description.replace("\n", "\n# "))); // Write comments to file
            writer.newLine();
        }
        writer.write("# Last edited: " + (new Date()).toString());
        writer.newLine();
        synchronized(this) {
            for(Map.Entry<Object, Object> e : this.entrySet()) {
                String key = (String)e.getKey();
                String val = (String)e.getValue();
                if(comments.containsKey(key)) {
                    writer.write("# " + (this.comments.get(key).replace("\n", "\n# ")));
                    writer.newLine();
                }
                writer.write(key + "=" + val);
                writer.newLine();
            }
        }
        writer.flush();
    }

    /**
     * Goes through all fields from objects and sets their values from the configuration
     * @param object Object to set the values to
     */
    public void setObjectValues(Object object) {
        for(Field field : object.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(ConfigurationValue.class)) { // Only access the field when ConfigurationValue annotation is present
                ConfigurationValue annotation = field.getAnnotation(ConfigurationValue.class);
                if(Modifier.isPrivate(field.getModifiers())) field.setAccessible(true); // Make the field accessible
                if(Modifier.isFinal(field.getModifiers())) continue; // Skip final fields
                var key = annotation.name();
                if(containsKey(key)) {
                    Object value = getObject(key, field.getType());
                    if(value != null) {
                        try {
                            field.set(object, value);
                        } catch (IllegalAccessException e) {
                            Logger.INSTANCE.error("There was an error while setting the value of a variable", e);
                        }
                    }
                }
            }
        }
    }

    /**
     * Loads a String value from properties
     * @param key Key of the value
     * @return String value
     */
    public String getString(String key) {
        return getProperty(key);
    }

    /**
     * Loads a Integer value from properties
     * @param key Key of the value
     * @return String value
     */
    public int getInteger(String key) {
        return Integer.parseInt(getProperty(key));
    }

    /**
     * Loads a float value from properties
     * @param key Key of the value
     * @return float value
     */
    public float getFloat(String key) {
        return Float.parseFloat(getProperty(key));
    }

    /**
     * Loads a double value from properties
     * @param key Key of the value
     * @return double value
     */
    public double getDouble(String key) {
        return Double.parseDouble(getProperty(key));
    }

    /**
     * Loads a char value from properties
     * @param key Key of the value
     * @return char value
     */
    public char getChar(String key) {
        return getProperty(key).charAt(0);
    }

    /**
     * Loads a boolean value from properties
     * @param key Key of the value
     * @return boolean value
     */
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    /**
     * Tries to get an appropriate value by the provided type
     * @param key Key of the value
     * @param type Value type
     * @return Found value with appropriate type or null of none is found
     */
    public Object getObject(String key, Class<?> type) {
        if(type.equals(String.class)) {
            return getString(key);
        }else if(type.equals(int.class)) {
            return getInteger(key);
        }else if(type.equals(float.class)) {
            return getFloat(key);
        }else if(type.equals(double.class)) {
            return getDouble(key);
        }else if(type.equals(boolean.class)) {
            return getBoolean(key);
        }else if(type.equals(char.class)) {
            return getChar(key);
        }
        return null;
    }
}
