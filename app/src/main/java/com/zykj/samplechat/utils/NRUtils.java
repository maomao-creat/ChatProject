package com.zykj.samplechat.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.internal.Primitives;
import com.zykj.samplechat.model.RespBean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * 网络请求类   数据加密类  整个类 拷贝即可
 */

public class NRUtils {
    private static Gson gson = new Gson();
    //请求数据加密  传入 Map 数据
    public static String str = "a1b2c3";//加密 算法
    public static String getJsonString(Map map){
        String json =gson.toJson(map);  //传入的参数转化为 String类型的 json
        Map maps = new HashMap();
        maps.put("paramValues",json);
        maps.put("token",getMD5(getMD5(json)+str).substring(0,10)); //加密.substring(0,10)
        return  gson.toJson(maps);
    }
    public static String getJsonString1(Map map){
        String json =gson.toJson(map);  //传入的参数转化为 String类型的 json
        Map maps = new HashMap();
        maps.put("paramValues",map);
        maps.put("token",getMD5(getMD5(json)+str).substring(0,10)); //加密.substring(0,10)
        return  gson.toJson(maps);
    }
    //MD5 加密
    public static String getMD5(String info)
    {
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++)
            {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1)
                {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                }
                else
                {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            return "";
        }
        catch (UnsupportedEncodingException e)
        {
            return "";
        }
    }

    public static String getBase64(Map map){
        String json2 = StringUtil.toJson(map);
       String zhi = "";
        try {
            zhi =  AESOperator.getInstance().encrypt(json2.length() + "&" + json2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zhi;
    }



    /**
     * 服务器返回数据解析 获取data
      * @param s   服务器返回的完整数据
     * @param classOfT   接受返回的数据类型 (本方只支持 bean 类型参数)
     * @param <T>
     * @return
     */
    public static <T> T getData(String s, Class<T> classOfT){
        Gson gson = new Gson();
        RespBean rb = gson.fromJson(s, RespBean.class);
        Object object = gson.fromJson(gson.toJson(rb.data), classOfT);//通用的情况下 此处记得支取内容 rb.data
        return Primitives.wrap(classOfT).cast(object);
    }
    /**
     * 把json 字符串转化成list
     * @param s
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> getDataList(String s , Class<T> cls  ){
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        RespBean rb = gson.fromJson(s, RespBean.class);
        JsonArray array = new JsonParser().parse(gson.toJson(rb.data)).getAsJsonArray();//通用的情况下 此处记得支取内容 gson.toJson(rb.data)
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, cls));
        }
        return list ;
    }
    /**
     * 服务器返回code 获取code  code 100:失败 200:成功 300:用户已存在 400:用户不存在 500:密码不正确
     * @param s   服务器返回的完整数据
     * @return
     */
    public static String getCode(String s){
        Gson gson = new Gson();
        RespBean rb = gson.fromJson(s, RespBean.class);
        String object = gson.fromJson(gson.toJson(rb.code), String.class);
        return object;
    }
    /**
     * 服务器返回错误信息
     * @param s   服务器返回的完整数据
     * @return
     */
    public static String getError(String s){
        Gson gson = new Gson();
        RespBean rb = gson.fromJson(s, RespBean.class);
        String object = gson.fromJson(gson.toJson(rb.error), String.class);
        return object;
    }

    /**
     * 服务器是否成功  并且输出错误信息
     * @param s   服务器返回的完整数据
     * @return
     */
    public static boolean getYse(Context context, String s){
        boolean n =false;
        if(StringUtils.isEmpty(s)){
            n =false;
        }else{
            if(NRUtils.getCode(s).equals("200")){
                n=true;
            }else{
                IsZH.getToast(context, NRUtils.getError(s));  //吐司
                n =false;
            }
        }
        return n;
    }
}
