package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rey.material.widget.Button;
import com.zykj.samplechat.R;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.WalletPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.SendYuEView;
import com.zykj.samplechat.utils.CommonUtil;
import com.zykj.samplechat.utils.ToolsUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 徐学坤 on 2018/2/9.
 */
public class BangDingZfbActivity extends ToolBarActivity<WalletPresenter> implements SendYuEView {


    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_action)
    TextView tvAction;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.xm)
    EditText xm;
    @Bind(R.id.zfbzh)
    EditText zfbzh;
    @Bind(R.id.ll_oldpwd)
    LinearLayout llOldpwd;
    @Bind(R.id.sjh)
    EditText sjh;
    @Bind(R.id.bdzh)
    Button bdzh;

    @Override
    protected CharSequence provideTitle() {
        return "绑定支付宝";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_reset_zfb;
    }


    @Override
    public void initListeners() {//初始化

    }

    @Override
    public WalletPresenter createPresenter() {
        return new WalletPresenter();
    }

    @Override
    public void success(String qian) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bdzh)
    public void onViewClicked() {
        if (CommonUtil.isEmpty(xm,this,"请输入姓名!"))
            return;
        if (CommonUtil.isEmpty(zfbzh,this,"请输入支付宝账号!"))
            return;

        HttpUtils.BindAliPay(new SubscriberRes<String>() {
            @Override
            public void onSuccess(String userBean) {
                ToolsUtils.toast(getContext(),"绑定成功");
                finish();
            }
            @Override
            public void completeDialog() {

            }
        },xm.getText().toString().trim(),zfbzh.getText().toString().trim());
    }
}
