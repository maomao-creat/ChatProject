package com.zykj.samplechat.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.WalletPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.SendYuEView;
import com.zykj.samplechat.utils.CleanMessageUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by 徐学坤 on 2018/2/9.
 */
public class SheZhiActivity extends ToolBarActivity<WalletPresenter> implements SendYuEView {

    @Bind(R.id.ll_zhgl)
    LinearLayout llZhgl;
    @Bind(R.id.ll_xgmm)
    LinearLayout llXgmm;
    @Bind(R.id.ll_qkltjl)
    LinearLayout llQkltjl;
    @Bind(R.id.ll_qchc)
    LinearLayout llQchc;
    @Bind(R.id.o_btn)
    LinearLayout oBtn;

    @Override
    protected CharSequence provideTitle() {
        return "设置";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_sz;
    }


    @Override
    public void initListeners() {//初始化
//        Map map = new HashMap();
//        map.put("key", Const.KEY);
//        map.put("uid", Const.UID);
//        map.put("function", "getMoneyRemainder");
//        map.put("userId", new UserUtil(getContext()).getUserId());
//        String json = StringUtil.toJson(map);
//        try {
//            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//            presenter.getyue(data);
//        } catch (Exception ex) {
//            dismissDialog();
//        }
    }

    @Override
    public WalletPresenter createPresenter() {
        return new WalletPresenter();
    }

    @Override
    public void success(String qian) {

    }


    @OnClick({R.id.ll_zhgl, R.id.ll_xgmm, R.id.ll_qkltjl, R.id.ll_qchc, R.id.o_btn, R.id.ll_zfmm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_zhgl:
                startActivity(OwnInfoActivity.class);
                break;
            case R.id.ll_xgmm:
                startActivity(LoginResetPwdActivity.class);
                break;
                case R.id.ll_zfmm://支付密码
                startActivity(LoginResetPwdzfActivity.class);
                break;
            case R.id.ll_qkltjl:
                ltjl();
                break;
            case R.id.ll_qchc:
                qkhc();
                break;
            case R.id.o_btn:
                showExitDialog02();
                break;
        }
    }

    // 带“是”和“否”的提示框
    private void showExitDialog02() {
        new AlertDialog.Builder(this)
                .setTitle("退出登录")
                .setMessage("是否退成登录？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //登出
                        TIMManager.getInstance().logout(new TIMCallBack() {
                            @Override
                            public void onError(int code, String desc) {

                                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                                //错误码 code 列表请参见错误码表
                                Log.d("logout", "logout failed. code: " + code + " errmsg: " + desc);
                            }

                            @Override
                            public void onSuccess() {
                                setResult(200);
                                finishActivity();
                                //登出成功
                            }
                        });



                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    // 带“是”和“否”的提示框
    private void ltjl() {
        new AlertDialog.Builder(this)
                .setTitle("清空聊天记录")
                .setMessage("是否清空聊天记录？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    // 带“是”和“否”的提示框
    private void qkhc() {
        new AlertDialog.Builder(this)
                .setTitle("清空缓存")
                .setMessage("是否清空缓存？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CleanMessageUtil.clearAllCache(getApplicationContext());
                    }
                })
                .setNegativeButton("取消", null)
                .show();
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
