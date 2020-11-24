package com.zykj.samplechat.ui.activity;

import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.JiaoYiPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;

/**
 * Created by 徐学坤 on 2018/2/1.
 */
public class ZJiaoYiActivity extends ToolBarActivity<JiaoYiPresenter>{
    @Override
    protected CharSequence provideTitle() {
        return "交易记录";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.ui_activity_jiaoyi;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public JiaoYiPresenter createPresenter() {
        return new JiaoYiPresenter();
    }
}
