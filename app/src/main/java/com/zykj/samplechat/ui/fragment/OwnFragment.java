package com.zykj.samplechat.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.rey.material.app.Dialog;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.About;
import com.zykj.samplechat.model.Login;
import com.zykj.samplechat.model.QuanJu;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.OwnPresenter;
import com.zykj.samplechat.ui.activity.BangDingZfbActivity;
import com.zykj.samplechat.ui.activity.LoginActivity;
import com.zykj.samplechat.ui.activity.MyShouYiActivity;
import com.zykj.samplechat.ui.activity.OwnInfoActivity;
import com.zykj.samplechat.ui.activity.SheZhiActivity;
import com.zykj.samplechat.ui.activity.ZWalletActivity;
import com.zykj.samplechat.ui.activity.ZZhuanQianActivity;
import com.zykj.samplechat.ui.activity.base.ToolBarFragment;
import com.zykj.samplechat.ui.activity.mYxiaxianActivity;
import com.zykj.samplechat.ui.view.OwnView;
import com.zykj.samplechat.utils.ImageUtil;
import com.zykj.samplechat.utils.UserUtil;
import com.zykj.samplechat.zxing.CaptureActivity;

import java.util.Hashtable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.rong.imkit.RongIM;

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
public class OwnFragment extends ToolBarFragment<OwnPresenter> implements OwnView {

    @Bind(R.id.tzxxnr)
    TextView tzxxnr;
    private LinearLayout dm_register;
    private LinearLayout dm_reset;
    private View view;
    private Dialog dialog;
    private Dialog dialog_phone;
    private User user;
    private int state = 0;

    public PopupWindow window;
    Bitmap bitmap;
    private int QR_WIDTH = 800, QR_HEIGHT = 800;
    public String url="http://www.zhangtuntun.love/Share/SHare.html";
    public LinearLayout ll_save;

    @Bind(R.id.o_btn)
    LinearLayout o_btn;
    @Bind(R.id.ll_wallet)
    LinearLayout ll_wallet;
    @Bind(R.id.ll_jilu)

    LinearLayout ll_jilu;
    @Bind(R.id.ll_zhongjiang)
    LinearLayout ll_zhongjiang;
    @Bind(R.id.ll_zhonglei)
    LinearLayout ll_zhonglei;
    @Bind(R.id.ll_tuijianma)
    LinearLayout ll_tuijianma;
    @Bind(R.id.ll_zhuanqina)
    LinearLayout ll_zhuanqina;
    @Bind(R.id.ll_wanjia)
    LinearLayout ll_wanjia;
    @Bind(R.id.own_info)
    RelativeLayout own_info;
    @Bind(R.id.o_userimg)
    ImageView o_userimg;
    @Bind(R.id.o_username)
    TextView o_username;
    @Bind(R.id.o_usernumber)
    TextView o_usernumber;
    @Bind(R.id.tv_money)
    TextView tv_money;
    @Bind(R.id.tv_tuijianma)
    TextView tv_tuijianma;

    @OnClick(R.id.own_info)//个人中心
    public void own_info() {
       // startActivity(OwnInfoActivity.class);
        Intent inttt = new Intent(getContext(),OwnInfoActivity.class);
        startActivityForResult(inttt,204);
    }

    @OnClick(R.id.o_btn)
    public void o_btn() {
        showDialogMore();
    }

    @Override
    protected void initThings(View view) {
        super.initThings(view);
        this.view = getActivity().getLayoutInflater().inflate(R.layout.dialog_exit, null);
        createQRImage(url);
        dm_register = (LinearLayout) this.view.findViewById(R.id.dm_register);
        dm_reset = (LinearLayout) this.view.findViewById(R.id.dm_reset);

    }

    @Override
    public void onResume() {
        super.onResume();
        user = new UserUtil(getContext()).getUser();
        tzxxnr.setText(QuanJu.getQuanJu().getTzxx());
        if (new UserUtil(getContext()).isLogin()) {
            //PhotoPath=http://thirdwx.qlogo.cn/mmopen/vi_32/vAQHG6Yhou77kQicrvXO27scAHQYK0ANGSXK0F5IoqibfibD6VINxKPSVrl8MxlGU77KQzICpBtiaTdQ9LlrlmzSlg/132
            Glide.with(getContext())
                    .load(Const.getbase(user.getHeadUrl()))
                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
                    .into(o_userimg);
            o_username.setText(user.getNickName());
            o_usernumber.setText("社交号：" + user.getUserCode());
            // tv_money.setText(user.Credit+"");
            tv_tuijianma.setText("" + user.getTJUserCode());
            tv_tuijianma.setVisibility(View.GONE);
            //  o_usernumber.setText("社交ID：" + user.getId());
        }
    }

    private void showDialogMore() {
        if (dialog == null)
            dialog = new Dialog(getContext()).backgroundColor(Color.parseColor("#ffffff")).contentView(view);
        dialog.show();
    }

    @Override
    protected String provideTitle() {
        return "我";
    }

    @Override
    public void initListeners() {
        dm_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.class);
                new UserUtil(getContext()).removeUserInfo();
                RongIM.getInstance().logout();
                finishActivity();
                dialog.dismiss();
            }
        });
        dm_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @OnClick({R.id.ll_wallet, R.id.ll_jilu, R.id.ll_tuijianma, R.id.ll_zhuanqina, R.id.ll_wanjia
            , R.id.ll_setting, R.id.own_info, R.id.o_btn, R.id.ll_zhongjiang, R.id.ll_zhonglei
            ,R.id.ll_sys, R.id.ll_fxlj, R.id.ll_fxewm, R.id.ll_wdsy})
    protected void button(View view) {
        switch (view.getId()) {
            case R.id.ll_wallet:
                /*钱包*/
                startActivity(ZWalletActivity.class);
                break;
            case R.id.ll_jilu:
                /*我的推荐*/
                Intent Intent = new Intent(getContext(), mYxiaxianActivity.class);
                Intent.putExtra("bs", ""+tv_tuijianma.getText().toString().trim());
                startActivity(Intent);
                break;
            case R.id.ll_tuijianma:
                /*银行卡*/
                startActivity(ZZhuanQianActivity.class);
                break;
//            case R.id.ll_zhuanqina:
//                /*我要赚钱*/
//                startActivity(ZZhuanQianActivity.class);
//                break;
            case R.id.ll_wanjia:
                /*支付宝绑定*/
                Intent Intent13 = new Intent(getContext(), BangDingZfbActivity.class);
                Intent13.putExtra("bs", "1");
                startActivity(Intent13);
                break;
            case R.id.ll_setting://SheZhiActivity
                /*设置*/
                Intent Intent9 = new Intent(getContext(), SheZhiActivity.class);
                Intent9.putExtra("bs", "1");
                startActivityForResult(Intent9, 200);
                break;
            case R.id.own_info:
                /*个人信息*/

                break;
            case R.id.o_btn:
                /*退出*/

                break;
            case R.id.ll_sys://扫码
                startActivityForResult(CaptureActivity.class, 17);
                break;
            case R.id.ll_fxlj://分享
                showShare();
                break;
            case R.id.ll_fxewm:
                /*我的推荐码*/
                showerweimaPopwindow();
//                startActivity(ZZhuanQianActivity.class);
                break;
            case R.id.ll_wdsy://MyShouYiActivity 我的收益
                startActivity(MyShouYiActivity.class);
                //  startActivity(XuQiuActivity.class);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 200) {
            startActivity(LoginActivity.class);
            new UserUtil(getContext()).removeUserInfo();
            UserUtil.setToken("");
//            RongIM.getInstance().logout();
            finishActivity();
        }

        if (resultCode == 201) {//二维码回调
            Toast.makeText(getContext(), "识别" + data.getStringExtra("jg"), Toast.LENGTH_SHORT).show();
        }

        if (resultCode == 204) {
            gengx();
        }
    }

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_own;
    }

    @Override
    public OwnPresenter createPresenter() {
        return new OwnPresenter();
    }

    @Override
    public void success(final About about) {

    }

    @Override
    public void error() {
        toast("获取数据失败，请检测您的网络连接");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    String yaoqingma="";

    void gengx(){//修改个人信息后 进行更新
        HttpUtils.UserInfo(new SubscriberRes<User>() {
            @Override
            public void onSuccess(User user) {
                UserUtil.putUser(user);
                UserUtil.putLogin(new Login(user.getMobile(), user.getHeadUrl()));
                tzxxnr.setText(QuanJu.getQuanJu().getTzxx());
                if (UserUtil.isLogin()) {
                    //PhotoPath=http://thirdwx.qlogo.cn/mmopen/vi_32/vAQHG6Yhou77kQicrvXO27scAHQYK0ANGSXK0F5IoqibfibD6VINxKPSVrl8MxlGU77KQzICpBtiaTdQ9LlrlmzSlg/132
                    Glide.with(getContext())
                            .load(Const.getbase(user.getHeadUrl()))
                            .apply(new RequestOptions().placeholder(R.drawable.userimg))
                            .into(o_userimg);
                    o_username.setText(user.getNickName());
                    o_usernumber.setText("社交号：" + user.getUserCode());
                    // tv_money.setText(user.Credit+"");
                    tv_tuijianma.setText("" + user.getTJUserCode());
                    yaoqingma="" + user.getTJUserCode();
                    tv_tuijianma.setVisibility(View.GONE);}
            }

            @Override
            public void completeDialog() {
            }
        });
//        Map map = new HashMap();
//        map.put("function", "Login");
//        map.put("mobile", new UserUtil(getContext()).getUser().getMobile());
//        map.put("password", new UserUtil(getContext()).getUser().getMm());
//        showDialog();
//        NR.posts("WebService/UserService.asmx/Entry",map,new StringCallback(){
//
//            @Override
//            public void onError(Request request, Exception e) {
//                dismissDialog();
//                //  LogUtils.i("xxxxx222  :", "" +e);  //输出测试
//                IsZH.getToast(getContext(), "服务器错误");  //吐司
//            }
//
//            @Override
//            public void onResponse(String s) {
//                dismissDialog();
//                //  LogUtils.i("xxxxx", "" +s);  //输出测试
//                if(NRUtils.getYse(getContext(),s)) {
//                   // IsZH.getToast(getContext(), "登陆成功");  //吐司
//                    User user = NRUtils.getData(s,User.class);
//                    //finish();
//                    UserUtil uu = new UserUtil(getContext());
//                    uu.putUser(user);
//                    uu.putLogin(new Login(user.getMobile(), user.getHeadUrl()));
//                    user = new UserUtil(getContext()).getUser();
//                    tzxxnr.setText(QuanJu.getQuanJu().getTzxx());
//                    if (new UserUtil(getContext()).isLogin()) {
//                        //PhotoPath=http://thirdwx.qlogo.cn/mmopen/vi_32/vAQHG6Yhou77kQicrvXO27scAHQYK0ANGSXK0F5IoqibfibD6VINxKPSVrl8MxlGU77KQzICpBtiaTdQ9LlrlmzSlg/132
//                        Glide.with(getContext())
//                                .load(Const.getbase(user.getHeadUrl()))
//                                .centerCrop()
//                                .crossFade()
//                                .placeholder(R.drawable.userimg)
//                                .into(o_userimg);
//                        o_username.setText(user.getNickName());
//                        o_usernumber.setText("社交号：" + user.getUserCode());
//                        // tv_money.setText(user.Credit+"");
//                        tv_tuijianma.setText("" + user.getTJUserCode());
//                        yaoqingma="" + user.getTJUserCode();
//                        tv_tuijianma.setVisibility(View.VISIBLE);}
////        if (StringUtil.isEmpty(new UserUtil(getContext()).getUser().RecommendCode)) {
////            sss();
////        } else {
////                    startActivity(SplashActivity.class);
////                    ActivityUtil.finishActivitys();
////                    finish();
//                }
//            }
//
//        });
    }

    private void showShare() {//分享
        Intent intent1=new Intent(Intent.ACTION_SEND);
        intent1.putExtra(Intent.EXTRA_TEXT,"http://www.zhangtuntun.love/Share/SHare.html");
        intent1.setType("text/plain");
        startActivity(Intent.createChooser(intent1,"贝聊"));
    }
    private void showShare2() {//分享
        String fxym = "http://www.zhangtuntun.love/Share/SHare.html";
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("" + fxym);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("快来加入我们把,点击进入下载页面");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl("https://appicon.pgyer.com/image/view/app_icons/ccfa64a4de2afe4c37c0906b7b3c8289/120");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("" + fxym);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("" + fxym);

// 启动分享GUI
        oks.show(getContext());
    }

    private void showerweimaPopwindow() {

        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ui_pop_erweima, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x38000000);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.Animation_Popup);
        // 在底部显示
        window.showAtLocation(ll_wallet, Gravity.BOTTOM, 0, 0);
        ImageView iv_erweima = (ImageView) view.findViewById(R.id.iv_erweima);
        iv_erweima.setImageBitmap(bitmap);
        TextView tv_save = (TextView) view.findViewById(R.id.tv_save);
        TextView tv_mycode = (TextView) view.findViewById(R.id.tv_mycode);
        tv_mycode.setText("我的推荐码："+UserUtil.getUser().getUserCode());
        ll_save = (LinearLayout) view.findViewById(R.id.ll_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveQrCodePicture(bitmap);
                ImageUtil.viewSaveToImage(ll_save,"贝聊下载码.jpg");
                window.dismiss();
            }
        });
        LinearLayout ll_erweima = (LinearLayout) view.findViewById(R.id.ll_erweima);
        ll_erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });
    }

    public void createQRImage(String url) {
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}