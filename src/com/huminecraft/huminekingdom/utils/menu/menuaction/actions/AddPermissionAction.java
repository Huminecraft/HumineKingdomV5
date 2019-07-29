package com.huminecraft.huminekingdom.utils.menu.menuaction.actions;

import com.huminecraft.humineaypi.utils.Button;
import com.huminecraft.humineaypi.utils.Menu;
import com.huminecraft.huminekingdom.HumineKingdom;
import com.huminecraft.huminekingdom.utils.grade.GradePermission;
import com.huminecraft.huminekingdom.utils.grade.KingdomGrade;
import com.huminecraft.huminekingdom.utils.menu.KingdomMenuManager;
import com.huminecraft.huminekingdom.utils.menu.menuaction.ButtonAction;
import com.huminecraft.huminekingdom.utils.messages.variable.Variable;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class AddPermissionAction extends ButtonAction {

    public AddPermissionAction() {
        super("add-permission", ((menu, item) -> AddPermissionAction.openMenu(menu, item)));
    }

    public static void openMenu(Menu menu, ItemStack item) {
        menu.closePlayerMenu();

        String gradeName = "";
        for (Variable variable : HumineKingdom.getVariableManager().getPlayerVariable(menu.getPlayer()).getVariables()) {
            if (variable.getName().equals("%GRADE_NAME%")) {
                gradeName = variable.getValue();
                break;
            }
        }

        KingdomGrade grade = HumineKingdom.getKingdomManager().getPlayerKingdom(menu.getPlayer()).getKingdomGradeManager().getKingdomGrade(gradeName);

        if (HumineKingdom.getKingdomManager().getPlayerKingdom(menu.getPlayer()).getKingGradeName().equals(grade.getName())) {
            HumineKingdom.getMessageManager().play("permission-cant-change", menu.getPlayer());
            return;
        }

        Menu permsMenu = new Menu(menu.getPlayer(), ChatColor.DARK_GRAY+"- "+ChatColor.DARK_BLUE+"s'occuper des permissions"+ChatColor.DARK_GRAY+" -", (grade.getPermissions().size() / 9 + 2) * 9, false);
        int slot = 0;
        for (GradePermission gp : HumineKingdom.getPermissionManager().getPermissions()) {
            ArrayList<String> lore = new ArrayList<String>();
            ItemStack itemStack = new ItemStack(Material.PAPER);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.BLUE+gp.getName());

            lore.add(ChatColor.DARK_PURPLE+"---------------");
            if (grade.hasPermission(gp)) {
                lore.add(ChatColor.GOLD+"équipé: "+ChatColor.GREEN+"✔");
            } else {
                lore.add(ChatColor.GOLD+"équipé: "+ChatColor.RED+"✗");
            }
            lore.add("");
            lore.addAll(gp.getDescription());
            lore.add("");
            if (grade.hasPermission(gp)) {
                lore.add(ChatColor.RED+"Clique pour déséquiper");
            } else {
                lore.add(ChatColor.GREEN+"Clique pour équiper");
            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            permsMenu.setButton(slot++, new Button(itemStack, () -> {
                if (!grade.hasPermission(gp)) {
                    grade.addPermission(gp);
                } else {
                    grade.removePermission(gp);
                }
                AddPermissionAction.openMenu(menu, item);
            }));
        }

        ItemStack glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemMeta glassm = glass.getItemMeta();
        glassm.setDisplayName(" ");
        glass.setItemMeta(glassm);

        ItemStack retour = new ItemStack(Material.BARRIER);
        ItemMeta retourm = retour.getItemMeta();
        retourm.setDisplayName(ChatColor.RED+"retour");
        retour.setItemMeta(retourm);

        permsMenu.fillLine(glass, permsMenu.getSize() / 9);

        ItemStack is = new ItemStack(Material.BARRIER);
        ItemMeta ism = is.getItemMeta();
        ism.setDisplayName(gradeName);
        is.setItemMeta(ism);

        permsMenu.setButton(permsMenu.getSize() - 1, new Button(retour, () -> KingdomMenuManager.getButtonAction("view-grade").getButtonActionHandler().handler(menu, is)));

        permsMenu.openMenu();
    }
}
