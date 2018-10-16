package com.parse.starter.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject {
    public static final String BODY_KEY = "body";
    public static final String READ = "READ";
    public static final String RECEIVER_ID_KEY = "receiver";
    public static final String STATE = "STATE";
    public static final String UNREAD = "UNREAD";
    public static final String URL = "profile";
    public static final String USER_ID_KEY = "sender";

    public String getreceiverId() {
        return getString("receiver");
    }

    public String getUserId() {
        return getString("sender");
    }

    public String getBody() {
        return getString(BODY_KEY);
    }

    public String getURL() {
        return getString("profile");
    }

    public void setURL(String URL) {
        URL = "profile";
    }

    public void setUserId(String sender) {
        put("sender", sender);
    }

    public void setReceiverIdKey(String receiverId) {
        put("sender", receiverId);
    }

    public void setBody(String body) {
        put(BODY_KEY, body);
    }
}
