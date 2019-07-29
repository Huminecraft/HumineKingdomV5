package com.huminecraft.huminekingdom.utils.menu;

import org.bukkit.entity.Player;

import com.huminecraft.humineaypi.utils.Menu;

public abstract class KingdomMenu {

    protected Menu menu;

    protected String id;
    protected String title;
    protected int number;
    protected boolean security;

    public KingdomMenu(String id, String title, int number, boolean security) {
        menu = null;
        this.id = id;
        this.title = title;
        this.number = number;
        this.security = security;
    }

    protected abstract void fillMenu(Menu menu);

    public void openMenu(Player player) {
        menu = new Menu(player, title, number * 9, security);
        fillMenu(menu);
        menu.openMenu();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setSize(int number) {
        this.number = number;
        if (menu != null)
            menu.setSize(number * 9);
    }

    public int getSize() {
        return number;
    }

    public Menu getMenu() {
        return menu;
    }

}
