package org.ibenrm01.ecoSyX.YAMLconfig;

import org.bukkit.configuration.file.YamlConfiguration;
import org.ibenrm01.ecoSyX.EcoSyX;

import java.io.File;

public class lang {

    private final static lang instance = new lang();

    private File file;
    private YamlConfiguration config;

    private lang() {

    }

    public void load(String langauges) {
        file = new File(EcoSyX.getInstance().getDataFolder(), "lang/" + langauges + ".yml");
        if (!file.exists()) {
            EcoSyX.getInstance().saveResource("lang/" + langauges + ".yml", false);
        }
        config = new YamlConfiguration();
        config.options().parseComments(true);
        try {
            config.load(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static lang getInstance() {
        return instance;
    }
}
