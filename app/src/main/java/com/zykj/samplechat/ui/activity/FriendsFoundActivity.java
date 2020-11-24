package com.zykj.samplechat.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.app.Dialog;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.sshyBean;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.FriendsFoundPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.FriendsFoundView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.ActivityUtil;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by ninos on 2016/7/7.
 */
public class FriendsFoundActivity extends ToolBarActivity<FriendsFoundPresenter> implements FriendsFoundView {

    private String phoneNumber;
    private Dialog dialog;

    @Bind(R.id.f_phone)EditText f_phone;
    @Bind(R.id.uf_found)TextView uf_found;
    @Bind(R.id.uf_foundrl)RelativeLayout uf_foundrl;

    @OnTextChanged(R.id.f_phone)
    public void f_phone(CharSequence s, int start, int before, int count){
        if(f_phone.getText().toString().length()==0) {
            uf_foundrl.setVisibility(View.INVISIBLE);
            uf_found.setText("");
        } else {
            uf_foundrl.setVisibility(View.VISIBLE);
            uf_found.setText(f_phone.getText().toString());
        }
    }

    @OnClick(R.id.uf_foundrl)
    public void uf_foundrl(){
        phoneNumber = uf_found.getText().toString();
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","SearchFriend");
        map.put("search",phoneNumber);
        map.put("userid",""+new UserUtil(getContext()).getUserId2());
        String json = StringUtil.toJson(map);
        showDialog();
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.SearchFriendByPhone(data);
        }catch (Exception ex){
            dismissDialog();
        }


//        Map map = new HashMap();
//        map.put("function",""+"SearchFriend");
//        map.put("search",""+phoneNumber);
//        showDialog();
//        NR.posts("WebService/UserService.asmx/Entry",map,new StringCallback(){
//
//            @Override
//            public void onError(Request request, Exception e) {
//                dismissDialog();
//                //  LogUtils.i("xxxxx222  :", "" +e);  //输出测试
//                IsZH.getToast(FriendsFoundActivity.this, "服务器错误");  //吐司
//            }
//
//            @Override
//            public void onResponse(String s) {
//                dismissDialog();
//                  LogUtils.i("xxxxx", "" +s);  //输出测试
//                if(NRUtils.getYse(FriendsFoundActivity.this,s)) {
//                    IsZH.getToast(FriendsFoundActivity.this, "成功");  //吐司
//                    sshyBean mye = NRUtils.getData(s,sshyBean.class);
//                    //finish();
//                }else{
//                    showDialogMore();
//                }
//            }
//
//        });
    }

    private void showDialogMore(){
        if(dialog == null)
            dialog = new Dialog(this).backgroundColor(Color.parseColor("#ffffff")).contentView(R.layout.dialog_nouser).positiveAction("确定").positiveActionClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        dialog.show();
    }

    @Override
    protected CharSequence provideTitle() {
        return "";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user_found;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public FriendsFoundPresenter createPresenter() {
        return new FriendsFoundPresenter();
    }

    @Override
    public void successFound(sshyBean friend) {//这里是获取数据成的地方
        dismissDialog();
        ActivityUtil.addActivity(this);
        startActivity(FriendsInfoActivity.class, new Bun().putSerializable("friend",friend).putString("bs","1").ok());
    }

    @Override
    public void errorFound() {
        dismissDialog();
        showDialogMore();
    }
}
