package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Gonggao;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.OwnGongPresenter;
import com.zykj.samplechat.ui.activity.base.RecycleViewActivity;
import com.zykj.samplechat.ui.adapter.OwnGongAdapter;
import com.zykj.samplechat.ui.view.OwnGongView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ninos on 2016/7/27.
 */
public class OwnGongActivity extends RecycleViewActivity<OwnGongPresenter,OwnGongAdapter,Gonggao> implements OwnGongView{
    @Override
    protected OwnGongAdapter provideAdapter() {
        return new OwnGongAdapter(getContext(), presenter);
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        presenter.getData(page,count);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected CharSequence provideTitle() {
        return "公告";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gonggao;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public OwnGongPresenter createPresenter() {
        return new OwnGongPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, Gonggao item) {
        startActivity(OwnGongInfoActivity.class,new Bun().putSerializable("gonggao",item).ok());
    }

    @Override
    public void success(ArrayList<Gonggao> list) {
        bd(list);
    }

    @Override
    public String getJsonData() {
        String data = "";
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","SelectGongGaoList");
        map.put("startRowIndex",(page-1)*count);
        map.put("maximumRows",count);
        String json = StringUtil.toJson(map);
        try {
            data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
        }catch (Exception e){
            data = "";
        }

        return data;
    }
}
