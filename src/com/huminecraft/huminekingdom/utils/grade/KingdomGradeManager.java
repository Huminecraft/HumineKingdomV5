package com.huminecraft.huminekingdom.utils.grade;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class KingdomGradeManager {

    private ArrayList<OfflinePlayer> playerGradeNameCatcher;
    private ArrayList<KingdomGrade> kingdomGrades;

    public KingdomGradeManager() {
        this.kingdomGrades = new ArrayList<KingdomGrade>();
        this.playerGradeNameCatcher = new ArrayList<OfflinePlayer>();
    }

    public void addGrade(KingdomGrade kingdomGrade) {
        kingdomGrades.add(kingdomGrade);
    }

    public void remove(KingdomGrade kingdomGrade) {
        kingdomGrades.remove(kingdomGrade);
    }

    public ArrayList<KingdomGrade> getKingdomGrades() {
        return kingdomGrades;
    }

    public KingdomGrade getKingdomGrade(String name) throws KingdomGradeNotFoundError {

        for (ChatColor color : ChatColor.values()) {
            name = name.replace(color.toString(), "");
        }

        for (KingdomGrade grades : kingdomGrades) {
            if (grades.getName().equals(name)) {
                return grades;
            }
        }
        throw new KingdomGradeNotFoundError(name);
    }

    public String toJSON() {
        String json = "\"grades\": [ ";
        for (KingdomGrade kg : getKingdomGrades()) {
            json+=kg.toJSON()+", ";
        }
        json = json.substring(0, json.lastIndexOf(", "));
        json+=" ]";

        return json;
    }

    public static KingdomGradeManager fromJSON(JSONArray grade) throws ParseException {
        KingdomGradeManager kingdomGradeManager = new KingdomGradeManager();
        for (int i = 0 ; i < grade.size() ; i++) {
            kingdomGradeManager.addGrade(KingdomGrade.fromJSON((JSONObject) grade.get(i)));
        }
        return kingdomGradeManager;
    }

    public void addPlayerNameCatcher(OfflinePlayer player) {
        playerGradeNameCatcher.add(player);
    }

    public void removePlayerNameCatcher(OfflinePlayer player) {
        for (int i = 0 ; i < playerGradeNameCatcher.size() ; i++) {
            if (playerGradeNameCatcher.get(i).getName().equals(player.getName())) {
                playerGradeNameCatcher.remove(i);
                i--;
            }
        }
    }

    public ArrayList<OfflinePlayer> getPlayerGradeNameCatcher() {
        return playerGradeNameCatcher;
    }

    public boolean isValideName(String name) {
        return true;
    }

    public boolean gradeNameCatcher(Player player) {
        for (OfflinePlayer pls : playerGradeNameCatcher) {
            if (pls.getName().equals(player.getName())) {
                return true;
            }
        }
        return false;
    }

}
