package com.zykj.samplechat.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.rey.material.app.Dialog;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Picture;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.QRCodePresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.QRCodeView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.WindowUtil;
import com.zykj.samplechat.zxing.decoding.RGBLuminanceSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import butterknife.Bind;
import butterknife.OnLongClick;

/**
 * Created by csh
 * Created date 2016/11/4.
 * Description 在线充值
 */

public class QRCodeActivity extends ToolBarActivity<QRCodePresenter> implements QRCodeView{

    @Bind(R.id.ll_top)
    LinearLayout ll_top;
    @Bind(R.id.ll_middle)
    LinearLayout ll_middle;
    @Bind(R.id.ll_center)
    LinearLayout ll_center;
    @Bind(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @Bind(R.id.rg_account)
    RadioGroup rg_account;//切换账户
    @Bind(R.id.rb_left)
    RadioButton rb_left;//左
    @Bind(R.id.rb_middle)
    RadioButton rb_middle;//中左
    @Bind(R.id.rb_center)
    RadioButton rb_center;//中右
    @Bind(R.id.rb_right)
    RadioButton rb_right;//右
    @Bind(R.id.iv_img1)
    ImageView iv_img1;
    @Bind(R.id.iv_img2)
    ImageView iv_img2;
    @Bind(R.id.iv_img3)
    ImageView iv_img3;
    @Bind(R.id.iv_img4)
    ImageView iv_img4;
    @Bind(R.id.iv_img5)
    ImageView iv_img5;
    @Bind(R.id.iv_img6)
    ImageView iv_img6;
    @Bind(R.id.iv_img7)
    ImageView iv_img7;
    @Bind(R.id.iv_img8)
    ImageView iv_img8;

    private int grey,black;
    private Picture pic;
    private Dialog dialogQuite;
    private String filedot = "";

    @Override
    protected CharSequence provideTitle() {
        return "在线充值";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initListeners() {
        grey = ContextCompat.getColor(getContext(), R.color.gray_font_1);
        black = ContextCompat.getColor(getContext(), R.color.gray_font_2);
        rg_account.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb_left.setTextColor(checkedId == R.id.rb_left?black:grey);
                rb_middle.setTextColor(checkedId == R.id.rb_middle?black:grey);
                rb_center.setTextColor(checkedId == R.id.rb_center?black:grey);
                rb_right.setTextColor(checkedId == R.id.rb_right?black:grey);
                ll_top.setVisibility(checkedId == R.id.rb_left?View.VISIBLE:View.GONE);
                ll_middle.setVisibility(checkedId == R.id.rb_middle?View.VISIBLE:View.GONE);
                ll_center.setVisibility(checkedId == R.id.rb_center?View.VISIBLE:View.GONE);
                ll_bottom.setVisibility(checkedId == R.id.rb_right?View.VISIBLE:View.GONE);
            }
        });
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        Map<String,Object> map = new HashMap<>();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "GetPicture");
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.GetPicture(data);
        } catch (Exception ex) {
            //ex.toString();
        }
    }

    @OnLongClick({R.id.iv_img1,R.id.iv_img2,R.id.iv_img3,R.id.iv_img4,R.id.iv_img5,R.id.iv_img6,R.id.iv_img7,R.id.iv_img8})
    protected boolean action(View v){
        if(v.getId() == R.id.iv_img1){
            new SubThread(Const.getbase(pic.Picd),0).start();
        }else if(v.getId() == R.id.iv_img2){
            new SubThread(Const.getbase(pic.Picc),1).start();
        }else if(v.getId() == R.id.iv_img3){
            new SubThread(Const.getbase(pic.Picf),0).start();
        }else if(v.getId() == R.id.iv_img4){
            new SubThread(Const.getbase(pic.Pice),1).start();
        }else if(v.getId() == R.id.iv_img5){
            new SubThread(Const.getbase(pic.Pich),0).start();
        }else if(v.getId() == R.id.iv_img6){
            new SubThread(Const.getbase(pic.Picg),1).start();
        }else if(v.getId() == R.id.iv_img7){
            new SubThread(Const.getbase(pic.Picj),0).start();
        }else{
            new SubThread(Const.getbase(pic.Pici),1).start();
        }
        return true;
    }

    @Override
    public QRCodePresenter createPresenter() {
        return new QRCodePresenter();
    }

    public void decodeQRCode(Bitmap bitmap, int pos){
        Hashtable<DecodeHintType, String> tab = new Hashtable<>();
        tab.put(DecodeHintType.CHARACTER_SET, "utf-8");

        //Bitmap bitmap = ((BitmapDrawable)ContextCompat.getDrawable(this, id)).getBitmap();
        RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
        // 转成二进制图片
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        // 实例化二维码解码对象
        QRCodeReader reader = new QRCodeReader();
        Result result;
        try {
            // 根据解码类型解码，返回解码结果
            result = reader.decode(bitmap1, tab);
            //result = reader.decode(bitmap1, hints);
            //System.out.println("res：》》》》》》》：" + result.getText());
            // 显示解码结果
            //toast(result.toString());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if(pos == 0){
                intent.setData(Uri.parse("alipayqr://platformapi/startapp?saId=10000007&qrcode="+result.toString()));//支付宝
            }else{
                intent.setData(Uri.parse(result.toString()));//微信
            }
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            snb("不识别二维码", rg_account);
        }
    }

    @Override
    public void success(Picture pic) {
        this.pic = pic;
        rb_left.setText(pic.Namea);
        rb_middle.setText(pic.Nameb);
        rb_center.setText(pic.Namec);
        rb_right.setText(pic.Named);
        String xx = Const.getbase(pic.Picd);
        Glide.with(this).load(xx).apply(new RequestOptions().placeholder(R.drawable.userimg)).into(iv_img1);
        Glide.with(this).load(xx).apply(new RequestOptions().placeholder(R.drawable.userimg)).into(iv_img2);
        Glide.with(this).load(xx).apply(new RequestOptions().placeholder(R.drawable.userimg)).into(iv_img3);
        Glide.with(this).load(xx).apply(new RequestOptions().placeholder(R.drawable.userimg)).into(iv_img4);
        Glide.with(this).load(xx).apply(new RequestOptions().placeholder(R.drawable.userimg)).into(iv_img5);
        Glide.with(this).load(xx).apply(new RequestOptions().placeholder(R.drawable.userimg)).into(iv_img6);
        Glide.with(this).load(xx).apply(new RequestOptions().placeholder(R.drawable.userimg)).into(iv_img7);
        Glide.with(this).load(xx).apply(new RequestOptions().placeholder(R.drawable.userimg)).into(iv_img8);
    }

    @Override
    public void error() {

    }

    private class SubThread extends Thread{
        private String url;
        private int pos;//0-支付宝 1-微信
        SubThread(String url,int pos){
            this.url = url;
            this.pos = pos;
        }
        @Override
        public void run() {
            Message message = Message.obtain();
            if(!WindowUtil.verifyImageUrl(url)){
                message.what = 0x121;
//            }else if(WindowUtil.isFileExists(url)){
//                message.what = 0x122;
            }else{
                int dot = url.lastIndexOf('.');
                filedot = dot > -1?url.substring(dot):url;
                Bitmap bitmap = GetImageInputStream(url);// 从网络获取图片
                message.what = 0x123;
                message.obj = bitmap;
            }
            handler.sendMessage(message);
//            try {
//                URL iconUrl = new URL(url);
//                URLConnection conn = iconUrl.openConnection();
//                HttpURLConnection http = (HttpURLConnection) conn;
//                int length = http.getContentLength();
//                conn.connect();
//                // 获得图像的字符流
//                InputStream is = conn.getInputStream();
//                BufferedInputStream bis = new BufferedInputStream(is, length);
//                Bitmap bm = BitmapFactory.decodeStream(bis);
//                bis.close();
//                is.close();// 关闭流
//                decodeQRCode(bm, pos);
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    /**
     * 获取网络图片
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public Bitmap GetImageInputStream(String imageurl){
        URL url;
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        try {
            url = new URL(imageurl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream=connection.getInputStream();
            bitmap=BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what==0x121){
                showDialogQuite("保存失败(图片异常)!");
//            }else if(msg.what==0x122){
//                showDialogQuite("图片已经存在!");
            }else if(msg.what==0x123){
                String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
                if(!WindowUtil.checkPermissions(QRCodeActivity.this, permissions[0])){
                    WindowUtil.requestPermissions(QRCodeActivity.this, permissions[0], 100);
                }else{
                    //将图片保存到SD卡跟目录下的Test文件夹内
                    SavaImage((Bitmap)msg.obj, Environment.getExternalStorageDirectory().getPath()+"/RongCloud/Image");
                }
            }
        }
    };

    /**
     * 保存位图到本地
     * @param bitmap 图片
     * @param dirPath 本地路径
     */
    public void SavaImage(Bitmap bitmap, String dirPath){
        File file=new File(dirPath);
        FileOutputStream fileOutputStream=null;
        //文件夹不存在，则创建它
        if(!file.exists()){
            file.mkdir();
        }
        try {
            String filepath = dirPath + "/"+System.currentTimeMillis() + filedot;
            fileOutputStream=new FileOutputStream(filepath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fileOutputStream);
            fileOutputStream.close();
            showDialogQuite("保存成功！");
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(new File(filepath));
            intent.setData(uri);
            sendBroadcast(intent);//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialogQuite(String title) {
        if (dialogQuite == null){
            dialogQuite = new Dialog(this);
            dialogQuite.backgroundColor(Color.parseColor("#ffffff"));
            dialogQuite.positiveAction("确定");
            dialogQuite.positiveActionClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogQuite.dismiss();
                }
            });
            dialogQuite.cancelable(false);
        }
        dialogQuite.title(title);
        dialogQuite.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==100 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            toast("无法保存图片，请检查朋友是否有操作相册的权限，或重启设备后重试");
        }
    }
}