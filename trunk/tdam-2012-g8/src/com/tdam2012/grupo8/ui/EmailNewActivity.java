package com.tdam2012.grupo8.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.tdam2012.grupo8.R;

public class EmailNewActivity extends Activity
{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_new);
        String listContact[] = {"pamef.2707@gmail.com", "pame_f27@hotmail.com"};
        
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, listContact);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Asunto: ");
        intent.putExtra(Intent.EXTRA_TEXT,"Contenido: ");
        startActivity(Intent.createChooser(intent, "Email"));
    }
	
	/*Button butSend;
	EditText txtTo;
	EditText txtSubject;
	EditText txtMessage;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_new);
        
        butSend = (Button) findViewById(R.id.button2);
        txtTo = (EditText) findViewById(R.id.editText1);
        txtSubject = (EditText) findViewById(R.id.EditText01);
        txtMessage = (EditText) findViewById(R.id.editText2);
        
        butSend.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String to = txtTo.getText().toString();
				String subject = txtSubject.getText().toString();
				String message = txtMessage.getText().toString();
				
				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
				email.putExtra(Intent.EXTRA_SUBJECT, subject);
				email.putExtra(Intent.EXTRA_TEXT, message);
				
				email.setType("message/rfc822");
				
				startActivity(Intent.createChooser(email, "pamef.2707@gmail.com"));
				
			}
		});
    }*/

}
