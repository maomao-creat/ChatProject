package com.zykj.samplechat.utils;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

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
public class Bun {

    private Bundle bundle;

    public Bun() {
        this.bundle = new Bundle();
    }

    public Bun putString(String name, String value) {
        this.bundle.putString(name, value);
        return this;
    }

    public Bun putInt(String name, int value) {
        this.bundle.putInt(name, value);
        return this;
    }

    public Bun putBoolean(String name, boolean value) {
        this.bundle.putBoolean(name, value);
        return this;
    }

    public Bun putSerializable(String name, Serializable value) {
        this.bundle.putSerializable(name, value);
        return this;
    }

    public Bun putParcelable(String name, Parcelable value) {
        this.bundle.putParcelable(name, value);
        return this;
    }


    /**
     * @return 操作完成
     */
    public Bundle ok() {
        return this.bundle;
    }

}
