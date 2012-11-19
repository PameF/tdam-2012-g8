package com.tdam2012.grupo8.receivers;

import java.util.Date;

import com.tdam2012.grupo8.data.ConnectivityStatusRepository;
import com.tdam2012.grupo8.entities.ConnectivityStatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityStatusReceiver extends BroadcastReceiver {

	//http://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		ConnectivityStatus status = new ConnectivityStatus();
		status.setDate(new Date());
		
		if(intent.getExtras()!=null) {
			NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);

			if(ni!=null && ni.getState() == NetworkInfo.State.CONNECTED) {
				status.setConnected(true);
				status.setType(ni.getType() == ConnectivityManager.TYPE_WIFI ? "WIFI" : "MOBILE");
			}
		}
		if(intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
			status.setConnected(false);
		}
		
		ConnectivityStatusRepository repository = new ConnectivityStatusRepository(context);
		repository.insert(status);
	}
}
