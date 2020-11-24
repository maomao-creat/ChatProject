package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.model.agerenBean;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.WalletPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.SendYuEView;
import com.zykj.samplechat.utils.UserUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 徐学坤 on 2018/2/9.
 */
public class ZWalletActivity extends ToolBarActivity<WalletPresenter> implements SendYuEView {
    @Bind(R.id.tv_yue)
    TextView tv_yue;
    private String qianshu = "0";

    @Override
    protected CharSequence provideTitle() {
        return "钱包";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.ui_activity_wallet;
    }

    @Override
    public void initListeners() {//初始化
        huoqsj();
//        Map map = new HashMap();
//        map.put("key", Const.KEY);
//        map.put("uid", Const.UID);
//        map.put("function", "getMoneyRemainder");
//        map.put("userId", new UserUtil(getContext()).getUserId());
//        String json = StringUtil.toJson(map);
//        try {
//            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//                presenter.getyue(data);
//        } catch (Exception ex) {
//            dismissDialog();
//        }
    }

    @OnClick({R.id.ll_chongzhi, R.id.ll_yue, R.id.ll_tixian, R.id.ll_czjl, R.id.ll_txjl, R.id.ll_qt})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_chongzhi://充值
//                Intent Intent2=new Intent(this,CongZhiActivity.class);
//                startActivity(Intent2);
                //   IsZH.getToast(this, "请联系客服!");  //吐司
//                QuanJu.getQuanJu().gokefu(this);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.zhangtuntun.love/Pay/Pay.html?uid="+UserUtil.getUser().getId()));
                startActivity(intent);
                break;
            case R.id.ll_yue://余额

                break;
            case R.id.ll_tixian://提现TiXianActivity
                Intent Intent1 = new Intent(this, TiXianActivity.class);
                Intent1.putExtra("bs", "" + qianshu);
                startActivity(Intent1);
                break;
            case R.id.ll_czjl://转账记录
//                JiLuActivity
                Intent Intent = new Intent(this, JiLuTwoActivity.class);
                Intent.putExtra("bs", "1");
                startActivity(Intent);
                break;
            case R.id.ll_txjl://提现记录
                Intent Intent5 = new Intent(this, TiXianJiLuActivity.class);
                Intent5.putExtra("bs", "2");
                startActivity(Intent5);
                break;
                case R.id.ll_qt://其他记录
                Intent Intent51 = new Intent(this, QiTaJiLuActivity.class);
                Intent51.putExtra("bs", "2");
                startActivity(Intent51);
                break;
        }
    }

    @Override
    public WalletPresenter createPresenter() {
        return new WalletPresenter();
    }

    @Override
    public void success(String qian) {
        qianshu = qian;
        int biaoshi = getIntent().getIntExtra("biaoshi", 0);
        if (biaoshi == 1) {
            Intent Intent1 = new Intent(this, TiXianActivity.class);
            Intent1.putExtra("bs", "" + qian);
            startActivity(Intent1);
            finish();
        }
        tv_yue.setText("￥" + qian);
    }

    agerenBean mye;

    void huoqsj() {
        HttpUtils.UserInfo(new SubscriberRes<User>() {
            @Override
            public void onSuccess(User user) {
                tv_yue.setText("￥" + user.getAmount());
                qianshu = user.getAmount() + "";
            }

            @Override
            public void completeDialog() {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
