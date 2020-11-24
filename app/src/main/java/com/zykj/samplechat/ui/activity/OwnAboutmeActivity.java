package com.zykj.samplechat.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.http.okhttp.callback.StringCallback;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.OwnUserPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.OwnUserView;
import com.zykj.samplechat.utils.IsZH;
import com.zykj.samplechat.utils.NR;
import com.zykj.samplechat.utils.NRUtils;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.ToolsUtils;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import okhttp3.Request;

/**
 * Created by ninos on 2016/7/6.
 */
public class OwnAboutmeActivity extends ToolBarActivity<OwnUserPresenter> implements OwnUserView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    private User user;
    private User tempUser;

    @Bind(R.id.aboutme)
    EditText aboutme;
    @Bind(R.id.a_size)
    TextView a_size;

    @OnTextChanged(R.id.aboutme)
    public void aboutme(CharSequence s, int start, int before, int count) {
        a_size.setText(10 - aboutme.getText().toString().length() + "");
    }

    @Override
    protected void action() {
        super.action();
        if (aboutme.getText().toString().trim().equals("")) {
            snb("名字不能为空", aboutme);
            return;
        }
//        setxb(sex, aboutme.getText().toString().trim(), "");
        UpdateUserinfo(imgBack,-1,"",aboutme.getText().toString().trim());
//        user = new UserUtil(this).getUser();
//        tempUser = user;
//        //tempUser.Description = aboutme.getText().toString();
//        Map map = new HashMap();
//        map.put("key", Const.KEY);
//        map.put("uid",Const.UID);
//        map.put("function","ChangeInfor");
////        map.put("userid",tempUser.Id);
////        map.put("nicname", tempUser.NicName);
////        map.put("sexuality",tempUser.Sexuality);
////        map.put("height",0);
////        map.put("weight",0);
////        map.put("address",tempUser.HomeAddress);
////        map.put("experience",0);
////        map.put("description",tempUser.Description);
//        map.put("birthday","1900/01/01");
//        String json = StringUtil.toJson(map);
//        try {
//            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//            presenter.ChangeInfor(data);
//        }catch (Exception ex){}
    }

    @Override
    public boolean canAction() {
        return true;
    }

    String sex = "1";

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tvAction.setText("保存");
        user = new UserUtil(this).getUser();
        if(StringUtil.isEmpty(getIntent().getStringExtra("name"))){
            aboutme.setText("未填写" );
        }else{
            aboutme.setText("" + getIntent().getStringExtra("name"));
        }
        sex = getIntent().getStringExtra("sex");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
    protected CharSequence provideTitle() {
        return "真实姓名";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_aboutme;
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
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(user.getId() + "", user.getNickName(), Uri.parse(Const.getbase(user.getMobile()))));
        finish();
    }

    @Override
    public void errorUpload() {
        toast("修改失败，请检测您的网络或内容是否合法");
    }

    void setxb(String xb, String realname, String nickname) {
        Map map = new HashMap();
        map.put("function", "" + "UpdateUserInfo");
        map.put("userid", "" + new UserUtil(getContext()).getUserId2());
        map.put("nickname", "" + nickname);
        map.put("sex", "" + xb);
        map.put("realname", "" + realname);
        showDialog();
        NR.posts("WebService/UserService.asmx/Entry", map, new StringCallback() {

            @Override
            public void onError(Request request, Exception e) {
                dismissDialog();
                //  LogUtils.i("xxxxx222  :", "" +e);  //输出测试
                IsZH.getToast(OwnAboutmeActivity.this, "服务器错误");  //吐司
            }

            @Override
            public void onResponse(String s) {
                dismissDialog();
                //  LogUtils.i("xxxxx", "" +s);  //输出测试
                if (NRUtils.getYse(OwnAboutmeActivity.this, s)) {
                    IsZH.getToast(OwnAboutmeActivity.this, "修改成功!");  //吐司
                    // MyYuEBean mye = NRUtils.getData(s,MyYuEBean.class);
                    finish();

                }
            }

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
