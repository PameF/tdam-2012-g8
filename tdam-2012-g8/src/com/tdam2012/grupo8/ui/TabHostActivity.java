package com.tdam2012.grupo8.ui;

import com.tdam2012.grupo8.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TabHostActivity extends TabActivity implements TabHost.OnTabChangeListener
{
	private TabHost tabHost;
	
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost);
        
        //creamos el tabhost
        tabHost = getTabHost();
        
        //creamos un recurso para las propiedades de la pestaña
        TabHost.TabSpec spec;
        
        //obtenemos los recursos
        Resources res = getResources();
        
        Intent intent = new Intent().setClass(this, HistoryCallActivity.class);
        
        //se configura la pestaña con sus propiedades
        spec = tabHost.newTabSpec("HistoryCallActivity")
        		.setIndicator("Llamadas", res.getDrawable(R.drawable.icon_call_white))
        		.setContent(intent);
        
        //se carga la pestaña en el contenedor
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, HistorySmsActivity.class);
        spec = tabHost.newTabSpec("HistorySmsActivity").setIndicator("SMS", res.getDrawable(R.drawable.icon_sms_white)).setContent(intent);
        tabHost.addTab(spec);
        
        
        intent = new Intent().setClass(this, HistoryEmailActivity.class);  
        spec = tabHost.newTabSpec("HistoryEmailActivity").setIndicator("Correo", res.getDrawable(R.drawable.icon_email_white)).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, HistorySmsWebActivity.class);
        spec = tabHost.newTabSpec("HistorySmsWebActivity").setIndicator("SMS Web", res.getDrawable(R.drawable.icon_sms_web_white)).setContent(intent);
        tabHost.addTab(spec);
        
        setDefaultBackgroundColor();
        
        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.shape_gradient_cian);
        findViewById(R.id.divider).setBackgroundResource(R.drawable.shape_gradient_cian);
        
        tabHost.setOnTabChangedListener(this);
    }
	
	private void setDefaultBackgroundColor() {
		for(int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {		
			tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.shape_gradient_gris_oscuro);
        }
	}

	public void onTabChanged(String tabId) {		
		int background = 0;
		int position = tabHost.getCurrentTab();
		
		setDefaultBackgroundColor();
		
		switch(position) {
			case 0:
				background = R.drawable.shape_gradient_cian;
				break;
			case 1:
				background = R.drawable.shape_gradient_naranja;
				break;
			case 2:
				background = R.drawable.shape_gradient_rosa;
				break;
			case 3:
				background = R.drawable.shape_gradient_verde;
				break;
		}
		
		tabHost.getCurrentTabView().setBackgroundResource(background);
		findViewById(R.id.divider).setBackgroundResource(background);
	}
}
