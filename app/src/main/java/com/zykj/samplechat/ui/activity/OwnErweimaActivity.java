package com.zykj.samplechat.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.zykj.samplechat.R;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.OwnErweimaPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.OwnErweimaView;

import java.util.Hashtable;

import butterknife.Bind;

/**
 * Created by ninos on 2016/7/15.
 */
public class OwnErweimaActivity extends ToolBarActivity<OwnErweimaPresenter> implements OwnErweimaView {

    @Bind(R.id.o_userimg)ImageView o_userimg;
    @Bind(R.id.o_username)TextView o_username;
    @Bind(R.id.e_adds)TextView e_adds;
    @Bind(R.id.erweima)ImageView erweima;

    private String username;
    private String address;
    private String userimg;
    private String id;
    private int QR_WIDTH = 400, QR_HEIGHT = 400;

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        username = getIntent().getBundleExtra("data").getString("username");
        address = getIntent().getBundleExtra("data").getString("address");
        userimg = getIntent().getBundleExtra("data").getString("userimg");
        id = getIntent().getBundleExtra("data").getString("id");

        Glide.with(this)
                .load(Const.getbase(userimg))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(o_userimg);

        o_username.setText(username);
        e_adds.setText(address);
        createQRImage("http://www.zhangtuntun.love/Share/SHare.html");

    }

    @Override
    protected CharSequence provideTitle() {
        return "二维码";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_erweima;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public OwnErweimaPresenter createPresenter() {
        return new OwnErweimaPresenter();
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
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            erweima.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
