package com.cj.widget;

import java.util.ArrayList;
import java.util.HashMap;






import com.cj.mywidget.R;
import com.cj.tools.Tools;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class SmartListViewExFromViewGroup extends ViewGroup implements OnScrollListener{

	private ArrayList<String> mTitle;
	private ArrayList<String> mColumnWidth;
	private ArrayList<ArrayList<String>> mData;
	private int columnCount=0;
	private LinearLayout header,footer;
	private ListView mListView;
	private boolean isHaveTitel=false;
	int firstViewPosition=0;
	int firstViewTop=0;
	int totalWidth=0;
	public SmartListViewExFromViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.my_smart_list_view, this);
		mListView=(ListView) findViewById(R.id.lv);
		mListView.setOnScrollListener(this);
		header=(LinearLayout)findViewById(R.id.header);
		footer=(LinearLayout)findViewById(R.id.footer);
		
	}
	
	public void init(ArrayList<String> title,ArrayList<String> columnWidth,ArrayList<ArrayList<String>> data)
	{
		mTitle=title;
		mColumnWidth=columnWidth;
		mData=data;
		if(mData!=null)
		{
			if(mData.size()>0)
				columnCount=mData.get(0).size();
		}
		columnCount=Math.max(columnCount, Math.min(title.size(), columnWidth.size()));
		if(mTitle!=null)
		{
			if(!isHaveTitel)
			{
				header.setVisibility(View.VISIBLE);
				header.removeViewAt(1);
				View temp=getItemView();
				setItemData((LinearLayout)temp,mTitle);
				header.addView(temp,1);
				isHaveTitel=true;
			}
			
		}
		
	}
	public void init(String[] listTitle, String[] columnWidth,
			ArrayList<ArrayList<String>> data) {
		ArrayList<String> title=new ArrayList<String>();
		for(int i=0;i<listTitle.length;i++)
		{
			title.add(listTitle[i]);
		}
		ArrayList<String> column=new ArrayList<String>();
		for(int i=0;i<columnWidth.length;i++)
		{
			column.add(columnWidth[i]);
		}
		init(title,column,data);
		
	}
	public void setOnItemClickListener(OnItemClickListener li)
	{
		mListView.setOnItemClickListener(li);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int measureWidth=0;
		for(int i=0;i<getChildCount();i++)
		{
			 View childView = getChildAt(i);  
	         measureChild(childView, widthMeasureSpec, heightMeasureSpec); 
	         measureWidth=Math.max(measureWidth, childView.getMeasuredWidth());
		}
		
		setMeasuredDimension(measureWidth, getMeasuredHeight());
		
	}
	public void loadList()
	{
		mListView.setAdapter(new MyListAdapter());
		mListView.setSelectionFromTop(firstViewPosition,firstViewTop);
	}
	public void setAdapter(MyListAdapter adapter)
	{
		mListView.setAdapter(adapter);
		mListView.setSelectionFromTop(firstViewPosition,firstViewTop);
	}
	public ListAdapter getAdapter()
	{
		return mListView.getAdapter();
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		
		super.onSizeChanged(w, h, oldw, oldh);
	}
	private void setItemData(LinearLayout ll,ArrayList<String> data)
	{
		int childCount =ll.getChildCount();
		for(int i=0;i<childCount;i++)
		{
			View view=ll.getChildAt(i);
			if(i<data.size())
				((TextView)view).setText(data.get(i));
			else 
				((TextView)view).setText("");
		}
	}
	
	public View getItemView()
	{
		LinearLayout ll=new LinearLayout(getContext());
		ll.setOrientation(LinearLayout.HORIZONTAL);
		ll.setMinimumHeight(50);
		ll.setGravity(Gravity.CENTER_VERTICAL);
		//ll.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		totalWidth=0;
		for(int i=0;i<columnCount;i++)
		{
			
			TextView tv=new TextView(getContext());
			
			if(i<mColumnWidth.size())
			{
				int width=Tools.str2int(mColumnWidth.get(i));
				tv.setLayoutParams(new LayoutParams(width==0?50:width, LayoutParams.WRAP_CONTENT));
				totalWidth+=(width==0?50:width);
			}else 
			{
				tv.setLayoutParams(new LayoutParams(50, LayoutParams.WRAP_CONTENT));
				totalWidth+=50;
			}
				
			
			ll.addView(tv);
		}
		return ll;
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int startHeight=t;
		for(int i=0;i<getChildCount();i++)
		{
			View child=getChildAt(i);
			int endHeight=startHeight+child.getMeasuredHeight();
			child.layout(l, startHeight, r, endHeight<b? endHeight:b);
			startHeight= endHeight<b? endHeight:b;
		}
		
			
		
	}
	public class MyListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout ll;
			
			if(convertView==null)
			{
				convertView=getItemView();
			}
			ll=(LinearLayout)convertView;
			setItemData(ll, mData.get(position));
				

			return convertView;
		}
		
		
		
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
			int totalItemCount) {
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int scrollState) {
		
		if (scrollState == SCROLL_STATE_IDLE) {
			firstViewPosition=mListView.getFirstVisiblePosition();
			View view1 =mListView.getChildAt(0);
			if(view1!=null)
				firstViewTop=view1.getTop();
		}
	}
	

}
