package com.tdam2012.grupo8.data;

import java.util.ArrayList;

import com.tdam2012.grupo8.entities.Contact;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Contacts.Photo;

public class ContactsRepository {
	
	private Context context;
	
	public ContactsRepository(Context context) {
		this.context = context;
	}
	
	public ArrayList<Contact> getContactList(String filter) {
		
		ArrayList<Contact> list = new ArrayList<Contact>();
		Contact contact;
		
		ContentResolver resolver = this.context.getContentResolver();
		Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		
		if (cursor.getCount() > 0) {
			
			while (cursor.moveToNext()) {

				String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
									
				if (name != null && (name.toLowerCase().startsWith(filter))) {
					
					contact = new Contact();					
					contact.setId(Long.parseLong(id));
					contact.setName(name);
					contact.setAvatar(ContentUris.withAppendedId(Contacts.CONTENT_URI, contact.getId()));

					list.add(contact);
				}
			}
		}
		cursor.close();
		
		return list;
	}
}
