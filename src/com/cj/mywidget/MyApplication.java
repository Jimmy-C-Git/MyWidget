package com.cj.mywidget;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class MyApplication extends Application{
	@Override
	public void onCreate() {
		super.onCreate();
		Intent i=new Intent(this,MyService.class);
		startService(i);
		Log.v("test","Create");
	}
	@Override
	public void onTerminate() {
		
		super.onTerminate();
		Log.v("test","Terminate");
	}
	@Override
	public void onTrimMemory(int level) {
		
		super.onTrimMemory(level);
		switch (level) {  
	    case TRIM_MEMORY_UI_HIDDEN:
			Log.v("test","TRIM_MEMORY_UI_HIDDEN");
	        break;  
	    }  
	}
	
}
