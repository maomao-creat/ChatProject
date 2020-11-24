package com.zykj.samplechat.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.zykj.samplechat.network.Const;

import java.util.Map;
import java.util.regex.Pattern;

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
public class StringUtil {

    public static boolean isEmpty(String str) {

        return null == str || "".equals(str);
    }

    /**
     * 判断是不是验证码
     *
     * @param code
     * @return
     */
    public static boolean isVerifyCode(String code) {
        return validation("\\d{6}", code);
    }

    public static boolean isMobile(String phoneNum) {
        // 如果手机中有+86则会自动替换掉
        return validation("^[1][3,4,5,7,8][0-9]{9}$",
                phoneNum.replace("+86", ""));
    }

    public static boolean isUserName(String username) {
        /***
         * 正则表达式为：^[a-z0-9_-]{3,15}$ 各部分作用如下： [a-z0-9_-] -----------
         * 匹配列表中的字符，a-z,0–9,下划线，连字符 {3,15}-----------------长度至少3个字符，最大长度为15
         * 如果有不同需求的可以参考以上修改正则表达式
         */
        return validation("^[a-z0-9_-]{3,15}$", username);
    }


    public static boolean isPassword(String pwd) {
        /**
         * ^ 匹配一行的开头位置(?![0-9]+$) 预测该位置后面不全是数字
         * (?![a-zA-Z]+$) 预测该位置后面不全是字母
         * [0-9A-Za-z] {6,16} 由6-16位数字或这字母组成
         */
        return validation("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$", pwd);
    }

    public static boolean isSimplePassword(String pwd) {
        /**
         * ^ 匹配一行的开头位置(?![0-9]+$) 预测该位置后面不全是数字
         * (?![a-zA-Z]+$) 预测该位置后面不全是字母
         * [0-9A-Za-z] {6,16} 由6-16位数字或这字母组成
         */
        return validation("^[0-9a-zA-Z]{6,16}$", pwd);
    }

    public static String getImagePath(String path) {
        if (path != null) {
            return path.startsWith("http") ? path : Const.BASE + path;
        } else {
            return "";
        }
    }

    public static boolean isEmail(String mail) {
        return validation(
                "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",
                mail);
    }

    /**
     * 正则校验
     *
     * @param str 需要校验的字符串
     * @return 验证通过返回true
     */
    public static boolean validation(String pattern, String str) {
        if (TextUtils.isEmpty(str))
            return false;
        return Pattern.compile(pattern).matcher(str).matches();
    }

    public static String formatJson(String json){
        try {
            return json.replace("\"","\'");
        } catch (Exception e) {
            return "";
        }
    }

    public static String toJson(Map map){
        return new Gson().toJson(map);
    }

    public static String formatPhone(String phone){
        return phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static String getFileName(String pathandname){

        int start=pathandname.lastIndexOf("/");
        if(start!=-1){
            return pathandname.substring(start+1);
        }else{
            return null;
        }

    }

}
