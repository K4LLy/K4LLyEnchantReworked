package main.java.de.k4lly.enchant.controller;

import main.java.de.k4lly.enchant.Main;

public class PluginController {
    private ConfigController config;
    private Main main;

    public ConfigController getConfig() {
        return config;
    }

    public void setConfig(ConfigController config) {
        this.config = config;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}