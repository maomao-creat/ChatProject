package com.zykj.samplechat.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.utils.ToolsUtils;

/**
 * @author 徐学坤
 * @date 2016年8月15日
 * @describe 自定义dialog
 */
public class MyDialog extends Dialog implements View.OnClickListener {
	private DCListener listener;
	Context context;
	private TextView tv_title;

	public MyDialog(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * 自定义dialog
	 * @param context 上下文
	 * @param listener 回调接口
	 */
	public MyDialog(Context context, DCListener listener) {
		super(context, R.style.MyDialog);
		this.context = context;
		this.listener = listener;
	}
	
	//设置标题
	public MyDialog setTitle(String title){
		tv_title.setText(title);
		return this;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.ui_dialog_input);
		tv_title = (TextView) findViewById(R.id.tv_title);
		TextView dialog_textViewID = (TextView) findViewById(R.id.dialog_textViewID);//左边按钮
		TextView dialog_textViewID1 = (TextView) findViewById(R.id.dialog_textViewID1);//右边按钮
		dialog_textViewID.setOnClickListener(this);
		dialog_textViewID1.setOnClickListener(this);
		initDialog(context);
	}

	/**
	 * 设置dialog的宽为屏幕的3分之1
	 * @param context
	 */
	private void initDialog(Context context) {
		//WindowManager windowManager = this.getWindow().getWindowManager();
		//Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = (int) (ToolsUtils.M_SCREEN_WIDTH / 6 * 5); // 设置宽度
		this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE | 
//				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | 
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//
		this.getWindow().setAttributes(lp);
	}
	
	//隐藏View
	public void hideView(int... viewIds){
		for (int id : viewIds) {
			findViewById(id).setVisibility(View.GONE);
		}
	}
	
	//隐藏View
	public void showView(int... viewIds){
		for (int id : viewIds) {
			findViewById(id).setVisibility(View.VISIBLE);
		}
	}
	
	//获取输入框的值
	public String getText(int viewId){
		TextView edit = (TextView)findViewById(viewId);
		return edit.getText().toString();
	}
	
	//设置输入框的值
	public MyDialog setText(int viewId, String val){
		TextView edit = (TextView)findViewById(viewId);
		edit.setText(val);
		if(edit instanceof EditText){
			((EditText)edit).setSelection(val.length());
		}
		return this;
	}
	
	//设置输入框提醒
	public MyDialog setHint(int viewId, String val){
		TextView edit = (TextView)findViewById(viewId);
		edit.setHint(val);
		return this;
	}

	//退出
	public MyDialog worning(){
		EditText edit = (EditText)findViewById(R.id.tv_edit);
		edit.setEnabled(false);
		return this;
	}

	//按钮点击事件
	public interface DCListener {
		void onRightBtnClick(Dialog dialog);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_textViewID:
			//左边按钮点击事件
			dismiss();
			break;
		case R.id.dialog_textViewID1:
			//右边按钮点击事件
			listener.onRightBtnClick(this);
			break;
		default:
			break;
		}
	}
}