package com.tdam2012.grupo8.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SmsDeliveredReceiver extends BroadcastReceiver {

	public static final String NAME = "com.tdam2012.grupo8.receiver.SMS_DELIVERED";
	
	@Override
	public void onReceive(Context context, Intent intent) {

		switch (getResultCode())
		{
			case Activity.RESULT_OK:
				Toast.makeText(context, "SMS delivered", Toast.LENGTH_SHORT).show();
				break;
				
			case Activity.RESULT_CANCELED:
				Toast.makeText(context, "SMS not delivered", Toast.LENGTH_SHORT).show();
				break;                        
		}
		
	}

}
