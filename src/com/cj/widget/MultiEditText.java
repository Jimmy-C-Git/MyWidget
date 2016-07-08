package com.cj.widget;

import com.cj.mywidget.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MultiEditText extends LinearLayout{
	public static final int EDITABLE=0;
	public static final int CANNOTEDITABLE=1;
	public static final int MULTISELECT=2;
	private TextView tvLabel;
	private EditText edt;
	private ImageView iv;
	private String[] items;
	private int selectedItem;
	private PopupWindow mPopupWindow;
	private LinearLayout llEdt;
	private int  mode=0;
	//editable �ɱ༭,������ѡ��
	//cannoteditable ֻ������ѡ��
	private String multiSelectAns;
	public MultiEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		setMyAttrs(attrs);
		
		initMode();
	}
	public  void init(String[] items){
		this.items=items;
	}
	public  void init(String[] items,int  mode){
		this.items=items;
		this.mode=mode;
	}
	private void initView(){
		setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout ll=(LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.my_pull_down_edt,this);
		tvLabel=(TextView) ll.findViewById(R.id.tvLabel);
		edt=(EditText)ll.findViewById(R.id.edt);
		iv=(ImageView)ll.findViewById(R.id.iv);
		llEdt=(LinearLayout) ((LinearLayout)ll.getChildAt(0)).getChildAt(1);
		
		
	}
	private void initMode()
	{
		switch (mode)
		{
		case EDITABLE:
			iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(items!=null){
						
						showPopupWindow(llEdt);
					}
					
				}
			});
			break;
		case CANNOTEDITABLE:
			edt.setFocusable(false);
			OnClickListener l = new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(items!=null){
						
						showPopupWindow(llEdt);
					}
					
				}
			};
			iv.setOnClickListener(l);
			edt.setOnClickListener(l);
			break;
		case MULTISELECT:
			edt.setFocusable(false);
			iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(items!=null){
						
						showMultiSelectDialog();
					}
					
				}
			});
			
			break;
			
		
		}
	}
	private void setMyAttrs(AttributeSet attrs){
		int resouceId = -1;
		TypedArray typeArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.EditTextWithLabel);
		int N = typeArray.getIndexCount();
	    for (int i = 0; i < N; i++){
	    	int attr = typeArray.getIndex(i);
	    	switch (attr) {
	    	
	    	case R.styleable.EditTextWithLabel_LabelText:
	    		resouceId = typeArray.getResourceId(
	    				R.styleable.EditTextWithLabel_LabelText, 0);
	    		tvLabel.setText(resouceId > 0 ? typeArray.getResources()
	    				.getText(resouceId) : typeArray
	    				.getString(R.styleable.EditTextWithLabel_LabelText));
	    		break;
	    	case R.styleable.EditTextWithLabel_LabelWidth:
	    		resouceId = typeArray.getResourceId(
	    				R.styleable.EditTextWithLabel_LabelWidth, 0);
	    		
	    		tvLabel.setWidth((int) (resouceId > 0 ? typeArray.getResources()
	    				.getDimension(resouceId) : typeArray
	    				.getDimension(R.styleable.EditTextWithLabel_LabelWidth, 0f)));
	    		break;
	    	case R.styleable.EditTextWithLabel_mode:
	    		resouceId = typeArray.getResourceId(
	    				R.styleable.EditTextWithLabel_mode, 0);
	    		mode=resouceId > 0 ? typeArray.getResources().getInteger(resouceId)
	    				: typeArray.getInteger(R.styleable.EditTextWithLabel_mode, 0);
	    		break;
	    	}
	    }
	    typeArray.recycle();
	}
	private void  showMultiSelectDialog(){
		multiSelectAns="";
		OnMultiChoiceClickListener  diaListener=new DialogInterface.OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int which, boolean arg2) {
				// TODO Auto-generated method stub
				if(arg2)
					multiSelectAns+=(items[which]+",");
			}
		};
	
		boolean[] mm = new boolean[items.length];
		Dialog dialog=new AlertDialog.Builder(getContext())
		.setMultiChoiceItems(items,mm,diaListener).setNegativeButton("ȡ��", null).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if(multiSelectAns.length()>0)
					multiSelectAns=multiSelectAns.substring(0,multiSelectAns.length()-1);
				edt.setText(multiSelectAns);
				
			}
		}).create();
		dialog.show();
	}
	 private void showPopupWindow(View view) {
		
		 // һ���Զ���Ĳ��֣���Ϊ��ʾ������
		 View contentView = LayoutInflater.from(getContext()).inflate(
				 R.layout.my_pop_window, null);
		 mPopupWindow = new PopupWindow(contentView,
				 edt.getWidth(), LayoutParams.WRAP_CONTENT, true);
		 
		 ListView lvPopWindow=(ListView) contentView.findViewById(R.id.lvPopWindow);
		 lvPopWindow.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items));
		 lvPopWindow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				selectedItem=position;
				edt.setText(items[position]);
				mPopupWindow.dismiss();
			}
		});
		 
		 
		 mPopupWindow.setTouchable(true);

		 /*mPopupWindow.setTouchInterceptor(new OnTouchListener() {
			 
			 @Override
			 public boolean onTouch(View v, MotionEvent event) {
				 
				
				 return false;
				 // �����������true�Ļ���touch�¼���������
				 // ���غ� PopupWindow��onTouchEvent�������ã���������ⲿ�����޷�dismiss
			 }

			 
		 });*/

		 // ���������PopupWindow�ı����������ǵ���ⲿ������Back�����޷�dismiss����
		 // �Ҿ���������API��һ��bug
		 mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xffffff));
		 
		 // ���úò���֮����show
		 mPopupWindow.showAsDropDown(view);
		 
	 }

}
