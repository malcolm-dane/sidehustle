package com.parse.starter;

public class vrData {
    private String Bid;
    private String Bidder;
    private String ImageUrl;
    private String Message;
    private String ObjID;
    private String ParseGeo;
    private String TimeStamp;
    private String rate;
    private String username;

    public String getusername() {
        return this.username;
    }

    public void setusername(String username) {
        this.username = username;
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

    public String getBid() {
        return this.Bid;
    }

    public void setBid(String Bid) {
        this.Bid = Bid;
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
}
