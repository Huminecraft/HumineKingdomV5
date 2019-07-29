package com.huminecraft.huminekingdom.utils.menu.menuaction.actions;

import com.huminecraft.huminekingdom.HumineKingdom;
import com.huminecraft.huminekingdom.utils.menu.menuaction.ButtonAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RefuseInvitationAction extends ButtonAction {

    public RefuseInvitationAction() {
        super("refuse-invitation", (menu, item) -> {

            String senderName = HumineKingdom.getVariableManager().getPlayerVariable(menu.getPlayer()).getVariable("TARGET_NAME").getValue();
            Player sender = Bukkit.getPlayer(senderName);

            HumineKingdom.getMessageManager().play("refuse-invitation", sender);

        });
    }
}
