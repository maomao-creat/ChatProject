package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.view.FriendsTuijianView;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/14.
 */
public class FriendsTuijianPresenter extends RefreshAndLoadMorePresenter<FriendsTuijianView> {

    private ArrayList<Friend> list=new ArrayList<>();

    @Override
    public void getData(int page, int count) {

    }

    public void GetFriendForKeyValue(String data){
//        list.clear();
//        Subscription subscription = Net.getService()
//                .GetFriendForKeyValue(data)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Res<ArrayList<FriendMap>>>() {
//
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.errorFound();
//                        view.refresh(false);
//                    }
//
//                    @Override
//                    public void onNext(Res<ArrayList<FriendMap>> res) {
//                        if (res.code== Const.OK){
//                            for (FriendMap friendMap : res.data) {
//                                Friend friend = friendMap.Value;
//                                friend.Id = friendMap.Key;
//                                list.add(friend);
//                            }
//
//                            view.successFound(list);
//
//                            view.refresh(false);
//                        }else {
//                            view.errorFound();
//                            view.refresh(false);
//                        }
//                    }
//                });
//
//        addSubscription(subscription);
    }

    public void YaoQing(String data){
        Subscription subscription = Net.getService()
                .YaoQing(data)
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
                            view.success();
                        }else {
                        }
                    }
                });

        addSubscription(subscription);
    }
}
