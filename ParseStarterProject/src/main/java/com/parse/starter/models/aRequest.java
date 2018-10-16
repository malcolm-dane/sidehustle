package com.parse.starter.models;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

@ParseClassName("aRequest")
public class aRequest extends ParseObject {
    public static final String OBJ_ID_KEY = "objID";
    public static final String RECEIVER_ID_KEY = "receiver";
    public static final String Status_ID_KEY = "status";
    public static final String TYPE_ID_KEY = "type";
    public static final String USER_ID_KEY = "poster";
    public static final String aBid = "aBid";
    public static final String driverLocation = "driverLocation";
    public static final String driverUsernameKey = "driverUsername";
    public static boolean hasPhoto = false;
    public static final String requesterLocation = "requesterLocation";

    public String getdriverUsernameKey() {
        return getString(driverUsernameKey);
    }

    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public String getBid() {
        return getString(aBid);
    }

    public String getObjID() {
        return getString(driverLocation);
    }

    public String getStatus() {
        return getString("status");
    }

    public boolean getPhotoUrl(Boolean hasPhoto) {
        return hasPhoto.booleanValue();
    }

    public void setPhotoUrl(boolean hasPhoto) {
        put("photo", Boolean.valueOf(hasPhoto));
    }

    public String getType() {
        return getString("type");
    }

    public void setType(String type) {
        put("type", type);
    }

    public void setObjIdKey(String OBJID) {
        put("objID", OBJID);
    }

    public void setStatus(String status) {
        put("status", status);
    }

    public void setUserId(String sender) {
        put(USER_ID_KEY, sender);
    }

    public void setdriverUsernameKey(String driverUsername) {
        put(driverUsernameKey, driverUsername);
    }

    public void setRequesterLocation(ParseGeoPoint rLocation) {
        put(requesterLocation, rLocation);
    }

    public void setDriverLocation(ParseGeoPoint DLocation) {
        put(driverLocation, DLocation);
    }
}
