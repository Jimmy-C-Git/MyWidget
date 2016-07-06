package com.cj.tools;

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
		}finally{
			return ans;
		}
		/*Pattern pattern = Pattern.compile("^[0-9]*");
		Matcher matcher = pattern.matcher(str);
		if(matcher.matches())
		{
			ans=Integer.valueOf(str);
		}*/
		
	}
	public static String getUUID()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "").toLowerCase();
	}
}
