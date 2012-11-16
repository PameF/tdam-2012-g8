package com.tdam2012.grupo8.entities;

import java.util.Date;

import com.tdam2012.grupo8.data.RegistrationRepository.ActionEnum;


public class Registration {
	private long id;
	private ActionEnum action;
	private long id_contact;
	private String name_contact;
	private String number_phone_contact;
	public long getId_contact() {
		return id_contact;
	}
	public void setId_contact(long id_contact) {
		this.id_contact = id_contact;
	}
	public String getName_contact() {
		return name_contact;
	}
	public void setName_contact(String name_contact) {
		this.name_contact = name_contact;
	}
	public String getNumber_phone_contact() {
		return number_phone_contact;
	}
	public void setNumber_phone_contact(String number_phone_contact) {
		this.number_phone_contact = number_phone_contact;
	}
	private Date date;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ActionEnum getAction() {
		return action;
	}
	public void setAction(ActionEnum action) {
		this.action = action;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
