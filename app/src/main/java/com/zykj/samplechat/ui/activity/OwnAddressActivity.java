package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.OwnUserPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.OwnUserView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by ninos on 2016/7/8.
 */
public class OwnAddressActivity extends ToolBarActivity<OwnUserPresenter> implements OwnUserView {

    private User user;
    private User tempUser;

    @Bind(R.id.address)EditText address;

    @Override
    protected void action() {
        super.action();
        if (address.getText().toString().trim().equals("")) {
            snb("地址不能为空",address);
            return;
        }
        user = new UserUtil(this).getUser();
        tempUser = user;
       // tempUser.HomeAddress = address.getText().toString();
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","ChangeInfor");
//        map.put("userid",tempUser.Id);
//        map.put("nicname", tempUser.NicName);
//        map.put("sexuality",tempUser.Sexuality);
//        map.put("height",0);
//        map.put("weight",0);
//        map.put("address",tempUser.HomeAddress);
//        map.put("experience",0);
//        map.put("description",tempUser.Description);
        map.put("birthday","1900/01/01");
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.ChangeInfor(data);
        }catch (Exception ex){}
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tvAction.setText("保存");
        user = new UserUtil(this).getUser();

        address.setText("");

    }

    @Override
    protected CharSequence provideTitle() {
        return "更改地址";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_address;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public OwnUserPresenter createPresenter() {
        return new OwnUserPresenter();
    }

    @Override
    public void successUpload() {
        user = tempUser;
        new UserUtil(this).putUser(user);
        toast("修改成功");
        finish();
    }

    @Override
    public void errorUpload() {
        toast("修改失败，请检测您的网络或内容是否合法");
    }
}
