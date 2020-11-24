package com.zykj.samplechat.presenter;

import com.google.gson.Gson;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.Video;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.UploadVideoView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/18.
 */
public class UploadVideoPresenter extends BasePresenterImp<UploadVideoView> {
    private Gson gson = new Gson();
    public void UpLoadVideo(String data) {
        try {
            Subscription subscription = Net.getService()
                    .UpLoadVideo(data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Res>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable e) {
                            view.successUpload("上传错误");
                        }
                        @Override
                        public void onNext(Res videoRes) {
                            if (videoRes.code == Const.OK){
                                String jsonstr = gson.toJson(videoRes.data);
                                view.successUpload(gson.fromJson(jsonstr, Video.class).VideoPath);
                            }else{
                                view.successUpload(videoRes.error);
                            }
                        }
                    });
            addSubscription(subscription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}