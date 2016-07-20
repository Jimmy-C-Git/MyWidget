package com.cj.glsurface;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class GLSurface extends GLSurfaceView implements OnGestureListener{
	GestureDetector detector;
	
	private float anglex = 0f;
	private float angley = 0f;
	static final float ROTATE_FACTOR = 60;
	
	public GLSurface(Context context) {
		super(context);
		setEGLContextClientVersion(2);
		
		setRenderer(new ThreeDRender(context));
		
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		detector=new GestureDetector(context,this);
		setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				return detector.onTouchEvent(event);
			}
		});
		
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		setAnglexAndAndley(distanceX, distanceY);

		Log.d("chen", "onScroll(),distanceX=" + distanceX + ",distanceY="
				+ distanceY);
		return true;
	}
	private void setAnglexAndAndley(float velocityX, float velocityY) {
		velocityX = velocityX > 2000 ? 2000 : velocityX;
		velocityX = velocityX < -2000 ? -2000 : velocityX;
		velocityY = velocityY > 2000 ? 2000 : velocityY;
		velocityY = velocityY < -2000 ? -2000 : velocityY;

		// 根据横向上的速度计算沿Y轴旋转的角度
		angley -= velocityX * ROTATE_FACTOR / 2000;
		// 根据纵向上的速度计算沿X轴旋转的角度
		anglex -= velocityY * ROTATE_FACTOR / 2000;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

}
