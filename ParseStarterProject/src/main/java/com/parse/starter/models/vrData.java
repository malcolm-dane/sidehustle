package com.parse.starter.models;

public class vrData {
    private String ImageUrl;
    private String Message;
    private String ParseGeo;
    private String TimeStamp;
    private String Type;
    private Double mylat;
    private Double mylong;
    private String objID;
    private Double olat;
    private Double olong;
    private String theBid;
    private String username;

    public String getusername() {
        return this.username;
    }

    public String getBid() {
        return this.theBid;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getobjID() {
        return this.objID;
    }

    public void setBid(String theBid) {
        this.theBid = theBid;
    }

    public void setobjID(String objID) {
        this.objID = objID;
    }

    public String getType() {
        return this.Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getTimeStamp() {
        return this.TimeStamp;
    }

    public void setTimeStamp(String TimeStamp) {
        this.TimeStamp = TimeStamp;
    }

    public String getMessage() {
        return this.Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getParseGeo() {
        return this.ParseGeo;
    }

    public void setParseGeo(String ParseGeo) {
        this.ParseGeo = ParseGeo;
    }

    public String getImageUrl() {
        return this.ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public Double getOtherLat() {
        return this.olat;
    }

    public void setOther(Double olat) {
        this.olat = olat;
    }

    public Double getOtherLong() {
        return this.olong;
    }

    public void setOtherLong(Double olong) {
        this.olong = olong;
    }

    public Double getMyLat() {
        return this.mylat;
    }

    public void setmyLat(Double mylat) {
        this.mylat = mylat;
    }

    public Double getMyLong() {
        return this.mylong;
    }

    public void setMyLong(Double mylong) {
        this.mylong = mylong;
    }
}
