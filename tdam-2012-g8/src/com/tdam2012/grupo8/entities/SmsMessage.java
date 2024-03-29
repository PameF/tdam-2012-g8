package com.tdam2012.grupo8.entities;

import java.util.Date;

public class SmsMessage {
	
	private long id;
	private long contact;
	private String phoneNumber;
	private String message;
	private Date sentDate;
	private Date deliveredDate;
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
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	
	public void setSentDate(Date date) {
		this.sentDate = date;
	}
	
	public Date getDeliveredDate() {
		return deliveredDate;
	}
	
	public void setDeliveredDate(Date date) {
		this.deliveredDate = date;
	}
	
	public Date getReceivedDate() {
		return receivedDate;
	}
	
	public void setReceivedDate(Date date) {
		this.receivedDate = date;
	}
}
