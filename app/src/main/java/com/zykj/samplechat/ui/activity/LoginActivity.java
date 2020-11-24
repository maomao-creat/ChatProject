package com.zykj.samplechat.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;
import com.rey.material.app.Dialog;
import com.rey.material.widget.Button;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.LoginPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.LoginView;
import com.zykj.samplechat.ui.widget.MyDialog;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.ActivityUtil;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.ToolsUtils;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * *****************************************************
 * *
 * *
 * _oo0oo_                      *
 * o8888888o                     *
 * 88" . "88                     *
 * (| -_- |)                     *
 * 0\  =  /0                     *
 * ___/`---'\___                   *
 * .' \\|     |# '.                  *
 * / \\|||  :  |||# \                 *
 * / _||||| -:- |||||- \               *
 * |   | \\\  -  #/ |   |               *
 * | \_|  ''\---/''  |_/ |              *
 * \  .-\__  '-'  ___/-. /              *
 * ___'. .'  /--.--\  `. .'___            *
 * ."" '<  `.___\_<|>_/___.' >' "".          *
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |        *
 * \  \ `_.   \_ __\ /__ _/   .-` /  /        *
 * =====`-.____`.___ \_____/___.-`___.-'=====     *
 * `=---='                      *
 * *
 * *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    *
 * *
 * 佛祖保佑         永无BUG              *
 * *
 * *
 * *****************************************************
 * <p>
 * Created by ninos on 2016/6/2.
 */
public class LoginActivity extends ToolBarActivity<LoginPresenter> implements LoginView {

    @Bind(R.id.imQQ)
    ImageView imQQ;
    @Bind(R.id.imwx)
    ImageView imwx;
    @Bind(R.id.wjmm)
    TextView wjmm;
    @Bind(R.id.xyhzc)
    TextView xyhzc;
    private Dialog dialog;
    private View view;
    private LinearLayout dm_register;
    private LinearLayout dm_reset;
    private boolean canClick = false;
    private String phoneNumber;
    private String password;
    private String yqm;
    public PopupWindow window;

    @Bind(R.id.l_problem)
    TextView l_problem;
    @Bind(R.id.l_more)
    TextView l_more;
    @Bind(R.id.l_userphone)
    EditText l_userphone;
    @Bind(R.id.l_userpwd)
    EditText l_userpwd;
    @Bind(R.id.l_phone_line)
    View l_phone_line;

    @Bind(R.id.l_login)
    Button l_login;

    private User mUser;
     PlatformDb platDB;
    public void l_userphone2() {
        if (l_userphone.getText().toString().length() == 0 || l_userpwd.getText().toString().length() == 0) {
            l_login.setBackgroundResource(R.drawable.shape_2);
            l_login.setText("登录");
            canClick = false;
        } else {
            l_login.setBackgroundResource(R.drawable.shape_3);
            l_login.setText("登录");
            canClick = true;
        }
    }

    @OnFocusChange(R.id.l_userphone)
    public void l_userphone(View v, boolean hasFocus) {
        if (!hasFocus) {
            //  l_phone_line.setBackgroundColor(getResources().getColor(R.color.gray_bg_1));
        } else {
            //  l_phone_line.setBackgroundColor(getResources().getColor(R.color.green_1));
        }
    }

    @OnFocusChange(R.id.l_userpwd)
    public void l_userpwd(View v, boolean hasFocus) {
        if (!hasFocus) {
            //    l_pwd_line.setBackgroundColor(getResources().getColor(R.color.gray_bg_1));
        } else {
            //  l_pwd_line.setBackgroundColor(getResources().getColor(R.color.green_1));
        }
    }

    @OnClick(R.id.l_problem)
    public void l_problem() {
        startActivity(LoginProblemActivity.class);
        ActivityUtil.addActivity(this);
    }

    @OnClick(R.id.l_more)
    public void l_more() {//服务条款
        // showDialogMore();
        startActivity(WebViewsActivity.class);
    }

    private void authorize(String name) {
        final Platform weibo = ShareSDK.getPlatform(Wechat.NAME);
        if (weibo.isAuthValid()) {
            weibo.removeAccount(true);
        }
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        weibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                runOnUiThread(new Runnable() {
                    public void run() {
                        //sss2(arg2);
                           toast("登陆失败");
                    }
                });
                arg2.printStackTrace();

            }

            @Override
            public void onComplete(Platform arg0, int arg1, final HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                platDB = arg0.getDb();//获取数平台数据DB
                //通过DB获取各种数据 "nickname" -> "顺其自然就好"
                //"headimgurl" -> "http://thirdwx.qlogo.cn/mmopen/vi_32/vAQHG6Yhou77kQicrvXO27scAHQYK0ANGSXK0F5IoqibfibD6VINxKPSVrl8MxlGU77KQzICpBtiaTdQ9LlrlmzSlg/132"
                //"openid" -> "ovxDg1GF8MYPgBREa62H4yyogeCE"
                platDB.getToken();
                platDB.getUserGender();
                platDB.getUserIcon();
                platDB.getUserId();
                platDB.getUserName();

                // System.out.print("xxxx"+arg2.get("openid")+"---"+arg2.get("nickname")+"---"+arg2.get("headimgurl")+"\n");
                // System.out.print("xxxx"+platDB.getUserId()+"---"+platDB.getUserName()+"---"+platDB.getUserIcon()+"\n");
                runOnUiThread(new Runnable() {
                    public void run() {
                        showDialog();
                        sss2(arg2, platDB);
                        // toast(""+arg2.get("nickname")+"--"+arg2.get("openid")+"--"+platDB.getUserId());
                    }
                });

                //                    String aa = arg0.getDb().exportData();
                //                    HashMap<String, String> hxm = new Hashon().fromJson(aa);
                //                    System.out.print("xxxx"+platDB.getUserId()+"-----"+hxm.get("nickname"));
            }


            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
                runOnUiThread(new Runnable() {
                    public void run() {
                        //sss2(arg2);
                        //     toast("登陆失败2");
                    }
                });
            }
        });
        //authorize与showUser单独调用一个即可
        //  weibo.authorize();//单独授权,OnComplete返回的hashmap是空的
        weibo.SSOSetting(false);
        weibo.showUser(null);//授权并获取用户信息
        //移除授权
        //weibo.removeAccount(true);
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    @OnClick(R.id.l_login)
    public void l_login() {
        l_userphone2();
        if (!canClick)
            return;
        phoneNumber = l_userphone.getText().toString();
        password = l_userpwd.getText().toString();
        presenter.login(l_userphone, phoneNumber, password);
    }

    private void sss() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("请输入邀请码");    //设置对话框标题
        final EditText edit = new EditText(LoginActivity.this);
        builder.setView(edit);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                yqm = edit.getText().toString().trim();
                Map map2 = new HashMap();
                map2.put("key", Const.KEY);
                map2.put("uid", Const.UID);
                map2.put("function", "UserCode");
                map2.put("userid", new UserUtil(getContext()).getUserId());
                map2.put("rcode", "" + yqm);
                String json2 = StringUtil.toJson(map2);
                showDialog();
                try {
                    String data2 = AESOperator.getInstance().encrypt(json2.length() + "&" + json2);
                    presenter.yaoqing(data2);
                } catch (Exception ex) {
                    dismissDialog();
                }
                //Toast.makeText(LoginActivity.this, "你输入的是: " + edit.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(true);    //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = builder.create();  //创建对话框
        dialog.setCanceledOnTouchOutside(true); //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();
    }

    private void sss2(final HashMap<String, Object> arg2, final PlatformDb platDB) {

        //  System.out.print("xxxx"+platDB.getUserId()+"---"+platDB.getUserName()+"---"+platDB.getUserIcon()+"\n");
        HttpUtils.LoginByOtherWithInfor(new SubscriberRes<User>() {
            @Override
            public void onSuccess(User userBean) {
                dismissDialog();
                UserUtil.putUser(userBean);
                successLogin(userBean);
            }

            @Override
            public void completeDialog() {
                dismissDialog();
            }
        }, platDB.getUserId() + "", platDB.getUserName() + "", platDB.getUserIcon() + "");
    }
    public  void  sss3(  PlatformDb platDB){
        //  System.out.print("xxxx"+platDB.getUserId()+"---"+platDB.getUserName()+"---"+platDB.getUserIcon()+"\n");
        HttpUtils.LoginByOtherWithInfor(new SubscriberRes<User>() {
            @Override
            public void onSuccess(User userBean) {
                dismissDialog();
                successLogin(userBean);
            }

            @Override
            public void completeDialog() {
                dismissDialog();
            }
        }, platDB.getUserId() + "", platDB.getUserName() + "", platDB.getUserIcon() + "");
    }

    private void showDialogMore() {
        if (dialog == null)
            dialog = new Dialog(this).backgroundColor(Color.parseColor("#ffffff")).contentView(view);
        dialog.show();
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
        view = getLayoutInflater().inflate(R.layout.dialog_more_1, null);
        dm_register = (LinearLayout) view.findViewById(R.id.dm_register);
        dm_reset = (LinearLayout) view.findViewById(R.id.dm_reset);
        mUser=new User();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initListeners() {

        dm_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegisterSmsActivity.class);
                ActivityUtil.addActivity(LoginActivity.this);
                dialog.dismiss();
            }
        });

        dm_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginResetPwdActivity.class);
                ActivityUtil.addActivity(LoginActivity.this);
                dialog.dismiss();
            }
        });

    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void successLogin(final User user) {
        dismissDialog();
//        mUser=user;
        if (StringUtil.isEmpty(user.getTJUserCode())) {
            showPopwindow();
        } else {
            TUIKit.login(user.getUserCode().toLowerCase(), user.getRYToken(), new IUIKitCallBack() {
                @Override
                public void onSuccess(Object data) {
                    HashMap<String, Object> hashMap = new HashMap<>();
// 头像，mIconUrl 就是您上传头像后的 URL，可以参考 Demo 中的随机头像作为示例
                    if (!TextUtils.isEmpty(platDB.getUserIcon())) {
                        hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_FACEURL, platDB.getUserIcon());
                        hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_NICK, platDB.getUserName());
                    }
                    TIMFriendshipManager.getInstance().modifySelfProfile(hashMap, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Log.e("WeLogin", "modifySelfProfile err code = " + i + ", desc = " + s);
                        }
                        @Override
                        public void onSuccess() {
                            Log.i("WeLogin", "modifySelfProfile success");
                        }
                    });
                    startActivity(new Intent(view.getContext(), MainActivity.class));
                    finishActivity();
                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                        Toast.makeText(view.getContext(), "腾讯云链接失败", Toast.LENGTH_SHORT);
                }
            });
//            startActivity(MainActivity.class);
        }
//        if (StringUtil.isEmpty(new UserUtil(getContext()).getUser().RecommendCode)) {
//            sss();
//        } else {
//            startActivity(SplashActivity.class);
        //  }

    }

    @Override
    public void successyaoqing(String use) {
        startActivity(SplashActivity.class);
        ActivityUtil.finishActivitys();
        finish();
    }

    @Override
    public void errorLogin(String str) {
        dismissDialog();
        new UserUtil(getContext()).removeUserInfo();
        snb(str, l_login);
    }


    @OnClick({R.id.imQQ, R.id.imwx, R.id.wjmm, R.id.xyhzc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imQQ:
                showDialog();
                authorize("QQ");
                break;
            case R.id.imwx:
//                showDialog();
                // 通过代码注册你的AppKey和AppSecret
                authorize("Wechat");
                break;
            case R.id.wjmm:
                startActivity(RegisterSmstoeActivity.class);
                ActivityUtil.addActivity(LoginActivity.this);
                // ces();
                break;
            case R.id.xyhzc:
                startActivity(RegisterSmsActivity.class);
                ActivityUtil.addActivity(LoginActivity.this);
                break;
        }
    }

    void AddTJUserCode(String tjusercode) {
        showDialog();
        HttpUtils.AddTJUserCode(new SubscriberRes<String>() {
            @Override
            public void onSuccess(String userBean) {
                dismissDialog();
//                startActivity(MainActivity.class);
//                ActivityUtil.finishActivitys();
//                finish();
                sss3(platDB);
            }

            @Override
            public void completeDialog() {
                dismissDialog();
            }
        }, tjusercode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    public void dialog() {
        new MyDialog(LoginActivity.this, new MyDialog.DCListener() {
            @Override
            public void onRightBtnClick(android.app.Dialog dialog) {
                String str = ((MyDialog) dialog).getText(R.id.tv_edit);
                if (StringUtil.isEmpty(str)) {
                    ToolsUtils.toast(getContext(), "邀请码不能为空");
                } else {
                    dialog.dismiss();
                    AddTJUserCode(str);
                }
            }
        }).show();
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.ui_pop_input, null);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, true);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

//        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x38000000);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.Animation_Popup);
        // 在底部显示
        ToolsUtils.showAsDropDown(window, l_userphone, 0, 0, 0);
        final EditText tv_edit = (EditText) view.findViewById(R.id.tv_edit);

        TextView dialog_textViewID = (TextView) view.findViewById(R.id.dialog_textViewID);
        dialog_textViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        TextView dialog_textViewID1 = (TextView) view.findViewById(R.id.dialog_textViewID1);
        dialog_textViewID1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = tv_edit.getText().toString();
                if (StringUtil.isEmpty(str)) {
                    ToolsUtils.toast(getContext(), "邀请人不能为空");
                } else {
                    window.dismiss();
                    AddTJUserCode(str);
                }
            }
        });
//        LinearLayout ll_setting = (LinearLayout) view.findViewById(R.id.ll_setting);
//        ll_setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                window.dismiss();
//            }
//        });

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });
    }
}
