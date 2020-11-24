package com.zykj.samplechat.model;


import java.io.Serializable;
import java.util.ArrayList;

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
public class Friend implements Serializable {
    public String topc;
    public String Id;
    public String TeamCode;
    public int Key;
    public int Credit;//1是管理员
    public String NicName;
    public String PhotoPath;
    public String UserCode;
    public int Rank;
    public String Phone;
    public int TeamId;
    public int MemnerId;
    public String MemnerId2;
    public String JoinTime;
    public boolean IsShield;
    public String UserId;
    public String RemarkName;
    public String RealName;
    public int Sexuality;
    public int IsFriend;
    public int IsWuyou;
    public String Description;
    public String UserName;
    public String Address;
    public String ActivityAddress;
    public String HomeAddress;
    public ArrayList<Image> PicList;
    public boolean isSelected = false;
    public boolean isAdd = false;
    public boolean isShare = false;
}