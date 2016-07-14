package com.cj.camera;

import java.io.IOException;  
import java.util.List;  
  

  






import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.graphics.PixelFormat;  
import android.hardware.Camera;  
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;  
import android.hardware.Camera.ShutterCallback;  
import android.hardware.Camera.Size;  
import android.util.Log;  
import android.view.SurfaceHolder;  
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
  
public class CameraInterface {  
    private static final String TAG = "yanzi";  
    private Camera mCamera;  
    private Camera.Parameters mParams;
    private boolean isPreviewing = false;
    private float mPreviwRate = -1f;  
    private static CameraInterface mCameraInterface;  
    private Context mContext;
    SharedPreferences mCameraShared;
    
    public interface CamOpenOverCallback{  
        public void cameraHasOpened(int previewWidth,int previewHeight);  
    }  
  
    private CameraInterface(){  
  
    }  
    public static synchronized CameraInterface getInstance(Context context){  
        if(mCameraInterface == null){  
            mCameraInterface = new CameraInterface();  
        }  
        mCameraInterface.mContext=context;
        return mCameraInterface;  
    }  
    /**��Camera 
     * @param callback 
     */  
    public void doOpenCamera(CamOpenOverCallback callback){  
        Log.i(TAG, "Camera open....");  
        mCamera = Camera.open();  
        Log.i(TAG, "Camera open over....");  
        initCameraParams();
        Size size=getPreviewSizeFromShare();
        callback.cameraHasOpened(size.width,size.height);  
    }  
    /**����Ԥ�� 
     * @param holder 
     * @param previewRate 
     */  
    private void saveMobileInfo(){
    	 Size maxPictureSize = CamParaUtil.getInstance().getMaxPictureSize(  
                 mParams.getSupportedPictureSizes());
    	 mCameraShared.edit().putInt("PictureSizeWidth", maxPictureSize.width).commit();
    	 mCameraShared.edit().putInt("PictureSizeHeight", maxPictureSize.height).commit();
    	 Size preViewSize= CamParaUtil.getInstance().getPropPreviewSize(
    			 mParams.getSupportedPreviewSizes(), maxPictureSize.height/maxPictureSize.width,500);
    	 mCameraShared.edit().putInt("PreviewSizeWidth", preViewSize.width).commit();
    	 mCameraShared.edit().putInt("PreviewSizeHeight", preViewSize.height).commit();
    }
    private Size getPictureSizeFromShare(){
    	if(mCameraShared==null)
    		mCameraShared=mContext.getSharedPreferences("camera",Activity.MODE_PRIVATE);
    	return mCamera.new Size(mCameraShared.getInt("PictureSizeWidth", 0),mCameraShared.getInt("PictureSizeHeight", 0));
    }
    private Size getPreviewSizeFromShare(){
    	if(mCameraShared==null)
    		mCameraShared=mContext.getSharedPreferences("camera",Activity.MODE_PRIVATE);
    	return mCamera.new Size(mCameraShared.getInt("PreviewSizeWidth", 0),mCameraShared.getInt("PreviewSizeHeight", 0));
    }
    private void initCameraParams(){
    	if(mCamera != null){
        	mParams = mCamera.getParameters();
        	mCameraShared=mContext.getSharedPreferences("camera",Activity.MODE_PRIVATE);
        	if(mCameraShared.getBoolean("isFirstUseCamera", true)){
        		mCameraShared.edit().putBoolean("isFirstUseCamera",false).commit();
        		saveMobileInfo();
        	};
        	
            mParams.setPictureFormat(PixelFormat.JPEG);//�������պ�洢��ͼƬ��ʽ
            
            CamParaUtil.getInstance().printSupportPictureSize(mParams);
            CamParaUtil.getInstance().printSupportPreviewSize(mParams);
            //����PreviewSize��PictureSize
            Size pictureSize = getPictureSizeFromShare(); 
            mParams.setPictureSize(pictureSize.width, pictureSize.height);  
            
            Size previewSize = getPreviewSizeFromShare();  
            mParams.setPreviewSize(previewSize.width, previewSize.height);  
            mCamera.setDisplayOrientation(90);  
  
            CamParaUtil.getInstance().printSupportFocusMode(mParams);  
            List<String> focusModes = mParams.getSupportedFocusModes();  
            if(focusModes.contains("continuous-video")){  
                mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);  
            }  
            mCamera.setParameters(mParams);   
    	}
    }
    public void doStartPreview(SurfaceHolder holder, float previewRate){  
        Log.i(TAG, "doStartPreview...");  
        if(isPreviewing){
            mCamera.stopPreview();
            return;
        }
        if(mCamera != null){
            try {  
                mCamera.setPreviewDisplay(holder);  
                mCamera.startPreview();//����Ԥ��  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
  
            isPreviewing = true;  
            mPreviwRate = previewRate;  
  
            mParams = mCamera.getParameters(); //����getһ��  
            Log.i(TAG, "��������:PreviewSize--With = " + mParams.getPreviewSize().width  
                    + "Height = " + mParams.getPreviewSize().height);  
            Log.i(TAG, "��������:PictureSize--With = " + mParams.getPictureSize().width  
                    + "Height = " + mParams.getPictureSize().height);  
        }  
    }  
    /** 
     * ֹͣԤ�����ͷ�Camera 
     */  
    public void doStopCamera(){  
        if(null != mCamera)  
        {  
            mCamera.setPreviewCallback(null);  
            mCamera.stopPreview();   
            isPreviewing = false;   
            mPreviwRate = -1f;  
            mCamera.release();  
            mCamera = null;       
        }  
    }  
    /** 
     * ���� 
     */  
    public void doTakePicture(){  
        if(isPreviewing && (mCamera != null)){  
            mCamera.takePicture(mShutterCallback, mRawCallback, mJpegPictureCallback);  
        }  
    }  
  
    /*Ϊ��ʵ�����յĿ������������ձ�����Ƭ��Ҫ���������ص�����*/  
    ShutterCallback mShutterCallback = new ShutterCallback()   
    //���Ű��µĻص������������ǿ����������Ʋ��š����ꡱ��֮��Ĳ�����Ĭ�ϵľ������ꡣ  
    {  
        public void onShutter() {  
            // TODO Auto-generated method stub  
            Log.i(TAG, "myShutterCallback:onShutter...");  
            
        }  
    };  
    PictureCallback mRawCallback = new PictureCallback()   
    // �����δѹ��ԭ���ݵĻص�,����Ϊnull  
    {  
  
        public void onPictureTaken(byte[] data, Camera camera) {  
            // TODO Auto-generated method stub  
            Log.i(TAG, "myRawCallback:onPictureTaken...");  
  
        }  
    };  
    PictureCallback mJpegPictureCallback = new PictureCallback()   
    //��jpegͼ�����ݵĻص�,����Ҫ��һ���ص�  
    {  
        public void onPictureTaken(byte[] data, Camera camera) {  
            // TODO Auto-generated method stub  
            Log.i(TAG, "myJpegCallback:onPictureTaken...");  
            Bitmap b = null;  
            if(null != data){  
                b = BitmapFactory.decodeByteArray(data, 0, data.length);//data���ֽ����ݣ����������λͼ  
                mCamera.stopPreview();  
                isPreviewing = false;  
            }  
            //����ͼƬ��sdcard  
            if(null != b)  
            {  
                //����FOCUS_MODE_CONTINUOUS_VIDEO)֮��myParam.set("rotation", 90)ʧЧ��  
                //ͼƬ��Ȼ������ת�ˣ�������Ҫ��ת��  
                Bitmap rotaBitmap = ImageUtil.getRotateBitmap(b, 90.0f);  
                FileUtil.saveBitmap(rotaBitmap);  
            }  
            //�ٴν���Ԥ��  
            mCamera.startPreview();  
            isPreviewing = true;  
        }  
    };  
  
  
}  