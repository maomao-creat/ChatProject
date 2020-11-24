package com.zykj.samplechat.presenter;

import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.MessageView;
import java.util.List;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * Created by csh
 * Created date 2016/11/4.
 * Description 搜索聊天记录
 */
public class SearchChattingPresenter extends BasePresenterImp<MessageView> {
    public void searchMessage(Conversation conversation, String filter){
        RongIMClient.getInstance().searchMessages(conversation.getConversationType(),
        conversation.getTargetId(), filter, 50, 0, new RongIMClient.ResultCallback<List<Message>>(){
            @Override
            public void onSuccess(List<Message> messages) {
                view.success(messages);
            }
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });
    }
}