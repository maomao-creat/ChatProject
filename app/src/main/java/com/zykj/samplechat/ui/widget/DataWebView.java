package com.zykj.samplechat.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by ninos on 2017/11/28.
 */

public class DataWebView extends WebView {

    private OnWebClickListener mOnWebClickListener;

    public DataWebView(Context context) {
        super(context);
    }

    public DataWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DataWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mOnWebClickListener.onWebClick(this);
        return true;
    }

    public void setOnWebClickListener(OnWebClickListener l) {
        this.mOnWebClickListener = l;
    }

    public interface OnWebClickListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        void onWebClick(DataWebView v);
    }
}