package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Group;
import com.zykj.samplechat.model.ImageTemp;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.view.GroupFriendsView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.Encoder;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/13.
 */
public class GroupFriendsPresenter extends RefreshAndLoadMorePresenter<GroupFriendsView> {

    private ArrayList<Group> list = new ArrayList<>();

    @Override
    public void getData(final int page, final int count) {
        int id = view.getFriendId();
        list.clear();
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "SelectDongtaiByFriendId");
        map.put("friendid", id);
        map.put("startRowIndex", (page - 1) * count);
        map.put("maximumRows", count);
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            Subscription subscription = Net.getService()
                    .GetDongtaiPageByFriends(data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Res<ArrayList<Group>>>() {

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.refresh(false);
                        }

                        @Override
                        public void onNext(Res<ArrayList<Group>> res) {
                            if (res.code == Const.OK) {
                                view.success(res.data,res.SpaceImagePath);
                                setDataStatus(page, count, res);
                                view.refresh(false);
                            } else {
                                view.refresh(false);
                            }
                        }
                    });

            addSubscription(subscription);
        } catch (Exception e) {
        }
    }

    public void GetUserInfor(String data){
//        Subscription subscription = Net.getService()
//                .GetUserInfor(data)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Res<Friend>>() {
//
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Res<Friend> res) {
//                        if (res.code== Const.OK){
//                          //  view.successUserInfo(res.data);
//                        }else {
//
//                        }
//                    }
//                });

       // addSubscription(subscription);
    }

    public void UpLoadImage(String path,String id){
        try {
            String fileStream = Encoder.getEnocodeStr(path);

            Map map = new HashMap();
            map.put("key", Const.KEY);
            map.put("uid",Const.UID);
            map.put("function","UpLoadImage");
            map.put("fileName",path);
            map.put("filestream",fileStream);
            String json = StringUtil.toJson(map);
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);

            Subscription subscription = Net.getService()
                    .UpLoadImage(data)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Res<ImageTemp>>() {
                        @Override
                        public void onNext(Res<ImageTemp> res) {
                            if (res.code == Const.OK) {

                                String p = res.data.BigImagePath;

                                view.toast("上传成功");

                                Map map = new HashMap();
                                map.put("key", Const.KEY);
                                map.put("uid",Const.UID);
                                map.put("function","SaveSpaceImage");
                                map.put("userid",new UserUtil(view.getContext()).getUserId());
                                map.put("imagepath",res.data.BigImagePath);
                                String json = StringUtil.toJson(map);
                                try{
                                    String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                                    SaveSpaceImage(data,p);
                                }catch (Exception ex){}

                            } else {
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    });

            addSubscription(subscription);

        } catch (Exception e) {
            e.toString();
        }
    }

    public void SaveSpaceImage(String data, final String p) {
        Subscription subscription = Net.getService()
                .SaveSpaceImage(data)
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
                        if (res.code == Const.OK) {
                            view.topImage(p);
                        } else {

                        }
                    }
                });
        addSubscription(subscription);
    }
}
