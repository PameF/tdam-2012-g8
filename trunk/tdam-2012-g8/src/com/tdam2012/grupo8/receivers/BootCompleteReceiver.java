package com.tdam2012.grupo8.receivers;

import com.tdam2012.grupo8.networking.SynchronizationService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {		
		Intent service = new Intent(context, SynchronizationService.class);
		context.startService(service);
	}
}
