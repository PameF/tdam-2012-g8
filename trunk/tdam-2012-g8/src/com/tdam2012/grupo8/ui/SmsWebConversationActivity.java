package com.tdam2012.grupo8.ui;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.data.ContactsRepository;
import com.tdam2012.grupo8.networking.SendMessageService;
import com.tdam2012.grupo8.ui.adapters.ContactSmsListAdapter;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SmsWebConversationActivity extends ListActivity implements OnClickListener
{
	public static final String CONTACT_USERNAME_KEY = "CONTACT_USERNAME_KEY";
	public static final String CONTACT_ID_KEY = "CONTACT_ID_KEY";
	public static final String CONTACT_NAME_KEY = "CONTACT_NAME_KEY";
	
	private String username;
	private String name;
	private long contact;
	
	private ContactSmsListAdapter adapter;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_web_conversation);
        
        inicializeContactInfo();
        
        Button enviar = (Button)findViewById(R.id.sms_list_contact_send);
        enviar.setOnClickListener(this);
        
        adapter = new ContactSmsListAdapter(this, R.layout.listitem_dialog_pink, R.layout.listitem_dialog_green);
		getListView().setAdapter(adapter);
    }
	
	private void inicializeContactInfo() {
		
		username = getIntent().getExtras().getString(CONTACT_USERNAME_KEY);
        contact = getIntent().getExtras().getLong(CONTACT_ID_KEY);
        name = getIntent().getExtras().getString(CONTACT_NAME_KEY);
        
        ContactsRepository repository = new ContactsRepository(this);
        Bitmap avatar = repository.getContactPhoto(contact);
        
        ImageView imageAvatar = (ImageView)findViewById(R.id.sms_list_contact_avatar);
        
        if(avatar != null)
        	imageAvatar.setImageBitmap(avatar);
        else
        	imageAvatar.setImageResource(R.drawable.ic_launcher);
        
        TextView textName = (TextView)findViewById(R.id.sms_list_contact_name);
        textName.setText(name);
	}

	public void onClick(View v) {
		
		if(v.getId() == R.id.sms_list_contact_send) {
			
			EditText editText = (EditText)findViewById(R.id.sms_list_contact_message);
			String message = editText.getText().toString();
			
			new SendMessageService().execute(this, username, name, contact, message);
			
			adapter.addMessage(username, message);
			editText.setText("");			
		}
	}
}