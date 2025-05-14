package org.ibenrm01.ecoSyX.YAMLconfig;

import org.bukkit.configuration.file.YamlConfiguration;
import org.ibenrm01.ecoSyX.EcoSyX;

import java.io.File;

public class settings {

    private final static settings instance = new settings();

    private File file;
    private YamlConfiguration config;

    private settings() {

    }

    public void load() {
        file = new File(EcoSyX.getInstance().getDataFolder(), "settings.yml");
        if (!file.exists()) {
            EcoSyX.getInstance().saveResource("settings.yml", false);
        }
        config = new YamlConfiguration();
        config.options().parseComments(true);
        try {
            config.load(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public static settings getInstance() {
        return instance;
    }
}
