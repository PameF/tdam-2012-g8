package com.tdam2012.grupo8.ui.user;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.networking.RegisterUserService;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_new);
		
		Button register = (Button)findViewById(R.id.user_new_register);
		register.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		if(v.getId() == R.id.user_new_register) {
			
			EditText textUserName = (EditText)findViewById(R.id.user_new_nickname);
			EditText textPass = (EditText)findViewById(R.id.user_new_password);
			EditText textPassConfirm = (EditText)findViewById(R.id.user_new_password_confirm);
			
			String name = textUserName.getText().toString();
			String pass = textPass.getText().toString();
			String passConfirm = textPassConfirm.getText().toString();
			
			if(pass.compareTo(passConfirm) != 0) {				
				Toast.makeText(this, "Las claves no coinciden", Toast.LENGTH_LONG).show();
				return;
			}
			
			new RegisterUserService().execute(this, name, pass);
		}		
	}	
}