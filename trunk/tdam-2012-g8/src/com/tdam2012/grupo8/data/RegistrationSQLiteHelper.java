package com.tdam2012.grupo8.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RegistrationSQLiteHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "registration.sqlite";
	private static final int DATABASE_VERSION = 1;
	
	public RegistrationSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase database)
	{
		String sql = "CREATE TABLE " + RegistrationRepository.NAME_DATABASE +
					"(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
						RegistrationRepository.ACTION + " TEXT, " + 
						RegistrationRepository.NAME_CONTACT + " TEXT, " + 
						RegistrationRepository.NUMBER_PHONE_CONTACT + " TEXT, " + 
						RegistrationRepository.ID_CONTACT + " TEXT, " + 
						RegistrationRepository.DATE + " DATETIME)";
		
		database.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
