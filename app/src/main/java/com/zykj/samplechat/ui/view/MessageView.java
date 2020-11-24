package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.ui.view.base.BaseView;

import java.util.List;

import io.rong.imlib.model.Message;

/**
 * Created by csh
 * Created date 2016/12/8.
 * Description 聊天记录
 */
public interface MessageView extends BaseView{
    void success(List<Message> data);
}
