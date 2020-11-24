package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.ui.view.base.LoadMoreView;

import java.util.ArrayList;

/**
 * Created by ninos on 2016/7/8.
 */
public interface FriendsContactsView extends LoadMoreView {
    void success(ArrayList<Friend> list);
    void error();
}
