package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.About;
import com.zykj.samplechat.presenter.OwnAboutPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.OwnAboutView;

import butterknife.Bind;

/**
 * Created by ninos on 2016/7/27.
 */
public class OwnAboutActivity extends ToolBarActivity<OwnAboutPresenter> implements OwnAboutView {

    private About about;

    @Bind(R.id.content)TextView content;

    @Override
    protected CharSequence provideTitle() {
        return "关于我们";
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        about = (About)getIntent().getBundleExtra("data").getSerializable("about");

        content.setText(about.AboutUs);

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public OwnAboutPresenter createPresenter() {
        return new OwnAboutPresenter();
    }
}
