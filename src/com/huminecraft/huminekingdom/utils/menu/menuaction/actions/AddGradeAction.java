package com.huminecraft.huminekingdom.utils.menu.menuaction.actions;

import com.huminecraft.huminekingdom.HumineKingdom;
import com.huminecraft.huminekingdom.utils.menu.menuaction.ButtonAction;

public class AddGradeAction extends ButtonAction {

    public AddGradeAction() {
        super("add-grade", (menu, item) -> {
            HumineKingdom.getMessageManager().play("grade-enter-name", menu.getPlayer());
            HumineKingdom.getKingdomManager().getPlayerKingdom(menu.getPlayer()).getKingdomGradeManager().addPlayerNameCatcher(menu.getPlayer());
            menu.closePlayerMenu();
        });
    }
}
