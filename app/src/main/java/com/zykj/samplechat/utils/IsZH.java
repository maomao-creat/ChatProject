package com.zykj.samplechat.utils;

import android.content.Context;

import java.util.Locale;

/**
 *
 * 获取当前系统手机语言 是中问还是英文
 */

public class IsZH {
    public static boolean isZh() {//true中文
        String language = Locale.getDefault().getLanguage();
        //LogUtils.i("xxxxxIsZH", "" +language);  //输出测试
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }

    /**
     * 获取ID的文字
     * @param context
     * @param id String  里面的ID R.string.login_rs_neme
     * @return
     */
    public static String Transformation(Context context, int id){
        return context.getResources().getString(id);
    }
    /**
     * 根据String 里面的ID  切换中英文
     * @param context
     * @param id String  里面的ID R.string.login_rs_neme
     * @return
     */
    public static void getToast(Context context, int id){
        ToastFactory.getToast(context,context.getResources().getString(id)).show();
    }
    /**
     * 根据String 里面的ID  切换中英文
     * @param context
     * @param id String  里面的ID R.string.login_rs_neme
     * @return
     */
    public static void getToast(Context context, String id){
        ToastFactory.getToast(context,id+"").show();
    }
}
