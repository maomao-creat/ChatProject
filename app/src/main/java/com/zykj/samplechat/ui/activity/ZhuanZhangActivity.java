package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.TeamBean;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.WalletZhuanZhangPresenter;
import com.zykj.samplechat.presenter.base.HotWeiXinBean;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.SendZhuanZhangView;
import com.zykj.samplechat.utils.StringUtil;

import butterknife.Bind;
import butterknife.OnClick;

//activity_cong_zhi
public class ZhuanZhangActivity extends ToolBarActivity<WalletZhuanZhangPresenter> implements SendZhuanZhangView {


    @Bind(R.id.tv_jine)
    EditText tvJine;
    @Bind(R.id.queding)
    Button queding;
    private String fid="";
    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        String id= getIntent().getStringExtra("id");
        HttpUtils.GetTeam(new SubscriberRes<TeamBean>() {
            @Override
            public void onSuccess(TeamBean teamBean) {
                fid=teamBean.Id;
            }
            @Override
            public void completeDialog() {
                dismissDialog();
            }
        },id,"1");
    }

    @Override
    public void success(final HotWeiXinBean qian) {

    }


    @Override
    public void zsuccess() {
        Intent intent = new Intent();
        intent.putExtra("content","转账"+tvJine.getText().toString().trim());
        intent.putExtra("extra",""+tvJine.getText().toString().trim());
        setResult(200,intent);//关闭界面回调
        finish();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_zhuan_zhang;
    }

    @Override
    public void initListeners() {//初始化
    }

    @Override
    public WalletZhuanZhangPresenter createPresenter() {
        return new WalletZhuanZhangPresenter();
    }

    @Override
    protected CharSequence provideTitle() {
        return "转账";
    }


    @OnClick(R.id.queding)
    public void onViewClicked() {
        queding.setEnabled(false);
        hideSoftMethod(tvJine);
//        String id= getIntent().getStringExtra("id");
        String jine = tvJine.getText().toString().trim();
        if(!StringUtil.isEmpty(jine)){
            presenter.getzhuanzhang(fid,jine);
//            Map map = new HashMap();
//            map.put("key", Const.KEY);
//            map.put("uid", Const.UID);
//            map.put("function", "Transfer");
//            map.put("fromuserid", new UserUtil(getContext()).getUserId2());
//            map.put("touserid",id);
//            map.put("money",""+tvJine.getText().toString().trim());
//
//            String json = StringUtil.toJson(map);
//            try {
//                String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//             presenter.getzhuanzhang(id,tvJine.getText().toString().trim());
//            } catch (Exception ex) {
//                dismissDialog();
//                queding.setEnabled(true);
//            }
        }
    }
}
