package com.huminecraft.huminekingdom.utils.messages.variable;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class VariableManager {

    private ArrayList<PlayerVariable> playerVariables;

    public VariableManager() {
        this.playerVariables = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        playerVariables.add(new PlayerVariable(player));
    }

    public PlayerVariable getPlayerVariable(OfflinePlayer player) {
        for (PlayerVariable pv : playerVariables) {
            if (pv.getPlayer().getName().equals(player.getName())) {
                return pv;
            }
        }
        addPlayer(player.getPlayer());
        return getPlayerVariable(player);
    }

}
