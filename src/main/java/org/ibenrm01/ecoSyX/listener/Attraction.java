package org.ibenrm01.ecoSyX.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.ibenrm01.ecoSyX.YAMLconfig.Money;
import org.ibenrm01.ecoSyX.api.EcoSystem;

public class Attraction implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        Boolean statue = EcoSystem.getInstance().newUser(p.getUniqueId().toString());
        if (!(statue)) {
            EcoSystem.getInstance().createData(p.getUniqueId().toString());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        Boolean statue = EcoSystem.getInstance().newUser(p.getUniqueId().toString());
        if (statue) {
            Money.getInstance().save();
        }
    }
}
