package org.ibenrm01.ecoSyX.YAMLconfig;

import org.bukkit.configuration.file.YamlConfiguration;
import org.ibenrm01.ecoSyX.EcoSyX;

import java.io.File;
import java.io.IOException;

public class Money {

    // Singleton instance
    private final static Money instance = new Money();

    private File file;
    private YamlConfiguration config;

    private String[] money;

    // Private constructor
    private Money() {
    }

    // Load the money.yml file
    public void load() {
        file = new File(EcoSyX.getInstance().getDataFolder(), "player/money.yml");

        // Create file if it doesn't exist
        if (!file.exists()) {
            EcoSyX.getInstance().saveResource("player/money.yml", false);
        }

        // Load the configuration from file
        config = YamlConfiguration.loadConfiguration(file);

    }

    // Save the configuration to the file
    public void save() {
        try {
            config.save(file);
        } catch (IOException ex) {
            System.out.println("[EcoSyX] Failed to save money.yml");
            ex.printStackTrace();
        }
    }

    public String[] getMoney() {
        return money;
    }

    // Get the config object
    public YamlConfiguration getConfig() {
        return config;
    }

    // Get the singleton instance
    public static Money getInstance() {
        return instance;
    }
}
