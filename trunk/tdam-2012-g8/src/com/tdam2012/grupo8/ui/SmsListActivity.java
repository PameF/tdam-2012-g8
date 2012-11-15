package com.tdam2012.grupo8.ui;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.ui.contacts.ListActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SmsListActivity extends Activity implements OnClickListener
{
	private static final int CONTACT_PHONE_NUMBER_REQUEST = 1;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_list);
        
        Button btn = (Button)findViewById(R.id.sms_list_new);
    	btn.setOnClickListener(this); 
    }
	
	public void onClick(View v)
	{
		switch(v.getId()) {
			
			case R.id.sms_list_new:
				
				Intent i = new Intent(SmsListActivity.this, ListActivity.class);
				i.putExtra(ListActivity.SELECT_ACTION_KEY, ListActivity.OnSelectActionEnum.PHONE_SELECT);
		
				startActivityForResult(i, CONTACT_PHONE_NUMBER_REQUEST);
				
				break;
		}
	}
	
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	 {	
		 if(resultCode == RESULT_OK && requestCode == CONTACT_PHONE_NUMBER_REQUEST) 
		 {		
			 String phoneNumber = data.getExtras().getString(ListActivity.PHONE_RESULT);
			 long contact = data.getExtras().getLong(ListActivity.CONTACT_ID);
			
			 Intent intent = new Intent(SmsListActivity.this, SmsListContactActivity.class);
			 intent.putExtra(SmsListContactActivity.PHONE_NUMBER_KEY, phoneNumber);
			 intent.putExtra(SmsListContactActivity.CONTACT_ID_KEY, contact);
			
			 startActivity(intent);
		 }
	}
	
}
