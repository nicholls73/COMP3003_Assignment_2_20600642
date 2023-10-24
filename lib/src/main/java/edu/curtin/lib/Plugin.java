package edu.curtin.lib;

import java.util.*;

public class Plugin {
    private String Id;
    private Hashtable<String, String> arguments;

    public Plugin() {
        arguments = new Hashtable<>();
    }

    public Plugin(String Id) {
        this.Id = Id;
        arguments = new Hashtable<>();
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public void addArgument(String key, String value) {
        arguments.put(key, value);
    }

    public String getArgument(String key) {
        return arguments.get(key);
    }
}