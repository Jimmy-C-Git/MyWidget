package com.cj.mywidget;

import java.util.ArrayList;

import com.cj.widget.SmartListViewExFromViewGroup;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SmartListActivity extends Activity {
	SmartListViewExFromViewGroup smartList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smart_list);
		smartList=(SmartListViewExFromViewGroup)findViewById(R.id.smartList);
		ArrayList<String> title=new ArrayList<String>();
		ArrayList<String> columnWidth=new ArrayList<String>();
		final ArrayList<ArrayList<String>> data=new ArrayList<ArrayList<String>>();
		for(int i=0;i<10;i++)
		{
			title.add(String.valueOf(i)+"title");
			columnWidth.add(String.valueOf(i*10+100));
		}
		for(int i=0;i<100;i++)
		{
			ArrayList<String>item =new ArrayList<String>();
			
			for(int j=0;j<10;j++)
			{
				item.add(String.format("%d-%d", i,j));
			}
			data.add(item);
		}
		smartList.init(title, columnWidth, data);
		smartList.loadList();
	}
}
