package com.tdam2012.grupo8.data;

import java.util.Date;

import com.tdam2012.grupo8.entities.Registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class RegistrationRepository {
	
	private RegistrationSQLiteHelper helper;
	
	public static final String ACTION = "action";
	public static final String ID_CONTACT = "id_contact";
	public static final String NAME_CONTACT = "name_contact";
	public static final String NUMBER_PHONE_CONTACT = "number_phone_contact";
	public static final String DATE = "date";
	public static final String NAME_DATABASE = "Registration";
	
	public enum ActionEnum {
		MISSED_CALL,
		INCOMING_CALL,
		OUTGOING_CALL,
		RECEIVED_MESSAGE,
		SENT_MESSAGE,
		RECEIVED_EMAIL,
		SENT_EMAIL,
		RECEIVED_MESSAGE_WEB,
		SENT_MESSAGE_WEB
	}

	
	public RegistrationRepository(Context context) {
		helper = new RegistrationSQLiteHelper(context);
	}

	public void insertRegistration(Registration reg) {
	
		ContentValues cv = new ContentValues();
		SQLiteDatabase db = helper.getWritableDatabase();
		cv.put(ACTION, reg.getAction().toString());
		cv.put(ID_CONTACT, reg.getId_contact());
		cv.put(NAME_CONTACT, reg.getName_contact());
		cv.put(NUMBER_PHONE_CONTACT, reg.getNumber_phone_contact());
		cv.put(DATE, new Date().toString());
		
		db.insert(NAME_DATABASE, null, cv);
	}
}