package com.zykj.samplechat.presenter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageLoader;
import com.yancy.imageselector.ImageSelector;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.UploadPhoto;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.OwnInfoView;
import com.zykj.samplechat.utils.Encoder;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.ToolsUtils;
import com.zykj.samplechat.utils.UserUtil;

import java.io.File;
import java.util.HashMap;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/5.
 */
public class OwnInfoPresenter extends BasePresenterImp<OwnInfoView> {
    //private User user;
    private Gson gson = new Gson();
    public void UpLoadPhoto(String path){
        String fileStream = Encoder.bitmapToString(path);
        HttpUtils.UploadQrcode(new SubscriberRes<UploadPhoto>() {
            @Override
            public void onSuccess(UploadPhoto friend) {
                String jsonstr = gson.toJson(friend);
                User user = new UserUtil(view.getContext()).getUser();
                user.setHeadUrl(gson.fromJson(jsonstr, UploadPhoto.class).HeadUrl);
                new UserUtil(view.getContext()).putUser(user);
                view.successUploadPhoto(user);
            }

            @Override
            public void completeDialog() {
            }
        },path,fileStream);
    }

    public void UploadImgByFile(final String info) {
        File file = new File(info);
        RequestBody requestBody=new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("image/png"), file))
                .build();
        HttpUtils.UploadImgByFile(new SubscriberRes<UploadPhoto>() {
                            @Override
                            public void onSuccess(UploadPhoto user) {
                                ChangeHeadUrl(user.Url);
                            }

                            @Override
                            public void completeDialog() {
                            }
                        }
                , requestBody);
    }

    public void ChangeHeadUrl(final String path){
        HttpUtils.ChangeHeadUrl(new SubscriberRes<String>() {
            @Override
            public void onSuccess(String friend) {
                User user = new UserUtil(view.getContext()).getUser();
                user.setHeadUrl(path);
                new UserUtil(view.getContext()).putUser(user);
                ToolsUtils.toast(view.getContext(),"上传成功");
                view.upImage();

                HashMap<String, Object> hashMap = new HashMap<>();
// 头像，mIconUrl 就是您上传头像后的 URL，可以参考 Demo 中的随机头像作为示例
                if (!StringUtil.isEmpty(path)) {
                    hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_FACEURL, Const.BASE+path);
                }
                TIMFriendshipManager.getInstance().modifySelfProfile(hashMap, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e("modifySelfProfile","err code = " + i + ", desc = " + s);
                    }
                    @Override
                    public void onSuccess() {
                        Log.e("modifySelfProfile", "modifySelfProfile success");
                    }
                });


//


//                RongIM.getInstance().refreshUserInfoCache(new UserInfo(UserUtil.getUser().getId() + "", UserUtil.getUser().getNickName(), Uri.parse(Const.getbase(UserUtil.getUser().getHeadUrl()))));
            }

            @Override
            public void completeDialog() {
            }
        },path);
    }
    public void touxiang(Activity activity, ImageLoader loader) {
        ImageConfig imageConfig
                = new ImageConfig.Builder(loader)
                .steepToolBarColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary))
                .titleBgColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary))
                .titleSubmitTextColor(ContextCompat.getColor(view.getContext(), R.color.white))
                .titleTextColor(ContextCompat.getColor(view.getContext(), R.color.white))
                // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
//                .crop()
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();
        ImageSelector.open(activity, imageConfig);   // 开启图片选择器
    }

    public void ChangeInfor(String data){

        Subscription subscription = Net.getService()
                .ChangeInfor(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.errorUpload("个人信息更改失败，请检查您的网络");
                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code== Const.OK){
                            view.successUpload();
                        }else {
                            view.errorUpload("个人信息更改失败，请检查您的网络");
                        }
                    }
                });
        addSubscription(subscription);
    }
}