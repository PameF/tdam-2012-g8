package com.tdam2012.grupo8.networking;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;

public class SynchronizationService extends AsyncTask<Context, Void, Void> {

	@Override
	protected Void doInBackground(Context... params) {
		
		while(true) {
			new ReceiveMessageService().execute(params[0]);
	        SystemClock.sleep(5000);
		}
	}
}
