package com.huminecraft.huminekingdom.utils.zone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Shield {

    private Location location;

    public Shield(Location location) {
        this.location = location;
    }

    public String toJSON() {
        return "{ \"location\": { \"world\": "+location.getWorld()+", \"x\": "+location.getBlockX()+", \"y\": "+location.getBlockY()+", \"z\": "+location.getBlockZ()+" }";
    }

    public static Shield fromJSON(String json) throws ParseException {
        JSONObject jo = (JSONObject) new JSONParser().parse(json);
        return new Shield(new Location(Bukkit.getWorld((String) jo.get("world")), Integer.parseInt((String) jo.get("x")), Integer.parseInt((String) jo.get("y")), Integer.parseInt((String) jo.get("z"))));
    }

}
