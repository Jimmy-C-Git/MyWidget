package com.cj.mywidget;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Spinner;

public class RunningTaskActivity extends Activity {
	ListView lvRunningTask;
	Spinner spMethod;
	List<RunningAppProcessInfo> runningAppProcesses;
	List<RunningTaskInfo> runningTask;
	
	AsyncTask<Integer, Integer, Integer> mytask;

	int firstViewPosition=0;
	int firstViewTop=0;
	boolean isScroll=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_running_task);
		lvRunningTask=(ListView) findViewById(R.id.lvRunningTask);
		lvRunningTask.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int scrollState) {
				if (scrollState == SCROLL_STATE_IDLE) {
					firstViewPosition=lvRunningTask.getFirstVisiblePosition();
					View view1 =lvRunningTask.getChildAt(0);
					if(view1!=null)
						firstViewTop=view1.getTop();
					isScroll=false;
				}else isScroll=true;
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				
			}
		});
		spMethod=(Spinner)findViewById(R.id.spMethod);
		spMethod.setAdapter(
				new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new String[]{
			"RunningApp","RunningTask"
		}));
		spMethod.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch(position){
				case 1:
					if(mytask!=null)
						mytask.cancel(true);
					mytask = new CheckRunningTask().execute(1);
					break;
				case 0:
					if(mytask!=null)
						mytask.cancel(true);
					mytask = new CheckRunningTask().execute(0);
					break;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		//bindListView();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mytask.cancel(false);
	}
	private void bindListView(){
		if(isScroll)return;
		ArrayList<String>data=new ArrayList<String>();
		
		if(runningAppProcesses!=null){
			int position=0;
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
				String processStatus = "";
				position++;
				switch(runningAppProcessInfo.importance)
				{
				case RunningAppProcessInfo.IMPORTANCE_VISIBLE:
					processStatus="VISIBLE";
					break;
				case RunningAppProcessInfo.IMPORTANCE_BACKGROUND:
					processStatus="BACKGROUND";
					break;
				case RunningAppProcessInfo.IMPORTANCE_EMPTY:
					processStatus="EMPTY";
					break;
				case RunningAppProcessInfo.IMPORTANCE_FOREGROUND:
					processStatus="FOREGROUND";
					break;
				case RunningAppProcessInfo.IMPORTANCE_PERCEPTIBLE:
					processStatus="PERCEPTIBLE";
					break;
				case RunningAppProcessInfo.IMPORTANCE_SERVICE:
					processStatus="SERVICE";
					break;
				}

				data.add(String.format("%d\n%s\n%s\npid:%d\nuid:%d",position, runningAppProcessInfo.processName,processStatus,runningAppProcessInfo.pid,runningAppProcessInfo.uid));
            }
        }
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
		lvRunningTask.setAdapter(adapter);
		lvRunningTask.setSelectionFromTop(firstViewPosition, firstViewTop);
	}
	private void bindListView1(){
		if(isScroll)return;
		ArrayList<String>data=new ArrayList<String>();
		
		if(runningTask!=null){
			int position=0;
            for (RunningTaskInfo runningTaskInfo : runningTask) {
				String processStatus = "";
				position++;

				data.add(String.format("%d\n%s\n%s\n%s\n%s",position, runningTaskInfo.baseActivity.getClassName(), runningTaskInfo.baseActivity.getPackageName(),
						runningTaskInfo.topActivity.getClassName(), runningTaskInfo.topActivity.getPackageName()));
            }
        }
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
		lvRunningTask.setAdapter(adapter);
		lvRunningTask.setSelectionFromTop(firstViewPosition, firstViewTop);
	}
	public class CheckRunningTask extends AsyncTask<Integer ,Integer, Integer>
	{

		
		@Override
		protected Integer doInBackground(Integer... arg0) {
			
			ActivityManager am = (ActivityManager) RunningTaskActivity.this.getSystemService(Activity.ACTIVITY_SERVICE);
			 
			switch (arg0[0]){
			case 0:
				while(!isCancelled()){
					runningAppProcesses = am.getRunningAppProcesses();
					publishProgress(0);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case 1:
				while(!isCancelled()){
					runningTask = am.getRunningTasks(50);
					publishProgress(1);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			}
			
			return null;
			
			
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			switch(values[0])
			{
			case 0:
				bindListView();
				break;
			case 1:
				bindListView1();
				break;
			}
		}
		@Override
		protected void onPostExecute(Integer result) {
			
			super.onPostExecute(result);
			
			
		}
		
	}
}
