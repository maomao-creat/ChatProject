package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.WanJiadPresenter;
import com.zykj.samplechat.presenter.WanJiasBean;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.OwnWanJiaView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ninos on 2016/7/6.
 */
public class WanJiadActivity extends ToolBarActivity<WanJiadPresenter> implements OwnWanJiaView {

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
    @Bind(R.id.touxiang)
    ImageView touxiang;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.zsr)
    TextView zsr;
    @Bind(R.id.zzc)
    TextView zzc;
    @Bind(R.id.ll_jiaoyi)
    LinearLayout llJiaoyi;
    private User user;
    private User tempUser;

    @Bind(R.id.username)
    EditText username;

    @Override
    protected void action() {
        super.action();
    }

    @Override
    public boolean canAction() {
        return false;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        int code = getIntent().getIntExtra("code",0);
        String name1 =getIntent().getStringExtra("name");

        name.setText(""+name1);
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "getTwoDaysRevenueById");
        map.put("subUserId", code);
        map.put("userId",  new UserUtil(getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.WanJia(data);
        } catch (Exception ex) {
        }
    }

    @Override
    protected CharSequence provideTitle() {
        return "消费";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_wanjiad;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public WanJiadPresenter createPresenter() {
        return new WanJiadPresenter();
    }

    @Override
    public void successUpload(WanJiasBean wjb) {
        zzc.setText("￥"+(wjb.getRevenue1()));
        zsr.setText("￥"+(wjb.getRevenue2()));
        String url =getIntent().getStringExtra("url");
        Glide.with(WanJiadActivity.this)
                .load(Const.getbase( url))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(touxiang);
    }

    @Override
    public void errorUpload() {
      //  toast("修改失败，请检测您的网络或名字是否合法");
    }


    @OnClick(R.id.ll_jiaoyi)
    public void onViewClicked() {
        toast("修改失败，请检测您的网络或名字是否合法");
    }
}
