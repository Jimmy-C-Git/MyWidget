package com.cj.mywidget;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service{

	List<RunningAppProcessInfo> runningAppProcesses;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.v("test","service create");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v("test","service start command");
		new Thread(new Runnable() {  
	        @Override  
	        public void run() {
	        	while(true){
	        		try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		getNewTask();
	        		
	        	}
	        }  
	    }).start(); 
		return super.onStartCommand(intent, flags, startId);
	}
	private void getNewTask(){
		new CheckRunningTask().execute(1);
	}
	public class CheckRunningTask extends AsyncTask<Integer ,Void, Integer>
	{

		
		@Override
		protected Integer doInBackground(Integer... arg0) {
			ActivityManager am = (ActivityManager) MyService.this.getSystemService(Activity.ACTIVITY_SERVICE);
			runningAppProcesses = am.getRunningAppProcesses();
			return null;
		}
		@Override
		protected void onPostExecute(Integer result) {
			
			super.onPostExecute(result);
			if(runningAppProcesses!=null)
	        {
	            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
	                
	                if(runningAppProcessInfo.processName.startsWith("com.exatell.eed_vgm"))
	                {
	                	//runningAppProcessInfo.importance!=RunningAppProcessInfo.IMPORTANCE_FOREGROUND && 
	                	boolean IsBackgroundRunning = runningAppProcessInfo.importance!=RunningAppProcessInfo.IMPORTANCE_FOREGROUND && runningAppProcessInfo.importance!=RunningAppProcessInfo.IMPORTANCE_VISIBLE;
	                	if(IsBackgroundRunning)
	                	{
	                		updateNotification();
	                	}
	                	break;
	                }
	            }
	        }
			
		}
		
	}
	int num=0;
	private void updateNotification(){
		NotificationCompat.Builder mBuilder =
    	        new NotificationCompat.Builder(MyService.this)
    	        .setSmallIcon(R.drawable.ic_pull_down_button)
    	        .setContentTitle("My notification")
    	        .setContentText("Hello World!").setAutoCancel(true).setNumber(num++);
    	// Creates an explicit intent for an Activity in your app
    	Intent resultIntent = new Intent(MyService.this, PullDownEditTextActivity.class);

    	/*// The stack builder object will contain an artificial back stack for the
    	// started Activity.
    	// This ensures that navigating backward from the Activity leads out of
    	// your application to the Home screen.
    	TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyService.this);
    	// Adds the back stack for the Intent (but not the Intent itself)
    	stackBuilder.addParentStack(PullDownEditTextActivity.class);
    	// Adds the Intent that starts the Activity to the top of the stack
    	stackBuilder.addNextIntent(resultIntent);*/
    	
    	
    	resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
    	//PendingIntent
    	PendingIntent resultPendingIntent = PendingIntent.getActivity( MyService.this, R.string.app_name, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT); 
    	
    	
    	mBuilder.setContentIntent(resultPendingIntent);
    	NotificationManager mNotificationManager =
    	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	// mId allows you to update the notification later on.
    	mNotificationManager.notify(1, mBuilder.build());
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v("test","service destory");
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.v("test","service onbind");
		return null;
	}

}
