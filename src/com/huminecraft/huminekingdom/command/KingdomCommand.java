package com.huminecraft.huminekingdom.command;

import com.huminecraft.huminekingdom.HumineKingdom;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KingdomCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String lbl, String[] args) {
        if (commandSender instanceof Player) {
            if (lbl.equals("kingdom")) {
                Player player = ((Player) commandSender).getPlayer();
                if (HumineKingdom.getKingdomManager().getPlayerKingdom(player) == null) {
                    System.out.println(player.getName()+" open no-kingdom...");
                    HumineKingdom.getKingdomMenuManager().getKingdomMenu("no-kingdom").openMenu(player);
                } else {
                    HumineKingdom.getKingdomMenuManager().getKingdomMenu("kingdom-main").openMenu(player);
                }
            }
            return true;
        } else {
            System.out.println("Vous ne pouvez utiliser cette commande que en jeu.");
        }

        return false;
    }

}
