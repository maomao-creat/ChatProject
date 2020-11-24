package com.zykj.samplechat.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.JiLuBean;
import com.zykj.samplechat.presenter.JiLuPresenter;
import com.zykj.samplechat.ui.activity.base.SwipeRecycleViewActivity;
import com.zykj.samplechat.ui.adapter.JiLuAdapter;

/**
 * Created by 徐学坤 on 2018/2/1.
 */
public class ZJiluActivity extends SwipeRecycleViewActivity<JiLuPresenter,JiLuAdapter,JiLuBean>{
    @Override
    protected JiLuAdapter provideAdapter() {
        return new JiLuAdapter(getContext());
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected CharSequence provideTitle() {
        return "发包记录";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gonggao;
    }

    @Override
    public JiLuPresenter createPresenter() {
        return new JiLuPresenter();
    }

    @Override
    public void initListeners() {
        presenter.getData(1,20);
    }

    @Override
    public void onItemClick(View view, int pos, JiLuBean item) {

    }
}
