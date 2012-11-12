package com.tdam2012.grupo8.entities;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.net.Uri;

public class Contact {
	private long id;
	private String name;
	private Bitmap avatar;
	
	private ArrayList<String> phoneNumbers;
	private ArrayList<String> emails;
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Bitmap getAvatar() {
		return avatar;
	}
	
	public void setAvatar(Bitmap avatar) {
		this.avatar = avatar;
	}
	
	public ArrayList<String> getPhoneNumbers() {
		return phoneNumbers;
	}
	
	public void setPhoneNumbers(ArrayList<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	
	public ArrayList<String> getEmails() {
		return emails;
	}
	
	public void setEmails(ArrayList<String> emails) {
		this.emails = emails;
	}
}
