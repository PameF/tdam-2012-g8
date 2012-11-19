package com.tdam2012.grupo8.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tdam2012.grupo8.R;

public class EmailNewActivity extends Activity
{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_new);
        
        String listContact[] = {"pamef.2707@gmail.com"};
        String listContact2[] = {"pame_f27@hotmail.com"};
        
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, listContact);
        intent.putExtra(Intent.EXTRA_CC, listContact2);
        intent.putExtra(Intent.EXTRA_SUBJECT,"");
        intent.putExtra(Intent.EXTRA_TEXT,"");
        startActivity(Intent.createChooser(intent, "Email"));
    }
	
}
