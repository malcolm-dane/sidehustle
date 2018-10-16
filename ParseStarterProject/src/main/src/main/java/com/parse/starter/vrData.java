package com.parse.starter;

public class vrData {
	private String username;
	private String Message;
	private String ParseGeo;
	private String ImageUrl;
	private String TimeStamp;
	private String ObjID;
	private String rate;
	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
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
}