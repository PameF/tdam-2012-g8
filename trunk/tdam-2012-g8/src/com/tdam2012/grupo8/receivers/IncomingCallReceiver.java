package com.tdam2012.grupo8.receivers;

import java.util.Date;

import com.tdam2012.grupo8.data.ActionsRegistryRepository;
import com.tdam2012.grupo8.data.ContactsRepository;
import com.tdam2012.grupo8.data.ActionsRegistryRepository.ActionEnum;
import com.tdam2012.grupo8.entities.ActionRegistry;
import com.tdam2012.grupo8.entities.Contact;
import com.tdam2012.grupo8.ui.contacts.ListActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;


public class IncomingCallReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {

       	Bundle extras = intent.getExtras();
       	
       	if (extras != null) {
       		
			String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);        
			
	        ContactsRepository contactRep = new ContactsRepository(context);
	        Contact contact = contactRep.getContactByPhoneNumber(phoneNumber);
	        
	     	ActionsRegistryRepository repository = new ActionsRegistryRepository(context);
	     	
	     	ActionRegistry reg = new ActionRegistry();
	       	reg.setContactId(contact.getId());
	       	reg.setContactName(contact.getName());
	       	reg.setContactPhoneNumber(phoneNumber);
	       	reg.setDate(new Date());

       		int state = extras.getInt(TelephonyManager.EXTRA_STATE);
	       	
       		switch(state) {	       	
		       	case TelephonyManager.CALL_STATE_RINGING:
		       		reg.setAction(ActionEnum.INCOMING_CALL);
		       		break;
		       		
		       	case TelephonyManager.CALL_STATE_OFFHOOK:
		       		reg.setAction(ActionEnum.MISSED_CALL);
		       		break;
	       	}	
       		
       		repository.insertRegistration(reg);
       	}
	}	
}

