package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.model.Comment;
import com.zykj.samplechat.model.Group;
import com.zykj.samplechat.ui.view.base.BaseView;

import java.util.ArrayList;

/**
 * Created by ninos on 2016/7/14.
 */
public interface GroupInfoView extends BaseView{
    void success(ArrayList<Group> data);
    void successLike();
    void successLikeCancel();
    void getDongtai(Group group);
    void commentSuccess(Comment comment);
    void replySuccess(Comment comment);
    void successDelComment();
    void successDelete();
}
