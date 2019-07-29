package com.huminecraft.huminekingdom;

import com.huminecraft.huminekingdom.command.CommandManager;
import com.huminecraft.huminekingdom.events.EventManager;
import com.huminecraft.huminekingdom.utils.grade.PermissionManager;
import com.huminecraft.huminekingdom.utils.kingdom.KingdomManager;
import com.huminecraft.huminekingdom.utils.menu.KingdomMenuManager;
import com.huminecraft.huminekingdom.utils.messages.MessageManager;
import com.huminecraft.huminekingdom.utils.messages.variable.VariableManager;
import com.huminecraft.huminekingdom.utils.zone.ShieldManager;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class HumineKingdom extends JavaPlugin {

    private static KingdomManager kingdomManager;
    private static ShieldManager zoneManager;
    private static KingdomMenuManager kingdomMenuManager;
    private static MessageManager messageManager;
    private static PermissionManager permissionManager;
    private static VariableManager variableManager;

    public void onEnable() {
        super.onEnable();

        kingdomManager = new KingdomManager();
        zoneManager = new ShieldManager();
        kingdomMenuManager = new KingdomMenuManager();
        messageManager = new MessageManager(new File("plugins/HumineKingdom/messages.json"));
        permissionManager = new PermissionManager();
        variableManager = new VariableManager();

        new EventManager(this);
        new CommandManager(this);
    }

    public void onDisable() {
        super.onDisable();
        System.out.println("HumineKingdom is disable !");
    }

    public static ShieldManager getZoneManager() {
        return zoneManager;
    }

    public static KingdomManager getKingdomManager() {
        return kingdomManager;
    }

    public static KingdomMenuManager getKingdomMenuManager() {
        return kingdomMenuManager;
    }

    public static MessageManager getMessageManager() {
        return messageManager;
    }

    public static PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public static VariableManager getVariableManager() { return variableManager; }
}

