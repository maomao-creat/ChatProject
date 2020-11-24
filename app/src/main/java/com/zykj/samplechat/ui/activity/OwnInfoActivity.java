package com.zykj.samplechat.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.rey.material.app.Dialog;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.yancy.imageselector.ImageLoader;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Login;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.OwnInfoPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.OwnInfoView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.ImageUtil;
import com.zykj.samplechat.utils.IsZH;
import com.zykj.samplechat.utils.NR;
import com.zykj.samplechat.utils.NRUtils;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.ToolsUtils;
import com.zykj.samplechat.utils.UserUtil;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by ninos on 2016/7/5.
 */
public class OwnInfoActivity extends ToolBarActivity<OwnInfoPresenter> implements OwnInfoView,ImageLoader {

    @Bind(R.id.sjh)
    TextView sjh;
    private User user;
    private User tempUser;
    private Dialog dialog;
    private View view;
    private LinearLayout dg_b;
    private LinearLayout dg_g;
    private ImageView dg_b_s;
    private ImageView dg_g_s;

    @Bind(R.id.username)
    TextView username;
    @Bind(R.id.userphone)
    TextView userphone;
    @Bind(R.id.usergender)
    TextView usergender;
    @Bind(R.id.useradds)
    TextView useradds;
    @Bind(R.id.useram)
    TextView useram;
    @Bind(R.id.oi_photo)
    RelativeLayout oi_photo;
    @Bind(R.id.oi_username)
    LinearLayout oi_username;
    @Bind(R.id.oi_phone)
    LinearLayout oi_phone;
    @Bind(R.id.oi_ewm)
    LinearLayout oi_ewm;
    @Bind(R.id.oi_gender)
    LinearLayout oi_gender;
    @Bind(R.id.oi_address)
    LinearLayout oi_address;
    @Bind(R.id.oi_aboutme)
    LinearLayout oi_aboutme;
    @Bind(R.id.oi_userimg)
    ImageView oi_userimg;
    public PopupWindow window;
    Bitmap bitmap;
    private int QR_WIDTH = 800, QR_HEIGHT = 800;
    public String url="http://www.zhangtuntun.love/Share/SHare.html";
    public LinearLayout ll_save;
    @OnClick(R.id.oi_photo)
    public void oi_photo() {
        quanx();
    }

    @OnClick(R.id.oi_username)//修改名字
    public void oi_username() {
        //   startActivity(OwnUserNameActivity.class);
        Intent inte = new Intent(OwnInfoActivity.this, OwnUserNameActivity.class);
        startActivityForResult(inte, 201);
    }

    @OnClick(R.id.oi_phone)
    public void oi_phone() {
    }

    @OnClick(R.id.oi_ewm)
    public void oi_ewm() {// startActivity(ZZhuanQianActivity.class);    startActivity(ZZhuanQianActivity.class);
        showerweimaPopwindow();
//        startActivity(ZZhuanQianActivity.class);
//        startActivity(ZZhuanQianActivity.class, new Bun()
//                .putString("username", user.getNickName())
//                .putString("address", "")
//                .putString("userimg", user.getMobile())
//                .putString("id", user.getId() + "").ok());
    }

    @OnClick(R.id.oi_gender)
    public void oi_gender() {//男女
        dg_b_s.setImageResource(mye.getSex() == 0 ? R.drawable.select : R.drawable.unselect);
        dg_g_s.setImageResource(mye.getSex() == 0 ? R.drawable.unselect : R.drawable.select);
        showDialogMore();
    }


    private int REQUEST_TAKE_PHOTO_PERMISSION = 1;

    // 判断权限
    void quanx() {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(OwnInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            Intent intent = new Intent(context, PhotoAlbumActivity.class);
//            context.startActivity(intent);
            //  IsZH.getToast(MyxxActivity.this,"OKKKK");  //吐司
            presenter.touxiang(this,this);
        } else {
            //提示用户开户权限
            // IsZH.getToast(MyxxActivity.this,"noooo");  //吐司
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                    REQUEST_TAKE_PHOTO_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请成功，可以拍照
                // takePhoto();
                presenter.touxiang(this,this);
            } else {
                Toast.makeText(this, "CAMERA PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    @OnClick(R.id.oi_address)
    public void oi_address() {
        startActivity(OwnAddressActivity.class);
    }

    @OnClick(R.id.oi_aboutme)//真实姓名
    public void oi_aboutme() {
        Intent ds = new Intent(OwnInfoActivity.this, OwnAboutmeActivity.class);
        ds.putExtra("name", mye.getRealName());
        ds.putExtra("sex", mye.getSex() + "");
        startActivityForResult(ds, 200);
    }

    private void showDialogMore() {
        if (dialog == null)
            dialog = new Dialog(this).backgroundColor(Color.parseColor("#ffffff")).title("性别").contentView(view);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserInfo(username);
//        huoqsj();
//        user = new UserUtil(this).getUser();
//
//        username.setText(user.getNickName());
//        userphone.setText(user.getHeadUrl());
//        usergender.setText(user.getSex() == 0 ? "未选择" : user.getSex() == 1 ? "男" : "女");
//        useradds.setText("");
//        useram.setText("未填写");
//        Glide.with(this)
//                .load(Const.getbase(user.getHeadUrl()))
//                .centerCrop()
//                .crossFade()
//                .placeholder(R.drawable.userimg)
//                .into(oi_userimg);
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        createQRImage(url);
        view = getLayoutInflater().inflate(R.layout.dialog_gender, null);
        dg_b = (LinearLayout) view.findViewById(R.id.dg_b);
        dg_g = (LinearLayout) view.findViewById(R.id.dg_g);
        dg_b_s = (ImageView) view.findViewById(R.id.dg_b_s);
        dg_g_s = (ImageView) view.findViewById(R.id.dg_g_s);
    }

    @Override
    protected CharSequence provideTitle() {
        return "个人信息";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_own_info;
    }

    @Override
    public void initListeners() {
        dg_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mye.getSex() !=0)
                    UpdateUserinfo(username,0, "", "");
//                    setxb(1, "", "");
                dialog.dismiss();
            }
        });

        dg_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mye.getSex() != 1)
                    UpdateUserinfo(username,1, "", "");
//                    setxb(2, "", "");
                dialog.dismiss();
            }
        });
    }

    public String getAESForGenderData(int g) {
        tempUser = user;
        tempUser.setSex(g);
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "ChangeInfor");
//        map.put("userid",tempUser.Id);
//        map.put("nicname", tempUser.NicName);
//        map.put("sexuality",tempUser.Sexuality);
//        map.put("height",0);
//        map.put("weight",0);
//        map.put("address",tempUser.HomeAddress);
//        map.put("experience",0);
//        map.put("description",tempUser.Description);
        map.put("birthday", "1900/01/01");
        String json = StringUtil.toJson(map);
        try {
            return AESOperator.getInstance().encrypt(json.length() + "&" + json);
        } catch (Exception ex) {
            return "";
        }
    }

    @Override
    public OwnInfoPresenter createPresenter() {
        return new OwnInfoPresenter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            //Bitmap bitmap= BitmapFactory.decodeFile(pathList.get(0));
            //  showDialog();
            Glide.with(this).load(pathList.get(0))
                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
                    .into(oi_userimg);
            presenter.UploadImgByFile(pathList.get(0));
        }
        if (resultCode == 201) {//修改姓名回调
            // huoqsj();
        }
    }

    @Override
    public void successUploadPhoto(User user) {
        dismissDialog();
        this.user = user;
        Login login = new Login(user.getMobile(), user.getHeadUrl());
        new UserUtil(this).putLogin(login);
        snb("头像上传成功", username);
        Glide.with(this)
                .load(Const.getbase(user.getHeadUrl()))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(oi_userimg);
        HashMap<String, Object> hashMap = new HashMap<>();
// 头像，mIconUrl 就是您上传头像后的 URL，可以参考 Demo 中的随机头像作为示例
        if (!StringUtil.isEmpty(Const.BASE+user.getHeadUrl())) {
            hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_FACEURL, Const.BASE+user.getHeadUrl());
            hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_NICK, username.getText().toString().trim());
        }
        TIMFriendshipManager.getInstance().modifySelfProfile(hashMap, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e("modifySelfProfile","modifySelfProfile err code = " + i + ", desc = " + s);
            }
            @Override
            public void onSuccess() {
                Log.e("modifySelfProfile","modifySelfProfile  Success");
            }
        });
//        RongIM.getInstance().refreshUserInfoCache(new UserInfo(user.getId() + "", user.getNickName(), Uri.parse(Const.getbase(user.getHeadUrl()))));
        //dismissDialog();
    }

    @Override
    public void successUpload() {
        snb("个人信息更改成功", username);
        user = tempUser;
        new UserUtil(this).putUser(user);
        usergender.setText(user.getSex() ==0 ? "男" : "女");
    }

    @Override
    public void errorUpload(String error) {
        dismissDialog();
//        snb(error, username);
    }

    @Override
    public void upImage() {
        UserInfo(username);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    User mye;

    void setxb(int xb, String realname, String nickname) {
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
                IsZH.getToast(OwnInfoActivity.this, "服务器错误");  //吐司
            }

            @Override
            public void onResponse(String s) {
                dismissDialog();
                //  LogUtils.i("xxxxx", "" +s);  //输出测试
                if (NRUtils.getYse(OwnInfoActivity.this, s)) {
                    IsZH.getToast(OwnInfoActivity.this, "修改成功!");  //吐司
                    // MyYuEBean mye = NRUtils.getData(s,MyYuEBean.class);
                    //finish();
//                    huoqsj();
                }
            }

        });
    }
    public void UserInfo(View rootView) {
            HttpUtils.UserInfo(new SubscriberRes<User>(rootView) {
                @Override
                public void onSuccess(User userBean) {
                    mye = userBean;
                    UserUtil.putUser(userBean);
//                    PicassoUtil.loadPicassoARGB_8888(getContext(), Const.getbase(userBean.getHeadUrl()), oi_userimg, false);//加载网络图片
                    Glide.with(getContext())
                            .load(Const.getbase(userBean.getHeadUrl()))
                            .apply(new RequestOptions().placeholder(R.drawable.userimg))
                            .into(oi_userimg);
                    username.setText("" + userBean.getNickName());//姓名
                    userphone.setText("" + userBean.getUserCode());//小莫号
                    usergender.setText(userBean.getSex() == 0 ? "男" : "女");//性别
                    // useradds.setText(mye.getSex()+"");//地区
                    if(!StringUtil.isEmpty(userBean.getRealName())){
                        useram.setText(userBean.getRealName() + "");//真实姓名
                    }
                    sjh.setText(userBean.getMobile() + "");//手机号
//                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(UserUtil.getUser().getId(),
//                            StringUtil.isEmpty(UserUtil.getUser().getNickName())?UserUtil.getUser().getRealName():UserUtil.getUser().getNickName()
//                            ,Uri.parse(Const.getbase(UserUtil.getUser().getHeadUrl()))));
                }

                @Override
                public void completeDialog() {
                }
            });
    }


    public void UpdateUserinfo(View rootView,int xb, String realname, String nickname){
        HttpUtils.UpdateUserInfo(new SubscriberRes<String>(rootView) {
            @Override
            public void onSuccess(String userBean) {
                ToolsUtils.toast(getContext(),"修改成功");
                UserInfo(rootView);
            }

            @Override
            public void completeDialog() {
            }
        }, xb,nickname,realname);
    }
    @OnClick(R.id.img_back)
    public void onViewClicked() {
        //img_back
        setResult(204);//关闭界面回调
        finish();
    }

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .apply(new RequestOptions().placeholder(R.mipmap.imageselector_photo).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
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
        window.showAtLocation(username, Gravity.BOTTOM, 0, 0);
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
                ImageUtil.viewSaveToImage(ll_save,"贝聊交下载码.jpg");
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