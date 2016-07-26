package com.cj.glsurface;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class MyGLSurfaceView extends GLSurfaceView {
	GestureDetector detector;
	
	private float anglex = 0f;
	private float angley = 0f;
	static final float ROTATE_FACTOR = 60;
	
	private final float TOUCH_SCALE_FACTOR =1.0f/ 600;
	private float mPreviousX;
	private float mPreviousY;

	private MyRender mRenderer;
	
	public MyGLSurfaceView(Context context) {
		super(context);
		mRenderer=new MyRender(context);
		setRenderer(mRenderer);
		//setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		//setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		// MotionEvent reports input details from the touch screen
	    // and other input controls. In this case, you are only
	    // interested in events where the touch position changed.

	    float x = e.getX();
	    float y = e.getY();

	    switch (e.getAction()) {
	        case MotionEvent.ACTION_MOVE:

	            float dx = x - mPreviousX;
	            float dy = y - mPreviousY;
	            /*mRenderer.transX+=dx/10;
	            mRenderer.transY+=dy/10;*/
	            
	            float dAlfa=(float) ((dy*TOUCH_SCALE_FACTOR)*Math.PI);
	            float dTheta=(float) (-dx*TOUCH_SCALE_FACTOR*Math.PI);
	            if(mRenderer.alfa+dAlfa<Math.PI/2&&mRenderer.alfa+dAlfa>-Math.PI/2)
	            	 mRenderer.alfa=mRenderer.alfa+dAlfa;
	            mRenderer.theta+=dTheta;
	            // reverse direction of rotation above the mid-line
	            if (y > getHeight() / 2) {
	              dx = dx * -1 ;
	            }

	            // reverse direction of rotation to left of the mid-line
	            if (x < getWidth() / 2) {
	              dy = dy * -1 ;
	            }
	            
	           /* mRenderer.setAngle(
	                    mRenderer.getAngle() +
	                    ((dx + dy) * TOUCH_SCALE_FACTOR));*/
	            requestRender();
	    }

	    mPreviousX = x;
	    mPreviousY = y;
	    return true;
	}

	
	

}
