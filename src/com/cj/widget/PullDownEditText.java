package com.cj.widget;



import com.cj.mywidget.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PullDownEditText extends LinearLayout{

	private TextView tvLabel;
	private EditText edt;
	private ImageView iv;
	private String[] items;
	private int selectedItem;
	public PullDownEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		int resouceId = -1;
		TypedArray typeArray = context.obtainStyledAttributes(attrs,
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
	    	}
	    }
	    typeArray.recycle();
	}
	private DialogInterface.OnClickListener diaListener=new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			selectedItem=which;
			setEditText(items[which]);
			dialog.dismiss();
		}
	};
	public void setEditText(String text){
		edt.setText(text);
	}
	public String getEditText(){
		return edt.getText().toString();
	}
	public  void init(String[] items){
		this.items=items;
	}
	private void initView(){
		setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout ll=(LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.my_pull_down_edt,this);
		tvLabel=(TextView) ll.findViewById(R.id.tvLabel);
		edt=(EditText)ll.findViewById(R.id.edt);
		iv=(ImageView)ll.findViewById(R.id.iv);
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(items!=null){
					
					Dialog dialog=new AlertDialog.Builder(getContext())
					.setSingleChoiceItems(items,0,diaListener)
					.create();
					dialog.show();
				}
				
			}
		});
	}

}
