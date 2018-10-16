package com.parse.starter.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("aUser")
public class userModel extends ParseObject {
    private String madeRequestType;
    private String password;
    private int rating;
    private String receivedChatFrom;
    private String respondedToType;
    private String sentChatTo;
    private String username;
}
