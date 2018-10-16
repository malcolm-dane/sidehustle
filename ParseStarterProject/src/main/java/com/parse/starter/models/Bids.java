package com.parse.starter.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Bids")
public class Bids extends ParseObject {
    public static final String Bid_KEY = "Bid";
    public static final String RECEIVER_ID_KEY = "receiver";
    public static final String TYPE_ID_KEY = "type";
    public static final String USER_ID_KEY = "sender";
    public static final String relationalOBJ = "objID";

    public String getreceiverId() {
        return getString("receiver");
    }

    public String getUserId() {
        return getString("sender");
    }

    public String getBid() {
        return getString(Bid_KEY);
    }

    public String getObjID() {
        return getString("objID");
    }

    public String getType() {
        return getString("objID");
    }

    public void setType(String type) {
        put("type", type);
    }

    public void setUserId(String sender) {
        put("type", sender);
    }

    public void setReceiverIdKey(String receiverId) {
        put("sender", receiverId);
    }

    public void setBid(String bid) {
        put(Bid_KEY, bid);
    }

    public void setObjID(String objID) {
        put("objID", objID);
    }
}
