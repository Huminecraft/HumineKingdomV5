package com.huminecraft.huminekingdom.utils.menu.menuaction.actions;

import com.huminecraft.huminekingdom.utils.menu.menuaction.ButtonAction;

public class CloseAction extends ButtonAction {

    public CloseAction() {
        super("close", (menu, item) -> menu.closePlayerMenu());
    }
}
