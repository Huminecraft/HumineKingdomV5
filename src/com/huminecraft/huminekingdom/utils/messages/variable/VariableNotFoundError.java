package com.huminecraft.huminekingdom.utils.messages.variable;

public class VariableNotFoundError extends Error {

    public VariableNotFoundError(String name) {
        System.out.println("The variable "+name+" is not found...");
    }

}
