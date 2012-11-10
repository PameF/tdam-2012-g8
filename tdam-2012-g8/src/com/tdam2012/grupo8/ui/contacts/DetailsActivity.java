package com.tdam2012.grupo8.ui.contacts;

import com.tdam2012.grupo8.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class DetailsActivity extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contact, menu);
        
        return true;
    }
}
