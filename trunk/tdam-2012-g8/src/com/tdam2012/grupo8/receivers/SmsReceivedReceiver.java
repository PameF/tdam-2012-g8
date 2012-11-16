package com.tdam2012.grupo8.receivers;

import java.util.Date;

import com.tdam2012.grupo8.data.ActionsRegistryRepository;
import com.tdam2012.grupo8.data.ContactsRepository;
import com.tdam2012.grupo8.data.ActionsRegistryRepository.ActionEnum;
import com.tdam2012.grupo8.entities.ActionRegistry;
import com.tdam2012.grupo8.entities.Contact;
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
	
	public SmsReceivedReceiver(String phoneNumber, ContactSmsListAdapter adapter) {
		this.adapter = adapter;
		this.phoneNumber = phoneNumber;
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
				
				sms = new com.tdam2012.grupo8.entities.SmsMessage();
				sms.setPhoneNumber(message.getDisplayOriginatingAddress());
				sms.setMessage(message.getMessageBody().toString());
				sms.setReceivedDate(new Date());
				
				ContactsRepository contactsRep = new ContactsRepository(context);
				Contact contact = contactsRep.getContactByPhoneNumber(sms.getPhoneNumber());
				
				ActionRegistry reg = new ActionRegistry();
	 	       	
				reg.setDate(new Date());
	 	       	reg.setAction(ActionEnum.RECEIVED_MESSAGE);
	 	       	reg.setContactId(contact.getId());
	 	       	reg.setContactName(contact.getName());
	 	       	reg.setContactPhoneNumber(sms.getPhoneNumber());
	 	       	reg.setMessage(sms.getMessage());
	 	       
	 	       	ActionsRegistryRepository repository = new ActionsRegistryRepository(context);
	 	       	repository.insertRegistration(reg);
	 	       	
	 	       	// Si corresponde al contacto de la conversacion actual, actualizo la lista
				if(sms.getPhoneNumber().endsWith(phoneNumber))
					adapter.addMessage(sms);
			}
		}
	}
}
