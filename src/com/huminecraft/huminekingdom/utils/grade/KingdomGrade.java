package com.huminecraft.huminekingdom.utils.grade;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class KingdomGrade {

    private String name;
    private ArrayList<GradePermission> permissions;

    public KingdomGrade(String name) {
        this.name = name;
        permissions = new ArrayList<GradePermission>();
    }

    public String getName() {
        return name;
    }

    public void addPermission(GradePermission gradePermission) {
        permissions.add(gradePermission);
    }

    public void removePermission(GradePermission gradePermission) {
        for (int i = 0 ; i < permissions.size() ; i++) {
            if (permissions.get(i).getName().equals(gradePermission.getName())) {
                permissions.remove(i);
                i--;
            }
        }
    }

    public ArrayList<GradePermission> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(GradePermission permission) {
        return hasPermission(permission.getName());
    }

    public boolean hasPermission(String permission) {
        for (GradePermission perm : permissions) {
            if (perm.getName().equals(permission)) {
                return true;
            }
        }
        return false;
    }

    public String toJSON() {
        String json = "{ \"name\": \""+getName()+"\", \"permissions\": [ ";
        for (GradePermission gp: permissions) {
            json += gp.toJSON()+", ";
        }
        if (permissions.size() > 0)
            json = json.substring(0, json.lastIndexOf(", "));
        json +=" ] }";
        return json;
    }

    public static KingdomGrade fromJSON(JSONObject grade) throws ParseException {
        String name = (String) grade.get("name");
        KingdomGrade kingdomGrade = new KingdomGrade(name);
        JSONArray perms = (JSONArray) grade.get("permissions");
        for (int i = 0 ; i < perms.size() ; i++) {
            kingdomGrade.addPermission(GradePermission.fromJSON((JSONObject) perms.get(i)));
        }
        return kingdomGrade;
    }

}
