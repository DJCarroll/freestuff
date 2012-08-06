package com.dj.craigslistapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class FinActivity extends Activity {
	private static final String TAG = "FinActivityActivity";

	
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	Log.d(TAG,"application exited on error...");
	    	finish();
		}
}
