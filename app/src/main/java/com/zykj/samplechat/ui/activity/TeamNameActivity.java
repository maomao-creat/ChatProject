package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.zykj.samplechat.R;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.TeamNamePresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.TeamNameView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by ninos on 2016/7/14.
 */
public class TeamNameActivity extends ToolBarActivity<TeamNamePresenter> implements TeamNameView {

    private String teamName;
    private String url;
    private int id;

    @Bind(R.id.username)EditText username;

    @Override
    protected CharSequence provideTitle() {
        return "修改群名称";
    }

    @Override
    public TeamNamePresenter createPresenter() {
        return new TeamNamePresenter();
    }


    @Override
    protected void action() {
        super.action();
        if (username.getText().toString().trim().equals("")) {
            snb("群名称不能为空",username);
            return;
        }
        teamName = username.getText().toString();
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","EditTeam");
        map.put("teamid",id);
        map.put("name", teamName);
        map.put("imagepath", url);
        map.put("userid",new UserUtil(this).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.EditTeam(data);
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
        id = getIntent().getBundleExtra("data").getInt("id");
        teamName = getIntent().getBundleExtra("data").getString("teamName");
        url = getIntent().getBundleExtra("data").getString("url");

        username.setText(teamName);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_team_name;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void successUpload() {
        toast("修改成功");
        finish();
    }

    @Override
    public void errorUpload() {
        toast("修改失败，请检测您的网络或名字是否合法");
    }
}
