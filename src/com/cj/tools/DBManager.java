package com.cj.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;




import com.cj.entity.ItemInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private DBHelper helper;  
	private SQLiteDatabase db; 
	public DBManager(Context context)
	{
		helper = new DBHelper(context);  
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);  
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里  
        db = helper.getWritableDatabase();
	}
	public void execSQL(String sql,Object[] bindArgs)
	{
		db.execSQL(sql, bindArgs);
	}
	public void execSQL(String sql)
	{
		db.execSQL(sql);
	}
	public void addTable(String tableName,ArrayList<String> columnName)
	{
		//eststdtable
		String sql="CREATE TABLE IF NOT EXISTS "+tableName+" (";
		for(int i=0;i<columnName.size();i++)
		{
			if(i==0)
				sql+=(columnName.get(i)+" VARCHAR");
			else 
				sql+=(","+columnName.get(i)+" VARCHAR");
		}
		sql+=");";
		db.execSQL(sql);
	}
	public void dropTable(String tableName)
	{
		String sql="DROP TABLE IF EXISTS "+tableName;
		db.execSQL(sql);
	}
	public void cleanTable(String tableName)
	{
		String sql="delete from ?";
		db.execSQL(sql,new Object[]{tableName});
	}
	public void insert2Table(ArrayList<ItemInfo> items,String tableName)
	{
		
		for(ItemInfo info:items){
			
			insert2Table(info, tableName);
		}
	}
	public void insert2Table(ItemInfo item,String tableName)
	{
		String columnNames="";
		String values="";
		ArrayList<String > valuesObject=new ArrayList<String>();
		Iterator<Entry<Object, Object>> it = item.entrySet().iterator(); 
		while (it.hasNext()) {  
            Entry<Object, Object> entry = it.next();  
            Object key = entry.getKey();  
            Object value = entry.getValue();
           	columnNames+=(key+",");
           	values+=("?"+",");
           	valuesObject.add(value.toString());
		}
		if(columnNames.length()>0)
			columnNames=columnNames.substring(0, columnNames.length()-1);
		if(values.length()>0)
			values=values.substring(0, values.length()-1);
		String sql=String.format("INSERT INTO %s (%s) VALUES (%s)", tableName ,columnNames,values);
		db.execSQL(sql,valuesObject.toArray());
	}
	public ArrayList<ItemInfo> queryAllFromTable(String tableName)
	{
		ArrayList<ItemInfo>data =new ArrayList<ItemInfo>();
		try {
			Cursor c = db.rawQuery("SELECT * FROM "+ tableName, null);
			while (c.moveToNext()) { 
				 ItemInfo info=new ItemInfo();
				 int columnCount =c.getColumnCount();
				 for(int i=0;i<columnCount;i++)
				 {
					 info.setProperty(c.getColumnName(i), c.getString(i));
				 }
				 data.add(info);
			 }  
		     c.close(); 
		} catch (Exception e) {
			return null;
		}
		 
	     return data;
	}
	public void closeDB() {  
        db.close();  
    }
	
}
