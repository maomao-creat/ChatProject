package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.Group;
import com.zykj.samplechat.ui.view.base.LoadMoreView;

import java.util.ArrayList;

/**
 * Created by ninos on 2016/7/13.
 */
public interface GroupFriendsView extends LoadMoreView {
    void success(ArrayList<Group> data,String path);
    void successUserInfo(Friend friend);
    void refresh(boolean refreshing);
    int getFriendId();
    void topImage(String path);
}
