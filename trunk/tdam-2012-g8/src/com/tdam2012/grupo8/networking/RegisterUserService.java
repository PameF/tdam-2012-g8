package com.tdam2012.grupo8.networking;

import com.tdam2012.grupo8.ui.DashboardActivity;

import android.content.Context;
import android.content.Intent;
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

		String message = "";
		boolean success = processRequest(result, message);
		
		if(success) {
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
		
	private boolean processRequest(String xml, String message) {
		
		return true;
	}
}
