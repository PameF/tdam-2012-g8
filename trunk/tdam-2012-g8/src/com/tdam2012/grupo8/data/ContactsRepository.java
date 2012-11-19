package com.tdam2012.grupo8.data;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import com.tdam2012.grupo8.entities.Contact;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;

public class ContactsRepository {
	
	public static final String DATABASE = "contacts_username";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CONTACT_ID = "contact_id";
	public static final String COLUMN_USERNAME = "username";
	
	private Context context;
	private DatabaseSQLiteHelper helper;
	
	public ContactsRepository(Context context) {
		this.context = context;
		this.helper = new DatabaseSQLiteHelper(context);
	}
	
	public ArrayList<Contact> getContactList(String filter, boolean orderAsc) {
		
		ArrayList<Contact> list = new ArrayList<Contact>();
		Contact contact;
		
		ContentResolver resolver = this.context.getContentResolver();
		Cursor cursor = resolver.query(
				ContactsContract.Contacts.CONTENT_URI, 
				null, 
				ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?", 
				new String[] { filter + "%" }, 
				ContactsContract.Contacts.DISPLAY_NAME + (orderAsc ? " ASC" : " DESC"));
		
		if (cursor.getCount() > 0) {
			
			while (cursor.moveToNext()) {

				String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					
				contact = new Contact();					
				contact.setId(Long.parseLong(id));
				contact.setName(name);
	
				list.add(contact);
			}
		}
		cursor.close();
		
		return list;
	}
	
	public String[] getContactPhoneNumbers(long id) {
		ArrayList<String> numbers = new ArrayList<String>();
		
		Cursor cursor = this.context.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
				null,
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", 
				new String[] { String.valueOf(id) }, 
				null);
		
		while (cursor.moveToNext()) {
			numbers.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		}
		
		cursor.close();
		
		return numbers.toArray(new String[numbers.size()]);
	}
	
	public String[] getContactEmails(long id) {
		ArrayList<String> emails = new ArrayList<String>();
		
		Cursor cursor = this.context.getContentResolver().query(
				ContactsContract.CommonDataKinds.Email.CONTENT_URI, 
				null,
				ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
				new String[] { String.valueOf(id) }, 
				null);
		
		while (cursor.moveToNext()) {
			String email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
			//String emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
			
			emails.add(email);
		}
		cursor.close();
		
		return emails.toArray(new String[emails.size()]);
	}
	
	public Bitmap getContactPhoto(long id) {
		Bitmap photo = null;
		
		Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, id);
	    Uri photoUri = Uri.withAppendedPath(contactUri, Contacts.Photo.CONTENT_DIRECTORY);
	    
	    Cursor cursor = this.context.getContentResolver().query(photoUri, new String[] { Contacts.Photo.DATA15 }, null, null, null);
	    
	    if (cursor == null) {
	        return null;
	    }
	    try {
	        if (cursor.moveToFirst()) {
	            byte[] data = cursor.getBlob(0);
	            if (data != null) {
	                photo = BitmapFactory.decodeStream(new ByteArrayInputStream(data));
	            }
	        }
	    } finally {
	        cursor.close();
	    }
	    
	    return photo;
	}
	
	public Contact getContactById(long id) {
		
		Contact contact = null;		
		ContentResolver resolver = this.context.getContentResolver();

	    Cursor cursor = resolver.query(
				ContactsContract.Contacts.CONTENT_URI, 
				null, 
				ContactsContract.Contacts._ID + " = ?", 
				new String[] { String.valueOf(id) }, 
				null);

	    if (cursor.moveToFirst()) 
	    { 
			contact = new Contact();					
			contact.setId(id);
			contact.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
	    } 

	    cursor.close();

	    return contact; 
	}
		
	public Contact getContactByPhoneNumber(String phoneNumber) {
		
		Contact contact = null;
		long contactId = 0;
		String number = "";
		
		Cursor cursor = this.context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		
	    while (cursor.moveToNext()) 
	    { 
	    	number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	    	contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
	    	
	    	if(number.contains(phoneNumber) || phoneNumber.contains(number)) {
	    		contact = getContactById(contactId);
	    		break;
	    	}
	    }

	    cursor.close();

	    return contact; 
	}
	
	public void saveContactUsername(long contactId, String username) {
		
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_CONTACT_ID, contactId);
		cv.put(COLUMN_USERNAME, username);
		
		SQLiteDatabase db = helper.getWritableDatabase();
		db.insert(DATABASE, null, cv);
		db.close();
	}
	
	public String getContactUsername(long contactId) {

		String sql = "SELECT " + COLUMN_USERNAME + " FROM " + DATABASE + " WHERE " + COLUMN_CONTACT_ID + "=?";
		String[] params = new String[] { String.valueOf(contactId) };
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, params);
		
		String username = null;
		
		if (cursor.moveToFirst())
			username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
		
		cursor.close();
		db.close();
		
		return username;
	}
	
	public Contact getContactByUsername(String username) {
		
		Contact contact = null;
		
		String sql = "SELECT " + COLUMN_CONTACT_ID + " FROM " + DATABASE + " WHERE " + COLUMN_USERNAME + "=?";
		String[] params = new String[] { String.valueOf(username) };
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, params);
		
		long id = 0;
		
		if (cursor.moveToFirst()) {
			id = cursor.getLong(cursor.getColumnIndex(COLUMN_CONTACT_ID));
			contact = getContactById(id);
		}
		
		cursor.close();
		db.close();
		
		return contact;
	}
}
