package com.tdam2012.grupo8.data;

import java.util.Date;

import com.tdam2012.grupo8.entities.ActionRegistry;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ActionsRegistryRepository {
	
	private DatabaseSQLiteHelper helper;

	public static final String DATABASE = "action_registry";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_ACTION = "action";
	public static final String COLUMN_CONTACT_ID = "contact_id";
	public static final String COLUMN_CONTACT_NAME = "contact_name";
	public static final String COLUMN_CONTACT_PHONE_NUMBER = "contact_phone_number";
	public static final String COLUMN_MESSAGE = "message";
		
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

	
	public ActionsRegistryRepository(Context context) {
		helper = new DatabaseSQLiteHelper(context);
	}

	public void insertRegistration(ActionRegistry reg) {
	
		ContentValues cv = new ContentValues();
		
		cv.put(COLUMN_DATE, new Date().toString());
		cv.put(COLUMN_ACTION, reg.getAction().toString());
		cv.put(COLUMN_CONTACT_ID, reg.getContactId());
		cv.put(COLUMN_CONTACT_NAME, reg.getContactName());
		cv.put(COLUMN_CONTACT_PHONE_NUMBER, reg.getContactPhoneNumber());
		cv.put(COLUMN_MESSAGE, reg.getMessage());

		SQLiteDatabase db = helper.getWritableDatabase();
		db.insert(DATABASE, null, cv);
	}
}