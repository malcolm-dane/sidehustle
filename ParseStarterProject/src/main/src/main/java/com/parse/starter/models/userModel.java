package com.parse.starter.models;

/**
 * Created by temp on 3/18/2017.
 */

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;

@ParseClassName("aUser")
public class userModel extends ParseObject {
private String username;
    private String password;
    private String sentChatTo;
    private String receivedChatFrom;
    private String respondedToType;
private String madeRequestType;
    private int rating;
}


