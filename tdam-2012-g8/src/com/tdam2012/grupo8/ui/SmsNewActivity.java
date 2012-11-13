package com.tdam2012.grupo8.ui;

import com.tdam2012.grupo8.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SmsNewActivity extends Activity 
{
	Button bot;
	EditText txtNum;
	EditText txtSms;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_sms);
        
       bot = (Button) findViewById(R.id.button1);
       txtNum = (EditText) findViewById(R.id.editText1);
       txtSms = (EditText) findViewById(R.id.editText2);
   
       bot.setOnClickListener(new OnClickListener(){
    		public void onClick(View v)
        	{
    			String num = txtNum.getText().toString();
    			String sms = txtSms.getText().toString();
    			
    			if(num.length()>0 && sms.length()>0)
    			{
    				sendSMS(num, sms);
    			}
    			
        	}
    	}); 
    }
	
	private void sendSMS(String num, String sms)
	{
		PendingIntent pi = PendingIntent.getActivity(this, 0 , new Intent(this, SmsNewActivity.class), 0);
		SmsManager sms_man = SmsManager.getDefault();
		sms_man.sendTextMessage(num, null, sms, pi, null);
		
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";
		
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
		
		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
		
		registerReceiver(new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode())
				{
					case Activity.RESULT_OK:
						Toast.makeText(getBaseContext(), "SMS sent", Toast.LENGTH_SHORT).show();
						break;
				
					case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
						Toast.makeText(getBaseContext(), "Generic failure", Toast.LENGTH_SHORT).show();
						break;
						
					case SmsManager.RESULT_ERROR_NO_SERVICE:
						Toast.makeText(getBaseContext(), "No service", Toast.LENGTH_SHORT).show();
						break;
						
					case SmsManager.RESULT_ERROR_NULL_PDU:
						Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
						break;
						
					case SmsManager.RESULT_ERROR_RADIO_OFF:
						Toast.makeText(getBaseContext(), "Radio off", Toast.LENGTH_SHORT).show();
						break;
				}
				
			}
		}, new IntentFilter(SENT));
	}
}
