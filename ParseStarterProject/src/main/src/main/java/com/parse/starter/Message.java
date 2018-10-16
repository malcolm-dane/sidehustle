package com.parse.starter;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject {
    public static final String USER_ID_KEY = "sender";
    public static final String BODY_KEY = "body";
    public static final String RECEIVER_ID_KEY = "receiver";
    public String getreceiverId() {
        return getString(RECEIVER_ID_KEY);
    }
    public String getUserId() {
        return getString(USER_ID_KEY);
    }
    public String getBody() {
        return getString(BODY_KEY);
    }

    public void setUserId(String sender) {
        put(USER_ID_KEY,sender);
    }
    public void setReceiverIdKey(String receiverId) {
        put(USER_ID_KEY,receiverId);
    }
    public void setBody(String body) {
        put(BODY_KEY, body);
    }
}