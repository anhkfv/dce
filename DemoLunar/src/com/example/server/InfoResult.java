package com.example.server;

public class InfoResult {
	private String userName;

	private String nameNotice;

	private String accessCode;

	private String linkTopPage;

	private boolean status;

	public InfoResult() {

	}

	public InfoResult(String userName, String nameNotice, String accessCode, String linkTopPage, boolean status) {
		this.userName = userName;
		this.nameNotice = nameNotice;
		this.accessCode = accessCode;
		this.linkTopPage = linkTopPage;
		this.status = status;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNameNotice() {
		return nameNotice;
	}

	public void setNameNotice(String nameNotice) {
		this.nameNotice = nameNotice;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getLinkTopPage() {
		return linkTopPage;
	}

	public void setLinkTopPage(String linkTopPage) {
		this.linkTopPage = linkTopPage;
	}
}
