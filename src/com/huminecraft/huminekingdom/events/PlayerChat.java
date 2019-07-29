package com.huminecraft.huminekingdom.events;

import com.huminecraft.huminekingdom.HumineKingdom;
import com.huminecraft.huminekingdom.utils.grade.KingdomGrade;
import com.huminecraft.huminekingdom.utils.grade.KingdomGradeManager;
import com.huminecraft.huminekingdom.utils.messages.variable.Variable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChat implements Listener {

    @EventHandler
    public void onNameCatcher(PlayerChatEvent e) {

        Player player = e.getPlayer();
        if (HumineKingdom.getKingdomManager().kingdomNameCatcher(player)) {
            e.setCancelled(true);
            if (HumineKingdom.getKingdomManager().isValideName(e.getMessage())) {
                HumineKingdom.getMessageManager().play("kingdom-valide-name", player);
                HumineKingdom.getKingdomManager().createKingdom(e.getMessage(), player, "Roi");
            } else {
                HumineKingdom.getMessageManager().play("error", player);
            }
            HumineKingdom.getKingdomManager().getPlayerKingdomNameCatcher().remove(player);
        }

        if (HumineKingdom.getKingdomManager().getPlayerKingdom(player) != null) {
            KingdomGradeManager kingdomGradeManager = HumineKingdom.getKingdomManager().getPlayerKingdom(player).getKingdomGradeManager();
            if (kingdomGradeManager.gradeNameCatcher(player)) {
                e.setCancelled(true);
                if (kingdomGradeManager.isValideName(e.getMessage())) {
                    HumineKingdom.getMessageManager().play("grade-valide-name", player);
                    kingdomGradeManager.addGrade(new KingdomGrade(e.getMessage()));
                    HumineKingdom.getKingdomManager().getPlayerKingdom(player).save();
                }
                kingdomGradeManager.getPlayerGradeNameCatcher().remove(player);
            }

            if (HumineKingdom.getKingdomManager().kingdomMemberNameCatcher(player)) {
                e.setCancelled(true);
                Player targetPlayer = Bukkit.getPlayer(e.getMessage());
                HumineKingdom.getVariableManager().getPlayerVariable(player).setVariables(new Variable("TARGET_NAME", e.getMessage()));
                if (targetPlayer != null) {
                    HumineKingdom.getMessageManager().play("invitation-send", player);
                    HumineKingdom.getVariableManager().getPlayerVariable(targetPlayer).setVariables(new Variable("SENDER_NAME", player.getDisplayName()));
                    HumineKingdom.getVariableManager().getPlayerVariable(targetPlayer).setVariables(new Variable("SENDER_KINGDOM", HumineKingdom.getKingdomManager().getPlayerKingdom(player).getName()));
                    HumineKingdom.getKingdomMenuManager().getKingdomMenu("kingdom-invitation").openMenu(targetPlayer);
                } else {
                    HumineKingdom.getMessageManager().play("invitation-not-send", player);
                }
                HumineKingdom.getKingdomManager().getPlayerMemberNameCatcher().remove(player);
            }

        }

    }

}
