package com.zykj.samplechat.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.SplashPresenter;
import com.zykj.samplechat.ui.activity.base.BaseActivity;
import com.zykj.samplechat.ui.view.SplashView;
import com.zykj.samplechat.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

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
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashView {
    @Bind(R.id.img_splash)
    ImageView imgSplash;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initListeners() {

    }

    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     *                           初始化一些事情
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        CommonUtil.setWindowStatusBarColor(SplashActivity.this,R.color.white);
        List<PermissionItem> PermissionItems = new ArrayList<PermissionItem>();
        PermissionItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_camera));
        PermissionItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储", R.drawable.permission_ic_storage));
        PermissionItems.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, "读取存储", R.drawable.permission_ic_storage));
        HiPermission.create(this)
                .permissions(PermissionItems)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                    }

                    @Override
                    public void onFinish() {
                        startAnim();
                    }

                    @Override
                    public void onDeny(String permisson, int position) {

                    }

                    @Override
                    public void onGuarantee(String permisson, int position) {
//
                    }
                });


    }

    /**
     * 绑定Presenter
     */
    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenter();
    }///这里 点击 SplashPresenter

    @Override
    public void startAnim() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                presenter.navigate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        imgSplash.startAnimation(animation);

    }

    @Override
    public void startActivity(Class clazz) {
        super.startActivity(clazz);
        finish();
    }
}