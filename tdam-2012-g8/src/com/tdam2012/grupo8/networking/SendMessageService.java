package com.tdam2012.grupo8.networking;

import java.util.Date;

import com.tdam2012.grupo8.base.Preferences;
import com.tdam2012.grupo8.data.ActionsRegistryRepository;
import com.tdam2012.grupo8.data.ActionsRegistryRepository.ActionEnum;
import com.tdam2012.grupo8.entities.ActionRegistry;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

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
		
		String response = null;
		
		context = (Context)params[0]; 
		
		if(isOnline()) {		
			contactUsername = (String)params[1];
			contactName = (String)params[2];
			contactId = String.valueOf(params[3]);
			message = (String)params[4];
			
			SharedPreferences preferences = context.getSharedPreferences(Preferences.PREFERENCE_USER, context.MODE_PRIVATE);
			
			username = preferences.getString(Preferences.PREFERENCE_USER_NAME, "");
			password = preferences.getString(Preferences.PREFERENCE_USER_PASSWORD, "");
			
			String xml = generateXmlSendMessage();
			response = MessageSenderService.post(xml);
		}
		
		return response;
	}

	@Override
	protected void onPostExecute(String result) {
		
		String error = null;
		
		if(result == null) 
			error = "No ha conexión a internet";
		else 
			error = MessageSenderService.processRequest(result);	
		
		if(error == null) {
			
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
			AlertDialog builder = new AlertDialog.Builder(context)
	        	.setTitle("Servicio Web")
	        	.setMessage(error)
	        	.setPositiveButton("OK",null)
	        	.create();
			
			builder.show(); 
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
	
	private boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    
	    return false;
	}
}
