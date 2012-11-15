package com.tdam2012.grupo8.ui.user;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.base.Preferences;
import com.tdam2012.grupo8.ui.DashboardActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_login);
		
		verificarUsuarioLogueado();
		
		Button button = (Button)findViewById(R.id.user_login_register);
		button.setOnClickListener(this);
		
		button = (Button)findViewById(R.id.user_login_login);
		button.setOnClickListener(this);
	}
    
    private void verificarUsuarioLogueado() {
    	
    	SharedPreferences preferences = getSharedPreferences(Preferences.PREFERENCE_USER, MODE_PRIVATE);
    	
    	boolean isLogged = preferences.getBoolean(Preferences.PREFERENCE_USER_IS_LOGGED, false);
    	
    	if(isLogged) {    		
    		Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
    		startActivity(intent);
    	}
    }

	public void onClick(View v) {
		
		Intent intent = null;
	
		switch(v.getId()) {		
			case R.id.user_login_login:
				
				EditText textUserName = (EditText)findViewById(R.id.user_login_nickname);
				EditText textPass = (EditText)findViewById(R.id.user_login_password);
				
				SharedPreferences preferences = getSharedPreferences(Preferences.PREFERENCE_USER, MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				
				editor.putBoolean(Preferences.PREFERENCE_USER_IS_LOGGED, true);
				editor.putString(Preferences.PREFERENCE_USER_NAME, textUserName.getText().toString());
				editor.putString(Preferences.PREFERENCE_USER_PASSWORD, textPass.getText().toString());
				editor.commit();
				
				intent = new Intent(LoginActivity.this, DashboardActivity.class);
				
				break;
				
			case R.id.user_login_register:
				intent = new Intent(LoginActivity.this, NewActivity.class);			
				break;			
		}
		
		if(intent != null) {
			startActivity(intent);
		}
	}
}