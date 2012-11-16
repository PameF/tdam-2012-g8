package com.tdam2012.grupo8.networking;

import java.util.Date;

import com.tdam2012.grupo8.base.Preferences;
import com.tdam2012.grupo8.data.ActionsRegistryRepository;
import com.tdam2012.grupo8.data.ActionsRegistryRepository.ActionEnum;
import com.tdam2012.grupo8.entities.ActionRegistry;
import com.tdam2012.grupo8.ui.DashboardActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

public class SendMessageService  extends AsyncTask<Object, Void, String> {

	private Context context;
	private String username;
	private String password;
	private String contactUsername; 
	private String contactName; 
	private String contactId; 
	private String message;
	
	@Override
	protected String doInBackground(Object... params) {
		
		context = (Context)params[0]; 
		contactUsername = (String)params[1];
		contactName = (String)params[2];
		contactId = (String)params[3];
		message = (String)params[4];
		
		SharedPreferences preferences = context.getSharedPreferences(Preferences.PREFERENCE_USER, context.MODE_PRIVATE);
		
		username = preferences.getString(Preferences.PREFERENCE_USER_NAME, "");
		password = preferences.getString(Preferences.PREFERENCE_USER_PASSWORD, "");
		
		String xml = generateXmlSendMessage();
		String response = MessageSenderService.post(xml);
		
		return response;
	}

	@Override
	protected void onPostExecute(String result) {
		
		String message = MessageSenderService.processRequest(result);		
		if(message == null) {
			
			ActionRegistry reg = new ActionRegistry();
 	       	reg.setAction(ActionEnum.SENT_MESSAGE_WEB);
 	       	reg.setContactId(Long.parseLong(contactId));
 	       	reg.setContactName(contactName);
 	       	reg.setContactPhoneNumber(contactUsername);
 	       	reg.setMessage(message);
 	       	reg.setDate(new Date());
 	       
 	       ActionsRegistryRepository repository = new ActionsRegistryRepository(context);
 	       repository.insertRegistration(reg);   
		}
		else {
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
	}
	
	private String generateXmlSendMessage() {
		
		String xml = "<action id=\"REQUEST_RANDOM_VALUE\" name=\"send-message\">" +
						"<action-detail>" +
							"<auth username=\"" + username + "\" key=\"" + password + "\"></auth>" +
							"<message to=\"" + contactUsername + "\">" + message + "</message>" +
						"</action-detail>" +
					"</action>";
		
		return xml;
	}
}
