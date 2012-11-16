package com.tdam2012.grupo8.receivers;

import java.util.Date;

import com.tdam2012.grupo8.data.ActionsRegistryRepository;
import com.tdam2012.grupo8.data.ContactsRepository;
import com.tdam2012.grupo8.data.ActionsRegistryRepository.ActionEnum;
import com.tdam2012.grupo8.entities.ActionRegistry;
import com.tdam2012.grupo8.entities.Contact;
import com.tdam2012.grupo8.ui.SmsListContactActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SentEmailReceiver extends BroadcastReceiver{

	public static final String NAME = "android.provider.Telephony.SENT_EMAIL";
	@Override
	public void onReceive(Context context, Intent intent) {
		switch (getResultCode())
		{
			case Activity.RESULT_OK:
				
				String phoneNumber = intent.getExtras().getString(SmsListContactActivity.PHONE_NUMBER_KEY);
		        long contactId = intent.getExtras().getLong(SmsListContactActivity.CONTACT_ID_KEY);
				
		        ContactsRepository contactRep = new ContactsRepository(context);
		        Contact contact = contactRep.getContactById(contactId);
		        
				ActionRegistry reg = new ActionRegistry();
	 	       	reg.setAction(ActionEnum.SENT_EMAIL);
	 	       	reg.setContactId(contactId);
	 	       	reg.setContactName(contact.getName());
	 	       	reg.setContactPhoneNumber(phoneNumber);
	 	       	reg.setDate(new Date());
	 	       
	 	       	ActionsRegistryRepository repository = new ActionsRegistryRepository(context);
	 	       	repository.insertRegistration(reg);
	 	       	
				break;
		
	}
	}
}
