package com.zykj.samplechat.ui.view.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

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
public interface BaseView {

    Context getContext();

    void toast(String text);

    void snb(String text, View view);

    void snb(String text, View view, String actionText, View.OnClickListener action);

    void showSoftInput(View v);

    void hideSoftMethod(View v);

    void showDialog();

    void dismissDialog();

    void showDialog(String title, int layoutId);

    void showDialog(String title, String positive, String negative);

    void showDialog(String title, int layoutId, String positive, String negative);

    void showDialog(String title, View view, String positive, String negative);

    boolean isShowingDialog();

    void startActivity(Class clazz);

    void startActivityForResult(Class clazz, int requestCode);

    void startActivity(Class clazz, Bundle bundle);

    void startActivityForResult(Class clazz, Bundle bundle, int requestCode);

    void finishActivity();


}
