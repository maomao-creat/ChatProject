package com.zykj.samplechat.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageLoader;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.TiXianPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.SendTiXianView;
import com.zykj.samplechat.utils.GlideLoader;
import com.zykj.samplechat.utils.MoneyTextWatcher;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TiXianActivity extends ToolBarActivity<TiXianPresenter> implements SendTiXianView ,ImageLoader {


    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_action)
    TextView tvAction;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.jine)
    TextView jine;
    @Bind(R.id.cb_weixin)
    CheckBox cbWeixin;
    @Bind(R.id.ll_weixin)
    LinearLayout llWeixin;
    @Bind(R.id.cb_zhifubao)
    CheckBox cbZhifubao;
    @Bind(R.id.ll_zhifubao)
    LinearLayout llZhifubao;
    @Bind(R.id.tv_jine)
    EditText tvJine;
    @Bind(R.id.queding)
    Button queding;
    @Bind(R.id.iv_ewm)
    ImageView ivEwm;
    @Bind(R.id.ll_ewm)
    LinearLayout llEwm;
    @Bind(R.id.kyyy)
    TextView kyyy;
    @Bind(R.id.qbtx)
    TextView qbtx;
    @Bind(R.id.zfbsxf)
    TextView zfbsxf;
    @Bind(R.id.wxsxf)
    TextView wxsxf;
    private int zhifufangshi = 1;//1 微信 2 支付宝
    String url = "";
    int i = 1;//提现多次标识

    @Override
    public void success(String qian) {
        Toast.makeText(getContext(), "提现成功!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void successUploadPhoto(String qian) {
        //  LogUtils.i("xxxxx2", "" +Const.getbase(qian));  //输出测试
        url = qian;
        Glide.with(this)
                .load(Const.getbase(qian))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(ivEwm);
        dismissDialog();
    }

    @Override
    public void errorUpload(String qian) {
        dismissDialog();
        Toast.makeText(getContext(), "" + qian, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ti_xian;
    }

    @Override
    public void initListeners() {//初始化
        //jine.setText("" + getIntent().getStringExtra("bs"));
        //  ButterKnife.bind(this);
        huoqsj();
        qbtx.setOnClickListener(new View.OnClickListener() {//全部提现按钮
            @Override
            public void onClick(View view) {
                tvJine.setText("" + mye.getAmount());
            }
        });
    }

    @Override
    public TiXianPresenter createPresenter() {
        return new TiXianPresenter();
    }

    @Override
    protected CharSequence provideTitle() {
        return "提现";
    }


    @OnClick({R.id.img_back, R.id.ll_weixin, R.id.ll_zhifubao, R.id.queding, R.id.cb_weixin, R.id.cb_zhifubao, R.id.iv_ewm, R.id.ll_ewm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.ll_weixin:
                cbZhifubao.setChecked(false);
                cbWeixin.setChecked(true);
                zhifufangshi = 1;
                break;
            case R.id.ll_zhifubao:
                cbZhifubao.setChecked(true);
                cbWeixin.setChecked(false);
                zhifufangshi = 2;
                break;
            case R.id.cb_weixin:
                cbZhifubao.setChecked(false);
                cbWeixin.setChecked(true);
                zhifufangshi = 1;
                break;
            case R.id.cb_zhifubao:
                cbZhifubao.setChecked(true);
                cbWeixin.setChecked(false);
                zhifufangshi = 2;
                break;
            case R.id.queding:
                tixian();
                break;
            case R.id.iv_ewm:
                quanx();
                break;
            case R.id.ll_ewm:
                quanx();
                break;
        }
    }

    void tixian() {
        if (i != 1) {
            return;
        }
        i = 20;
        String qian = tvJine.getText().toString().trim();
        if (qian.isEmpty()) {
            Toast.makeText(getContext(), "请输入提现金额", Toast.LENGTH_SHORT).show();
            i = 1;
            return;
        }
        if (Double.parseDouble(qian) == 0) {
            Toast.makeText(getContext(), "提现金额大于0", Toast.LENGTH_SHORT).show();
            i = 1;
            return;
        }
        if (Double.parseDouble(qian) > mye.getAmount()) {
            Toast.makeText(getContext(), "提现金额不能大于余额", Toast.LENGTH_SHORT).show();
            i = 1;
            return;
        }
//        if (Double.parseDouble(qian) % mye.getCashConfig().getMultiple() != 0) {
//            //mye.getCashConfig().getMultiple()+"元的整数倍"
//            Toast.makeText(getContext(), "提现金额必须是" + mye.getCashConfig().getMultiple() + "元的整数倍", Toast.LENGTH_SHORT).show();
//            i = 1;
//            return;
//        }
//        if (url.equals("")) {
//            Toast.makeText(getContext(), "请上传提现图片", Toast.LENGTH_SHORT).show();
//            i = 1;
//            return;
//        }
            presenter.CreateCash(zhifufangshi == 1 ? 1 : 0,qian + "");
//        Map map = new HashMap();
//        map.put("key", Const.KEY);
//        map.put("uid", Const.UID);
//        map.put("function", "doWithdraw");
//        map.put("userId", new UserUtil(getContext()).getUserId());
//        map.put("value", qian);
//        String json = StringUtil.toJson(map);
//        try {
//
//            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//            presenter.getyue(data);
//        } catch (Exception ex) {
//            dismissDialog();
//        }

    }

    //上传图片
    public void oi_photo() {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.colorPrimary))
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
//                .crop()
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();
        ImageSelector.open(this, imageConfig);   // 开启图片选择器
    }

    private int REQUEST_TAKE_PHOTO_PERMISSION = 1;

    // 判断权限
    void quanx() {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(TiXianActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            Intent intent = new Intent(context, PhotoAlbumActivity.class);
//            context.startActivity(intent);
            //  IsZH.getToast(MyxxActivity.this,"OKKKK");  //吐司
            oi_photo();
        } else {
            //提示用户开户权限
            // IsZH.getToast(MyxxActivity.this,"noooo");  //吐司
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_TAKE_PHOTO_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请成功，可以拍照
                // takePhoto();
                oi_photo();
            } else {
                Toast.makeText(this, "CAMERA PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            //Bitmap bitmap= BitmapFactory.decodeFile(pathList.get(0));
            showDialog();
            Glide.with(this).load(pathList.get(0)).apply(new RequestOptions().placeholder(R.drawable.userimg)).into(ivEwm);
            presenter.UploadQrCodeByFile(pathList.get(0));
//            presenter.UpLoadPhoto(, new UserUtil(this).getUserId2() + "");
        }
    }

    User mye;

    void huoqsj() {
        HttpUtils.UserInfo(new SubscriberRes<User>() {
            @Override
            public void onSuccess(User user) {
                mye = user;
                kyyy.setText("可用余额" + user.getAmount() + "元");
                // qianshu = mye.getAmount() + "";
                zfbsxf.setText("系统自动扣除" + user.getCashConfig().getServiceFeeBL() + "%的手续费");
                wxsxf.setText("扣除" + user.getCashConfig().getServiceFeeBL() + "%的手续费");
                tvJine.setHint(user.getCashConfig().getMultiple() + "元的整数倍");
                tvJine.addTextChangedListener(new MoneyTextWatcher(tvJine));
            }

            @Override
            public void completeDialog() {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .apply(new RequestOptions().placeholder(R.mipmap.imageselector_photo).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }
}
