package com.eeesys.frame.net.model;

public class URLComplete {
	public static final String ERROR = "com.eeesys.frame.inter.URLComplete.error";

	private int responseCode = -100; //响应码
 
	private String cookie;    //cookie

	private String message = ERROR; //返回结果

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
