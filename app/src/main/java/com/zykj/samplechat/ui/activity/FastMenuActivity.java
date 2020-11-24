package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.rey.material.app.Dialog;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.About;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.OwnPresenter;
import com.zykj.samplechat.ui.activity.base.BaseActivity;
import com.zykj.samplechat.ui.view.OwnView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import java.util.HashMap;
import butterknife.OnClick;

/**
 * Created by csh
 * Created date 2016/11/4.
 * Description 快捷菜单
 */

public class FastMenuActivity extends BaseActivity<OwnPresenter> implements OwnView{

    private Dialog dialog_phone;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fase_menu;
    }

    @Override
    public void initListeners() {}

    @Override
    protected void initThings(Bundle savedInstanceState) {}

    @Override
    public OwnPresenter createPresenter() {
        return new OwnPresenter();
    }

    @OnClick(R.id.ll_Profile)
    public void clickProfile(){
        startActivity(FriendsAddActivity.class);
    }

    @OnClick(R.id.ll_Phones)
    public void clickPhones(){
        HashMap<String,String> map = new HashMap<>();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","SelectAboutUs");
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.SelectAboutUs(data);
        }catch (Exception ex){}
    }

    @OnClick(R.id.ll_Coin)
    public void clickCoin(){
        startActivity(QRCodeActivity.class);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }

    @Override
    public void success(final About about) {
        if(dialog_phone==null) {
            dialog_phone = new Dialog(getContext()).title("是否拨打客服电话").positiveAction("立即拨打").positiveActionClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + about.TelePhone)));
                }
            }).negativeAction("取消").negativeActionClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_phone.dismiss();
                }
            }).backgroundColor(Color.parseColor("#ffffff")).titleColor(Color.parseColor("#292A2F"));
        }
        dialog_phone.show();
    }

    @Override
    public void error() {
        toast("获取数据失败，请检测您的网络连接");
    }
}