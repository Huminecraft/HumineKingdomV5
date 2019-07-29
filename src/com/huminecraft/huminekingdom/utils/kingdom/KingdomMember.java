package com.huminecraft.huminekingdom.utils.kingdom;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.json.simple.JSONObject;

import java.util.UUID;

public class KingdomMember {

    private OfflinePlayer player;
    private String gradeName;

    public KingdomMember(OfflinePlayer player, String gradeName) {
        this.player = player;
        this.gradeName = gradeName;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public String getGradeName() {
        return gradeName;
    }

    public String toJSON() {
        return "{ \"uuid\": \""+getPlayer().getUniqueId().toString()+"\", \"grade\": \""+getGradeName()+"\" }";
    }

    public static KingdomMember fromJSON(JSONObject member) {
        String uuid = (String) member.get("uuid");
        String grade = (String) member.get("grade");
        return new KingdomMember(Bukkit.getOfflinePlayer(UUID.fromString(uuid)), grade);
    }

}
