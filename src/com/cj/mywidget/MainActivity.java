package com.cj.mywidget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.style.IconMarginSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	Button btnPullDownEditText,btnSmartListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
    	btnPullDownEditText=(Button)findViewById(R.id.btnPullDownEditText);
    	btnSmartListView=(Button)findViewById(R.id.btnSmartListView);
    	btnSmartListView.setOnClickListener(this);
    	btnPullDownEditText.setOnClickListener(this);
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
		}
		
	}
}
