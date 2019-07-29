package com.huminecraft.huminekingdom.utils.messages;

public class MessageNotFoundError extends Error {

    public MessageNotFoundError(String id) {
        System.out.println("Message id: "+id+" not found...");
    }

}
