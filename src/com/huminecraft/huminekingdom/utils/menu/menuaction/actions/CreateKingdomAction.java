package com.huminecraft.huminekingdom.utils.menu.menuaction.actions;

import com.huminecraft.huminekingdom.HumineKingdom;
import com.huminecraft.huminekingdom.utils.menu.menuaction.ButtonAction;

public class CreateKingdomAction extends ButtonAction {

    public CreateKingdomAction() {
        super("create-kingdom", (menu, item ) -> {
            menu.closePlayerMenu();
            HumineKingdom.getMessageManager().play("kingdom-enter-name", menu.getPlayer());
            HumineKingdom.getKingdomManager().getPlayerKingdomNameCatcher().add(menu.getPlayer());
        });
    }
}
