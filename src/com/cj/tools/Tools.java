package com.cj.tools;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Tools {
	public static void showMsg2DiaLog(Context context,String Msg)
	{
		Dialog dialog=new AlertDialog.Builder(context).setTitle("提示").setMessage(Msg).setPositiveButton("确定", null).create();
		dialog.show();
	}
	public static void showMsg2Toast(Context context,String Msg)
	{
		Toast.makeText(context, Msg, Toast.LENGTH_SHORT).show();
	}
	public static String singleChoice(Context context,String title)
	{
//		Dialog dialog=new AlertDialog.Builder(context).setTitle(title).setPositiveButton("确定", new)
		return "";
	}
	
	public static boolean isNetWorkAwaliable(Context context){
		 ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		 NetworkInfo[] info = manager.getAllNetworkInfo();  
	        if (info != null) {  
	            for (int i = 0; i < info.length; i++) {  
	                if (info[i].getState() == NetworkInfo.State.CONNECTED) {  
	                    return true;  
	                }  
	            }  
	        }  

		return false;
	}
	public static float str2float(String str){
		float ans=0f;
		try {
			ans=Float.valueOf(str);
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			return ans;
		}
		/*Pattern pattern = Pattern.compile("^([+-]?)\\d*\\.\\d+$");
		Matcher matcher = pattern.matcher(str);
		if(matcher.matches())
		{
			ans=Float.valueOf(ans);
		}*/
		
	}
	public static int str2int(String str){
		int  ans=0;
		try {
			ans=Integer.valueOf(str);
		} catch (Exception e) {
			// TODO: handle exception
			return ans;
		}
		return ans;
			
	}
	public static String getUUID()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "").toLowerCase();
	}
	public static void dialogCheat(Dialog dialog ,boolean isShow){
		try {
			Field field = dialog.getClass().getSuperclass()
					.getDeclaredField("mShowing");
			field.setAccessible(true);
			
			// 将isShow设为false，表示对话框已关闭,此时dismiss的时候对话框不会消失
			field.set(dialog, isShow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static int dip2px(Context context, float dipValue){              
        final float scale = context.getResources().getDisplayMetrics().density;                   
        return (int)(dipValue * scale + 0.5f);           
    }       
    
    public static int px2dip(Context context, float pxValue){                  
        final float scale = context.getResources().getDisplayMetrics().density;                   
        return (int)(pxValue / scale + 0.5f);           
    } 
}
