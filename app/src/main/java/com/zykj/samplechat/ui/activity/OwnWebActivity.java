package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.About;
import com.zykj.samplechat.presenter.OwnWebPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.OwnWebView;

import butterknife.Bind;

/**
 * Created by ninos on 2016/7/27.
 */
public class OwnWebActivity extends ToolBarActivity<OwnWebPresenter> implements OwnWebView{

    private About about;

    @Bind(R.id.web)WebView web;

    @Override
    protected CharSequence provideTitle() {
        return "商城";
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        about = (About)getIntent().getBundleExtra("data").getSerializable("about");
        web.loadUrl(about.ImageUrl);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public OwnWebPresenter createPresenter() {
        return new OwnWebPresenter();
    }
}
