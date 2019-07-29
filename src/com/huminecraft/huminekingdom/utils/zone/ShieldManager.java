package com.huminecraft.huminekingdom.utils.zone;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class ShieldManager {

    private ArrayList<Shield> shields;

    public ShieldManager() {
        shields = new ArrayList<Shield>();
    }

    public void addShield(Shield shield) {
        this.shields.add(shield);
    }

    public void removeShield(Shield shield) {
        this.shields.remove(shield);
    }

    public ArrayList<Shield> getShields() {
        return shields;
    }

    public String toJSON() {
        String json = "\"shields\": [ ";
        for (Shield shield : shields) {
            json+=shield.toJSON()+", ";
        }
        if (shields.size() > 0)
            json = json.substring(0, json.lastIndexOf(", "));
        json+=" ]";
        return json;
    }

    public static ShieldManager fromJSON(JSONArray list) throws ParseException {
        ShieldManager shieldManager = new ShieldManager();
        for (int i = 0 ; i < list.size() ; i++) {
            shieldManager.addShield(Shield.fromJSON((String) list.get(i)));
        }
        return shieldManager;
    }

}
