package com.huminecraft.huminekingdom.utils.menu.menuaction.actions;

import com.huminecraft.huminekingdom.HumineKingdom;
import com.huminecraft.huminekingdom.utils.menu.menuaction.ButtonAction;

public class SendInvitationAction extends ButtonAction {

    public SendInvitationAction() {
        super("send-invitation", (menu, item) -> {
            menu.closePlayerMenu();
            HumineKingdom.getMessageManager().play("invitation-add-player", menu.getPlayer());
            HumineKingdom.getKingdomManager().getPlayerMemberNameCatcher().add(menu.getPlayer());
        });
    }
}
