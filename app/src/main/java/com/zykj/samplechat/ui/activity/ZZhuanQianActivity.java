package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rey.material.widget.Button;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.ZhuanQianPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.EntityView;
import com.zykj.samplechat.utils.CommonUtil;
import com.zykj.samplechat.utils.IsZH;
import com.zykj.samplechat.utils.UserUtil;
import com.zykj.samplechat.zxing.CaptureActivity;
import com.zykj.samplechat.zxing.decoding.EncodingUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 徐学坤 on 2018/2/1.
 */
public class ZZhuanQianActivity extends ToolBarActivity<ZhuanQianPresenter> implements EntityView<Object> {
    @Bind(R.id.iv_ewm)
    ImageView iv_ewm;
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
    @Bind(R.id.tuijm)
    TextView tuijm;
    @Bind(R.id.tv_action1)
    ImageView tvAction1;
    @Bind(R.id.touxiang)
    ImageView touxiang;
    @Bind(R.id.nc)
    TextView nc;
    @Bind(R.id.bcdsj)
    Button bcdsj;
    @Bind(R.id.smewm)
    Button smewm;

    @Override
    protected CharSequence provideTitle() {
        return "我的二维码";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.ui_activity_zhuanqian;
    }

    @Override
    public void initListeners() {
        //tuijm.setText("推荐码:" + (String) data);
        //第一个参数是你输入的编码,第二个参数就是宽和高,因为生成的二维码图片是正方形,所以设置一个即可
        Bitmap grcode = EncodingUtils.createQRCode((String) "https://www.pgyer.com/newpengyou", 300, 300, null);
        //将生成的二维码显示到指定的ImageView中tAG
        iv_ewm.setImageBitmap(grcode);
        User user = new UserUtil(getContext()).getUser();
        Glide.with(getContext())
                .load(Const.getbase(user.getHeadUrl()))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(touxiang);
        nc.setText(user.getNickName());
//        if (new UserUtil(getContext()).isLogin()) {
//            //PhotoPath=http://thirdwx.qlogo.cn/mmopen/vi_32/vAQHG6Yhou77kQicrvXO27scAHQYK0ANGSXK0F5IoqibfibD6VINxKPSVrl8MxlGU77KQzICpBtiaTdQ9LlrlmzSlg/132
//        }
        tvAction1.setVisibility(View.GONE);
    }


    @Override
    public ZhuanQianPresenter createPresenter() {
        return new ZhuanQianPresenter();
    }

    @Override
    public void model(Object data) {

    }

    @Override
    public void error() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bcdsj, R.id.smewm, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bcdsj:
                Bitmap bm = ((BitmapDrawable) iv_ewm.getDrawable()).getBitmap();
                if (bm != null) {
                    if (CommonUtil.saveImageToGallery(getContext(), bm)) {
                        IsZH.getToast(getContext(), "保存成功!");  //吐司
                    } else {
                        IsZH.getToast(getContext(), "保存失败!");  //吐司
                    }
                }
                break;
            case R.id.smewm:
                startActivityForResult(CaptureActivity.class, 17);
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 201) {//二维码回调
            Toast.makeText(getContext(), "识别" + data.getStringExtra("jg"), Toast.LENGTH_SHORT).show();
        }
    }

}
