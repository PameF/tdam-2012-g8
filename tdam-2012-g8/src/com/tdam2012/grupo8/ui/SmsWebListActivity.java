package com.tdam2012.grupo8.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.data.ActionsRegistryRepository;
import com.tdam2012.grupo8.entities.ActionRegistry;
import com.tdam2012.grupo8.ui.contacts.ListActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SmsWebListActivity extends android.app.ListActivity implements OnClickListener
{
	public static final int CONTACT_USERNAME_REQUEST = 1;
	
	private SmsWebListAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_web_list);
        
        Button btn = (Button)findViewById(R.id.sms_web_new);
    	btn.setOnClickListener(this);
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		initializeListView();
	}
	
	private void initializeListView() {
		
		ActionsRegistryRepository repository = new ActionsRegistryRepository(this);	
		ArrayList<ActionRegistry> conversations = repository.getSmsWebContactConversations();
		
		adapter = new SmsWebListAdapter(this, conversations);		
		getListView().setAdapter(adapter);
	}

	public void onClick(View v) {
		
		switch(v.getId()) {		
			case R.id.sms_web_new:
				
				Intent i = new Intent(SmsWebListActivity.this, ListActivity.class);
				i.putExtra(ListActivity.SELECT_ACTION_KEY, ListActivity.OnSelectActionEnum.USER_SELECT);
		
				startActivityForResult(i, CONTACT_USERNAME_REQUEST);				
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{	
		if(resultCode == RESULT_OK && requestCode == CONTACT_USERNAME_REQUEST) 
		{		
			String username = data.getExtras().getString(ListActivity.USER_RESULT);
			String name = data.getExtras().getString(ListActivity.CONTACT_NAME);
			long contact = data.getExtras().getLong(ListActivity.CONTACT_ID);
			
			openContactConversation(name, username, contact);
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		ActionRegistry registry = (ActionRegistry)adapter.getItem(position);		
		openContactConversation(registry.getContactName(), registry.getContactPhoneNumber(), registry.getContactId());
	}
	
	private void openContactConversation (String name, String username, long id) {
		Intent intent = new Intent(SmsWebListActivity.this, SmsWebConversationActivity.class);
		
		intent.putExtra(SmsWebConversationActivity.CONTACT_USERNAME_KEY, username);
		intent.putExtra(SmsWebConversationActivity.CONTACT_ID_KEY, id);
		intent.putExtra(SmsWebConversationActivity.CONTACT_NAME_KEY, name);
		
		startActivity(intent);
	}
	
	class SmsWebListAdapter extends BaseAdapter {

		private ArrayList<ActionRegistry> conversations;
		private LayoutInflater inflater;
		
		public SmsWebListAdapter(Context context, ArrayList<ActionRegistry> conversations) {
			this.conversations = conversations;
			this.inflater = LayoutInflater.from(context);
		}
		
		public int getCount() {
			return conversations.size();
		}

		public Object getItem(int position) {
			return conversations.get(position);
		}

		public long getItemId(int position) {
			ActionRegistry reg = (ActionRegistry)conversations.get(position);
			return reg.getContactId();
		}

		class Holder {			
			public TextView textContactName;
			public TextView textLastMessage;
			public TextView textDate;
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			
			Holder holder;
			String message;
			int messageLength;
			
			if (convertView == null) {
				
				convertView = inflater.inflate(R.layout.listitem_sms_web, null);
				
				holder = new Holder();
				holder.textContactName = (TextView) convertView.findViewById(R.id.sms_conversation_contact_name);
				holder.textLastMessage = (TextView) convertView.findViewById(R.id.sms_conversation_last_message);
				holder.textDate = (TextView) convertView.findViewById(R.id.sms_conversation_date);
				
				convertView.setTag(holder);	
				
			} else {
				holder = (Holder) convertView.getTag();
			}
		
			ActionRegistry item = (ActionRegistry) getItem(position);
			
			message = item.getMessage();
			messageLength = message.length() > 20 ? 20 : message.length();
			message = message.substring(0, messageLength) + "...";
			
			holder.textContactName.setText(item.getContactName());
			holder.textLastMessage.setText(message);
			holder.textDate.setText(new SimpleDateFormat("dd MMM, kk:mm").format(item.getDate()));

			return convertView;
		}
	} 
}