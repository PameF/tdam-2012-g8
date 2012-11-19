package com.tdam2012.grupo8.ui;

import java.util.ArrayList;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.data.ActionsRegistryRepository;
import com.tdam2012.grupo8.data.ContactsRepository;
import com.tdam2012.grupo8.entities.SmsMessage;
import com.tdam2012.grupo8.receivers.SmsDeliveredReceiver;
import com.tdam2012.grupo8.receivers.SmsReceivedReceiver;
import com.tdam2012.grupo8.receivers.SmsSentReceiver;
import com.tdam2012.grupo8.ui.adapters.ContactSmsListAdapter;

import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SmsConversationActivity extends ListActivity implements OnClickListener
{
	public static final String PHONE_NUMBER_KEY = "PHONE_NUMBER";
	public static final String CONTACT_ID_KEY = "CONTACT_ID";
	public static final String CONTACT_NAME_KEY = "CONTACT_NAME";
	public static final String MESSAGE_KEY = "MESSAGE";

	private String phoneNumber;
	private String name;
	private long contact;
	
	private BroadcastReceiver sentReceiver;
	//private BroadcastReceiver deliveredReceiver;
	private BroadcastReceiver receivedReceiver;
	
	private ContactSmsListAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_conversation);
        
        inicializeContactInfo();
                
        Button enviar = (Button)findViewById(R.id.sms_list_contact_send);
        enviar.setOnClickListener(this);
        
        adapter = new ContactSmsListAdapter(this, R.layout.listitem_dialog_orange, R.layout.listitem_dialog_blue);
		getListView().setAdapter(adapter);
    }
	
	public void onResume() {
		super.onResume();

        sentReceiver = new SmsSentReceiver();
        //deliveredReceiver = new SmsDeliveredReceiver();
        receivedReceiver = new SmsReceivedReceiver(phoneNumber, adapter);
        
		registerReceiver(sentReceiver, new IntentFilter(SmsSentReceiver.NAME));
		//registerReceiver(deliveredReceiver, new IntentFilter(SmsDeliveredReceiver.NAME));
		registerReceiver(receivedReceiver, new IntentFilter(SmsReceivedReceiver.NAME));
        
		inicializeListView();
	}
	
	public void onPause() {
		super.onPause();
		
		unregisterReceiver(sentReceiver);
		//unregisterReceiver(deliveredReceiver);
		unregisterReceiver(receivedReceiver);
	}
	
	private void inicializeContactInfo() {
		
		phoneNumber = getIntent().getExtras().getString(PHONE_NUMBER_KEY);
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
	
	private void inicializeListView() {
				
		ActionsRegistryRepository repository = new ActionsRegistryRepository(this);
		ArrayList<SmsMessage> messages = repository.getSmsContactConversation(contact, false);
		
		adapter.setData(messages);
	}

	public void onClick(View v) {
		
		if(v.getId() == R.id.sms_list_contact_send) {
			
			EditText editText = (EditText)findViewById(R.id.sms_list_contact_message);
			String message = editText.getText().toString();
			
			Intent sentIntent = new Intent(SmsSentReceiver.NAME);
			sentIntent.putExtra(CONTACT_ID_KEY, contact);
			sentIntent.putExtra(PHONE_NUMBER_KEY, phoneNumber);
			sentIntent.putExtra(MESSAGE_KEY, message);
			
			PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			/*Intent deliveredIntent =  new Intent(SmsDeliveredReceiver.NAME);
			deliveredIntent.putExtra(CONTACT_ID_KEY, contact);
			deliveredIntent.putExtra(PHONE_NUMBER_KEY, phoneNumber);
			
			PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, deliveredIntent, 0);*/
									
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage(phoneNumber, null, message, sentPI, /*deliveredPI*/ null);
			
			adapter.addMessage(phoneNumber, message);
			editText.setText("");			
		}
	}
}
