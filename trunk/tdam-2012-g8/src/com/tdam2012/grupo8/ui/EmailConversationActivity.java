package com.tdam2012.grupo8.ui;

import java.util.ArrayList;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.data.ActionsRegistryRepository;
import com.tdam2012.grupo8.data.ContactsRepository;
import com.tdam2012.grupo8.entities.Email;
import com.tdam2012.grupo8.receivers.ReceivedEmailReceiver;
import com.tdam2012.grupo8.receivers.SentEmailReceiver;
import com.tdam2012.grupo8.ui.adapters.ContactEmailListAdapter;

import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EmailConversationActivity extends ListActivity implements OnClickListener {

	public static final String EMAIL_ADDRESS_KEY = "CONTACT_EMAIL_ADDRESS";
	public static final String CONTACT_ID_KEY = "CONTACT_ID";
	public static final String CONTACT_NAME_KEY = "CONTACT_NAME";
	public static final String SUBJECT_KEY = "SUBJECT";
	public static final String CONTENT_EMAIL__KEY = "CONTENT_EMAIL";

	private String emailAddress;
	private String name;
	private long contact;
	private String subject;
	private String contentEmail;
	
	private BroadcastReceiver sentReceiver;
	private BroadcastReceiver receivedReceiver;
	
	private ContactEmailListAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_conversation);
        
        inicializeContactInfo();
                
        Button enviar = (Button)findViewById(R.id.email_list_contact_send);
        enviar.setOnClickListener(this);
        
        adapter = new ContactEmailListAdapter(this, R.layout.listitem_email);
		getListView().setAdapter(adapter);
    }
	
	public void onResume() {
		super.onResume();

        sentReceiver = new SentEmailReceiver();
        receivedReceiver = new ReceivedEmailReceiver(emailAddress, adapter);
        
		inicializeListView();
	}
	
	public void onPause() {
		super.onPause();
		
		unregisterReceiver(sentReceiver);
		unregisterReceiver(receivedReceiver);
	}
		
	private void inicializeContactInfo() {
		
		emailAddress = getIntent().getExtras().getString(EMAIL_ADDRESS_KEY);
        contact = getIntent().getExtras().getLong(CONTACT_ID_KEY);
        name = getIntent().getExtras().getString(CONTACT_NAME_KEY);
        subject = getIntent().getExtras().getString(SUBJECT_KEY);
        contentEmail = getIntent().getExtras().getString(CONTENT_EMAIL__KEY);
        
        ContactsRepository repository = new ContactsRepository(this);
        Bitmap avatar = repository.getContactPhoto(contact);
        
        ImageView imageAvatar = (ImageView)findViewById(R.id.sms_list_contact_avatar);
        
        if(avatar != null)
        	imageAvatar.setImageBitmap(avatar);
        else
        	imageAvatar.setImageResource(R.drawable.ic_launcher);
        
        TextView textName = (TextView)findViewById(R.id.email_list_contact_name);
        textName.setText(name);
	}
	
	private void inicializeListView() {
				
		ActionsRegistryRepository repository = new ActionsRegistryRepository(this);
		ArrayList<Email> email = repository.getEmailsContact(contact);
		
		adapter.setData(email);
	}
	
	public void onClick(View v) {
		
		if(v.getId() == R.id.email_list_contact_send) {
			
			EditText editText = (EditText)findViewById(R.id.email_list_contact_message);
			String email = editText.getText().toString();
			
			Intent sentIntent = new Intent(SentEmailReceiver.NAME);
			sentIntent.putExtra(CONTACT_ID_KEY, contact);
			sentIntent.putExtra(EMAIL_ADDRESS_KEY, emailAddress);
			sentIntent.putExtra(SUBJECT_KEY, subject);
			
			PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			adapter.addEmail(emailAddress, subject, email);
			editText.setText("");			
		}
		
	}

}
