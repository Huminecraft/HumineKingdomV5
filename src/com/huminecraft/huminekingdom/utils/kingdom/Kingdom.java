package com.huminecraft.huminekingdom.utils.kingdom;

import com.huminecraft.huminekingdom.HumineKingdom;
import com.huminecraft.huminekingdom.utils.grade.GradePermission;
import com.huminecraft.huminekingdom.utils.grade.KingdomGrade;
import com.huminecraft.huminekingdom.utils.grade.KingdomGradeManager;
import com.huminecraft.huminekingdom.utils.zone.ShieldManager;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.io.File;
import java.util.UUID;

public class Kingdom {

    private File file;

    //Kingdom stats
    private String name;
    private int glory;
    private ShieldManager shieldManager;
    private boolean egg;

    //Players stats
    private OfflinePlayer king;
    private String kingGradeName;
    private ArrayList<KingdomMember> members;
    private KingdomGradeManager kingdomGradeManager;

    public Kingdom(String name, OfflinePlayer king, String kingGradeName) {
        this.name = name;
        this.glory = 0;
        this.king = king;
        this.kingGradeName = kingGradeName;
        this.members = new ArrayList<KingdomMember>();

        this.members.add(new KingdomMember(king, kingGradeName));

        shieldManager = new ShieldManager();
        kingdomGradeManager = new KingdomGradeManager();

        KingdomGrade kingGrade = new KingdomGrade(kingGradeName);

        for (GradePermission gp : HumineKingdom.getPermissionManager().getPermissions()) {
            kingGrade.addPermission(gp);
        }

        kingdomGradeManager.addGrade(kingGrade);

        egg = false;

        file = new File("plugins/HumineKingdom/kingdoms/"+name.replace(" ", "_")+".json");
    }

    public Kingdom(String name, int glory, ShieldManager shieldManager, boolean egg, OfflinePlayer king, String kingGradeName, ArrayList<KingdomMember> members, KingdomGradeManager kingdomGradeManager) {
        this.name = name;
        this.glory = glory;
        this.shieldManager = shieldManager;
        this.egg = egg;
        this.king = king;
        this.kingGradeName = kingGradeName;
        this.members = members;
        this.kingdomGradeManager = kingdomGradeManager;
        file = new File("plugins/HumineKingdom/kingdoms/"+name.replace(" ", "_")+".json");
    }

    //FUNCTIONS


    public void delete() {

    }

    public void save() {
        HumineKingdom.getKingdomManager().registerInFile(this);
    }

    // GETTERS AND SETTERS

    public String toJSON() {
        String json = "{ \"name\": \""+name+"\", \"glory\": \""+glory+"\", \"king-grade\": \""+kingGradeName+"\", \"egg\": \""+egg+"\", "+kingdomGradeManager.toJSON()+", "+shieldManager.toJSON()+", \"members\": [ ";
        for (KingdomMember km : members) {
            json+=km.toJSON()+", ";
        }
        json = json.substring(0, json.lastIndexOf(", "));
        json += "] }";
        return json;
    }

    public static Kingdom fromJSON(String json) throws ParseException {

        JSONObject root = (JSONObject) new JSONParser().parse(json);

        String name = (String) root.get("name");
        int glory = Integer.parseInt((String) root.get("glory"));
        String kingGradeName = (String) root.get("king-grade");
        boolean egg = Boolean.getBoolean((String) root.get("egg"));

        OfflinePlayer king = null;

        KingdomGradeManager kingdomGradeManager = KingdomGradeManager.fromJSON((JSONArray) root.get("grades"));
        ShieldManager shieldManager = ShieldManager.fromJSON((JSONArray) root.get("shields"));

        ArrayList<KingdomMember> kingdomMembers = new ArrayList<KingdomMember>();
        JSONArray list = (JSONArray) root.get("members");
        for (int i = 0 ; i < list.size() ; i++) {
            KingdomMember member = KingdomMember.fromJSON((JSONObject) list.get(i));
            kingdomMembers.add(member);
            if (member.getGradeName().equals(kingGradeName)) {
                king = member.getPlayer();
            }
        }

        return new Kingdom(name, glory, shieldManager, egg, king, kingGradeName, kingdomMembers, kingdomGradeManager);
    }

    public String getName() {
        return name;
    }

    public int getGlory() {
        return glory;
    }

    public OfflinePlayer getKing() {
        return king;
    }

    public String getKingGradeName() {
        return kingGradeName;
    }

    public KingdomGradeManager getKingdomGradeManager() {
        return kingdomGradeManager;
    }

    public ArrayList<KingdomMember> getMembers() {
        return members;
    }

    public File getFile() {
        return file;
    }

}
