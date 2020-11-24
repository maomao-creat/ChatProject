package com.zykj.samplechat.presenter;

import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.FriendsMenuView;

/**
 * Created by ninos on 2016/7/13.
 */
public class FriendsMenuPresenter extends BasePresenterImp<FriendsMenuView> {
    public void DeleteFriend(int friendid){
        HttpUtils.DeleteFriend(new SubscriberRes<String>() {
            @Override
            public void onSuccess(String userBean) {
                view.successDelete();
            }

            @Override
            public void completeDialog() {
            }
        }, friendid);
    }
}