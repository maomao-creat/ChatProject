package com.zykj.samplechat.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by AbyssKitty on 2016/9/7.
 * 基于picasso 2.5.2  +  okhttp 3.4.1 的图片加载库的封装
 * 使用前必须在 Application中初始化 自定义Picasso.Builder使用okhttp缓存机制
 */
public class PicassoUtil {
    //picasso封装的像翔一样 等待重新封装
//使用介绍
//        Picasso.with(context)                        //上下文
//                .load(url)                           //url、mipmap、file、uri
//                .config(Bitmap.Config.ARGB_8888)     //图片格式ARGB_8888、RGB_565、ARGB_4444、ALPHA_8
//                .placeholder(R.mipmap.sss)           //加载图片之前显示的图片
//                .error(R.mipmap.ssss)                //加载失败显示的图片 如果加载发生错误会重复三次请求，三次都失败才会显示erro
//                .noPlaceholder()                     //同一个Imageview二次加载时，显示上一次的图片，而不是从头初始化
//                .noFade()                            //不淡入淡出显示图片
//                .resize(200,200)                     //设置图片大小
//                .onlyScaleDown()                     //如果图片大小超过设置的大小则直接显示，不进行重新计算 -- 使用本属性必须设置图片大小
//                .centerCrop()                        //剪切图片 展示满控件 -- 使用本属性必须设置图片大小
//                .centerInside()                      //完整显示 等比缩放-- 使用本属性必须设置图片大小
//                .fit()                               //智能显示，智能缩放 为剪切铺满Imageview
//                .priority(Picasso.Priority.HIGH)     //优先级，HIGH、MEDIUM、LOW
//                .tag()                               //tag
//                .rotate(20f)                         //旋转0~360f
//                .rotate(20f,10,10)                   //在哪个点进行旋转
//                .transform()                         //模糊
//                .memoryPolicy()                      //MemoryPolicy.NO_CACHE=跳过从内存读取、NO_STORE=不进行缓存
//                .networkPolicy();                    //NetworkPolicy.NO_CACH=跳过本地读取、ENO_STORE=不本地缓存 OFFLINE只读本地，本地无内容再读网络

    //（可选）在Application中初始化下载模式 - 本代码只需要执行一次
//    public static void initPicassoBuilder(Context context){
//        PicassoBuilder.initPicassoBuilder(context);
//    }
    //开启debug模式 - 本代码只需要执行一次
    public static void initPicassoDebug(Context context){
        Picasso.with(context).setIndicatorsEnabled(true);  //debug//显示加载路径角标  蓝色=内存  绿色=本地  红色=网络
        Picasso.with(context).setLoggingEnabled(true);     //debug//显示图片加载时间
    }


    //以下代码简直不忍直视，请不要往下看了！
    //以下代码简直不忍直视，请不要往下看了！
    //以下代码简直不忍直视，请不要往下看了！


    /*
    *  ------------------------------使用说明 基础篇方法介绍-----------------------------
    *
    * 参数详解：
    *    ：基础三个参数 + 一个可选参数
    *       1. Context （最好使用getApplicationContext（））
    *       2. 地址  （可传 1-url-String、2-id-int、3-file-File、4-uri-Uri）
    *       3. ImageView （ImageView）
    *       4. -可选 传 boolean 不传值默认为true，所以传值只传false （是否满屏展示图片，满屏会剪切图片，不满屏会等比缩放图片）
    *    ：基础五个参数 + 一个可选参数
    *       1. Context （最好使用getApplicationContext（））
    *       2. 地址  （可传 1-url-String、2-id-int、3-file-File、4-uri-Uri）
    *       3. int id （加载前展示图片）
    *       4. int id （加载三次失败后显示的图片）
    *       5. ImageView （ImageView）
    *       6. -可选 传 boolean 不传值默认为true，所以传值只传false （是否满屏展示图片，满屏会剪切图片，不满屏会等比缩放图片）
    *    : 如果想体验更详细的方法，请使用原生Builder 设置，见本类最上面的注释
    * */
    //普通加载
    // ==========================三八线=====================最基础加载图片方式==========默认铺满imageView
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载png 支持透明图片，每像素占 4 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param url 图片的网络地址
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoARGB_8888(Context context, String url, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(url)
                    .config(Bitmap.Config.ARGB_8888)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(url)
                    .config(Bitmap.Config.ARGB_8888)
                    .fit()     //根据imageview的大小计算图片大小 铺满
                    .into(view);
        }
    }
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载jpg 不支持透明图片，每像素占 2 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param url 图片的网络地址
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoRGB_565(Context context, String url, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(url)
                    .config(Bitmap.Config.RGB_565)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(url)
                    .config(Bitmap.Config.RGB_565)
                    .fit()
                    .into(view);
        }
    }
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载png 支持透明图片，每像素占 4 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param id 资源文件
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoARGB_8888(Context context, int id, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(id)
                    .config(Bitmap.Config.ARGB_8888)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(id)
                    .config(Bitmap.Config.ARGB_8888)
                    .fit()     //根据imageview的大小计算图片大小 铺满
                    .into(view);
        }
    }
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载jpg 不支持透明图片，每像素占 2 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param id 资源文件
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoRGB_565(Context context, int id, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(id)
                    .config(Bitmap.Config.RGB_565)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(id)
                    .config(Bitmap.Config.RGB_565)
                    .fit()
                    .into(view);
        }
    }
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载png 支持透明图片，每像素占 4 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param file 资源文件
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoARGB_8888(Context context, File file, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(file)
                    .config(Bitmap.Config.ARGB_8888)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(file)
                    .config(Bitmap.Config.ARGB_8888)
                    .fit()     //根据imageview的大小计算图片大小 铺满
                    .into(view);
        }
    }
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载jpg 不支持透明图片，每像素占 2 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param file 资源文件
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoRGB_565(Context context, File file, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(file)
                    .config(Bitmap.Config.RGB_565)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(file)
                    .config(Bitmap.Config.RGB_565)
                    .fit()
                    .into(view);
        }
    }
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载png 支持透明图片，每像素占 4 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param uri 资源文件
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoARGB_8888(Context context, Uri uri, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(uri)
                    .config(Bitmap.Config.ARGB_8888)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(uri)
                    .config(Bitmap.Config.ARGB_8888)
                    .fit()     //根据imageview的大小计算图片大小 铺满
                    .into(view);
        }
    }
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载jpg 不支持透明图片，每像素占 2 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param uri 资源文件
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoRGB_565(Context context, Uri uri, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(uri)
                    .config(Bitmap.Config.RGB_565)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(uri)
                    .config(Bitmap.Config.RGB_565)
                    .fit()
                    .into(view);
        }
    }

    // ==========================三八线=====================中级加载图片方式==========默认铺满imageView
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载png 支持透明图片，每像素占 4 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param url 图片的网络地址
     * @param pid 网络图片加载之前显示的图片
     * @param eid 网络图片加载失败显示的图片
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoARGB_8888(Context context, String url, int pid, int eid, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(url)
                    .config(Bitmap.Config.ARGB_8888)
                    .placeholder(pid)
                    .error(eid)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(url)
                    .config(Bitmap.Config.ARGB_8888)
                    .placeholder(pid)
                    .error(eid)
                    .fit()
                    .into(view);
        }
    }
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载jpg 不支持透明图片，每像素占 2 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param url 图片的网络地址
     * @param pid 网络图片加载之前显示的图片
     * @param eid 网络图片加载失败显示的图片
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoRGB_565(Context context, String url, int pid, int eid, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(url)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(pid)
                    .error(eid)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(url)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(pid)
                    .error(eid)
                    .fit()
                    .into(view);
        }
    }
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载png 支持透明图片，每像素占 4 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param id 资源文件
     * @param pid 网络图片加载之前显示的图片
     * @param eid 网络图片加载失败显示的图片
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoARGB_8888(Context context, int id, int pid, int eid, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(id)
                    .config(Bitmap.Config.ARGB_8888)
                    .placeholder(pid)
                    .error(eid)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(id)
                    .config(Bitmap.Config.ARGB_8888)
                    .placeholder(pid)
                    .error(eid)
                    .fit()
                    .into(view);
        }
    }
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载jpg 不支持透明图片，每像素占 2 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param id 资源文件
     * @param pid 网络图片加载之前显示的图片
     * @param eid 网络图片加载失败显示的图片
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoRGB_565(Context context, int id, int pid, int eid, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(id)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(pid)
                    .error(eid)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(id)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(pid)
                    .error(eid)
                    .fit()
                    .into(view);
        }
    }
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载png 支持透明图片，每像素占 4 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param file 资源文件
     * @param pid 网络图片加载之前显示的图片
     * @param eid 网络图片加载失败显示的图片
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoARGB_8888(Context context, File file, int pid, int eid, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(file)
                    .config(Bitmap.Config.ARGB_8888)
                    .placeholder(pid)
                    .error(eid)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(file)
                    .config(Bitmap.Config.ARGB_8888)
                    .placeholder(pid)
                    .error(eid)
                    .fit()
                    .into(view);
        }
    }
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载jpg 不支持透明图片，每像素占 2 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param file 资源文件
     * @param pid 网络图片加载之前显示的图片
     * @param eid 网络图片加载失败显示的图片
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoRGB_565(Context context, File file, int pid, int eid, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(file)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(pid)
                    .error(eid)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(file)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(pid)
                    .error(eid)
                    .fit()
                    .into(view);
        }
    }
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载png 支持透明图片，每像素占 4 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param uri 资源文件
     * @param pid 网络图片加载之前显示的图片
     * @param eid 网络图片加载失败显示的图片
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoARGB_8888(Context context, Uri uri, int pid, int eid, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(uri)
                    .config(Bitmap.Config.ARGB_8888)
                    .placeholder(pid)
                    .error(eid)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(uri)
                    .config(Bitmap.Config.ARGB_8888)
                    .placeholder(pid)
                    .error(eid)
                    .fit()
                    .into(view);
        }
    }
    /**
     * Created by AbyssKitty on 2016/9/7.
     * 加载jpg 不支持透明图片，每像素占 2 byte
     * ------  最基础的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param uri 资源文件
     * @param pid 网络图片加载之前显示的图片
     * @param eid 网络图片加载失败显示的图片
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadPicassoRGB_565(Context context, Uri uri, int pid, int eid, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(uri)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(pid)
                    .error(eid)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(uri)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(pid)
                    .error(eid)
                    .fit()
                    .into(view);
        }
    }
    // ==========================三八线=====================ListView-tag加载图片方式==========默认铺满imageView

    /*
    *  ------------------------------使用说明 ListView篇方法介绍-----------------------------
    *
    * 参数详解：
    *    ：基础三个参数 + 一个可选参数
    *       1. Context （最好使用getApplicationContext（））
    *       2. 地址  （可传 1-url-String、2-id-int、3-file-File、4-uri-Uri）
    *       3. ImageView （ImageView）
    *       4. -可选 传 boolean 不传值默认为true，所以传值只传false （是否满屏展示图片，满屏会剪切图片，不满屏会等比缩放图片）
    *    ：基础五个参数 + 一个可选参数
    *       1. Context （最好使用getApplicationContext（））
    *       2. 地址  （可传 1-url-String、2-id-int、3-file-File、4-uri-Uri）
    *       3. int id （加载前展示图片）
    *       4. int id （加载三次失败后显示的图片）
    *       5. ImageView （ImageView）
    *       6. -可选 传 boolean 不传值默认为true，所以传值只传false （是否满屏展示图片，满屏会剪切图片，不满屏会等比缩放图片）
    * */

    /**
     * Create by AbyssKitty on 2016/09/09
     * 加载png 支持透明图片，每像素占 4 byte
     * ------  列表带tag的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param url 资源文件
     * @param tag 标示，用来进行暂停加载操作
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadListPicassoARGB_8888(Context context, String url, Object tag, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(url)
                    .config(Bitmap.Config.ARGB_8888)
                    .tag(tag)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(url)
                    .config(Bitmap.Config.ARGB_8888)
                    .fit()
                    .tag(tag)
                    .into(view);
        }
    }
    /**
     * Create by AbyssKitty on 2016/09/09
     * 加载jpg 不支持透明图片，每像素占 2 byte
     * ------  列表带tag的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param url 资源文件
     * @param tag 标示，用来进行暂停加载操作
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadListPicassoRGB_565(Context context, String url, Object tag, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(url)
                    .config(Bitmap.Config.RGB_565)
                    .tag(tag)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(url)
                    .config(Bitmap.Config.RGB_565)
                    .fit()
                    .tag(tag)
                    .into(view);
        }
    }
    /**
     * Create by AbyssKitty on 2016/09/09
     * 加载png 支持透明图片，每像素占 4 byte
     * ------  列表带tag的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param id 资源文件
     * @param tag 标示，用来进行暂停加载操作
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadListPicassoARGB_8888(Context context, int id, Object tag, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(id)
                    .config(Bitmap.Config.ARGB_8888)
                    .tag(tag)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(id)
                    .config(Bitmap.Config.ARGB_8888)
                    .fit()
                    .tag(tag)
                    .into(view);
        }
    }
    /**
     * Create by AbyssKitty on 2016/09/09
     * 加载jpg 不支持透明图片，每像素占 2 byte
     * ------  列表带tag的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param id 资源文件
     * @param tag 标示，用来进行暂停加载操作
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadListPicassoRGB_565(Context context, int id, Object tag, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(id)
                    .config(Bitmap.Config.RGB_565)
                    .tag(tag)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(id)
                    .config(Bitmap.Config.RGB_565)
                    .fit()
                    .tag(tag)
                    .into(view);
        }
    }
    /**
     * Create by AbyssKitty on 2016/09/09
     * 加载png 支持透明图片，每像素占 4 byte
     * ------  列表带tag的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param file 资源文件
     * @param tag 标示，用来进行暂停加载操作
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadListPicassoARGB_8888(Context context, File file, Object tag, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(file)
                    .config(Bitmap.Config.ARGB_8888)
                    .tag(tag)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(file)
                    .config(Bitmap.Config.ARGB_8888)
                    .fit()
                    .tag(tag)
                    .into(view);
        }
    }
    /**
     * Create by AbyssKitty on 2016/09/09
     * 加载jpg 不支持透明图片，每像素占 2 byte
     * ------  列表带tag的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param file 资源文件
     * @param tag 标示，用来进行暂停加载操作
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadListPicassoRGB_565(Context context, File file, Object tag, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(file)
                    .config(Bitmap.Config.RGB_565)
                    .tag(tag)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(file)
                    .config(Bitmap.Config.RGB_565)
                    .fit()
                    .tag(tag)
                    .into(view);
        }
    }
    /**
     * Create by AbyssKitty on 2016/09/09
     * 加载png 支持透明图片，每像素占 4 byte
     * ------  列表带tag的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param uri 资源文件
     * @param tag 标示，用来进行暂停加载操作
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadListPicassoARGB_8888(Context context, Uri uri, Object tag, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(uri)
                    .config(Bitmap.Config.ARGB_8888)
                    .tag(tag)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(uri)
                    .config(Bitmap.Config.ARGB_8888)
                    .fit()
                    .tag(tag)
                    .into(view);
        }
    }
    /**
     * Create by AbyssKitty on 2016/09/09
     * 加载jpg 不支持透明图片，每像素占 2 byte
     * ------  列表带tag的图片显示  ------
     * @param context 上下文 ApplicationContext
     * @param uri 资源文件
     * @param tag 标示，用来进行暂停加载操作
     * @param view ImageView
     * @param b 可选参数，是否铺满控件，默认为true
     * */
    public static void loadListPicassoRGB_565(Context context, Uri uri, Object tag, ImageView view, boolean ... b){
        if(b.length != 0 && b[0] == false){
            Picasso
                    .with(context)
                    .load(uri)
                    .config(Bitmap.Config.RGB_565)
                    .tag(tag)
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(uri)
                    .config(Bitmap.Config.RGB_565)
                    .fit()
                    .tag(tag)
                    .into(view);
        }
    }
    //============================三八.5线=============对含有tag的picasso进行暂停等操作===============
    /**
     * Create by aby on 2016/09/10
     * 对含有tag的picasso进行暂停操作
     * @param context 上下文
     * @param tag 标示
     * */
    public static void picassoPauseTag(Context context, Object tag){
        Picasso
                .with(context)
                .pauseTag(tag);
    }
    /**
     * Create by aby on 2016/09/10
     * 对含有tag的picasso进行显示操作
     * @param context 上下文
     * @param tag 标示
     * */
    public static void picassoResumeTag(Context context, Object tag){
        Picasso
                .with(context)
                .resumeTag(tag);
    }
    /**
     * Create by aby on 2016/09/10
     * 对含有tag的picasso进行取消操作
     * @param context 上下文
     * @param tag 标示
     * */
    public static void picassoCancleTag(Context context, Object tag){
        Picasso
                .with(context)
                .cancelTag(tag);
    }
    //============================三八线=============更改图片大小操作===============
    /**
     * loadSizePicassoARGB_8888
     *
     * */
    public static void loadSizePicassoARGB_8888(Context context, String url, int x, int y){
//        Picasso
//                .with(context)
//                .load(url)
//                .resize(x,y)
//                .centerCrop()  //剪切
    }
    //高级加载
//    public static void loadSeniorPicasso(){
//
//    }
    /**
     * create by AbyssKitty on 2016/09/09.
     * 终极Picasso加载方法
     *
     * */
//    public static void loadBossPicasso(Context context,String url,int uid,int eid){
//        Picasso
//                .with(context)
//                .load(url)
//                .config(Bitmap.Config.ARGB_8888)
//                .placeholder(uid)      //未加载图片之前显示的图片
//                .error(eid)            //加载失败显示的图片
//                .noPlaceholder()       //当二次加载时，只显示上一张图片，不重新初始化
//                .noFade();              //不使用淡入淡出效果
//    }
}
