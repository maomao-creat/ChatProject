package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.view.TeamCreateView;

import java.util.ArrayList;

/**
 * Created by ninos on 2016/7/14.
 */
public class TeamCreatePresenter extends RefreshAndLoadMorePresenter<TeamCreateView> {

    private ArrayList<Friend> list=new ArrayList<>();

    @Override
    public void getData(int page, int count) {}

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
//                            view.successFound(list);
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

    public ArrayList<Friend> getList() {
        return list;
    }
}