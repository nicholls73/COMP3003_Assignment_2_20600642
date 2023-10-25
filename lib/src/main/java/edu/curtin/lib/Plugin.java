package edu.curtin.lib;

import java.util.*;

public class Plugin {
    private String id;
    private Dictionary<String, String> arguments;

    public Plugin() {
        arguments = new Hashtable<>();
    }

    public Plugin(String id) {
        this.id = id;
        arguments = new Hashtable<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addArgument(String key, String value) {
        arguments.put(key, value);
    }

    public String getArgument(String key) {
        return arguments.get(key);
    }

    public Dictionary<String, String> getArguments() {
        return arguments;
    }
}