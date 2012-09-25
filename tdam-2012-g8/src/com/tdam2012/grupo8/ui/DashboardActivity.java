package com.tdam2012.grupo8.ui;

import com.tdam2012.grupo8.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
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
				intent = new Intent(this, ContactDetailsActivity.class);
				break;
				
			case R.id.dashboard_btn_sms:
				break;
				
			case R.id.dashboard_btn_email:
				break;
				
			case R.id.dashboard_btn_sms_web:
				break;
		}
		
		if(intent != null)
		{
			startActivity(intent);
		}
	}
}