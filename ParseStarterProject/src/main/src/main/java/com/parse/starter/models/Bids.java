package com.parse.starter.models;

/**
 * Created by temp on 3/20/2017.
 */

import com.parse.ParseClassName;
import com.parse.ParseObject;


import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Bids")
public class Bids extends ParseObject {
    public static final String USER_ID_KEY = "sender";
    public static final String Bid_KEY = "Bid";
    public static final String RECEIVER_ID_KEY = "receiver";
    public static final String Object_ID_KEY = "objID";
    public static final String  TYPE_ID_KEY = "type";
    public String getreceiverId() {
        return getString(RECEIVER_ID_KEY);
    }
    public String getUserId() {
        return getString(USER_ID_KEY);
    }
    public String getBid() {return getString(Bid_KEY);}
    public String getObjID() {return getString(Object_ID_KEY);}
    public String getType() {return getString(Object_ID_KEY);}
    public void setType(String type) {
        put(TYPE_ID_KEY,type);
    }
    public void setUserId(String sender) {
        put(TYPE_ID_KEY,sender);
    }
    public void setReceiverIdKey(String receiverId) {
        put(USER_ID_KEY,receiverId);
    }
    public void setBid(String bid) {put(Bid_KEY, bid);}
    public void setObjID(String objID) {put(Object_ID_KEY, objID);}
}