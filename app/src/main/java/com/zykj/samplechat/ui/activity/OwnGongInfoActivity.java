package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Gonggao;
import com.zykj.samplechat.presenter.OwnGongInfoPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.OwnGongInfoView;

import butterknife.Bind;

/**
 * Created by ninos on 2016/7/27.
 */
public class OwnGongInfoActivity extends ToolBarActivity<OwnGongInfoPresenter> implements OwnGongInfoView {

    private Gonggao gonggao;

    @Bind(R.id.title_name)TextView title_name;
    @Bind(R.id.content)TextView content;

    @Override
    protected CharSequence provideTitle() {
        return "公告";
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        gonggao = (Gonggao) getIntent().getBundleExtra("data").getSerializable("gonggao");

        title_name.setText(gonggao.Title);
        content.setText(gonggao.Message);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gonggao_info;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public OwnGongInfoPresenter createPresenter() {
        return new OwnGongInfoPresenter();
    }
}
