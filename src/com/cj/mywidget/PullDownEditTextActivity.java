package com.cj.mywidget;

import com.cj.widget.PullDownEditText;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PullDownEditTextActivity extends Activity {
	PullDownEditText pullDown;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pull_down_edit_text);
		pullDown=(PullDownEditText)findViewById(R.id.pullDown);
		pullDown.init(new String[]{"1","2","3","4","5","6","7","8","9"});
	}
}
