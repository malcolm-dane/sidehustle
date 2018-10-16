package com.parse.starter.models;

/**
 * Created by temp on 3/20/2017.
 */

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

@ParseClassName("aRequest")
public class aRequest extends ParseObject {
    public static final String USER_ID_KEY = "requesterUsername";
    public static final String driverUsernameKey = "driverUsername";
   // public static final String RECEIVER_ID_KEY = "receiver";
    public static final String requesterLocation = "requesterLocation";
    public static final String driverLocation = "driverLocation";
    public static final String  TYPE_ID_KEY = "type";
    public static boolean hasPhoto;
    public String getdriverUsernameKey() {
        return getString(driverUsernameKey);
    }
    public String getUserId() {
        return getString(USER_ID_KEY);
    }
    public String getBid() {return getString(requesterLocation);}
    public String getObjID() {return getString(driverLocation);}
    public boolean getPhotoUrl(Boolean hasPhoto){return hasPhoto;}
    public  void setPhotoUrl(boolean hasPhoto) {put("photo",hasPhoto);}
    public String getType() {return getString(TYPE_ID_KEY);}
    public void setType(String type) {
        put(TYPE_ID_KEY,type);
    }
    public void setUserId(String sender) {
        put(USER_ID_KEY,sender);
    }
    public void setdriverUsernameKey(String driverUsername) {
        put(driverUsernameKey,driverUsername);
    }
    public void setRequesterLocation( ParseGeoPoint rLocation) {put(requesterLocation, rLocation);}
    public void setDriverLocation( ParseGeoPoint DLocation) {put(driverLocation, DLocation);}

}