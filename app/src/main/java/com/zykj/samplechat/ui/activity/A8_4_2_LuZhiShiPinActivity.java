package com.zykj.samplechat.ui.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zykj.samplechat.R;
import com.zykj.samplechat.ui.widget.MovieRecorderView;

import java.io.File;

/**
 * 
 * @author lc
 * @date 创建时间：2016-2-17 上午9:40:02
 * @version 1.0 
 * @Description 录制视频页
 */
public class A8_4_2_LuZhiShiPinActivity extends Activity {

	private Context mContext = A8_4_2_LuZhiShiPinActivity.this;
	private File file;
	private TextView txt_baocun,txt_chongpai;
 
	 private MovieRecorderView mRecorderView;//视频录制控件
	    private Button mShootBtn;//视频开始录制按钮
	    private boolean isFinish = true;
	    private boolean success = false;//防止录制完成后出现多次跳转事件

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.a8_4_1_activity_luzhisp);
			
	        mRecorderView = (MovieRecorderView) findViewById(R.id.movieRecorderView);
	        
	        mShootBtn = (Button) findViewById(R.id.shoot_button);
	        txt_baocun = (TextView) findViewById(R.id.baocun);
	        txt_chongpai = (TextView) findViewById(R.id.chongpai);

	        initData();
	    }
	    
	    public void initData(){
	    	//用户长按事件监听
	        mShootBtn.setOnTouchListener(new OnTouchListener() {

	            @Override
	            public boolean onTouch(View v, MotionEvent event) {
	                if (event.getAction() == MotionEvent.ACTION_DOWN) {//用户按下拍摄按钮
	                    mRecorderView.record(new MovieRecorderView.OnRecordFinishListener() {
	                        @Override
	                        public void onRecordFinish() {
	                            if(!success&&mRecorderView.getTimeCount()<20){//判断用户按下时间是否大于10秒
	                                success = true;
	                                handler.sendEmptyMessage(1);
	                            }
	                        }
	                    });
	                } else if (event.getAction() == MotionEvent.ACTION_UP) {//用户抬起拍摄按钮
	                    if (mRecorderView.getTimeCount() > 3){//判断用户按下时间是否大于3秒
	                        if(!success){
	                            success = true;
	                            handler.sendEmptyMessage(1);
	                        }
	                    } else {
	                        success = false;
	                        if (mRecorderView.getmVecordFile() != null)
	                            mRecorderView.getmVecordFile().delete();//删除录制的过短视频
	                        mRecorderView.stop();//停止录制
	                        Toast.makeText(A8_4_2_LuZhiShiPinActivity.this, "视频录制时间太短", Toast.LENGTH_SHORT).show();
	                    }
	                }
	                return true;
	            }
	        });
	    }

	    @Override
	    public void onResume() {
	        super.onResume();
	        txt_baocun.setVisibility(View.GONE);
			txt_chongpai.setVisibility(View.GONE);
			mShootBtn.setVisibility(View.VISIBLE);
	        isFinish = true;
	        if (mRecorderView.getmVecordFile() != null)
	            mRecorderView.getmVecordFile().delete();//视频使用后删除
	        
	        initData();
	    }

	    @Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        isFinish = false;
	        success = false;
	        mRecorderView.stop();//停止录制
	    }

	    @Override
	    public void onPause() {
	        super.onPause();
	    }

	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	        System.gc();
	    }

	    private Handler handler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            if(success){
	                finishActivity();
	            }
	        }
	    };

	    //视频录制结束后，跳转的函数
	    private void finishActivity() {
	        if (isFinish) {
	            mRecorderView.stop();
	            
	            mShootBtn.setVisibility(View.GONE);
	            txt_baocun.setVisibility(View.VISIBLE);
	            txt_chongpai.setVisibility(View.VISIBLE);
	            
	            txt_baocun.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.putExtra("path",mRecorderView.getmVecordFile().toString());
						setResult(14,intent);
			            A8_4_2_LuZhiShiPinActivity.this.finish();
					}
				});
	            
	            txt_chongpai.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO 重拍
						txt_baocun.setVisibility(View.GONE);
						txt_chongpai.setVisibility(View.GONE);
						mShootBtn.setVisibility(View.VISIBLE);
						isFinish = true;
				        if (mRecorderView.getmVecordFile() != null)
				            mRecorderView.getmVecordFile().delete();//视频使用后删除
				        
				        initData();
					}
				});
	          
	        }
	        success = false;
	    }

	    /**
	     * 录制完成回调
	     */
	     public interface OnShootCompletionListener {
	         public void OnShootSuccess(String path, int second);
	         public void OnShootFailure();
	     }
}
