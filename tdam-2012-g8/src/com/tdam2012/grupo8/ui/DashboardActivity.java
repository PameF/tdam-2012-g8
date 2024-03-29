package com.tdam2012.grupo8.ui;

import java.util.Date;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.data.ActionsRegistryRepository;
import com.tdam2012.grupo8.data.ActionsRegistryRepository.ActionEnum;
import com.tdam2012.grupo8.data.ContactsRepository;
import com.tdam2012.grupo8.entities.ActionRegistry;
import com.tdam2012.grupo8.entities.Contact;
import com.tdam2012.grupo8.networking.SynchronizationService;
import com.tdam2012.grupo8.ui.contacts.ListActivity;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class DashboardActivity extends Activity implements OnClickListener, OnMenuItemClickListener {

	private static final int CONTACT_PHONE_NUMBER_REQUEST = 1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        
        inicializarEventos();
        
        new SynchronizationService().execute(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
                
        MenuItem item = menu.findItem(R.id.menu_history);
        item.setOnMenuItemClickListener(this);
        
        item = menu.findItem(R.id.menu_contacts);
        item.setOnMenuItemClickListener(this);
        
        return true;
    }
    
    private void inicializarEventos () {
    	ImageButton btn = (ImageButton)findViewById(R.id.dashboard_btn_llamadas);
    	btn.setOnClickListener(this);
    	
    	btn = (ImageButton)findViewById(R.id.dashboard_btn_sms);
    	btn.setOnClickListener(this);
    	
    	btn = (ImageButton)findViewById(R.id.dashboard_btn_email);
    	btn.setOnClickListener(this);
    	
    	btn = (ImageButton)findViewById(R.id.dashboard_btn_sms_web);
    	btn.setOnClickListener(this);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	    	
    	switch(requestCode) {
	    	case CONTACT_PHONE_NUMBER_REQUEST:
	    		
	    		if(resultCode == RESULT_OK) {
	    			
	    			
	    			String phoneNumber = data.getExtras().getString(ListActivity.PHONE_RESULT);
	    			long contactId = data.getExtras().getLong(ListActivity.CONTACT_ID);
	    			String name = data.getExtras().getString(ListActivity.CONTACT_NAME);
	    	        
	    			Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
	    	        startActivity(callIntent);
	    	        
	    	        ActionRegistry reg = new ActionRegistry();
	    	        reg.setAction(ActionEnum.INCOMING_CALL);
	    	        reg.setContactId(contactId);
	    	        reg.setContactName(name);
	    	        reg.setContactPhoneNumber(phoneNumber);
	    	        reg.setDate(new Date());
	    	       
	    	        ActionsRegistryRepository rr = new ActionsRegistryRepository(this);
	    	        rr.insertRegistration(reg);
	    		}
	    		break;
    	}
    }

	public void onClick(View v) {
		
		Intent intent = null;
		
		switch(v.getId()) {
			case R.id.dashboard_btn_llamadas:
				intent = new Intent(this, ListActivity.class);
				intent.putExtra(ListActivity.SELECT_ACTION_KEY, ListActivity.OnSelectActionEnum.PHONE_SELECT);
				startActivityForResult(intent, CONTACT_PHONE_NUMBER_REQUEST);
				break;
				
			case R.id.dashboard_btn_sms:
				intent = new Intent(this, SmsListActivity.class);
				
				startActivity(intent);
				break;
				
			case R.id.dashboard_btn_email:
				intent = new Intent(this, EmailListActivity.class);
				startActivity(intent);
				break;
				
			case R.id.dashboard_btn_sms_web:
				intent = new Intent(this, SmsWebListActivity.class);
				startActivity(intent);
				break;
		}
	}

	public boolean onMenuItemClick(MenuItem item) {
		Intent intent = null;
		
		switch(item.getItemId()) {
			case R.id.menu_contacts:
				intent = new Intent(DashboardActivity.this, ListActivity.class);
				intent.putExtra(ListActivity.SELECT_ACTION_KEY, ListActivity.OnSelectActionEnum.SHOW_DETAILS);
				break;
				
			case R.id.menu_history:
				intent = new Intent(DashboardActivity.this, TabHostActivity.class);
				break;
		}
		
		if(intent != null)
			startActivity(intent);
		
		return false;
	}
}