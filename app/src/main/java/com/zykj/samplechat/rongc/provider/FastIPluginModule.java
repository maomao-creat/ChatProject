package com.zykj.samplechat.rongc.provider;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.QuanJu;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.rongc.messge.ZhuanZhangMessage;
import com.zykj.samplechat.ui.activity.ZWalletActivity;
import com.zykj.samplechat.ui.activity.ZhuanZhangActivity;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by csh
 * Created date 2016/12/7.
 * Description 查走势、查余额、查流水
 */

public class FastIPluginModule implements IPluginModule,IRongCallback.ISendMessageCallback {
    private int pos = 0;
    protected CompositeSubscription mCompositeSubscription;
    Fragment fragment;
    private int[] icos = new int[]{R.drawable.chongzhi1,R.drawable.yue1,R.drawable.zhuanzhang1};
    private String[] strs = new String[]{"充值","提现","转账"};
    String ider;
    public FastIPluginModule(int pos) {
        this.pos = pos;
    }
    Context context ;
    @Override
    public Drawable obtainDrawable(Context context) {
        this.context=context;
        return ContextCompat.getDrawable(context, icos[pos]);
    }

    @Override
    public String obtainTitle(Context context) {
        return strs[pos];
    }
    private String  idxx;
    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        this.fragment = fragment;
        if(pos == 0){
        //    RongIM.getInstance().startPrivateChat(context,"2625","充值客服");
            QuanJu.getQuanJu().gokefu(context);
         //   fragment.startActivity(new Intent(fragment.getContext(),CongZhiActivity.class));
        }else if(pos == 1){
            Intent intent = new Intent(fragment.getContext(),ZWalletActivity.class);
            intent.putExtra("biaoshi",1);
            fragment.startActivity(intent);
//            if(flag){
//                flag = false;
//                ider = rongExtension.getTargetId();
//                TextMessage textMessage = TextMessage.obtain(strs[pos]);
//                Message message = Message.obtain(ider, Conversation.ConversationType.GROUP,textMessage);
//                RongIM.getInstance().sendMessage(message, null, null, this);
//                handler.sendEmptyMessageDelayed(1,5000);
//            }else{
//                Toast.makeText(fragment.getContext(),"操作过于频繁，请稍候再试",Toast.LENGTH_SHORT).show();
//            }
        }else if(pos == 2){//2497
            Intent intent =new Intent(fragment.getContext(),ZhuanZhangActivity.class);
            intent.putExtra("id",""+rongExtension.getTargetId());
            idxx=""+rongExtension.getTargetId();
            rongExtension.startActivityForPluginResult(intent,200,this);
        }
    }
    public byte[] encode( String content, String state) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("content", ""+content);
            jsonObj.put("extra", state);
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==200){
            ZhuanZhangMessage moneyMessage = new ZhuanZhangMessage(encode(data.getStringExtra("content"), data.getStringExtra("extra")));
            Message myMessage = Message.obtain(idxx, Conversation.ConversationType.PRIVATE, moneyMessage);
            RongIM.getInstance().sendMessage(myMessage, "您收到来自好友的转账", "", new RongIMClient.SendMessageCallback() {
                @Override
                public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                    Log.d("rongmoney", integer + "");
                }

                @Override
                public void onSuccess(Integer integer) {
                    Log.d("rongmoney", integer + "");
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("key", Const.KEY);
//                    map.put("uid", Const.UID);
//                    map.put("function", "SelectIsLei");
//                    map.put("packetid", hongId);
//                    map.put("teamid", fid);
//                    String json = StringUtil.toJson(map);
//                    try {
//                        String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//                        SelectIsLei(data);
//                    } catch (Exception e) {
//                    }
                }
            }, new RongIMClient.ResultCallback<Message>() {
                @Override
                public void onSuccess(Message message) {
                    Log.d("rongmoney", message.toString() + "");
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d("rongmoney", errorCode + "");
                }
            });
        }

    }

    @Override
    public void onAttached(Message message) {

    }

    @Override
    public void onSuccess(Message message) {
        if (pos == 1) {
            Map<String, Object> map = new HashMap<>();
            map.put("key", Const.KEY);
            map.put("uid", Const.UID);
            map.put("function", "SendYue");
            map.put("userid", new UserUtil(fragment.getContext()).getUserId());
            map.put("teamid", ider);
            String json = StringUtil.toJson(map);
            try {
                String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                SendYue(data);
            } catch (Exception e) {
            }
        }else if (pos == 2){
            Map<String, Object> map = new HashMap<>();
            map.put("key", Const.KEY);
            map.put("uid", Const.UID);
            map.put("function", "SendLiuShui");
            map.put("userid", new UserUtil(fragment.getContext()).getUserId());
            map.put("teamid", ider);
            String json = StringUtil.toJson(map);
            try {
                String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                SendYue(data);
            } catch (Exception e) {
            }
        }
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    public void SendYue(String data){
        Subscription subscription = Net.getService()
                .SendYue(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code== Const.OK){
                        }else {
                        }
                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void onError(Message message, RongIMClient.ErrorCode errorCode) {}

    private boolean flag = true;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            if(msg.what == 1){
                flag = true;
            }
        }
    };
}