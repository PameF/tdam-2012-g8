package com.tdam2012.grupo8.receivers;

import java.util.Date;

import com.tdam2012.grupo8.data.ActionsRegistryRepository;
import com.tdam2012.grupo8.data.ActionsRegistryRepository.ActionEnum;
import com.tdam2012.grupo8.data.ContactsRepository;
import com.tdam2012.grupo8.entities.ActionRegistry;
import com.tdam2012.grupo8.entities.Contact;
import com.tdam2012.grupo8.ui.SmsListContactActivity;

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
				
				String phoneNumber = intent.getExtras().getString(SmsListContactActivity.PHONE_NUMBER_KEY);
		        long contactId = intent.getExtras().getLong(SmsListContactActivity.CONTACT_ID_KEY);
		        String messageBody = intent.getExtras().getString(SmsListContactActivity.MESSAGE_KEY);
				
		        ContactsRepository contactRep = new ContactsRepository(context);
		        Contact contact = contactRep.getContactById(contactId);
		        		        
		        // Guardo del envío de sms en el registro del contacto
				ActionRegistry reg = new ActionRegistry();
	 	       	reg.setAction(ActionEnum.SENT_MESSAGE);
	 	       	reg.setContactId(contact.getId());
	 	       	reg.setContactName(contact.getName());
	 	       	reg.setContactPhoneNumber(phoneNumber);
	 	       	reg.setMessage(messageBody);
	 	       	reg.setDate(new Date());
	 	       
	 	       	ActionsRegistryRepository repository = new ActionsRegistryRepository(context);
	 	       	repository.insertRegistration(reg);
	 	       	
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
	}
}
