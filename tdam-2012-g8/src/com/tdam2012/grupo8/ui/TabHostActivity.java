package com.tdam2012.grupo8.ui;

import com.tdam2012.grupo8.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TabHostActivity extends TabActivity
{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost);
        
        TabHost tabHost = getTabHost();//creamos el tabhost
        TabHost.TabSpec spec;//creamos un recurso para las propiedades de la pestaña
        Intent intent;
        Resources res = getResources();//obtenemos los recursos
        
        intent = new Intent().setClass(this, HistoryCallActivity.class);
        spec = tabHost.newTabSpec("HistoryCallActivity").setIndicator("Llamadas", res.getDrawable(R.drawable.icon_call_white)).setContent(intent);//se configura la pestaña con sus propiedades
        tabHost.addTab(spec);//se carga la pestaña en el contenedor
        //tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.CYAN);
        
        intent = new Intent().setClass(this, HistorySmsActivity.class);
        spec = tabHost.newTabSpec("HistorySmsActivity").setIndicator("SMS", res.getDrawable(R.drawable.icon_sms_white)).setContent(intent);
        tabHost.addTab(spec);
        //tabHost.getTabWidget().getChildAt(1).setBackgroundColor(color.naranja_claro);
        
        
        intent = new Intent().setClass(this, HistoryEmailActivity.class);  
        spec = tabHost.newTabSpec("HistoryEmailActivity").setIndicator("Correo", res.getDrawable(R.drawable.icon_email_white)).setContent(intent);
        tabHost.addTab(spec);
        //tabHost.getTabWidget().getChildAt(2).setBackgroundColor(R.drawable.shape_gradient_morado);
        
        intent = new Intent().setClass(this, HistorySmsWebActivity.class);
        spec = tabHost.newTabSpec("HistorySmsWebActivity").setIndicator("SMS Web", res.getDrawable(R.drawable.icon_sms_web_white)).setContent(intent);
        tabHost.addTab(spec);
        //tabHost.getTabWidget().getChildAt(3).setBackgroundColor(R.drawable.shape_gradient_verde);
        
    }

}
