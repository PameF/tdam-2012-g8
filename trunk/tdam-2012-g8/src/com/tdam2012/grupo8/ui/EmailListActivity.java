package com.tdam2012.grupo8.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tdam2012.grupo8.R;

public class EmailListActivity extends Activity 
{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_list);
        
        Button btnNew = (Button)findViewById(R.id.email_list_new);
        btnNew.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(EmailListActivity.this, EmailNewActivity.class);
				startActivity(intent);
			}
		});
    }
}