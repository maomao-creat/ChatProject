package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.ChaiHongbao;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.GetHongbaoPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.GetHongbaoView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.CommonUtil;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by ninos on 2017/8/23.
 */

public class GetHongbaoActivity extends ToolBarActivity<GetHongbaoPresenter> implements GetHongbaoView {

    @Bind(R.id.gh_img)
    ImageView gh_img;
    @Bind(R.id.gh_name)
    TextView gh_name;
    @Bind(R.id.gh_content)
    TextView gh_content;
    @Bind(R.id.gh_type)
    TextView gh_type;
    @Bind(R.id.gh_more)
    TextView gh_more;

    private String hongbaoId;
    private String name;
    private String photo;
    private String content;
    private String amount;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_get_hongbao;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        CommonUtil.setWindowStatusBarColor(GetHongbaoActivity.this,R.color.bg_red);
        name = getIntent().getBundleExtra("data").getString("name");
        photo = getIntent().getBundleExtra("data").getString("photo");
        content = getIntent().getBundleExtra("data").getString("content");
        amount = getIntent().getBundleExtra("data").getString("amount");
        hongbaoId = getIntent().getBundleExtra("data").getString("hongbaoId");
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "OpenSingleRedPacket");
        map.put("userid", new UserUtil(getContext()).getUserId());
        map.put("packetid", hongbaoId);
        String json = StringUtil.toJson(map);
        showDialog();
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.OpenSingleRedPacket(data);
        } catch (Exception ex) {
            dismissDialog();
        }
    }

    @Override
    public void initListeners() {
        gh_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public GetHongbaoPresenter createPresenter() {
        return new GetHongbaoPresenter();
    }

    @Override
    protected CharSequence provideTitle() {
        return "红包";
    }

    @Override
    public void success(ChaiHongbao chaiHongbao) {
        dismissDialog();
        if (!chaiHongbao.PhotoPath.contains("http")) {
            Glide.with(getContext())
                    .load(Const.getbase(chaiHongbao.PhotoPath))
                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
                    .into(gh_img);
        }else{
            Glide.with(getContext())
                    .load(chaiHongbao.PhotoPath)
                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
                    .into(gh_img);
        }
        gh_name.setText(chaiHongbao.NicName);
        gh_content.setText(chaiHongbao.Description);
        gh_type.setText(chaiHongbao.State.equals("0")?"红包金额"+chaiHongbao.Amount+"元，等待对方领取":"红包已领取");
    }
}
