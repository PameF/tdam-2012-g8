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
       		
       		ActionsRegistryRepository repository = new ActionsRegistryRepository(context);
       		int state = extras.getInt(TelephonyManager.EXTRA_STATE);
	       	
       		switch(state) {	       	
       		
	       		case TelephonyManager.CALL_STATE_IDLE:
	       			state = state;
	       			break;
       		
		       	case TelephonyManager.CALL_STATE_RINGING:
		       		// Se llama cuando recibo una llamada
		       		state = state;
		       		break;
		       		
		       	case TelephonyManager.CALL_STATE_OFFHOOK:
		       		// Se llama cuando hago una llamada
		       		state = state;
		       		break;
	       	}
       	}
       	
       	//http://stackoverflow.com/questions/2872214/how-do-i-get-state-of-a-outgoing-call-in-android-phone
       	//http://stackoverflow.com/questions/10213659/how-to-get-the-state-for-outgoing-calls
       	//http://stackoverflow.com/questions/11031590/how-to-get-outgoing-call-connected-state
	}
	
	private ActionRegistry createActionRegistry(Context context, Bundle extras) {
		String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);        
		
        ContactsRepository contactRep = new ContactsRepository(context);
        Contact contact = contactRep.getContactByPhoneNumber(phoneNumber);
        
     	ActionRegistry reg = new ActionRegistry();
       	reg.setContactId(contact.getId());
       	reg.setContactName(contact.getName());
       	reg.setContactPhoneNumber(phoneNumber);
       	reg.setDate(new Date());
       	
       	return reg;
	}
}

