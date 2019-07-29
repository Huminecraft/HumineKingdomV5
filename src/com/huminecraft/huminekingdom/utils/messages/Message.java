package com.huminecraft.huminekingdom.utils.messages;

import com.huminecraft.huminekingdom.utils.messages.variable.VariableManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

public class Message {

    private String id;
    private String value;
    private Sound sound;

    public Message(String id, String value, Sound sound) {
        this.id = id;
        this.value = value;
        this.sound = sound;
    }

    public String getId() {
        return id;
    }

    public String getMessage(Player player) {
        return new VariableManager().getPlayerVariable(player).parseText(value);
    }

    public Sound getSound() {
        return sound;
    }

    public void play(Player player) {
        player.sendMessage(getMessage(player));
        player.playSound(player.getLocation(), sound, 5,  1);
    }

    public String toJSON() {
        return "{ \"id\": \""+id+"\", \"value\":\""+value+"\""+((sound != null) ? ", \"sound\": \""+sound.name()+"\"" : "")+" }";
    }

    public static Message fromJSON(JSONObject msg) {
        String id = (String) msg.get("id");
        String value = (String) msg.get("value");
        Sound sound = (msg.get("sound") != null) ? Sound.valueOf((String) msg.get("sound")) : null;
        return new Message(id, value, sound);
    }



}
