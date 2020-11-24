package com.zykj.samplechat.ui.activity.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.base.BaseView;

import butterknife.ButterKnife;

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
public abstract class BaseActivity<P extends BasePresenterImp> extends AutoLayoutActivity implements BaseView {

    protected Intent intent;

    public P presenter;

    private Dialog dialog;

    /**
     * @return 提供LayoutId
     */
    abstract protected int provideContentViewId();


    /**
     * 初始化事件监听者
     */
    public abstract void initListeners();

    /**
     * @param savedInstanceState 缓存数据
     *                           <p>
     *                           初始化一些事情
     */
    protected abstract void initThings(Bundle savedInstanceState);

    /**
     * 绑定Presenter
     */
    public abstract P createPresenter();

    /**
     * 解绑Presenter
     */
    public void killPresenter() {
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetCView(savedInstanceState);
        setContentView(provideContentViewId());
        ButterKnife.bind(this);

        this.presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
        changStatusIconCollor(true);
        initThings(savedInstanceState);
        initListeners();
    }

    public void beforeSetCView(Bundle savedInstanceState) {
    }

    //设置状态栏图标颜色，true:黑色
    //false：白色
    public void changStatusIconCollor(boolean setDark) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            View decorView = getWindow().getDecorView();
            if(decorView != null){
                int vis = decorView.getSystemUiVisibility();
                if(setDark){
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else{
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    public void snb(String text, View view) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public void snb(String text, View view, String actionText, View.OnClickListener action) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).setAction(actionText, action).show();
    }


    @Override
    public void showDialog() {

        dialog = new Dialog(getContext()).backgroundColor(Color.parseColor("#FFFFFF")).titleColor(Color.parseColor("#292A2F")).contentView(R.layout.dialog_circular).cancelable(false);
        dialog.show();
    }

    @Override
    public void showDialog(String title, String positive, String negative) {
        dialog = new Dialog(this).title(title).positiveAction(positive).negativeAction(negative).backgroundColor(Color.parseColor("#fffaee")).titleColor(Color.parseColor("#877652"));
        dialog.show();
    }

    @Override
    public void showDialog(String title,int layoutId) {
        dialog = new Dialog(this).backgroundColor(Color.parseColor("#fffaee")).titleColor(Color.parseColor("#877652")).contentView(layoutId).cancelable(false);
        dialog.show();
    }

    @Override
    public void showDialog(String title, int layoutId, String positive, String negative) {
        dialog = new Dialog(this).title(title).positiveAction(positive).negativeAction(negative).backgroundColor(Color.parseColor("#fffaee")).titleColor(Color.parseColor("#877652")).contentView(layoutId);
        dialog.show();
    }

    @Override
    public void showDialog(String title, View view, String positive, String negative) {
        dialog = new Dialog(this).title(title).positiveAction(positive).negativeAction(negative).backgroundColor(Color.parseColor("#fffaee")).titleColor(Color.parseColor("#877652")).contentView(view);
        dialog.show();
    }

    @Override
    public void dismissDialog() {
        if(dialog!=null){
            dialog.dismiss();
        }

    }

    @Override
    public void showSoftInput(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }


    @Override
    public void hideSoftMethod(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        killPresenter();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean isShowingDialog() {
        return false;
    }

    @Override
    public void startActivity(Class clazz) {
        startActivity(new Intent(getContext(), clazz));

    }

    @Override
    public void startActivity(Class clazz, Bundle bundle) {
        startActivity(new Intent(getContext(), clazz).putExtra("data", bundle));
    }

    @Override
    public void startActivityForResult(Class clazz, int requestCode) {
        startActivityForResult(new Intent(getContext(), clazz), requestCode);

    }

    @Override
    public void startActivityForResult(Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtra("data", bundle);
        startActivityForResult(intent, requestCode);

    }


    public Bundle getIntentData() {
        Bundle data = getIntent().getBundleExtra("data");
        return data == null ? new Bundle() : data;
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
