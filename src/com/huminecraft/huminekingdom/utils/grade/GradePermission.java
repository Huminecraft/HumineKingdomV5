package com.huminecraft.huminekingdom.utils.grade;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class GradePermission {

    private String name;
    private ArrayList<String> description;

    public GradePermission(String name, ArrayList<String> description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public String toJSON() {
        String json = "{ \"name\": \""+name+"\", \"lore\": [ ";
        for (String str : description) {
            json+=str+", ";
        }
        if (description.size() != 0)
            json = json.substring(0, json.lastIndexOf(", "));
        json += "] }";
        return json;
    }

    public static GradePermission fromJSON(JSONObject permission) {
        String name = (String) permission.get("name");
        ArrayList<String> desc = new ArrayList<>();
        JSONArray array = (JSONArray) permission.get("lore");
        for (int i = 0 ; i < array.size() ; i++) {
            desc.add((String) array.get(i));
        }
        return new GradePermission(name, desc);
    }

}
