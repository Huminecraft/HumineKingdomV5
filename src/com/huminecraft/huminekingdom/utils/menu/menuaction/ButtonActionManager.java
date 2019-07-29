package com.huminecraft.huminekingdom.utils.menu.menuaction;

import com.huminecraft.huminekingdom.utils.menu.menuaction.actions.*;

import java.util.ArrayList;

public class ButtonActionManager {

    ArrayList<ButtonAction> actions;

    public ButtonActionManager() {
        actions = new ArrayList<>();

        actions.add(new CloseAction());
        actions.add(new CreateKingdomAction());
        actions.add(new AddGradeAction());
        actions.add(new ViewGradeAction());
        actions.add(new AddPermissionAction());
        actions.add(new RemoveGradeAction());
        actions.add(new SendInvitationAction());
    }

    public ArrayList<ButtonAction> getActions() {
        return actions;
    }
}
