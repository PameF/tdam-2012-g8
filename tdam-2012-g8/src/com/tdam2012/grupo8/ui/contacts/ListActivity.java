package com.tdam2012.grupo8.ui.contacts;

import java.util.ArrayList;
import java.util.List;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.data.ContactsRepository;
import com.tdam2012.grupo8.entities.Contact;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends android.app.ListActivity implements OnClickListener, OnItemLongClickListener
{
	// Parámetro que indica que tipo de comportamiento debe tener al seleccionar un contacto
	public static final String SELECT_ACTION_KEY = "SELECT_ACTION";
	
	// Identificadores de los posibles resultados que devuelve esta activity
	public static final String EMAIL_RESULT = "EMAIL_RESULT";
	public static final String PHONE_RESULT = "PHONE_RESULT";
	public static final String USER_RESULT = "USER_RESULT";
	
	public static final String CONTACT_ID = "CONTACT_ID";
	public static final String CONTACT_NAME = "CONTACT_NAME";
	
	public enum OnSelectActionEnum {
		PHONE_SELECT,
		EMAIL_SELECT,
		USER_SELECT,
		SHOW_DETAILS
	}
	
	private ContactsRepository repository;
	private ListAdapter adapter;
	private OnSelectActionEnum action;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        
        Object actionName = getIntent().getExtras().get(SELECT_ACTION_KEY);
        this.action = OnSelectActionEnum.valueOf(actionName.toString());
        
        initComponentEvents();

		this.repository = new ContactsRepository(this); 
		this.adapter = new ListAdapter();
        setListAdapter(adapter);
        
        getListView().setOnItemLongClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		refreshList();
	}
	
	private void refreshList() {
		EditText txtFilter = (EditText)findViewById(R.id.contact_list_search_name);
		String filter = txtFilter.getText().toString();
		
		adapter.setData(repository.getContactList(filter, true));
	}
	
	public void initComponentEvents() {
        Button btn = (Button)findViewById(R.id.contact_list_new);
        btn.setOnClickListener(this);
        
        ImageButton img = (ImageButton)findViewById(R.id.contact_list_search);
        img.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		switch(v.getId()) {
		case R.id.contact_list_new:
			//Intent intent = new Intent(ListActivity.this,  NewActivity.class);
	   		//startActivity(intent);
			
			Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
			startActivity(intent);
	   		break;
	   		
		case R.id.contact_list_search:
			refreshList();
			break;
		}
	}
	
	@Override
	protected void onListItemClick(ListView list, View view, int position, long id) {
		
		String items[];
		
		switch(action) {
			case SHOW_DETAILS:
				openContactDetails(id);
				break;
				
			case EMAIL_SELECT:
				items = repository.getContactEmails(id);
				onSelectContact(position, items, EMAIL_RESULT, R.string.contact_dialog_email);
				break;
				
			case PHONE_SELECT:
				items = repository.getContactPhoneNumbers(id);
				onSelectContact(position, items, PHONE_RESULT, R.string.contact_dialog_phone);
				break;
				
			case USER_SELECT:
				items = repository.getContactPhoneNumbers(id);
				onSelectContact(position, items, USER_RESULT, R.string.contact_dialog_phone);
				break;
		}
	}
	
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		
		Contact contact = (Contact)adapter.getItem(position);
		new EditUsernameTask().execute(contact.getId());
		
		return false;
	}
	
	private void openContactDetails(long id) {
		Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(id));
		
	    Intent intent = new Intent(Intent.ACTION_VIEW);
	    intent.setData(uri);
	    
	    startActivity(intent);
	}
	
	private void onSelectContact(final int position, final String[] items, final String key, int title) {		
		
		if(items.length == 0) {
			Toast.makeText(this, "El contacto no tiene los datos necesarios", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(items.length == 1) {
			setSelectResult(position, key, items[0]);
			return;
		}

        AlertDialog dialog = new AlertDialog.Builder(ListActivity.this)
        	.setTitle(title)
        	.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
        		
	        	public void onClick(DialogInterface dialog, int whichButton) {
	        		dialog.dismiss();
	        		setSelectResult(position, key, items[whichButton]);
	        	}
	        	
        	}).show();
	}
	
	private void setSelectResult(int position, String key, String value) {
		
		Contact contact = (Contact)adapter.getItem(position);
		Intent data = new Intent();
		
		data.putExtra(key, value);
		data.putExtra(CONTACT_ID, contact.getId());
		data.putExtra(CONTACT_NAME, contact.getName());
		
		setResult(RESULT_OK, data);
		finish();
	}
	
	class ListAdapter extends BaseAdapter {

    	private List<Contact> items;
    	private LayoutInflater inflater;
    	
    	public ListAdapter() {
    		this.items = new ArrayList<Contact>();
    		this.inflater = LayoutInflater.from(ListActivity.this);
    	}
    	
    	public void setData(List<Contact> items) {
    		this.items = items;
    		notifyDataSetChanged();
    	}
    	
		public int getCount() {
			return items.size();
		}

		public Object getItem(int position) {
			return items.get(position);
		}

		public long getItemId(int position) {
			Contact contact = (Contact)items.get(position);
			return contact.getId();
		}
		
		
		class Holder {
			private TextView textName;
			private TextView textPhone;
			private ImageView imageAvatar;
		}
		

		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.listitem_contact_data, null);
				
				holder = new Holder();
				holder.imageAvatar = (ImageView) convertView.findViewById(R.id.listitem_contact_avatar);
				holder.textName = (TextView) convertView.findViewById(R.id.listitem_contact_name);
				holder.textPhone = (TextView) convertView.findViewById(R.id.listitem_contact_phone);
				
				convertView.setTag(holder);
				
			} else {
				holder = (Holder) convertView.getTag();
			}

			Contact item = (Contact) getItem(position);
						
			if(item.getAvatar() == null) {
				item.setAvatar(repository.getContactPhoto(item.getId()));
			}			
			
			if(item.getAvatar() != null)
				holder.imageAvatar.setImageBitmap(item.getAvatar());
			else
				holder.imageAvatar.setImageResource(R.drawable.android_avatar);
				
			String username = repository.getContactUsername(item.getId());
			
			holder.textName.setText(item.getName());
			holder.textPhone.setText(username);

			return convertView;
		}	
    }

	class EditUsernameTask extends AsyncTask<Long, Void, String> {

		private String value;
		private long id;
		
		@Override
		protected String doInBackground(Long... params) {
					
			final EditText input = new EditText(ListActivity.this);
			
			id = params[0];
			value = "";
			
			Looper.prepare();
			
			AlertDialog dialog = new AlertDialog.Builder(ListActivity.this)
			    .setTitle(R.string.title_username)
			    .setMessage(R.string.contact_edit_username)
			    .setView(input)
			    
			    .setPositiveButton(R.string.button_done, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			        	
			            value = input.getText().toString();
			            repository.saveContactUsername(id, value);
			            			            
			            dialog.dismiss();
			        }
			    }).show();

			Looper.myLooper();
			Looper.loop();
			
			return value;
		}
	}
}
