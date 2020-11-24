package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.sshyBean;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.FriendsInfoView;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/8.
 */
public class FriendsInfoPresenter extends BasePresenterImp<FriendsInfoView> {
    public void GetUserInfor(String data) {
        Subscription subscription = Net.getService()
                .GetUserInfor(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<sshyBean>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.errorFound();
                    }

                    @Override
                    public void onNext(Res<sshyBean> res) {
                        if (res.code == Const.OK) {
                            view.successFound(res.data);
                        } else {
                            view.errorFound();
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void SaveFriend(String data, final int id) {
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
                        if (res.code == Const.OK) {
                            view.toast("好友请求已发送");
                            /**
                             * 发送消息。
                             * @param type        会话类型。
                             * @param targetId    目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
                             * @param content     消息内容。
                             * @param pushContent push 时提示内容，为空时提示文本内容。
                             * @param callback    发送消息的回调。
                             * @return
                             */
                            TextMessage textMessage = TextMessage.obtain("好友请求");
                            textMessage.setExtra("jiahaoyou");
                            Message myMessage = Message.obtain(id+"", Conversation.ConversationType.PRIVATE, textMessage);
                            RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
                                @Override
                                public void onAttached(Message message) {}
                                @Override
                                public void onSuccess(Message message) {
                                    RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, id+"", new RongIMClient.ResultCallback<Boolean>() {
                                        @Override
                                        public void onSuccess(Boolean aBoolean) {}
                                        @Override
                                        public void onError(RongIMClient.ErrorCode errorCode) {}
                                    });
                                }
                                @Override
                                public void onError(Message message, RongIMClient.ErrorCode errorCode) {}
                            });
                            view.dismissDialog();
                        } else {
                            view.dismissDialog();
                        }
                    }
                });
        addSubscription(subscription);
    }
}
