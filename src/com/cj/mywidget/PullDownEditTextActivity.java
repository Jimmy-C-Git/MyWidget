package com.cj.mywidget;

import com.cj.widget.MultiEditText;

import android.app.Activity;
import android.os.Bundle;

public class PullDownEditTextActivity extends Activity {
	MultiEditText multiEdt,edit,canedit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pull_down_edit_text);
		multiEdt=(MultiEditText)findViewById(R.id.multiEdt);
		multiEdt.init(new String[]{"1","2","3","4","5","6","7","8","9","9"});
		edit=(MultiEditText)findViewById(R.id.editable);
		edit.init(new String[]{"1","2","3","4","5","6","7","8","9"});
		canedit=(MultiEditText)findViewById(R.id.cannoteditable);
		//canedit.init(new String[]{"1","2","3","4","5","6","7","8","9"});
	}
}
