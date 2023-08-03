package com.example.blogapprestapi.payload;

import java.util.Date;

public class ErrorDetail {
	
	private Date timestamp;
	private String name;
	private String detail;
	public ErrorDetail(Date timestamp, String name, String detail) {
		super();
		this.timestamp = timestamp;
		this.name = name;
		this.detail = detail;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public String getName() {
		return name;
	}
	public String getDetail() {
		return detail;
	}
	
	

}
