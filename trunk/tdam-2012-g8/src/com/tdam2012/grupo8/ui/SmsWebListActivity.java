package com.tdam2012.grupo8.ui;

import com.tdam2012.grupo8.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class SmsWebListActivity extends Activity 
{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_web_list_general);
        
        Button btn = (Button)findViewById(R.id.button1);
    	btn.setOnClickListener(new OnClickListener(){
    		public void onClick(View v)
        	{
        		Intent i = new Intent(SmsWebListActivity.this,  SmsWebNewActivity.class);
        		startActivity(i);
        	}
    	}); 
    	
      LinearLayout list = (LinearLayout)findViewById(R.id.sms_web_list_item1);
      list.setOnClickListener(new OnClickListener(){
     		public void onClick(View v)
         	{
         		Intent intent = new Intent(SmsWebListActivity.this,  SmsWebListContactActivity.class);
         		startActivity(intent);
         	}
     	}); 
    }
}