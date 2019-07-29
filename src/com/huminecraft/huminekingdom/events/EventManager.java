package com.huminecraft.huminekingdom.events;

import com.huminecraft.huminekingdom.HumineKingdom;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventManager {

    public EventManager(HumineKingdom pl) {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerChat(), pl);

    }

}
