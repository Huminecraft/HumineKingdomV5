package com.huminecraft.huminekingdom.utils.messages.variable;

public class ListNotFoundError extends Error {

    public ListNotFoundError(String name) {
        System.out.println(name+" not found...");
    }
}
