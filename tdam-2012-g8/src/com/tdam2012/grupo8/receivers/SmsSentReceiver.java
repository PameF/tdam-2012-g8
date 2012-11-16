package com.tdam2012.grupo8.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SmsSentReceiver extends BroadcastReceiver {
	
	public static final String NAME = "com.tdam2012.grupo8.receiver.SMS_SENT";

	@Override
	public void onReceive(Context context, Intent intent) {
		
		String message = null;
		
		switch (getResultCode())
		{
			case Activity.RESULT_OK:
				message = "SMS sent";
				
				// TODO PAME Agregar registro
				break;
		
			case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				message = "Generic failure";
				break;
				
			case SmsManager.RESULT_ERROR_NO_SERVICE:
				message = "No service";
				break;
				
			case SmsManager.RESULT_ERROR_NULL_PDU:
				message = "Null PDU";
				break;
				
			case SmsManager.RESULT_ERROR_RADIO_OFF:
				message = "Radio off";
				break;
		}
		
		if(message != null)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		
		//TODO Actualizar fecha y hora de envío en el mensaje		
	}

}
