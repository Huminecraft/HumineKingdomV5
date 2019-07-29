package com.huminecraft.huminekingdom.utils.kingdom;

import com.huminecraft.humineaypi.manager.FileManager;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class KingdomManager {


    private ArrayList<OfflinePlayer> playerKingdomNameCatcher;
    private ArrayList<OfflinePlayer> playerMemberNameCatcher;
    private ArrayList<Kingdom> kingdoms;

    public KingdomManager() {
        this.kingdoms = new ArrayList<Kingdom>();
        this.playerKingdomNameCatcher = new ArrayList<OfflinePlayer>();
        this.playerMemberNameCatcher = new ArrayList<OfflinePlayer>();
        File kingdomsFile = new File("plugins/HumineKingdom/kingdoms");
        kingdomsFile.mkdirs();
        for (File kingdomFile : kingdomsFile.listFiles()) {
            try {
                generateKingdom(kingdomFile);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void createKingdom(String kingdomName, OfflinePlayer king, String kingGradeName) {
        Kingdom kingdom = new Kingdom(kingdomName, king, kingGradeName);
        this.kingdoms.add(kingdom);
        kingdom.save();
    }

    public void generateKingdom(File file) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath()), "UTF-8"));
        String line;
        String jsonString = "";
        while ((line = br.readLine()) != null) {
            jsonString+=line;
        }
        br.close();

        kingdoms.add(Kingdom.fromJSON(jsonString));
    }

    public void deleteKingdom(Kingdom kingdom) {
        kingdoms.remove(kingdom);
        kingdom.getFile().delete();
        kingdom.delete();
    }

    public void registerInFile(Kingdom kingdom) {
        kingdom.getFile().delete();
        new FileManager(kingdom.getFile()).printLine(kingdom.toJSON());
    }

    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
    }

    public ArrayList<OfflinePlayer> getPlayerKingdomNameCatcher() {
        return playerKingdomNameCatcher;
    }

    public ArrayList<OfflinePlayer> getPlayerMemberNameCatcher() {
        return playerMemberNameCatcher;
    }

    public boolean kingdomNameCatcher(Player player) {
        for (OfflinePlayer pls : getPlayerKingdomNameCatcher()) {
            if (pls.getName().equals(player.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean kingdomMemberNameCatcher(Player player) {
        for (OfflinePlayer pls : getPlayerMemberNameCatcher()) {
            if (pls.getName().equals(player.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isValideName(String name) {
        return true;
    }

    public Kingdom getPlayerKingdom(OfflinePlayer player) {
        for (Kingdom k : kingdoms) {
            for (KingdomMember m : k.getMembers()) {
                if (m.getPlayer().getName().equals(player.getName())) {
                    return k;
                }
            }
        }
        return null;
    }

}
