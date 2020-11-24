package com.zykj.samplechat.model;

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

import android.support.annotation.Nullable;

public class Res<T> {

    /**
     * 数据
     */
    @Nullable
    public T data;

    @Nullable
    public T date;

    /**
     * 状态吗
     */
    public int code;

    public String SpaceImagePath="";

    /**
     * 数据总条数
     */
    @Nullable
    public int recordcount = 0;

    /**
     * 评论插入的ID
     */
    @Nullable
    public int insertid = 0;

    public String error;
    public String message;
    /**
     * 状态吗
     */
    public int Credit;
    public int data4;
    @Nullable
    public T data1;

}