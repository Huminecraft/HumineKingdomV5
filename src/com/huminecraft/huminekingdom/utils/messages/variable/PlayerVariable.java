package com.huminecraft.huminekingdom.utils.messages.variable;

import com.huminecraft.huminekingdom.HumineKingdom;
import com.huminecraft.huminekingdom.utils.grade.KingdomGrade;
import com.huminecraft.huminekingdom.utils.kingdom.KingdomMember;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;

public class PlayerVariable {

    private OfflinePlayer player;
    private ArrayList<Variable> variables;
    private ArrayList<List> lists;

    public PlayerVariable(OfflinePlayer player) {
        this.player = player;
        this.variables = new ArrayList<>();

        for (ChatColor cc : ChatColor.values()) {
            variables.add(new Variable(cc.name(), cc.toString()));
        }

        this.lists = new ArrayList<>();
    }

    public void setVariables(Variable variable) {
        for (Variable var : variables) {
            if (var.getName().equals(variable.getName())) {
                var.setValue(variable.getValue());
                return;
            }
        }
        variables.add(variable);
    }

    public Variable getVariable(String name) throws VariableNotFoundError {
        for (Variable var : variables) {
            if (var.getName().equals("%"+name+"%")) {
                return var;
            }
        }
        throw new VariableNotFoundError(name);
    }

    public void setLists(List list) {
        for (List l : lists) {
            if (l.getName().equals(list.getName())) {
                l.setList(list.getValues());
                return;
            }
        }
        lists.add(list);
    }

    public String parseText(String text) {
        if (text != null) {

            setVariables(new Variable("PLAYER_NAME", player.getName()));
            if (HumineKingdom.getKingdomManager().getPlayerKingdom(player) != null)
                setVariables(new Variable("KINGDOM_NAME", HumineKingdom.getKingdomManager().getPlayerKingdom(player).getName()));

            for (Variable var : variables) {
                text = text.replace(var.getName(), var.getValue());
            }
        }
        return text;
    }

    public ArrayList<String> parseList(String name) throws ParseListError {

        //MEMBERS
        ArrayList<String> members = new ArrayList<>();
        for (KingdomMember member : HumineKingdom.getKingdomManager().getPlayerKingdom(player).getMembers()) {
            members.add(member.getPlayer().getName());
        }
        setLists(new List("KINGDOM_MEMBERS", members));

        //GRADES
        ArrayList<String> grades = new ArrayList<>();
        for (KingdomGrade grade : HumineKingdom.getKingdomManager().getPlayerKingdom(player).getKingdomGradeManager().getKingdomGrades()) {
            grades.add(grade.getName());
        }
        setLists(new List("KINGDOM_GRADES", grades));

        for (List list : lists) {
            if (list.getName().equals(name)) {
                return list.getValues();
            }
        }
        throw new ParseListError(name);
    }

    public ArrayList<String> parseDescription(String text) {
        ArrayList<String> lore = new ArrayList<>();
        if (containList(text)) {
            List item = getListOfTheDescription(text);
            for (String value : item.getValues()) {
                this.setVariables(new Variable(item.getName().replace("%", ""), value));
                lore.add(parseText(text));
            }
        }
        return lore;
    }

    private List getListOfTheDescription(String value) throws ListNotFoundError{
        for (List list : lists) {
            if (value.contains(list.getName())) {
                return list;
            }
        }
        throw new ListNotFoundError(value);
    }

    private boolean containList(String text) {
        for (List list : lists) {
            if (text.contains(list.getName())) {
                return true;
            }
        }
        return false;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public ArrayList<List> getLists() {
        return lists;
    }
}
