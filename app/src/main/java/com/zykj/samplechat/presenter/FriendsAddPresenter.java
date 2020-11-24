package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.FriendMap;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.view.FriendsAddView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.message.TextMessage;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/7.
 */
public class FriendsAddPresenter extends RefreshAndLoadMorePresenter<FriendsAddView> {

    private ArrayList<Friend> list = new ArrayList<>();

    @Override
    public void getData(final int page, final int count) {
        list.clear();
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "SelectApplyList");
        map.put("startRowIndex", (page - 1) * count);
        map.put("maximumRows", count);
        map.put("userid", new UserUtil(view.getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            Subscription subscription = Net.getService()
                    .SelectApplyList(data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Res<ArrayList<FriendMap>>>() {

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.error();
                        }

                        @Override
                        public void onNext(Res<ArrayList<FriendMap>> res) {
                            if (res.code == Const.OK) {
//                                for (FriendMap friendMap : res.data) {
//                                    Friend friend = friendMap.Value;
//                                    list.add(friend);
//                                }
                                view.success(res.data);
                                setDataStatus(page, count, res);
                            } else {
                                view.error();
                            }
                        }
                    });

            addSubscription(subscription);
        } catch (Exception e) {
        }

    }

    public void AgreeFriendAndSaveFriend(String data, final int Id, final int userId) {
        Subscription subscription = Net.getService()
                .AgreeFriendAndSaveFriend(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.error();
                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code == Const.OK) {
                            TextMessage textMessage = TextMessage.obtain("我已成为你的好友，现在我们可以开始聊天了");
                            textMessage.setExtra("jieshou");
                            RongIM.getInstance().getRongIMClient().sendMessage(Conversation.ConversationType.PRIVATE, userId + "", textMessage, "", "", new RongIMClient.SendMessageCallback() {
                                @Override
                                public void onError(Integer messageId, RongIMClient.ErrorCode e) {
                                }

                                @Override
                                public void onSuccess(Integer integer) {
                                }
                            });
                            view.successAdd(Id);
                        } else {
                            view.error();
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void DeleteFriend(int friendid) {
        HttpUtils.DeleteFriend(new SubscriberRes<String>() {
            @Override
            public void onSuccess(String userBean) {
                view.successAdd(-1);
            }

            @Override
            public void completeDialog() {
            }
        }, friendid);
    }
}