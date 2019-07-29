package com.huminecraft.huminekingdom.utils.messages;

import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class MessageManager {

    private ArrayList<Message> messages;

    public MessageManager(File messagesFile) {
        messages = new ArrayList<Message>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(messagesFile.getPath()), "UTF-8"));
            String line;
            String json = "";
            while ((line = br.readLine()) != null) {
                json+=line;
            }
            br.close();
            JSONObject msgJson = (JSONObject) new JSONParser().parse(json);

            JSONArray messages = (JSONArray) msgJson.get("messages");
            for (int i = 0 ; i < messages.size() ; i++) {
                this.messages.add(Message.fromJSON((JSONObject) messages.get(i)));
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void play(String id, Player player) {
        getMessageByID(id).play(player);
    }

    public Message getMessageByID(String id) throws MessageNotFoundError {
        for (Message msg : messages) {
            if (msg.getId().equals(id)) {
                return msg;
            }
        }
        throw new MessageNotFoundError(id);
    }
}
