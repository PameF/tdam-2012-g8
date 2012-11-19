package com.tdam2012.grupo8.ui.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.entities.Email;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactEmailListAdapter extends BaseAdapter {

	
	private ArrayList<Email> email;
	private LayoutInflater inflater;
	
	private int View;
	
	public ContactEmailListAdapter(Context context, int View) {
		this.email = new ArrayList<Email>(); 
		this.inflater = LayoutInflater.from(context);
		
		this.View = View;
	}
		
	public int getCount() {
		return email.size();
	}

	public Object getItem(int position) {
		return email.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void addEmail(String emailAddress, String subject, String contentEmail) {
		Email mail = new Email();
		mail.setEmailAddress(emailAddress);
		mail.setSubject(subject);
		mail.setContentEmail(contentEmail);
		
		email.add(mail);
		notifyDataSetChanged();
	}
	
	public void setData(ArrayList<Email> email) {
		this.email = email;
		notifyDataSetChanged();
	}
	
	class Holder {
		public TextView textSubject;
		public TextView textContentEmail;
		public TextView textDate;
		public boolean sent;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		Holder holder;
		Email item = (Email) getItem(position);
		
		boolean sent = item.getSentDate() != null;
		Date date = sent ? item.getSentDate() : item.getReceivedDate();
		convertView = createView(R.layout.listitem_email, sent);
		holder = (Holder) convertView.getTag();
		holder.textSubject.setText(item.getSubject());
		holder.textContentEmail.setText(item.getContentEmail());
		holder.textDate.setText(new SimpleDateFormat("dd MMM, kk:mm").format(date));

		return convertView;
	}
	
	private View createView(int layout, boolean sent) {
		View convertView = inflater.inflate(layout, null);
		
		Holder holder = new Holder();
		holder.textSubject = (TextView) convertView.findViewById(R.id.email_list_subject);
		holder.textDate = (TextView) convertView.findViewById(R.id.email_list_date);
		holder.sent = sent;
		
		convertView.setTag(holder);		
		return convertView;
	}

}
