package com.cj.tools;



import java.util.List;




import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class NetWorkStatus extends BroadcastReceiver {
	List<RunningAppProcessInfo> runningAppProcesses;
	boolean appIsBackgroundRunning=false ;
	Handler handler=new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			if(msg.what==100)
			{
				 if(runningAppProcesses!=null)
			        {
			            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
			                
			                if(runningAppProcessInfo.processName.startsWith("com.exatell.eed_vgm"))
			                {
			                	//runningAppProcessInfo.importance!=RunningAppProcessInfo.IMPORTANCE_FOREGROUND && 
			                	boolean isBackgroundRunning = runningAppProcessInfo.importance!=RunningAppProcessInfo.IMPORTANCE_FOREGROUND
			                			&& runningAppProcessInfo.importance!=RunningAppProcessInfo.IMPORTANCE_VISIBLE;
			                	if(!isBackgroundRunning)
			                	{
									//intent.setClass(context, NetNotAvailableActivity.class);
			        				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
			        				context.startActivity(intent);
			                	}
			                	break;
			                }
			            }
			        }
			}
			return false;
		}
	});
	private  Context context ;
	private Intent intent;
	@Override
	public void onReceive(Context context, Intent intent) {
		
		this.context=context;
		this.intent=intent;
		Log.i("....BroadcastReceiver", "网络连接发生变化!");
		
		/*if(!isNetWorkAwaliable(context)&&!(context instanceof NetNotAvailableActivity))
		{
			if(!appIsBackgroundRunning(context,"com.exatell.eed_vgm")){
				
			}
			
		}else if(isNetWorkAwaliable(context) &&context instanceof NetNotAvailableActivity)
		{
			((NetNotAvailableActivity) context).finish();
		}*/
		
	}
	
	public boolean appIsBackgroundRunning(final Context ctx,String packageName)
    {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				ActivityManager am = (ActivityManager) ctx.getSystemService(Activity.ACTIVITY_SERVICE);
		        
		        runningAppProcesses = am.getRunningAppProcesses();
		        Looper.prepare();
		        handler.sendEmptyMessage(100);
		        Looper.loop();
			}
		}).start();
        
        
        return false;
    }
	
	public  static boolean isNetWorkAwaliable(Context context){
		 ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		 NetworkInfo[] info = manager.getAllNetworkInfo();  
	        if (info != null) {  
	            for (int i = 0; i < info.length; i++) {  
	                if (info[i].getState() == NetworkInfo.State.CONNECTED) {  
	                    return true;  
	                }  
	            }  
	        }  

		return false;
	}
	
}
