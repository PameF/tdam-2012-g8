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

public class ReceivedEmailReceiver extends BroadcastReceiver {

	public static final String NAME = "android.provider.Telephony.RECEIVED_EMAIL";
	@Override
	public void onReceive(Context context, Intent intent) {
		
		String phoneNumber = intent.getExtras().getString(ListActivity.PHONE_RESULT);
        long contactId = intent.getExtras().getLong(ListActivity.CONTACT_ID);
         
		ContactsRepository contactsRep = new ContactsRepository(context);
		Contact contact = contactsRep.getContactByPhoneNumber(phoneNumber);
		
		ActionRegistry reg = new ActionRegistry();
	       	
			reg.setDate(new Date());
	       	reg.setAction(ActionEnum.RECEIVED_EMAIL);
	       	reg.setContactId(contactId);
	       	reg.setContactName(contact.getName());
	       	reg.setContactPhoneNumber(phoneNumber);
	       
	       	ActionsRegistryRepository repository = new ActionsRegistryRepository(context);
	       	repository.insertRegistration(reg);
		
	}

}
