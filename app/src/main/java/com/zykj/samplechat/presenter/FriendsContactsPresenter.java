package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.view.FriendsContactsView;

import java.util.ArrayList;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.message.TextMessage;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/8.
 */
public class FriendsContactsPresenter extends RefreshAndLoadMorePresenter<FriendsContactsView> {
    @Override
    public void getData(int page, int count) {

    }

    public void ContactsMatch(String data){
        Subscription subscription = Net.getService()
                .ContactsMatch(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<ArrayList<Friend>>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res<ArrayList<Friend>> res) {
                        if (res.code== Const.OK){
                            view.success(res.data);
                        }else {
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void SaveFriend(String data, final int id){
        Subscription subscription = Net.getService()
                .SaveFriend(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code== Const.OK){
                            view.dismissDialog();
                            view.toast("好友请求已发送");
                            /**
                             * 发送消息。
                             *
                             * @param type        会话类型。
                             * @param targetId    目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
                             * @param content     消息内容。
                             * @param pushContent push 时提示内容，为空时提示文本内容。
                             * @param callback    发送消息的回调。
                             * @return
                             */
                            TextMessage textMessage = TextMessage.obtain("好友请求");
                            textMessage.setExtra("jiahaoyou");

                            RongIM.getInstance().getRongIMClient().sendMessage(Conversation.ConversationType.PRIVATE, id+"",textMessage, "", "", new RongIMClient.SendMessageCallback() {
                                @Override
                                public void onError(Integer messageId, RongIMClient.ErrorCode e) {

                                }

                                @Override
                                public void onSuccess(Integer integer) {
                                    RongIM.getInstance().clearMessages(Conversation.ConversationType.PRIVATE,id+"" );
                                    RongIM.getInstance().getRongIMClient().removeConversation(Conversation.ConversationType.PRIVATE, id+"", new RongIMClient.ResultCallback<Boolean>() {
                                        @Override
                                        public void onSuccess(Boolean aBoolean) {

                                        }

                                        @Override
                                        public void onError(RongIMClient.ErrorCode errorCode) {

                                        }
                                    });

                                }
                            });
                            view.toast("好友请求已发送");
                        }else {
                            view.dismissDialog();
                            view.error();
                        }
                    }
                });

        addSubscription(subscription);
    }
}
