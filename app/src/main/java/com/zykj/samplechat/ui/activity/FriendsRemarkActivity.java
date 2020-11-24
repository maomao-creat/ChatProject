package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.zykj.samplechat.R;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.FriendsRemarkPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.FriendsRemarkView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by ninos on 2016/7/15.
 */
public class FriendsRemarkActivity extends ToolBarActivity<FriendsRemarkPresenter> implements FriendsRemarkView {

    @Bind(R.id.ur_remark)EditText ur_remark;
    private String remarkName;
    private int id;

    @Override
    protected void action() {
        super.action();
        if (ur_remark.getText().toString().trim().equals(""))
            finishActivity();
        else{
            remarkName = ur_remark.getText().toString();
            Map map = new HashMap();
            map.put("key", Const.KEY);
            map.put("uid", Const.UID);
            map.put("function", "ChangeFriendRName");
            map.put("userid", new UserUtil(this).getUserId());
            map.put("friendid", id);
            map.put("rname", remarkName);
            String json = StringUtil.toJson(map);
            try {
                String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                presenter.ChangeFriendRName(data);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tvAction.setText("保存");
        id = getIntent().getBundleExtra("data").getInt("id");
    }

    @Override
    protected CharSequence provideTitle() {
        return "备注信息";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user_remark;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public FriendsRemarkPresenter createPresenter() {
        return new FriendsRemarkPresenter();
    }

    @Override
    public void success() {
        toast("备注名保存成功");
        setResult(1112);
        finish();
    }
}
