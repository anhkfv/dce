package com.example.server;

import java.util.Date;

public class DataResult {

	private String detail;

	private Date date;

	public DataResult() {

	}

	public DataResult(String detail, Date date) {
		super();
		this.detail = detail;
		this.date = date;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
