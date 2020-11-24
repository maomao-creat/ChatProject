package com.zykj.samplechat.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zykj.samplechat.network.Const;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 网络请求类  所有网络请求 都写在这个类中
 */

public class NR {
 public static String url = "http://47.105.199.154:8909/";//服务器

    private static Gson gson = new Gson();
    //get 请求 测试模版
    public static void get(StringCallback sc) {
        OkHttpUtils
                .get()
                .url("https://www.391k.com/api/xapi.ashx/info.json")
                .addParams("key", "bd_hyrzjjfb4modhj")
                .addParams("size", "10")
                .addParams("page", "1")
                .build()
                .execute(sc);
    }

    /**
     * POST 请求
     * @param urls  请求地址  http://121.42.36.249:8099/jubao 已经前置 该部分内容
     * @param maps  请求参数
     * @param callback  new StringCallback() 即可
     */
    public  static  void post(String urls, Map maps, StringCallback callback){
     //   LogUtils.i("xxxxx", "111");  //输出测试
                OkHttpUtils
                .post()
                .url(url+urls)
                .addParams("jsonString",NRUtils.getJsonString(maps))//此处传递参数 需要进行加密
                .build()
                .execute(callback);
       // LogUtils.i("xxxxx", "222");  //输出测试
    }
    /**
     * POST 请求 (搜了么的)
     * @param urls  请求地址  http://47.100.15.175/souleme/ 已经前置 该部分内容
     * @param maps  请求参数
     * @param callback  new StringCallback() 即可
     */
    public  static  void posts(String urls, Map maps, StringCallback callback){
        //   LogUtils.i("xxxxx", "111");  //输出测试
        maps.put("key", Const.KEY);
        maps.put("uid", Const.UID);
        OkHttpUtils
                .post()
                .url(url+urls)
                .addParams("args",NRUtils.getBase64(maps))//此处传递参数 需要进行加密
                .build()
                .execute(callback);
        // LogUtils.i("xxxxx", "222");  //输出测试
    }

    /**
     * POST1 请求 此处注意  传输 list 的时候 使用此方法
     * @param urls  请求地址  http://121.42.36.249:8099/jubao 已经前置 该部分内容
     * @param maps  请求参数
     * @param callback  new StringCallback() 即可
     */
    public  static  void post1(String urls, Map maps, StringCallback callback){
        OkHttpUtils
                .post()
                .url(url+urls)
                .addParams("jsonString",NRUtils.getJsonString1(maps))//此处传递参数 需要进行加密
                .build()
                .execute(callback);
    }

    /**
     * POST 请求
     * @param urls  请求地址  完整URL
     * @param id  请求参数
     * @param callback  new StringCallback() 即可
     */
    public  static  void post3(String urls, String id, StringCallback callback){
        //   LogUtils.i("xxxxx", "111");  //输出测试
        OkHttpUtils
                .post()
                .url(urls)
                .addParams("userId",id)//此处传递参数 需要进行加密
                .build()
                .execute(callback);
        // LogUtils.i("xxxxx", "222");  //输出测试
    }
    /**
     * POST 请求 开视频请求
     * @param urls  请求地址  完整URL
     * @param myid  请求参数  我的ID
     * @param callback  new StringCallback() 即可
     */
    public  static  void post4(String urls, String myid, String youid, StringCallback callback){
        //   LogUtils.i("xxxxx", "111");  //输出测试
        OkHttpUtils
                .post()
                .url(urls)
                .addParams("userId",myid)//此处传递参数 需要进行加密
                .addParams("fid",youid)//此处传递参数 需要进行加密
                .build()
                .execute(callback);
        // LogUtils.i("xxxxx", "222");  //输出测试
    }
    /**
     * POST 请求房间处理
     * @param urls  请求地址  完整URL
     * @param myid  请求参数  我的ID
     * @param callback  new StringCallback() 即可
     */
    public  static  void post5(String urls, String myid, StringCallback callback){
        //   LogUtils.i("xxxxx", "111");  //输出测试
        OkHttpUtils
                .post()
                .url(urls)
                .addParams("roomId",myid)//此处传递参数 需要进行加密
                .build()
                .execute(callback);
        // LogUtils.i("xxxxx", "222");  //输出测试
    }

    /**
     * 上传图片
     * @param urls  地址
     * @param file  图片流
     * @param maps  数据
     * @param fileName  图片 messenger_01.png  视频 messenger_01.MP4
     * @param callback
     */
    public  static void postTop(String urls, File file, String fileName , Map maps, StringCallback callback){
        Map map12 = new HashMap();
        map12.put("jsonString",NRUtils.getJsonString(maps));
        map12.put("businessType","circleImg");
        if(fileName.equals("1")){
            OkHttpUtils.post()//
                   .addFile("uploadFile", ".mp4", file)//
                    //   .addFile("uploadFile", ".png", file)//
                    .url(url+urls)
                    .params(map12)//
                    //.headers(headers)//未知
                    .build()//
                    .execute(callback);
        }else{
            OkHttpUtils.post()//
                    //  .addFile("uploadFile", ".mp4", file)//
                    .addFile("uploadFile", ".png", file)//
                    .url(url+urls)
                    .params(map12)//
                    //.headers(headers)//未知
                    .build()//
                    .execute(callback);
        }

    } /**
     * 上传图片 搜了么
     * @param urls  地址
     * @param file  图片流
     * @param maps  数据
     * @param fileName  图片 messenger_01.png  视频 messenger_01.MP4
     * @param callback
     */
    public  static void postTops(Context c,String urls, File file, String fileName , Map maps, StringCallback callback){
        Map map12 = new HashMap();
        //.addParams("parameter",NRUtils.getBase64(maps))//此处传递参数 需要进行加密
        map12.put("parameter",NRUtils.getBase64(maps));
        //map12.put("businessType","circleImg");
        if(fileName.equals("1")){
            OkHttpUtils.post()//
                    .addFile("uploadFile", ".png", file)//
                    //   .addFile("uploadFile", ".png", file)//
                    .url(url+urls)
                    .params(map12)//
                    //.headers(headers)//未知
                    .build()//
                    .execute(callback);
        }else{
            OkHttpUtils.post()//
                    //  .addFile("uploadFile", ".mp4", file)//
                    .addFile("filestream", ".png", file)//
                    .url(url+urls)
                  //  .params(map12)//
                    .addParams("function","UpLoadPhoto")//此处传递参数 需要进行加密
                    .addParams("filename","messenger_01.png")//此处传递参数 需要进行加密
                    .addParams("userid",""+""+new UserUtil(c).getUserId2())//此处传递参数 需要进行加密
                    //.headers(headers)//未知
                    .build()//
                    .execute(callback);
        }

    }



}
