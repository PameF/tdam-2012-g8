package com.tdam2012.grupo8.ui;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.data.ActionsRegistryRepository;
import com.tdam2012.grupo8.data.ContactsRepository;
import com.tdam2012.grupo8.entities.ActionRegistry;
import com.tdam2012.grupo8.entities.Email;
import com.tdam2012.grupo8.ui.SmsListActivity.SmsListAdapter;
import com.tdam2012.grupo8.ui.SmsListActivity.SmsListAdapter.Holder;
import com.tdam2012.grupo8.ui.adapters.ContactEmailListAdapter;
import com.tdam2012.grupo8.ui.contacts.ListActivity;

public class EmailListActivity extends android.app.ListActivity implements OnClickListener 
{
	private EmailListAdapter adapter;
	
	//private static final int CONTACT_PHONE_NUMBER_REQUEST = 1;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_list);
        
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener(){
    		public void onClick(View v)
        	{
        		Intent i = new Intent(EmailListActivity.this,  EmailNewActivity.class);
        		startActivity(i);
        	}
    	}); 
    }
	
	protected void onResume() {
		super.onResume();
		initializeListView();
	}
	
	private void initializeListView() {
		
		ActionsRegistryRepository repository = new ActionsRegistryRepository(this);	
		ArrayList<ActionRegistry> emails = repository.getEmailsContact();
		
		adapter = new EmailListAdapter(this, emails);		
		getListView().setAdapter(adapter);
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		ActionRegistry registry = (ActionRegistry)adapter.getItem(position);		
		openContactConversation(registry.getContactName(), registry.getContactPhoneNumber(), registry.getContactId());
	}
	
	private void openContactConversation (String name, String emailAddress, long id) {
		Intent intent = new Intent(EmailListActivity.this, EmailConversationActivity.class);
		
		intent.putExtra(EmailConversationActivity.EMAIL_ADDRESS_KEY, emailAddress);
		intent.putExtra(EmailConversationActivity.CONTACT_ID_KEY, id);
		intent.putExtra(EmailConversationActivity.CONTACT_NAME_KEY, name);
		
		startActivity(intent);
	}

	public void onClick(View v) {
			
		Intent i = new Intent(EmailListActivity.this, ListActivity.class);
		i.putExtra(ListActivity.SELECT_ACTION_KEY, ListActivity.OnSelectActionEnum.EMAIL_SELECT);

		startActivity(i);
		
	}
	
	class EmailListAdapter extends BaseAdapter {

		private ArrayList<ActionRegistry> emails;
		private LayoutInflater inflater;
		
		public EmailListAdapter(Context context, ArrayList<ActionRegistry> conversations) {
			this.emails = conversations;
			this.inflater = LayoutInflater.from(context);
		}
		
		public int getCount() {
			return emails.size();
		}

		public Object getItem(int position) {
			return emails.get(position);
		}

		public long getItemId(int position) {
			ActionRegistry reg = (ActionRegistry)emails.get(position);
			return reg.getContactId();
		}

		class Holder {			
			public TextView textContactName;
			public TextView textSubject;
			public TextView textDate;
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			
			Holder holder;
			String mail;
			int mailLength;
			
			if (convertView == null) {
				
				convertView = inflater.inflate(R.layout.listitem_email, null);
				
				holder = new Holder();
				holder.textContactName = (TextView) convertView.findViewById(R.id.email_list_contact_name);
				holder.textSubject = (TextView) convertView.findViewById(R.id.email_list_subject);
				holder.textDate = (TextView) convertView.findViewById(R.id.email_list_date);
				
				convertView.setTag(holder);	
				
			} else {
				holder = (Holder) convertView.getTag();
			}
		
			ActionRegistry item = (ActionRegistry) getItem(position);
			
			mail = item.getMessage();
			mailLength = mail.length() > 20 ? 20 : mail.length();
			mail = mail.substring(0, mailLength) + "...";
			
			holder.textContactName.setText(item.getContactName());
			holder.textSubject.setText(mail);
			holder.textDate.setText(new SimpleDateFormat("dd MMM, kk:mm").format(item.getDate()));

			return convertView;
		}
	} 	
	
}