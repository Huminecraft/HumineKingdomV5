package com.huminecraft.huminekingdom.utils.menu.menuaction.actions;

import com.huminecraft.huminekingdom.HumineKingdom;
import com.huminecraft.huminekingdom.utils.grade.KingdomGrade;
import com.huminecraft.huminekingdom.utils.grade.KingdomGradeManager;
import com.huminecraft.huminekingdom.utils.kingdom.Kingdom;
import com.huminecraft.huminekingdom.utils.menu.menuaction.ButtonAction;
import com.huminecraft.huminekingdom.utils.messages.variable.Variable;

public class RemoveGradeAction extends ButtonAction {

    public RemoveGradeAction() {
        super("grade-delete", (menu, item) -> {

            String gradeName = "";
            for (Variable variable : HumineKingdom.getVariableManager().getPlayerVariable(menu.getPlayer()).getVariables()) {
                if (variable.getName().equals("%GRADE_NAME%")) {
                    gradeName = variable.getValue();
                    break;
                }
            }
            Kingdom kingdom = HumineKingdom.getKingdomManager().getPlayerKingdom(menu.getPlayer());
            KingdomGradeManager kingdomGradeManager = kingdom.getKingdomGradeManager();
            KingdomGrade grade = kingdomGradeManager.getKingdomGrade(gradeName);

            if (kingdom.getKingGradeName().equals(grade.getName())) {
                HumineKingdom.getMessageManager().play("grade-cant-delete", menu.getPlayer());
            } else {
                kingdomGradeManager.remove(grade);
                HumineKingdom.getMessageManager().play("grade-delete", menu.getPlayer());
                kingdom.save();
            }

            menu.closePlayerMenu();
        });
    }
}
