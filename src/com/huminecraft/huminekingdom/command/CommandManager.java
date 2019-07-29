package com.huminecraft.huminekingdom.command;

import com.huminecraft.huminekingdom.HumineKingdom;

public class CommandManager {

    public CommandManager(HumineKingdom pl) {
        pl.getCommand("kingdom").setExecutor(new KingdomCommand());
        pl.getCommand("rank").setExecutor(new RankCommand());
    }
}
