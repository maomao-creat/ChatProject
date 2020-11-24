package com.zykj.samplechat.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import com.rey.material.app.Dialog;

import java.util.Arrays;
import java.util.List;

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
public class WindowUtil {

    /**
     * 获取手机屏幕宽度
     * @param activity 当前Activity
     * @return 屏幕的宽度
     */
    public static int getScreenWidth(Activity activity){
        android.view.WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }


    public static int getScreenHeight(Activity activity){
        android.view.WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void showDialog(String str, final Activity activity){
        Dialog dialog = new Dialog(activity);
        dialog.title(str);
        dialog.positiveAction("确定");
        dialog.positiveActionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        dialog.backgroundColor(Color.parseColor("#FFFFFF"));
        dialog.titleColor(Color.parseColor("#292A2F"));
        dialog.cancelable(false);
        dialog.show();
    }

    public static boolean checkPermissions(Context context, String permission) {
        if(Build.VERSION.SDK_INT < 23)
            return true;
        else
            return ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(Activity activity, String permission, int requestCode) {
        String[] permissions = new String[]{permission};
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public static void call_phone(Activity activity, String phone) {
        String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
        boolean flag = ContextCompat.checkSelfPermission(activity,permissions[0]) == PackageManager.PERMISSION_GRANTED;
        if(Build.VERSION.SDK_INT < 23 || flag)
            activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone)));
        else
            requestPermissions(activity, permissions[0], 100);
    }

    public static boolean verifyImageUrl(String url){
        String extStr = "";
        int dot = url.lastIndexOf('.');
        if ((dot > -1) && (dot < (url.length() - 3))) {
            extStr = url.substring(dot);
        }
        List<String> allowType = Arrays.asList(".bmp",".png",".gif",".jpg",".jpeg",".BMP",".PNG",".GIF",".JPG",".JPEG");
        return extStr.length() > 0 && allowType.contains(extStr);
    }

//    public static boolean isFileExists(String url){
//        String path = Environment.getExternalStorageDirectory().getPath()+"/乔家大院";
//        int dot = url.lastIndexOf('/');
//        String fileName = dot > -1?url.substring(dot):url;
//        File file = new File(path + fileName);
//        return file.exists();
//    }
}
