package com.huminecraft.huminekingdom.utils.menu.menuaction.actions;

import com.huminecraft.huminekingdom.HumineKingdom;
import com.huminecraft.huminekingdom.utils.grade.GradePermission;
import com.huminecraft.huminekingdom.utils.grade.KingdomGrade;
import com.huminecraft.huminekingdom.utils.menu.menuaction.ButtonAction;
import com.huminecraft.huminekingdom.utils.messages.variable.List;
import com.huminecraft.huminekingdom.utils.messages.variable.Variable;

import java.util.ArrayList;

public class ViewGradeAction extends ButtonAction {

    public ViewGradeAction() {
        super("view-grade", (menu, item) -> {
            String name = item.getItemMeta().getDisplayName();
            HumineKingdom.getVariableManager().getPlayerVariable(menu.getPlayer()).setVariables(new Variable("GRADE_NAME", name));
            KingdomGrade kingdomGrade = HumineKingdom.getKingdomManager().getPlayerKingdom(menu.getPlayer()).getKingdomGradeManager().getKingdomGrade(item.getItemMeta().getDisplayName());
            ArrayList<String> perms = new ArrayList<>();
            for (GradePermission gp : kingdomGrade.getPermissions()) {
                perms.add(gp.getName());
            }
            List permsList = new List("PERMS_LIST", perms);
            HumineKingdom.getVariableManager().getPlayerVariable(menu.getPlayer()).setLists(permsList);
            HumineKingdom.getKingdomMenuManager().getKingdomMenu("kingdom-grade-item").openMenu(menu.getPlayer());
        });
    }
}
