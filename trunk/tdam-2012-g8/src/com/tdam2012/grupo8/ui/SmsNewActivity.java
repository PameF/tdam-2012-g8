package com.tdam2012.grupo8.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.tdam2012.grupo8.R;

public class SmsNewActivity extends Activity 
{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_new);
        ImageButton btn = (ImageButton)findViewById(R.id.imageButton1);
    	btn.setOnClickListener(new OnClickListener(){
    		public void onClick(View v)
        	{
        		Intent i = new Intent(SmsNewActivity.this,  ContactListActivity.class);
        		startActivity(i);
        	}
    	}); 
    }
}
