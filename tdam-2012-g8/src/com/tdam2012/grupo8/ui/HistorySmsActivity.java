package com.tdam2012.grupo8.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabWidget;

import com.tdam2012.grupo8.R;

public class HistorySmsActivity extends Activity implements OnClickListener {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_call);
        
        initTabs();
    }
	
	private void initTabs() {
		TabWidget tab = (TabWidget)findViewById(R.id.history_tab_call);
		tab.setOnClickListener(this);
		
		tab = (TabWidget)findViewById(R.id.history_tab_sms);
		tab.setOnClickListener(this);
		
		tab = (TabWidget)findViewById(R.id.history_tab_email);
		tab.setOnClickListener(this);
		
		tab = (TabWidget)findViewById(R.id.history_tab_sms_web);
		tab.setOnClickListener(this);
	}

	public void onClick(View v) {
		Intent intent = null;
		
		switch(v.getId()) {
			case R.id.history_tab_call:
				intent = new Intent(this, HistorySmsActivity.class);
				break;
			case R.id.history_tab_sms:
				intent = new Intent(this, HistorySmsActivity.class);
				break;
			case R.id.history_tab_email:
				intent = new Intent(this, HistoryEmailActivity.class);
				break;
			case R.id.history_tab_sms_web:
				intent = new Intent(this, HistorySmsWebActivity.class);
				break;
		}
		
		startActivity(intent);
	}
}
