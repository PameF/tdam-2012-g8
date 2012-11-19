package com.tdam2012.grupo8.entities;

import android.R.string;

public class Email {
	
	private long id;
	private long contact;
	private string email;
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
	public string getEmail() {
		return email;
	}
	public void setEmail(string email) {
		this.email = email;
	}
	public string getSubject() {
		return subject;
	}
	public void setSubject(string subject) {
		this.subject = subject;
	}
	public string getContent_email() {
		return content_email;
	}
	public void setContent_email(string content_email) {
		this.content_email = content_email;
	}
	private string subject;
	private string content_email;

}
