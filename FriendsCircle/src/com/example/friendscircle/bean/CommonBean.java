package com.example.friendscircle.bean;

public class CommonBean {

	private String username;
	private String content;

	public CommonBean() {
	}

	public CommonBean(String username, String content) {
		this.username = username;
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
