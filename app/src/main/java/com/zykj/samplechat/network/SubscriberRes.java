package com.zykj.samplechat.network;

import android.util.Log;
import android.view.View;

import com.zykj.samplechat.ui.widget.App;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.ToolsUtils;
import com.zykj.samplechat.utils.UserUtil;

import rx.Subscriber;

/**
 * Created by 徐学坤 on 2018/3/19.
 * Description 实体列表数据
 * T 实体
 */
public abstract class SubscriberRes<T> extends Subscriber<BaseEntityRes<T>> {

    protected View rootView;
    protected int recordCount = 0;
    public SubscriberRes(View rootView){
        this.rootView=rootView;
    }

    public SubscriberRes(){
    }

    @Override
    public void onNext(BaseEntityRes<T> res) {
        if (res.code != 200) {
            completeDialog();
            //String json = JsonUtils.serialize(res.data);
            //String error = JsonUtils.deserialize(json, ErrorBean.class).error;
            Log.e("%", "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            Log.e("%", res.error);
            Log.e("%", "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//            Snackbar.make(rootView, res.error, Snackbar.LENGTH_SHORT).show();
//            if(rootView!=null) {
                ToolsUtils.toast(App.getAppContext(),res.error);
//            }
        } else {
            if(!StringUtil.isEmpty(res.Token)) {
                Log.e("TAG","TOKEN="+res.Token);
                UserUtil.setToken(res.Token);
            }
            recordCount = res.recordCount;
//            if(res.data!=null) {
                onSuccess(res.data);
//            }

        }
    }

    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable throwable) {
         completeDialog();
        Log.e("%", "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        Log.e("%", throwable.getMessage());
        Log.e("%", "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//        if(rootView!=null) {
        ToolsUtils.toast(App.getAppContext(),"服务器繁忙，请稍后重试！");
//            Snackbar.make(rootView,"服务器繁忙，请稍后重试！" , Snackbar.LENGTH_SHORT).show();
//        }
    }

    public abstract void onSuccess(T t);

    public void completeDialog(){
    }
}