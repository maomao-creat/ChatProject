package com.zykj.samplechat.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.utils.CommonUtil;

;

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
public abstract class ToolBarActivity<P extends BasePresenterImp> extends BaseActivity<P> {

    protected Toolbar toolBar;
    protected ImageView imgBack;
    protected TextView tvTitle;
    protected
    @Nullable
    AppBarLayout appBar;
    protected boolean mIsHidden = false;
    protected
    @Nullable
    TextView tvAction;
    protected
    @Nullable
    ImageView imgAction;


    /**
     * @return 提供标题
     */
    abstract protected CharSequence provideTitle();

    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     *                           初始化一些事情
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        initToolBar();
    }

    private void initToolBar() {
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        imgBack = (ImageView) findViewById(R.id.img_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvAction = (TextView) findViewById(R.id.tv_action);
        appBar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        if (canAction()) {
            if (tvAction != null) {
                tvAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action();
                    }
                });
            }
            if (imgAction != null) {
                imgAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action();
                    }
                });
            }
        } else {
            if (tvAction != null) {

                tvAction.setVisibility(View.GONE);
            }

            if (imgAction != null) {

                imgAction.setVisibility(View.GONE);
            }

        }


        tvTitle.setText(provideTitle());
        CommonUtil.setWindowStatusBarColor(this,R.color.colorPrimary);
    }

    /**
     * Toolbar右边按钮的点击事件
     */
    protected void action() {

    }


    /**
     * @param alpha 设置标题栏的透明度
     */
    protected void setAppBarAlpha(float alpha) {
        if (appBar != null) {
            appBar.setAlpha(alpha);
        }
    }

    /**
     * 隐藏和显示Toolbar
     */
    protected void hideOrShowToolbar() {
        if (appBar != null) {
            appBar.animate()
                    .translationY(mIsHidden ? 0 : -appBar.getHeight())
                    .setInterpolator(new DecelerateInterpolator(2))
                    .start();
            mIsHidden = !mIsHidden;
        }

    }

    /**
     * @return 返回按钮是否可以显示
     */
    public boolean canBack() {
        return true;
    }


    /**
     * @return 右边按钮是否显示
     */
    public boolean canAction() {
        return false;
    }

}
