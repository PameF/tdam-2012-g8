package com.tdam2012.grupo8.ui;

import com.tdam2012.grupo8.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ContactListActivity extends Activity
{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        
        LinearLayout list = (LinearLayout)findViewById(R.id.contact_list_item1);
        list.setOnClickListener(new OnClickListener(){
       		public void onClick(View v)
           	{
           		Intent intent = new Intent(ContactListActivity.this,  ContactDetailsActivity.class);
           		startActivity(intent);
           	}
       	}); 
        
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(ContactListActivity.this,  ContactNewActivity.class);
           		startActivity(intent);
			}
		});
	}
}
