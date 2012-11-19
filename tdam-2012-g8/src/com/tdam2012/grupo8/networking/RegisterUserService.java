package com.tdam2012.grupo8.networking;

import com.tdam2012.grupo8.base.Preferences;
import com.tdam2012.grupo8.ui.DashboardActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

public class RegisterUserService extends AsyncTask<Object, Void, String> {

	private Context context;
	private String username;
	private String password;
	
	@Override
	protected String doInBackground(Object... params) {

		context = (Context)params[0];
		username = (String)params[1];
		password = (String)params[2];
		
		String xml = generateXmlUserRegister(username, password);
		return MessageSenderService.post(xml);	
	}

	@Override
	protected void onPostExecute(String result) {

		String message = MessageSenderService.processRequest(result);		
		if(message == null) {
			
			SharedPreferences preferences = context.getSharedPreferences(Preferences.PREFERENCE_USER, context.MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();
			
			editor.putBoolean(Preferences.PREFERENCE_USER_IS_LOGGED, true);
			editor.putString(Preferences.PREFERENCE_USER_NAME, username);
			editor.putString(Preferences.PREFERENCE_USER_PASSWORD, password);
			editor.commit();
			
			Intent intent = new Intent(context, DashboardActivity.class);
			context.startActivity(intent);
		}
		else {
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
	}
	
	private String generateXmlUserRegister(String username, String password) {
		
		String xml = "<action id=\"REQUEST_RANDOM_VALUE\" name=\"register-user\">" +
						"<action-detail>" +
							"<user username=\"" + username + "\" password=\"" + password + "\"/>" +
						"</action-detail>" +
					 "</action>";
		
		return xml;
	}
}
