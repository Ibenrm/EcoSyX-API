package org.ibenrm01.ecoSyX;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.ibenrm01.ecoSyX.YAMLconfig.Money;
import org.ibenrm01.ecoSyX.YAMLconfig.lang;
import org.ibenrm01.ecoSyX.YAMLconfig.settings;
import org.ibenrm01.ecoSyX.commands.MoneyCommand;
import org.ibenrm01.ecoSyX.listener.Attraction;

public final class EcoSyX extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("Ekonomi Server Enabled");

        settings.getInstance().load();
        Money.getInstance().load();
        lang.getInstance().load(settings.getInstance().getConfig().getString("lang"));

        getCommand("economy").setExecutor(new MoneyCommand());
        getServer().getPluginManager().registerEvents(new Attraction(), this);
    }


    @Override
    public void onDisable() {
        getLogger().info("Ekonomi Server Disabled");

        settings.getInstance().save();
        Money.getInstance().save();
    }

    public static EcoSyX getInstance() {
        return getPlugin(EcoSyX.class);
    }
}
