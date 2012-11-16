package com.tdam2012.grupo8.ui.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.entities.SmsMessage;

public class ContactSmsListAdapter extends BaseAdapter {

	private ArrayList<SmsMessage> messages;
	private LayoutInflater inflater;
	
	private int sentView;
	private int receivedView;
	
	public ContactSmsListAdapter(Context context, int sentView, int receivedView) {
		this.messages = new ArrayList<SmsMessage>(); 
		this.inflater = LayoutInflater.from(context);
		
		this.sentView = sentView;
		this.receivedView = receivedView;
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
		m.setPhoneNumber(phone);
		m.setMessage(message);
		m.setSentDate(new Date());
		
		messages.add(m);
		notifyDataSetChanged();
	}
	
	public void addMessage(SmsMessage message) {		
		messages.add(message);
		notifyDataSetChanged();
	}
	
	class Holder {
		public TextView textMessage;
		public TextView textDate;
		public boolean sent;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		Holder holder;
		SmsMessage item = (SmsMessage) getItem(position);
		
		boolean sent = item.getSentDate() != null;
		int layout = sent ? sentView : receivedView;;
		Date date = sent ? item.getSentDate() : item.getReceivedDate();
		
		/*if (convertView == null) {*/
			
			convertView = createView(layout, sent);
			holder = (Holder) convertView.getTag();
			
		/*} else {
			holder = (Holder) convertView.getTag();
			
			if(holder.sent != sent)
				createView(layout, sent);
		}*/
		
		holder.textMessage.setText(item.getMessage());
		holder.textDate.setText(new SimpleDateFormat("dd MMM, kk:mm").format(date));

		return convertView;
	}
	
	private View createView(int layout, boolean sent) {
		View convertView = inflater.inflate(layout, null);
		
		Holder holder = new Holder();
		holder.textMessage = (TextView) convertView.findViewById(R.id.sms_dialog_message);
		holder.textDate = (TextView) convertView.findViewById(R.id.sms_dialog_date);
		holder.sent = sent;
		
		convertView.setTag(holder);		
		return convertView;
	}
}
