package com.cj.camera;
  

  
import com.cj.camera.CameraInterface.CamOpenOverCallback;
import com.cj.mywidget.R;

import android.app.Activity;  
import android.content.SharedPreferences;
import android.graphics.Point;  
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;  
import android.os.Handler;
import android.view.Menu;  
import android.view.SurfaceHolder;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.view.ViewGroup.LayoutParams;  
import android.widget.ImageButton;  
  
public class CameraActivity extends Activity implements CamOpenOverCallback {  
    private static final String TAG = "yanzi";  
    CameraSurfaceView surfaceView = null;  
    ImageButton shutterBtn;  
    float previewRate = -1f; 
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        
        setContentView(R.layout.activity_camera);  
        initUI();  
        initViewParams();  
          
        shutterBtn.setOnClickListener(new BtnListeners()); 
      
    }
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	 Thread openThread = new Thread(){  
             @Override  
             public void run() {  
                 // TODO Auto-generated method stub  
                 CameraInterface.getInstance(CameraActivity.this).doOpenCamera(CameraActivity.this);  
             }  
         };  
         openThread.start(); 
    }
 
    private void initUI(){  
        surfaceView = (CameraSurfaceView)findViewById(R.id.camera_surfaceview);  
        shutterBtn = (ImageButton)findViewById(R.id.btn_shutter);  
    }  
    private void initViewParams(){  
        LayoutParams params = surfaceView.getLayoutParams();  
        Point p = DisplayUtil.getScreenMetrics(this);  
        params.width = p.x;  
        params.height = p.y;  
        previewRate = DisplayUtil.getScreenRate(this); //默认全屏的比例预览  
        surfaceView.setLayoutParams(params);  
  
        //手动设置拍照ImageButton的大小为120dip×120dip,原图片大小是64×64  
        LayoutParams p2 = shutterBtn.getLayoutParams();  
        p2.width = DisplayUtil.dip2px(this, 80);  
        p2.height = DisplayUtil.dip2px(this, 80);;        
        shutterBtn.setLayoutParams(p2);   
  
    }  
    Handler handler=new Handler();
    
    @Override  
    public void cameraHasOpened(final int previewWidth,final int previewHeight) {  
        final SurfaceHolder holder = surfaceView.getSurfaceHolder();  
        
        
        handler.post(new Runnable() {
			
			@Override
			public void run() {
				LayoutParams params = surfaceView.getLayoutParams();
				Point p=DisplayUtil.getScreenMetrics(CameraActivity.this);
				int screenWidth=p.x;
				int screenHeight=p.y;
				float rate=(float)previewWidth/previewHeight;
				float screenRate=(float)screenHeight/screenWidth;
				
				/*if(rate>1){
					screenRate=screenHeight/screenWidth;
				}else {
					screenRate=screenHeight/screenWidth;
				}*/
				if(rate>screenRate){
					params.width =(int) (screenHeight/rate) ;  
			        params.height =screenHeight ; 
					//params.width =screenWidth ;  
			        //params.height = (int) (screenWidth/rate); 
				}else {
					/*params.width =(int) (screenHeight*rate) ;  
			        params.height =screenHeight ;*/ 
			        params.width =screenWidth ;  
			        params.height = (int) (screenWidth*rate); 
				}
					 
		        
		        surfaceView.setLayoutParams(params);
		        CameraInterface.getInstance(CameraActivity.this).doStartPreview(holder, previewRate);
			}
		});
    }  
    private class BtnListeners implements OnClickListener{  
  
        @Override  
        public void onClick(View v) {  
            // TODO Auto-generated method stub  
            switch(v.getId()){  
            case R.id.btn_shutter:  
                CameraInterface.getInstance(CameraActivity.this).doTakePicture();  
                break;  
            default:break;  
            }  
        }  
  
    }  
  
}  