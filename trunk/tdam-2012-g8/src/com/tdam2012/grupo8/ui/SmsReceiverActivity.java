package com.tdam2012.grupo8.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;

public class SmsReceiverActivity extends BroadcastReceiver 
{
	public void onReceive(Context context, Intent intent)
	{
		//toma el sms aprobado
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		if(bundle != null)
		{
			//si llega un sms lo recupera
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for(int i = 0; i < msgs.length; i++)
			{
				msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				str += "SMS from " + msgs[i].getOriginatingAddress();
				str += ":";
				str += msgs[i].getMessageBody().toString();
				str += "\n";
			}
			
			Toast.makeText(context, str, Toast.LENGTH_SHORT).show();//muestra el nuevo sms q ha llegado
		}
		
	}


}
