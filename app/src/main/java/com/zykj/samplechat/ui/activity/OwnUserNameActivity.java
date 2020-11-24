package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.OwnUserPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.OwnUserView;
import com.zykj.samplechat.utils.ToolsUtils;
import com.zykj.samplechat.utils.UserUtil;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ninos on 2016/7/6.
 */
public class OwnUserNameActivity extends ToolBarActivity<OwnUserPresenter> implements OwnUserView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    private User user;
    private User tempUser;

    @Bind(R.id.username)
    EditText username;

    @Override
    protected void action() {
        super.action();
        final String name = username.getText().toString().trim();
        if (name.trim().equals("")) {
            snb("名字不能为空", username);
            return;
        } else if (Pattern.compile("^[0-9]+$").matcher(name).matches()) {
            snb("名字不能为纯数字", username);
            return;
        }
        UpdateUserinfo(imgBack,-1,name,"");
//        tempUser.setNickName(username.getText().toString());
//        ;
//        Map map = new HashMap();
//        map.put("function", "" + "UpdateNickName");
//        map.put("userid", "" + new UserUtil(getContext()).getUserId2());
//        map.put("newnickname", "" + name);
//        showDialog();
//        NR.posts("WebService/UserService.asmx/Entry", map, new StringCallback() {
//
//            @Override
//            public void onError(Request request, Exception e) {
//                dismissDialog();
//                //  LogUtils.i("xxxxx222  :", OwnUserNameActivity" +e);  //输出测试
//                IsZH.getToast(OwnUserNameActivity.this, "服务器错误");  //吐司
//            }
//
//            @Override
//            public void onResponse(String s) {
//                dismissDialog();
//                //  LogUtils.i("xxxxx", "" +s);  //输出测试
//                if (NRUtils.getYse(OwnUserNameActivity.this, s)) {
//                    IsZH.getToast(OwnUserNameActivity.this, "姓名修改成功");  //吐司
//                    // MyYuEBean mye = NRUtils.getData(s,MyYuEBean.class);
//                    //finish();
//                    Intent inte = new Intent();
//                    inte.putExtra("name", name);
//                    user = tempUser;
//                    new UserUtil(OwnUserNameActivity.this).putUser(user);
//                    setResult(201, intent);//关闭界面回调
//                    finish();
//                }
//            }

//        });
    }

    public void UpdateUserinfo(View rootView,int xb, String nickname, String realname){
        HttpUtils.UpdateUserInfo(new SubscriberRes<String>(rootView) {
            @Override
            public void onSuccess(String userBean) {
                ToolsUtils.toast(getContext(),"修改成功");
                finish();
            }

            @Override
            public void completeDialog() {
            }
        }, xb,nickname,realname);
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

        username.setText(user.getNickName());
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected CharSequence provideTitle() {
        return "更改名字";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_username;
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
        toast("修改失败，请检测您的网络或名字是否合法");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
