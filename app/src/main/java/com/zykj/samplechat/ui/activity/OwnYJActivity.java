package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.OwnYJPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.OwnYJView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by ninos on 2016/7/27.
 */
public class OwnYJActivity extends ToolBarActivity<OwnYJPresenter> implements OwnYJView{

    @Bind(R.id.name)EditText name;
    @Bind(R.id.content)EditText content;

    public String t;
    public String c;

    @Override
    protected CharSequence provideTitle() {
        return "意见反馈";
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tvAction.setText("提交");

    }

    @Override
    protected void action() {
        super.action();
        if(name.getText().toString().trim().equals("")){
            snb("意见反馈标题不能为空",name);
            return;
        }else if(content.getText().toString().trim().equals("")){
            snb("意见反馈内容不能为空",name);
            return;
        }

        t = name.getText().toString();
        c = content.getText().toString();

        User user = new UserUtil(getContext()).getUser();

        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);

        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","UpdateSuggestion");
//        map.put("userid",user.Id);
//        map.put("username", user.NicName);
//        map.put("title",t);
//        map.put("content",c);
//        map.put("publishtime",time);
//        map.put("userphone",user.Phone);
        String json = StringUtil.toJson(map);
        showDialog();
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.putInfo(data);
        }catch (Exception ex){
            dismissDialog();
            error();
        }

    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_yj;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public OwnYJPresenter createPresenter() {
        return new OwnYJPresenter();
    }

    @Override
    public void success() {
        dismissDialog();
        toast("您的意见已成功提交，感谢您对朋友的支持");
        finish();
    }

    @Override
    public void error() {
        dismissDialog();
        toast("您的意见反馈提交失败，请检查您的网络是否正常");

    }
}
