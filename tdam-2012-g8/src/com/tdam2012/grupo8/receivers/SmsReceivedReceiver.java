package com.tdam2012.grupo8.receivers;

import java.util.Date;

import com.tdam2012.grupo8.ui.adapters.ContactSmsListAdapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;

public class SmsReceivedReceiver extends BroadcastReceiver 
{
	public static final String NAME = "android.provider.Telephony.SMS_RECEIVED";
	
	private ContactSmsListAdapter adapter;
	private String phoneNumber;
	
	public SmsReceivedReceiver(String phone, ContactSmsListAdapter adapter) {
		this.adapter = adapter;
	}
	
	@SuppressWarnings("deprecation")
	public void onReceive(Context context, Intent intent)
	{
		com.tdam2012.grupo8.entities.SmsMessage sms;
		Bundle bundle = intent.getExtras();

		if(bundle != null)
		{
			//si llega un sms lo recupera
			Object[] pdus = (Object[]) bundle.get("pdus");
			
			for(int i = 0; i < pdus.length; i++)
			{
				SmsMessage message = SmsMessage.createFromPdu((byte[])pdus[i]);
				
				if(message.getOriginatingAddress().endsWith(phoneNumber)) {
					
					sms = new com.tdam2012.grupo8.entities.SmsMessage();
					
					sms.setMessage(message.getMessageBody().toString());
					sms.setReceivedDate(new Date());
					
					adapter.addMessage(sms);
				}
			}
		}
	}
}
