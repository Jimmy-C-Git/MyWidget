package com.cj.glsurface;

import com.cj.mywidget.R;
import com.cj.mywidget.R.layout;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class GLSurfaceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_glsurface);
		FrameLayout container=(FrameLayout) findViewById(R.id.container);
		
		container.addView(new MyGLSurfaceView(this));
	}
}
