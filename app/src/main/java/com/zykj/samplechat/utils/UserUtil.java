package com.zykj.samplechat.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.zykj.samplechat.model.Login;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.ui.widget.App;

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
public class UserUtil {
    private final Context context;

    public UserUtil(Context context) {
        this.context = context;
    }

    public static String getUserInfo() {
        SharedPreferences share = App.getAppContext().getSharedPreferences("user", 0);
        String userInfo = share.getString("userInfo", "");
        return userInfo;
    }

    public static void putUserInfoStr(String userInfoStr) {
        SharedPreferences share = App.getAppContext().getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("F", userInfoStr);
        editor.commit();
    }

    public static void putUser(User user) {
        SharedPreferences share = App.getAppContext().getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("userInfo", new Gson().toJson(user));
        editor.commit();
    }


    public static void removeUserInfo() {
        SharedPreferences share = App.getAppContext().getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("userInfo", "");
        editor.commit();
    }

    public static String getToken(){
        SharedPreferences share = App.getAppContext().getSharedPreferences("token", 0);
        String userInfo = share.getString("token", "");
        if ("".equals(userInfo) || "null".equals(userInfo)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(userInfo, String.class);
        }
    }

    public static void setToken(String token) {
        SharedPreferences share = App.getAppContext().getSharedPreferences("token", 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("token", new Gson().toJson(token));
        editor.commit();
    }

    public static void setPassword(String password) {
        SharedPreferences share = App.getAppContext().getSharedPreferences("password", 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("password", new Gson().toJson(password));
        editor.commit();
    }

    public static String getPassword(){
        SharedPreferences share = App.getAppContext().getSharedPreferences("password", 0);
        String userInfo = share.getString("password", "");
        if ("".equals(userInfo) || "null".equals(userInfo)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(userInfo, String.class);
        }
    }

    public static User getUser() {
        SharedPreferences share = App.getAppContext().getSharedPreferences("user", 0);
        String userInfo = share.getString("userInfo", "");
        if ("".equals(userInfo) || "null".equals(userInfo)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(userInfo, User.class);
        }
    }


    public String getUserId2() {
        User user = getUser();
        if (user != null) {
            return user.getId();
        }
        return "";
    }
    public int getUserId() {
        User user = getUser();
        if (user != null) {
            return 1;
        }
        return 0;
    }

    /**
     * @param
     */
    public static void putLogin(Login login) {
        removeLogin();
        SharedPreferences share = App.getAppContext().getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("login", new Gson().toJson(login));
        editor.commit();
    }

    public static void removeLogin() {
        SharedPreferences share = App.getAppContext().getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("login", "");
        editor.commit();
    }


    /**
     * @return 获取登录数据
     */
    public static Login getLogin() {
        SharedPreferences share = App.getAppContext().getSharedPreferences("user", 0);
        String login = share.getString("login", "");
        if ("".equals(login)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(login, Login.class);
        }
    }


    /**
     * @return
     */
    public static boolean isLogin() {
        return StringUtil.isEmpty(getUserInfo()) ? false : true;
    }

}