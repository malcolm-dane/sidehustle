package com.parse.starter.models;



//Model for the view requests Activity.
public class vrData {
	private String username;
	private String Message;
	private String ParseGeo;
	private String ImageUrl;
	private String TimeStamp;
	private String Type;
	private String objID;
	private Double olat;
	private Double olong;
	private Double mylat;
	private Double mylong;
	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
	}
	public String getobjID() {
		return objID;
	}

	public void setobjID(String objID) {
		this.objID = objID;
	}
	public String getType() {
		return Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}
	public String getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(String TimeStamp) {
		this.TimeStamp= TimeStamp;;
	}
	public String getMessage() {
		return Message;
	}

	public void setMessage(String Message) {
		this.Message = Message;
	}

	public String getParseGeo() {
		return ParseGeo;
	}

	public void setParseGeo(String ParseGeo) {
		this.ParseGeo = ParseGeo;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String ImageUrl) {
		this.ImageUrl = ImageUrl;
	}

	public Double getOtherLat() {
		return olat;
	}

	public void setOther(Double olat) {
		this.olat = olat;
	}
	public Double getOtherLong() {
		return olong;
	}

	public void setOtherLong(Double olong) {
		this.olong = olong;
	}
	public Double getMyLat() {
		return mylat;
	}

	public void setmyLat(Double mylat) {
		this.mylat = mylat;
	}

	public Double getMyLong() {
		return mylong;
	}

	public void setMyLong(Double mylong) {
		this.mylong = mylong;
	}
}