package com.zykj.samplechat.presenter;

import com.google.gson.Gson;
import com.zykj.samplechat.model.Image;
import com.zykj.samplechat.model.ImageTemp;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.Video;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.PublishVideoView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.Encoder;
import com.zykj.samplechat.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/13.
 */
public class PublishVideoPresenter extends BasePresenterImp<PublishVideoView> {

    private int count = 0;
    ArrayList<ImageTemp> paths = new ArrayList<>();
    private Gson gson = new Gson();

    public void UpLoadImageBefore(ArrayList<Image> list) {

        if(list.size() <= count){
            view.toast("上传成功");
            view.uploadImage(paths);
        }else {
            Image image = list.get(count);
            Map map = new HashMap();
            map.put("key", Const.KEY);
            map.put("uid", Const.UID);
            map.put("function", "UpLoadImage");
            map.put("fileName", image.FilePath);
            map.put("filestream", Encoder.encodeBase64File(image.FilePath));
            String json = StringUtil.toJson(map);
            try {
                String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                UpLoadImage(data,list);
            } catch (Exception ex) {
                ex.toString();
            }
        }
    }

    public void UpLoadImage(String data, final ArrayList<Image> list){
        Subscription subscription = Net.getService()
                .UpLoadImage(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<ImageTemp>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.toString();
                    }

                    @Override
                    public void onNext(Res<ImageTemp> res) {
                        if (res.code== Const.OK){
                            paths.add(res.data);
                            count++;
                            UpLoadImageBefore(list);
                        }else {

                        }
                    }
                });

        addSubscription(subscription);
    }

    public void SaveDongtai(String data){
        Subscription subscription = Net.getService()
                .SaveDongtai(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.toString();
                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code== Const.OK){
                            view.saveSuccess();
                        }else {

                        }
                    }
                });

        addSubscription(subscription);
    }

    public void UpLoadVideo(String data) {
        try {
            Subscription subscription = Net.getService()
                    .UpLoadVideo(data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Res>() {
                        @Override
                        public void onCompleted() {}
                        @Override
                        public void onError(Throwable e) {}
                        @Override
                        public void onNext(Res videoRes) {
                            if (videoRes.code== Const.OK){
                                String jsonstr = gson.toJson(videoRes.data);
                                view.successUpload(gson.fromJson(jsonstr, Video.class));
                            }else{
                                view.errorUpload();
                            }
                        }
                    });
            addSubscription(subscription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
