package com.tdam2012.grupo8.ui;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.data.SmsRepository;
import com.tdam2012.grupo8.receivers.SmsDeliveredReceiver;
import com.tdam2012.grupo8.receivers.SmsReceivedReceiver;
import com.tdam2012.grupo8.receivers.SmsSentReceiver;
import com.tdam2012.grupo8.ui.adapters.ContactSmsListAdapter;

import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SmsListContactActivity extends ListActivity implements OnClickListener
{
	public static final String PHONE_NUMBER_KEY = "PHONE_NUMBER";
	public static final String CONTACT_ID_KEY = "CONTACT_ID";

	private String phoneNumber;
	private long contact;
	
	private BroadcastReceiver sentReceiver;
	private BroadcastReceiver deliveredReceiver;
	private BroadcastReceiver receivedReceiver;
	
	private ContactSmsListAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_list_contact);
        
        phoneNumber = getIntent().getExtras().getString(PHONE_NUMBER_KEY);
        contact = getIntent().getExtras().getLong(CONTACT_ID_KEY);
        
        Button enviar = (Button)findViewById(R.id.sms_list_contact_send);
        enviar.setOnClickListener(this);
        
        SmsRepository repository = new SmsRepository(this);
        repository.getContactConversations("", false);

        inicializeListView();
    }
	
	public void onResume() {
		super.onResume();

        sentReceiver = new SmsSentReceiver();
        deliveredReceiver = new SmsDeliveredReceiver();
        receivedReceiver = new SmsReceivedReceiver(phoneNumber, adapter);
        
		registerReceiver(sentReceiver, new IntentFilter(SmsSentReceiver.NAME));
		registerReceiver(deliveredReceiver, new IntentFilter(SmsDeliveredReceiver.NAME));
		registerReceiver(receivedReceiver, new IntentFilter(SmsReceivedReceiver.NAME));
	}
	
	public void onPause() {
		super.onPause();
		
		unregisterReceiver(sentReceiver);
		unregisterReceiver(deliveredReceiver);
		unregisterReceiver(receivedReceiver);
	}
	
	private void inicializeListView() {		
		adapter = new ContactSmsListAdapter(this, R.layout.listitem_dialog_orange, R.layout.listitem_dialog_blue);
		getListView().setAdapter(adapter);
	}

	public void onClick(View v) {
		
		if(v.getId() == R.id.sms_list_contact_send) {
			
			EditText editText = (EditText)findViewById(R.id.sms_list_contact_message);
			String message = editText.getText().toString();
			
			Intent sentIntent = new Intent(SmsSentReceiver.NAME);
			sentIntent.putExtra(CONTACT_ID_KEY, contact);
			
			PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);
			
			Intent deliveredIntent =  new Intent(SmsDeliveredReceiver.NAME);
			deliveredIntent.putExtra(CONTACT_ID_KEY, contact);
			
			PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, deliveredIntent, 0);
									
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
			
			adapter.addMessage(phoneNumber, message);
			editText.setText("");
		}
	}
}
