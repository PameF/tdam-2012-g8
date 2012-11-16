package com.tdam2012.grupo8.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseSQLiteHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "tdam.sqlite";
	private static final int DATABASE_VERSION = 1;
	
	public DatabaseSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase database)
	{
		String sql = "CREATE TABLE " + ActionsRegistryRepository.DATABASE +
					"(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
						ActionsRegistryRepository.COLUMN_ACTION + " TEXT, " + 
						ActionsRegistryRepository.COLUMN_CONTACT_NAME + " TEXT, " + 
						ActionsRegistryRepository.COLUMN_CONTACT_PHONE_NUMBER + " TEXT, " + 
						ActionsRegistryRepository.COLUMN_CONTACT_ID + " TEXT, " +
						ActionsRegistryRepository.COLUMN_MESSAGE + " TEXT, " +
						ActionsRegistryRepository.COLUMN_DATE + " DATETIME)";
		
		database.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
