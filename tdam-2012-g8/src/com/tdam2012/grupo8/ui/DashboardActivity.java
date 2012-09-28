package com.tdam2012.grupo8.ui;

import com.tdam2012.grupo8.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DashboardActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        
        inicializarEventos ();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
                
        MenuItem item = menu.findItem(R.id.menu_history);
        item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(DashboardActivity.this, HistoryCallActivity.class);
				startActivity(intent);
				return false;
			}
		});
        
        item = menu.findItem(R.id.menu_contacts);
        item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(DashboardActivity.this, ContactListActivity.class);
				startActivity(intent);
				return false;
			}
		});
        
        return true;
    }
    
    private void inicializarEventos () {
    	Button btn = (Button)findViewById(R.id.dashboard_btn_llamadas);
    	btn.setOnClickListener(this);
    	
    	btn = (Button)findViewById(R.id.dashboard_btn_sms);
    	btn.setOnClickListener(this);
    	
    	btn = (Button)findViewById(R.id.dashboard_btn_email);
    	btn.setOnClickListener(this);
    	
    	btn = (Button)findViewById(R.id.dashboard_btn_sms_web);
    	btn.setOnClickListener(this);
    }

	public void onClick(View v) {
		
		Intent intent = null;
		
		switch(v.getId()) {
			case R.id.dashboard_btn_llamadas:
				intent = new Intent(this, CallKeyboardActivity.class);
				break;
				
			case R.id.dashboard_btn_sms:
				intent = new Intent(this, SmsListActivity.class);
				break;
				
			case R.id.dashboard_btn_email:
				intent = new Intent(this, EmailListActivity.class);
				break;
				
			case R.id.dashboard_btn_sms_web:
				intent = new Intent(this, SmsWebListActivity.class);
				break;
		}
		
		if(intent != null)
		{
			startActivity(intent);
		}
	}
}