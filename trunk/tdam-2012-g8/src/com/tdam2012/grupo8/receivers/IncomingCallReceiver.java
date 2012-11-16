package com.tdam2012.grupo8.receivers;

import java.util.Date;

import com.tdam2012.grupo8.data.ActionsRegistryRepository;
import com.tdam2012.grupo8.data.ContactsRepository;
import com.tdam2012.grupo8.data.ActionsRegistryRepository.ActionEnum;
import com.tdam2012.grupo8.entities.ActionRegistry;
import com.tdam2012.grupo8.entities.Contact;
import com.tdam2012.grupo8.ui.SmsListContactActivity;
import com.tdam2012.grupo8.ui.contacts.ListActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Region;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;


public class IncomingCallReceiver extends BroadcastReceiver{

	public static final String NAME = "com.tdam2012.grupo8.receiver.INCOMING_CALL";
	String phoneNumber;
	long contactId;
	Contact contact;
	

	@Override
	public void onReceive(Context context, Intent intent) {
		
		phoneNumber = intent.getExtras().getString(ListActivity.PHONE_RESULT);
        contactId = intent.getExtras().getLong(ListActivity.CONTACT_ID);
        
		
        ContactsRepository contactRep = new ContactsRepository(context);
        contact = contactRep.getContactById(contactId);
        
     	ActionsRegistryRepository repository = new ActionsRegistryRepository(context);
     	ActionRegistry reg = new ActionRegistry();
        
       	Bundle extras = intent.getExtras();
       	if (extras != null){
       		String state = extras.getString(TelephonyManager.EXTRA_STATE);
       		if (state.equals(TelephonyManager.CALL_STATE_RINGING)){
       		
       			reg.setAction(ActionEnum.INCOMING_CALL);
		       	reg.setContactId(contactId);
		       	reg.setContactName(contact.getName());
		       	reg.setContactPhoneNumber(phoneNumber);
		       	reg.setDate(new Date());
       		}
       		else{
       			if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
       				
           			reg.setAction(ActionEnum.MISSED_CALL);
    		       	reg.setContactId(contactId);
    		       	reg.setContactName(contact.getName());
    		       	reg.setContactPhoneNumber(phoneNumber);
    		       	reg.setDate(new Date());
           		}
       		}
       	}
       	repository.insertRegistration(reg);
	}
	
	/*public void onCallStateChanged(int state, String incomingNumber){
	
		switch (state)
		{
			case TelephonyManager.CALL_STATE_IDLE:
				    
		       	reg.setAction(ActionEnum.INCOMING_CALL);
		       	reg.setContactId(contactId);
		       	reg.setContactName(contact.getName());
		       	reg.setContactPhoneNumber(phoneNumber);
		       	reg.setDate(new Date());
		       	      	
				break;
			
			case TelephonyManager.CALL_STATE_OFFHOOK:
				
		       	reg.setAction(ActionEnum.MISSED_CALL);
		       	reg.setContactId(contactId);
		       	reg.setContactName(contact.getName());
		       	reg.setContactPhoneNumber(phoneNumber);
		       	reg.setDate(new Date());
		       	
				break;						
		}
				                  
	}*/
		
}

