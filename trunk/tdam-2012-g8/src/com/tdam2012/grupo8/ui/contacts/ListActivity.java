package com.tdam2012.grupo8.ui.contacts;

import java.util.List;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.data.ContactsRepository;
import com.tdam2012.grupo8.entities.Contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ListActivity extends android.app.ListActivity implements OnClickListener
{
	private ContactsRepository repository;
	private ListAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        
        repository = new ContactsRepository(this);
        adapter = new ListAdapter(repository.getContactList(""));
        
        setListAdapter(adapter);
        
        Button btn = (Button)findViewById(R.id.list_contact_new);
        btn.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		Intent intent = new Intent(ListActivity.this,  NewActivity.class);
   		startActivity(intent);
	}
	
	class ListAdapter extends BaseAdapter {

    	private List<Contact> items;
    	private LayoutInflater inflater;
    	
    	public ListAdapter(List<Contact> items) {
    		this.items = items;
    		this.inflater = LayoutInflater.from(ListActivity.this);
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
						
			//holder.imageAvatar.setImageURI(item.getAvatar());
			holder.textName.setText(item.getName());
			holder.textPhone.setText("");

			return convertView;
		}
    }
}
