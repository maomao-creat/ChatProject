package com.zykj.samplechat.rongc.messge;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by ninos on 2017/8/22.
 */

/*
* 注解名：MessageTag ；属性：value ，flag； value 即 ObjectName 是消息的唯一标识不可以重复，
* 开发者命名时不能以 RC 开头，避免和融云内置消息冲突；flag 是用来定义消息的可操作状态。
*如下面代码段，自定义消息名称 CustomizeMessage ，vaule 是 app:custom ，
* flag 是 MessageTag.ISCOUNTED | MessageTag.ISPERSISTED 表示消息计数且存库。
* app:RedPkgMsg: 这是自定义消息类型的名称，测试的时候用"app:RedPkgMsg"；
* */
@MessageTag(value = "ZhuanZhangMessage", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class ZhuanZhangMessage extends MessageContent {
    private String extra;
    private String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }




    //给消息赋值。
    public ZhuanZhangMessage(Parcel in) {
        try {
            setExtra(ParcelUtils.readFromParcel(in));
            setContent(ParcelUtils.readFromParcel(in));
        } catch (Exception ex) {

        }
    }

    public ZhuanZhangMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("extra"))
                setExtra(jsonObj.optString("extra"));
            if (jsonObj.has("content"))
                setContent(jsonObj.optString("content"));

        } catch (JSONException e) {
        }

    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<ZhuanZhangMessage> CREATOR = new Creator<ZhuanZhangMessage>() {

        @Override
        public ZhuanZhangMessage createFromParcel(Parcel source) {
            return new ZhuanZhangMessage(source);
        }

        @Override
        public ZhuanZhangMessage[] newArray(int size) {
            return new ZhuanZhangMessage[size];
        }
    };

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {

            jsonObj.put("extra", getExtra());
            jsonObj.put("content", getContent());

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, getExtra());
        ParcelUtils.writeToParcel(dest, getContent());

    }
}