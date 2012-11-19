package com.tdam2012.grupo8.data;

import com.tdam2012.grupo8.entities.ConnectivityStatus;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ConnectivityStatusRepository {

	public static final String DATABASE = "connectivity_status";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_DATE = "date";
	
	private DatabaseSQLiteHelper helper;
	
	public ConnectivityStatusRepository(Context context) {
		helper = new DatabaseSQLiteHelper(context);
	}
	
	public void insert(ConnectivityStatus status) {
		
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_STATUS, status.isConnected());
		cv.put(COLUMN_TYPE, status.getType());
		cv.put(COLUMN_DATE, status.getDate().toString());
		
		SQLiteDatabase db = helper.getWritableDatabase();
		db.insert(DATABASE, null, cv);
		
		db.close();
	}
}
