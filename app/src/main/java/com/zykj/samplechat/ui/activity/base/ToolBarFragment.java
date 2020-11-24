package com.zykj.samplechat.ui.activity.base;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.activity.MainActivity;
import com.zykj.samplechat.utils.CommonUtil;

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
public abstract class ToolBarFragment<P extends BasePresenterImp> extends BaseFragment<P> {

    protected Toolbar toolBar;
    private ImageView imgBack;
    private TextView tvTitle;
    private AppBarLayout appBar;
    private boolean mIsHidden = false;
    protected TextView tvAction;

    abstract protected String provideTitle();

    @Override
    protected void initThings(View view) {
        initToolBar(view);
    }

    public void initToolBar(View rootView) {
        toolBar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if(toolBar != null) {
            ((MainActivity) getActivity()).setSupportActionBar(toolBar);
            imgBack = (ImageView) rootView.findViewById(R.id.img_back);
            tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
            tvAction = (TextView) rootView.findViewById(R.id.tv_action);

            appBar = (AppBarLayout) rootView.findViewById(R.id.app_bar_layout);
        }

        if (canBack()) {
        } else {
            imgBack.setVisibility(View.GONE);
        }

        if (canTvAction()) {
            tvAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvAction();
                }
            });
        } else {
            tvAction.setVisibility(View.GONE);
        }
        tvTitle.setText(provideTitle());
        CommonUtil.setWindowStatusBarColor(getActivity(),R.color.colorPrimary);
    }

    public boolean canTvAction() {
        return false;
    }

    protected void tvAction() {

    }

    protected void setAppBarAlpha(float alpha) {
        appBar.setAlpha(alpha);
    }


    protected void hideOrShowToolbar() {
        appBar.animate()
                .translationY(mIsHidden ? 0 : -appBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();

        mIsHidden = !mIsHidden;
    }


    public boolean canBack() {
        return false;
    }

}
