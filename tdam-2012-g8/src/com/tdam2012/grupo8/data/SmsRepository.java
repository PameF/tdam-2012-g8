package com.tdam2012.grupo8.data;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class SmsRepository {

	private Context context;
	
	public SmsRepository(Context context) {
		this.context = context;
	}
	
	public ArrayList<Object> getContactConversations(String filter, boolean orderAsc) {
		
		ArrayList<Object> list = new ArrayList<Object>();
		Object sms;
		
		ContentResolver resolver = this.context.getContentResolver();
		Uri uri = Uri.parse("content://sms/sent");
		Cursor cursor = resolver.query(uri, null, null, null, null);
		
		if (cursor.getCount() > 0) {
			
			while (cursor.moveToNext()) {

				//http://mobdev.olin.edu/mobdevwiki/FrontPage/Tutorials/SMS%20Messaging#Catch_the_new_SMS_message
				//http://www.androidjavadoc.com/1.0_r1_src/android/provider/Telephony.TextBasedSmsColumns.html#ADDRESS
				
				String personCol= cursor.getString(cursor.getColumnIndex("thread_id"));
				String dateCol = cursor.getString(cursor.getColumnIndex("msg_count"));
				
				/*String addressCol= cursor.getString(cursor.getColumnIndex("ADDRESS")); 
				String personCol= cursor.getString(cursor.getColumnIndex("PERSON")); 
				String dateCol = cursor.getString(cursor.getColumnIndex("DATE"));
				String statusCol = cursor.getString(cursor.getColumnIndex("BODY"));*/
			}
		}
		cursor.close();
		
		return list;
	}
}
