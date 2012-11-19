package com.tdam2012.grupo8.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.ui.contacts.ListActivity;

public class HistoryActionActivity extends ListActivity implements OnClickListener{

	
	public static final String DATE_KEY = "DATE";
	public static final String CONTACT_ID_KEY = "CONTACT_ID";
	public static final String CONTACT_NAME_KEY = "CONTACT_NAME";
	public static final String CONTACT_PHONE_NUMBER_KEY = "CONTACT_PHONE_NUMBER";
	public static final String ACTION_KEY = "ACTION";
	public static final String MESSAGE_KEY = "MESSAGE";
	
	private String phoneNumber;
	private String name;
	private long contact;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_actions);
    }
	
	
	
}
