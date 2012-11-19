package com.tdam2012.grupo8.entities;

import java.util.Date;

import android.R.string;


public class Email {
	
	private long id;
	private long contact;
	private String emailAddress;
	private String subject;
	private String contentEmail;
	private Date sentDate;
	private Date receivedDate;
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContentEmail() {
		return contentEmail;
	}
	public void setContentEmail(String contentEmail) {
		this.contentEmail = contentEmail;
	}
	
}
