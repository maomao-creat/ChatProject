package com.tencent.qcloud.tim.uikit.utils;



import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Description : Json转换工具类
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/17
 */
public class JsonUtils {

    private static Gson mGson = new Gson();


    /**
     * 将对象准换为json字符串
     * @param object 对象
     * @param <T> 对象
     * @return 返回数据
     */
    public static <T> String serialize(T object) {
        return mGson.toJson(object);
    }

    /**
     * 将json字符串转换为对象
     * @param json json字符串
     * @param clz 对象
     * @param <T> 泛型
     * @return 返回数据
     */
    public static <T> T deserialize(String json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * 将json对象转换为实体对象
     * @param json json字符串
     * @param clz 对象
     * @param <T> 泛型
     * @return 返回数据
     * @throws JsonSyntaxException
     */
    public static <T> T deserialize(JsonObject json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * 将json字符串转换为对象
     * @param json json字符串
     * @param type 对象
     * @param <T> 泛型
     * @return 返回数据
     */
    public static <T> T deserialize(String json, Type type) throws JsonSyntaxException {
        return mGson.fromJson(json, type);
    }

    /**
     * 将json字符串转换为对象
     * @param jsonString json字符串
     * @param cls 对象
     * @param <T> 泛型
     * @return 返回数据
     */
    public static <T> List<T> getModel(String jsonString, Class cls) {
        List<T> list = new ArrayList<T>();
        try {

            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
        }
        return list;
    }

    public static Gson getGson(){
        return mGson;
    }
}