package com.huminecraft.huminekingdom.utils.messages.variable;

import java.util.ArrayList;

public class List {

    private String name;
    private ArrayList<String> values;

    public List(String name, ArrayList<String> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return "%"+name+"%";
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setList(ArrayList<String> values) {
        this.values = values;
    }

}
