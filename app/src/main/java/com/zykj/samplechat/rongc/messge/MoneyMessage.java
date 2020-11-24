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
@MessageTag(value = "CustomHongbaoMessage", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class MoneyMessage extends MessageContent {
    private String hongbaoId;
    private String amount;
    private String desc;
    private String extra;
    private String content;
    private String photo;
    private String name;
    private String num;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHongbaoId() {
        return hongbaoId;
    }

    public void setHongbaoId(String hongbaoId) {
        this.hongbaoId = hongbaoId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    //给消息赋值。
    public MoneyMessage(Parcel in) {
        try {
            setHongbaoId(ParcelUtils.readFromParcel(in));
            setAmount(ParcelUtils.readFromParcel(in));
            setDesc(ParcelUtils.readFromParcel(in));
            setExtra(ParcelUtils.readFromParcel(in));
            setContent(ParcelUtils.readFromParcel(in));
            setPhoto(ParcelUtils.readFromParcel(in));
            setName(ParcelUtils.readFromParcel(in));
            setNum(ParcelUtils.readFromParcel(in));
        } catch (Exception ex) {

        }
    }

    public MoneyMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("hongbaoId"))
                setHongbaoId(jsonObj.optString("hongbaoId"));
            if (jsonObj.has("amount"))
                setAmount(jsonObj.optString("amount"));
            if (jsonObj.has("desc"))
                setDesc(jsonObj.optString("desc"));
            if (jsonObj.has("extra"))
                setExtra(jsonObj.optString("extra"));
            if (jsonObj.has("content"))
                setContent(jsonObj.optString("content"));
            if (jsonObj.has("photo"))
                setPhoto(jsonObj.optString("photo"));
            if (jsonObj.has("name"))
                setName(jsonObj.optString("name"));
            if (jsonObj.has("num"))
                setNum(jsonObj.optString("num"));

        } catch (JSONException e) {
        }

    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<MoneyMessage> CREATOR = new Creator<MoneyMessage>() {

        @Override
        public MoneyMessage createFromParcel(Parcel source) {
            return new MoneyMessage(source);
        }

        @Override
        public MoneyMessage[] newArray(int size) {
            return new MoneyMessage[size];
        }
    };

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("hongbaoId", getHongbaoId());
            jsonObj.put("amount", getAmount());
            jsonObj.put("desc", getDesc());
            jsonObj.put("extra", getExtra());
            jsonObj.put("content", getContent());
            jsonObj.put("photo", getPhoto());
            jsonObj.put("name", getName());
            jsonObj.put("num", getNum());
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
        ParcelUtils.writeToParcel(dest, getHongbaoId());
        ParcelUtils.writeToParcel(dest, getAmount());
        ParcelUtils.writeToParcel(dest, getDesc());
        ParcelUtils.writeToParcel(dest, getExtra());
        ParcelUtils.writeToParcel(dest, getContent());
        ParcelUtils.writeToParcel(dest, getPhoto());
        ParcelUtils.writeToParcel(dest, getName());
        ParcelUtils.writeToParcel(dest, getNum());
    }
}