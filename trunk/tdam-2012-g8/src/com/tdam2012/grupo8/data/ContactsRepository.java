package com.tdam2012.grupo8.data;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import com.tdam2012.grupo8.entities.Contact;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;

public class ContactsRepository {
	
	private Context context;
	
	public ContactsRepository(Context context) {
		this.context = context;
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
		Uri uri;
		Uri baseUri = ContactsContract.PhoneLookup.CONTENT_FILTER_URI;
        
	    uri = Uri.withAppendedPath(baseUri, Uri.encode("1555521" + phoneNumber)); 
	    Cursor cursor = context.getContentResolver().query(uri, null, null, null, null); 

	    if (cursor.moveToFirst()) 
	    { 
	    	String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				
			contact = new Contact();					
			contact.setId(Long.parseLong(id));
			contact.setName(name);
	    } 

	    cursor.close();

	    return contact; 
	}
}
