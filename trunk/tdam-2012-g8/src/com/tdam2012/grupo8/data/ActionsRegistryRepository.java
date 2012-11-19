package com.tdam2012.grupo8.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.tdam2012.grupo8.entities.ActionRegistry;
import com.tdam2012.grupo8.entities.SmsMessage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		
		cv.put(COLUMN_DATE, new Date().toString());
		cv.put(COLUMN_ACTION, reg.getAction().toString());
		cv.put(COLUMN_CONTACT_ID, reg.getContactId());
		cv.put(COLUMN_CONTACT_NAME, reg.getContactName());
		cv.put(COLUMN_CONTACT_PHONE_NUMBER, reg.getContactPhoneNumber());
		cv.put(COLUMN_MESSAGE, reg.getMessage());

		db.insert(DATABASE, null, cv);
		db.close();
	}
	
	public ArrayList<ActionRegistry> getSmsContactConversations() {
		
		String sql = "SELECT * FROM " + DATABASE + " WHERE " + COLUMN_ACTION + " IN (?, ?) GROUP BY " + COLUMN_CONTACT_ID;
		String[] params = new String [] { ActionEnum.SENT_MESSAGE.toString(), ActionEnum.RECEIVED_MESSAGE.toString() };
	
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, params);
		
		ArrayList<ActionRegistry> conversations = createActionRegistry(cursor);
		
		cursor.close();
		db.close();
		
		return conversations;	
	}
	
	public ArrayList<SmsMessage> getSmsContactConversation(long contact_id) {
				
		String sql = "SELECT * FROM " + DATABASE + " WHERE " + COLUMN_ACTION + " IN (?, ?) AND " + COLUMN_CONTACT_ID + "=?";
		String[] params = new String [] { ActionEnum.SENT_MESSAGE.toString(), ActionEnum.RECEIVED_MESSAGE.toString(), String.valueOf(contact_id) };
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, params);
		
		ArrayList<SmsMessage> messages = new ArrayList<SmsMessage>();		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzzzzzzz yyyy");
		
		SmsMessage sms;
		Date date;
		ActionEnum action;
		
		if (cursor.getCount() > 0) {			
			while (cursor.moveToNext()) {

				try {
					date = dateFormatter.parse(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
					action = ActionEnum.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_ACTION)));
					
					sms = new SmsMessage();
					
					sms.setContact(cursor.getLong(cursor.getColumnIndex(COLUMN_CONTACT_ID)));
					sms.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_PHONE_NUMBER)));
					sms.setMessage(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE)));
					
					switch(action) {
						case RECEIVED_MESSAGE:
							sms.setReceivedDate(date);
							break;
						case SENT_MESSAGE:
							sms.setSentDate(date);
							break;
					}
					
					messages.add(sms);
					
				} catch (ParseException e) {
					e.printStackTrace();
				}			
			}
		}
		
		cursor.close();
		db.close();
		
		return messages;		
	}
	
	private ArrayList<ActionRegistry> createActionRegistry(Cursor cursor) {
		ArrayList<ActionRegistry> list = new ArrayList<ActionRegistry>();
		
		ActionRegistry registry;		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzzzzzzz yyyy");
				
		if (cursor.getCount() > 0) {			
			while (cursor.moveToNext()) {

				try {
					registry = new ActionRegistry();
					
					registry.setContactId(cursor.getLong(cursor.getColumnIndex(COLUMN_CONTACT_ID)));
					registry.setContactName(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NAME)));
					registry.setContactPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_PHONE_NUMBER)));
					registry.setMessage(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE)));
					registry.setDate(dateFormatter.parse(cursor.getString(cursor.getColumnIndex(COLUMN_DATE))));
					registry.setAction(ActionEnum.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_ACTION))));
					
					list.add(registry);
					
				} catch (ParseException e) {
					e.printStackTrace();
				}			
			}
		}
		
		return list;
	}
}