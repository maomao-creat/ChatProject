package com.zykj.samplechat.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rey.material.app.Dialog;
import com.rey.material.widget.Button;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Login;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.LoginExistPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.LoginExistView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.ActivityUtil;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 ******************************************************
 *                                                    *
 *                                                    *
 *                       _oo0oo_                      *
 *                      o8888888o                     *
 *                      88" . "88                     *
 *                      (| -_- |)                     *
 *                      0\  =  /0                     *
 *                    ___/`---'\___                   *
 *                  .' \\|     |# '.                  *
 *                 / \\|||  :  |||# \                 *
 *                / _||||| -:- |||||- \               *
 *               |   | \\\  -  #/ |   |               *
 *               | \_|  ''\---/''  |_/ |              *
 *               \  .-\__  '-'  ___/-. /              *
 *             ___'. .'  /--.--\  `. .'___            *
 *          ."" '<  `.___\_<|>_/___.' >' "".          *
 *         | | :  `- \`.;`\ _ /`;.`/ - ` : | |        *
 *         \  \ `_.   \_ __\ /__ _/   .-` /  /        *
 *     =====`-.____`.___ \_____/___.-`___.-'=====     *
 *                       `=---='                      *
 *                                                    *
 *                                                    *
 *     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    *
 *                                                    *
 *               佛祖保佑         永无BUG              *
 *                                                    *
 *                                                    *
 ******************************************************
 *
 * Created by ninos on 2016/6/2.
 *
 */
public class LoginExistActivity extends ToolBarActivity<LoginExistPresenter> implements LoginExistView {
    private static final String TAG = "LoginExistActivity";
    private Dialog dialog;
    private Dialog dialogQuite;
    private View view;
    private LinearLayout dm_anthor;
    private LinearLayout dm_register;
    private LinearLayout dm_reset;
    private Login user;
    private String phoneNumber;
    private String password;
    private boolean canClick=false;
    private int state=0;
    private String alert = "";

    @Bind(R.id.l_problem)TextView l_problem;
    @Bind(R.id.l_more)TextView l_more;
    @Bind(R.id.le_phone)TextView le_phone;
    @Bind(R.id.le_pwd)EditText le_pwd;
    @Bind(R.id.le_userimg)ImageView le_userimg;
    @Bind(R.id.le_btn)Button le_btn;

    @OnTextChanged(R.id.le_pwd)
    public void le_pwd(CharSequence s, int start, int before, int count){
        if(le_pwd.getText().toString().length()==0) {
            le_btn.setBackgroundResource(R.drawable.shape_2);
            le_btn.setText("登录");
            canClick = false;
        } else {
            le_btn.setBackgroundResource(R.drawable.shape_3);
            le_btn.setText("登录");
            canClick = true;
        }
    }

    @OnClick(R.id.le_btn)
    public void le_btn(){
        if(!canClick)
            return;
        password = le_pwd.getText().toString();
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","Login");
        map.put("username",phoneNumber);
        map.put("password",password);
        String json = StringUtil.toJson(map);
        showDialog();
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.Login(data);
        }catch (Exception ex){
            dismissDialog();
        }
    }

    @OnClick(R.id.l_problem)
    public void l_problem(){
        startActivity(LoginProblemActivity.class);
    }

    @OnClick(R.id.l_more)
    public void l_more(){
        showDialogMore();
    }

    private void showDialogMore(){
        if(dialog == null)
            dialog = new Dialog(this).backgroundColor(Color.parseColor("#ffffff")).contentView(view);
        dialog.show();
    }

    private void showDialogQuite() {
        if (dialogQuite == null){
            dialogQuite = new Dialog(this);
            dialogQuite.backgroundColor(Color.parseColor("#ffffff"));
            dialogQuite.title(state==4?"您的账号已在其他设备登录,如果不是您本人操作，请及时更换密码。":alert);
            dialogQuite.positiveAction("确定");
            dialogQuite.positiveActionClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogQuite.dismiss();
                }
            });
        }
        dialogQuite.cancelable(false);
        dialogQuite.show();
    }

    @Override
    protected CharSequence provideTitle() {
        return "登录";
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        presenter.getVersion();
        imgBack.setVisibility(View.GONE);
        try {
            state = getIntent().getBundleExtra("data").getInt("state");
            alert = getIntent().getBundleExtra("data").getString("alert");
        }catch (Exception ex){
            state = 0;alert = "";
        }


        if(state >= 4 && state <= 6){
            if(state != 6)
                showDialogQuite();
            //mHandler.sendMessage(mHandler.obtainMessage(1001, "alias_0"));
        }

        view = getLayoutInflater().inflate(R.layout.dialog_more, null);
        dm_anthor = (LinearLayout)view.findViewById(R.id.dm_anthor);
        dm_register = (LinearLayout)view.findViewById(R.id.dm_register);
        dm_reset = (LinearLayout)view.findViewById(R.id.dm_reset);

        user = new UserUtil(this).getLogin();
        le_phone.setText(user.userName);
        if(StringUtil.isEmpty(user.userName)){
            startActivity(LoginActivity.class);
            ActivityUtil.addActivity(LoginExistActivity.this);
            if(dialog!=null){
                dialog.dismiss();
            }
        }
        phoneNumber = user.userName;
        Glide.with(getContext())
                .load(Const.getbase( user.userImage))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(le_userimg);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login_exist;
    }

    @Override
    public void initListeners() {

        dm_anthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.class);
                ActivityUtil.addActivity(LoginExistActivity.this);
                dialog.dismiss();
            }
        });

        dm_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegisterSmsActivity.class);
                ActivityUtil.addActivity(LoginExistActivity.this);
                dialog.dismiss();
            }
        });

        dm_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginResetPwdActivity.class);
                ActivityUtil.addActivity(LoginExistActivity.this);
                dialog.dismiss();
            }
        });

    }

    @Override
    public LoginExistPresenter createPresenter() {
        return new LoginExistPresenter();
    }

    @Override
    public void successLogin(User user) {
        dismissDialog();
        UserUtil uu = new UserUtil(getContext());
        uu.putUser(user);
        uu.putLogin(new Login(user.getMobile(),user.getHeadUrl()));
        if(state != 8)
            startActivity(SplashActivity.class);
        finish();
    }

    @Override
    public void errorLogin(String str) {
        dismissDialog();
        snb(str, le_btn);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 返回按钮
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示").setMessage("您确定退出当前应用").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        // 判断是否存在临时创建的文件
                        File temp_file = new File(Environment.getExternalStorageDirectory() + File.separator + "heyi_dir");
                        if (temp_file.exists()) {
                            File[] file_detail = temp_file.listFiles();
                            for (File file_del : file_detail) {
                                file_del.delete();
                            }
                            temp_file.delete();
                        }
                    } catch (Exception e) {
                    }
                    System.exit(0);
                }
            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    dialog.dismiss();
                }

            }).show();
        }
        return super.onKeyDown(keyCode,event);
    }
}
