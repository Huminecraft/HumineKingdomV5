package com.huminecraft.huminekingdom.utils.menu.menuaction;

public class ButtonAction {

    private String name;
    private ButtonActionHandler buttonActionHandler;

    public ButtonAction(String name, ButtonActionHandler buttonActionHandler) {
        this.name = name;
        this.buttonActionHandler = buttonActionHandler;
    }

    public String getName() {
        return name;
    }

    public ButtonActionHandler getButtonActionHandler() {
        return buttonActionHandler;
    }

    public boolean equals(Object object) {
        if (object instanceof ButtonAction) {
            return ((ButtonAction) object).getName() == getName();
        }
        return false;
    }

}
