package com.tdam2012.grupo8.entities;

import java.util.Date;

public class WebMessage {
	
	private long id;
	private long contact;
	private String usernameFrom;
	private String usernameTo;
	private String message;
	private Date sentDate;
	private Date receivedDate;
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getContact() {
		return contact;
	}
	
	public void setContact(long contact) {
		this.contact = contact;
	}
	
	public String getUsernameFrom() {
		return usernameFrom;
	}
	
	public void setUsernameFrom(String usernameFrom) {
		this.usernameFrom = usernameFrom;
	}
	
	public String getUsernameTo() {
		return usernameTo;
	}
	
	public void setUsernameTo(String usernameTo) {
		this.usernameTo = usernameTo;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Date getSentDate() {
		return sentDate;
	}
	
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	
	public Date getReceivedDate() {
		return receivedDate;
	}
	
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
}
