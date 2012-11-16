package com.tdam2012.grupo8.entities;

import java.util.Date;

import com.tdam2012.grupo8.data.ActionsRegistryRepository.ActionEnum;


public class ActionRegistry {
	
	private long id;
	private Date date;
	private ActionEnum action;
	private long contactId;
	private String contactName;
	private String contactPhoneNumber;
	private String message;
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public ActionEnum getAction() {
		return action;
	}
	
	public void setAction(ActionEnum action) {
		this.action = action;
	}
	
	public long getContactId() {
		return contactId;
	}
	
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}
	
	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
