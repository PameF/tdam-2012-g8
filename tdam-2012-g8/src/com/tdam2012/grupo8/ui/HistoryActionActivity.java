package com.tdam2012.grupo8.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.data.ActionsRegistryRepository;
import com.tdam2012.grupo8.entities.ActionRegistry;

public class HistoryActionActivity extends ListActivity {
	
	public static final int TAB_CALL = 1;
	public static final int TAB_SMS = 2;
	public static final int TAB_SMS_WEB = 3;
	public static final int TAB_EMAIL = 4;
	
	public static final String TAB = "TAB";
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_actions);
    }	

	@Override
	protected void onResume() {
		super.onResume();
		
		int tab = getIntent().getExtras().getInt(TAB);
		
		ActionsRegistryRepository repository = new ActionsRegistryRepository(this);
		ArrayList<ActionRegistry> list = null;		
		
		switch(tab) {
			case TAB_CALL:
				list = repository.getCallsContact();
				break;
				
			case TAB_SMS:
				list = repository.getSmsContactConversations();
				break;
				
			case TAB_SMS_WEB:
				list = repository.getSmsWebContactConversations();
				break;
				
			case TAB_EMAIL:
				list = repository.getEmailsContact();
				break;
		}
				
		HistoryActionAdapter adapter = new HistoryActionAdapter(list);
		getListView().setAdapter(adapter);
	}
	
	class HistoryActionAdapter extends BaseAdapter {

		private ArrayList<ActionRegistry> actions;
    	private LayoutInflater inflater;
    	
    	HistoryActionAdapter(ArrayList<ActionRegistry> actions) {
    		this.actions = actions;
    		this.inflater = LayoutInflater.from(HistoryActionActivity.this);
    	}
		
		public int getCount() {
			return actions.size();
		}

		public Object getItem(int position) {
			return actions.get(position);
		}

		public long getItemId(int position) {
			ActionRegistry action = (ActionRegistry)actions.get(position);
			return action.getId();
		}
		
		
		class Holder {
			private TextView textName;
			private TextView textPhone;
			private TextView textDate;
			private ImageView imageAction;
		}
		

		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.listitem_contact_action, null);
				
				holder = new Holder();
				holder.imageAction = (ImageView) convertView.findViewById(R.id.history_action_status);
				holder.textName = (TextView) convertView.findViewById(R.id.history_action_contact);
				holder.textDate = (TextView) convertView.findViewById(R.id.history_action_date);
				holder.textPhone = (TextView) convertView.findViewById(R.id.history_action_number_email);
				
				convertView.setTag(holder);
				
			} else {
				holder = (Holder) convertView.getTag();
			}

			ActionRegistry item = (ActionRegistry) getItem(position);
			
			holder.textName.setText(item.getContactName());
			holder.textPhone.setText(item.getContactPhoneNumber());
			holder.textDate.setText(new SimpleDateFormat("dd MMM, kk:mm").format(item.getDate()));
			
			switch(item.getAction()) {
				case INCOMING_CALL:
				case OUTGOING_CALL:
					holder.imageAction.setImageResource(R.drawable.icon_call_conecting);
					break;
					
				case MISSED_CALL:
					holder.imageAction.setImageResource(R.drawable.icon_call_fail);
					break;
					
				case RECEIVED_EMAIL:
				case SENT_EMAIL:
					holder.imageAction.setImageResource(R.drawable.icon_email);
					break;
					
				case RECEIVED_MESSAGE:
				case SENT_MESSAGE:
					holder.imageAction.setImageResource(R.drawable.icon_sms);
					break;
					
				case RECEIVED_MESSAGE_WEB:
				case SENT_MESSAGE_WEB:
					holder.imageAction.setImageResource(R.drawable.icon_sms_web);
					break;
			}

			return convertView;
		}
		
	}
}
