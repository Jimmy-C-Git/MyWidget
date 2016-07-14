package com.cj.mywidget;

import com.cj.camera.CameraActivity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.style.IconMarginSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	Button btnPullDownEditText,btnSmartListView,btnCamera,btnRunningTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
   
    private void initView(){
    	btnPullDownEditText=(Button)findViewById(R.id.btnPullDownEditText);
    	btnSmartListView=(Button)findViewById(R.id.btnSmartListView);
    	btnCamera=(Button)findViewById(R.id.btnCamera);
    	btnRunningTask=(Button)findViewById(R.id.btnRunningTask);
    	
    	btnSmartListView.setOnClickListener(this);
    	btnPullDownEditText.setOnClickListener(this);
    	btnCamera.setOnClickListener(this);
    	btnRunningTask.setOnClickListener(this);
    }
    
	@Override
	public void onClick(View source) {
		switch(source.getId())
		{
		case R.id.btnPullDownEditText:
			Intent i=new Intent(this,PullDownEditTextActivity.class);
			startActivity(i);
			break;
		case R.id.btnSmartListView:
			i=new Intent(this,SmartListActivity.class);
			startActivity(i);
			break;
		case R.id.btnCamera:
			i=new Intent(this,CameraActivity.class);
			startActivity(i);
			break;
		case R.id.btnRunningTask:
			i=new Intent(this,RunningTaskActivity.class);
			startActivity(i);
			break;
		}
		
	}
}
