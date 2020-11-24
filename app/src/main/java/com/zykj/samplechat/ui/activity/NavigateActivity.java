package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.activity.base.BaseActivity;
import com.zykj.samplechat.ui.adapter.NavigateViewPagerAdapter;
import com.zykj.samplechat.ui.fragment.NavigateOneFragment;
import com.zykj.samplechat.ui.fragment.NavigateThreeFragment;
import com.zykj.samplechat.ui.fragment.NavigateTwoFragment;
import com.zykj.samplechat.utils.SharedPreferenceUtil;

import butterknife.Bind;

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

public class NavigateActivity extends BaseActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private GestureDetector gestureDetector;

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_navigate;
    }

    /**
     * 初始化事件监听者
     */
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

        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getContext())
                .open("settings");
        sharedPreferenceUtil.putBoolean("msg", true);
        sharedPreferenceUtil.putBoolean("sound", true);
        sharedPreferenceUtil.putBoolean("vibrate", true);

        NavigateViewPagerAdapter viewPagerAdapter = new NavigateViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(NavigateOneFragment.newInstance(), "");
        viewPagerAdapter.addFragment(NavigateTwoFragment.newInstance(), "");
        viewPagerAdapter.addFragment(NavigateThreeFragment.newInstance(), "");
        viewpager.setAdapter(viewPagerAdapter);

        viewpager.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this);

    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
        if (e1.getX() - e2.getX() > 120) {
            if (viewpager.getCurrentItem() == 2) {

                /**
                 * 更新isNotFirst*/

                new SharedPreferenceUtil(getContext()).open("user").putBoolean("isNotFirst", true);

                startActivity(LoginActivity.class);
            }
        }

        return false;
    }


    @Override
    public void startActivity(Class clazz) {
        super.startActivity(clazz);
        finish();
    }
}
