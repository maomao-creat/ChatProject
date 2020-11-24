package com.zykj.samplechat.rongc.provider;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.rongc.messge.MoneyMessage;
import com.zykj.samplechat.ui.activity.SendMoneyActivity;
import com.zykj.samplechat.utils.Bun;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ninos on 2017/8/22.
 */

public class SendMoneyProvider implements IPluginModule {
    private Context mContext;
    String ider = "";
    String type = "";
    String fid = "";
    private RongExtension rongExtension;
    protected CompositeSubscription mCompositeSubscription;
    private Fragment fragment;

    public SendMoneyProvider() {
    }

    private void startRecordActivity() {
        rongExtension.startActivityForPluginResult(new Intent(fragment.getContext(),
                SendMoneyActivity.class).putExtra("data", new Bun().
                putString("fid", fid).putString("type", type).
                putString("teamid", fid).putString("type", type).ok()), 21, this);
    }

    public byte[] encode(String hongbaoid, String amount, String content, String name, String photo, String num, String state) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("hongbaoId", hongbaoid);
            jsonObj.put("amount", amount);
            double d=Double.parseDouble(amount);
            int i = (new Double(d)).intValue();
            jsonObj.put("desc", i+"-"+content);
            jsonObj.put("content", "");
            jsonObj.put("extra", state);
            jsonObj.put("photo", photo);
            jsonObj.put("name", name);
            jsonObj.put("num", num);
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
    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.hongbao1);
    }

    @Override
    public String obtainTitle(Context context) {
        return "红包";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        this.fragment = fragment;
        this.rongExtension = rongExtension;
        ider = rongExtension.getTargetId();
        fid = ider;
        type = rongExtension.getConversationType().getName();
        startRecordActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 21 && resultCode == 22) {
            final String hongId = data.getStringExtra("hongbaoId");
            MoneyMessage moneyMessage = new MoneyMessage(encode(data.getStringExtra("hongbaoId"), data.getStringExtra("amount"), data.getStringExtra("content"), data.getStringExtra("name"), data.getStringExtra("photo"), data.getStringExtra("num"), data.getStringExtra("state")));
            Message myMessage = Message.obtain(fid, type.equals("group") ? Conversation.ConversationType.GROUP : Conversation.ConversationType.PRIVATE, moneyMessage);
            RongIM.getInstance().sendMessage(myMessage, "您收到来自好友的红包消息", "", new RongIMClient.SendMessageCallback() {
                @Override
                public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                    Log.d("rongmoney", integer + "");
                }

                @Override
                public void onSuccess(Integer integer) {
                    Log.d("rongmoney", integer + "");

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

    public void SelectIsLei(String data){
        Subscription subscription = Net.getService()
                .SelectIsLei(data)
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

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }
}