package com.tdam2012.grupo8.ui;

import com.tdam2012.grupo8.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.view.View.OnClickListener;

public class CallKeyboardActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_keyboard);
        
        ImageButton btn = (ImageButton)findViewById(R.id.imageButton1);
    	btn.setOnClickListener(new OnClickListener(){
    		public void onClick(View v)
        	{
        		Intent i = new Intent(CallKeyboardActivity.this,  CallActivity.class);
        		startActivity(i);
        	}    		
    	}); 	  	
    }
	 
	public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.activity_main, menu);
       return true;
	}
}