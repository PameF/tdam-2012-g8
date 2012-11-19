package com.tdam2012.grupo8.ui;

import com.tdam2012.grupo8.R;

import android.app.Activity;
import android.os.Bundle;

public class SmsWebConversationActivity extends Activity
{
	public static final String CONTACT_USERNAME_KEY = "CONTACT_USERNAME_KEY";
	public static final String CONTACT_ID_KEY = "CONTACT_ID_KEY";
	public static final String CONTACT_NAME_KEY = "CONTACT_NAME_KEY";
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_web_list);
    }
}
