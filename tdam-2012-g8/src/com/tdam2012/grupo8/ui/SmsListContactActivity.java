package com.tdam2012.grupo8.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.receivers.SmsDeliveredReceiver;
import com.tdam2012.grupo8.receivers.SmsSentReceiver;

import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SmsListContactActivity extends ListActivity implements OnClickListener
{
	public static final String PHONE_NUMBER_KEY = "PHONE_NUMBER";

	private String phoneNumber;
	
	private BroadcastReceiver sentReceiver;
	private BroadcastReceiver deliveredReceiver;
	
	private SmsListContactAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_list_contact);
        
        phoneNumber = getIntent().getExtras().getString(PHONE_NUMBER_KEY);
        
        Button enviar = (Button)findViewById(R.id.sms_list_contact_send);
        enviar.setOnClickListener(this);

        inicializeListView();
        registerBroadcastReceivers();
    }
	
	public void onStop() {
		super.onStop();
		
		unregisterReceiver(sentReceiver);
		unregisterReceiver(deliveredReceiver);
	}
	
	private void registerBroadcastReceivers() {
        
        sentReceiver = new SmsSentReceiver();
        deliveredReceiver = new SmsDeliveredReceiver();

		registerReceiver(sentReceiver, new IntentFilter(SmsSentReceiver.NAME));
		registerReceiver(deliveredReceiver, new IntentFilter(SmsDeliveredReceiver.NAME));
	}
	
	private void inicializeListView() {		
		adapter = new SmsListContactAdapter();
		getListView().setAdapter(adapter);
	}

	public void onClick(View v) {
		
		if(v.getId() == R.id.sms_list_contact_send) {
			
			EditText editText = (EditText)findViewById(R.id.sms_list_contact_message);
			String message = editText.getText().toString();
			
			PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SmsSentReceiver.NAME), 0);
			PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(SmsDeliveredReceiver.NAME), 0);
									
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
			
			adapter.addMessage(phoneNumber, message);
			editText.setText("");
		}
	}
	
	
	class SmsMessage {
		public String phoneNumber;
		public String message;
		public String date;
		
		public String getPhoneNumber() {
			return phoneNumber;
		}
		
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		
		public String getMessage() {
			return message;
		}
		
		public void setMessage(String message) {
			this.message = message;
		}
		
		public String getDate() {
			return date;
		}
		
		public void setDate(String date) {
			this.date = date;
		}
	}
	
	class SmsListContactAdapter extends BaseAdapter {

		private ArrayList<SmsMessage> messages;
    	private LayoutInflater inflater;
		
		public SmsListContactAdapter() {
			this.messages = new ArrayList<SmsMessage>();
    		this.inflater = LayoutInflater.from(SmsListContactActivity.this);
		}
		
		public int getCount() {
			return messages.size();
		}

		public Object getItem(int position) {
			return messages.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		public void addMessage(String phone, String message) {
			SmsMessage m = new SmsMessage();
			m.setPhoneNumber(phoneNumber);
			m.setMessage(message);
			m.setDate(new SimpleDateFormat("dd MMM, kk:mm").format(new Date()));			
			
			messages.add(m);
			notifyDataSetChanged();
		}
		
		class Holder {
			public TextView textMessage;
			public TextView textDate;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.listitem_dialog_orange, null);
				
				holder = new Holder();
				holder.textMessage = (TextView) convertView.findViewById(R.id.sms_dialog_message);
				holder.textDate = (TextView) convertView.findViewById(R.id.sms_dialog_date);
				
				convertView.setTag(holder);
				
			} else {
				holder = (Holder) convertView.getTag();
			}

			SmsMessage item = (SmsMessage) getItem(position);
				
			holder.textMessage.setText(item.getMessage());
			holder.textDate.setText(item.getDate());

			return convertView;
		}
		
	}
}
