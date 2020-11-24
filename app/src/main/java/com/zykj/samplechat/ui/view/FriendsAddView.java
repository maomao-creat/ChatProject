package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.model.FriendMap;
import com.zykj.samplechat.ui.view.base.LoadMoreView;

import java.util.ArrayList;

/**
 * Created by ninos on 2016/7/7.
 */
public interface FriendsAddView extends LoadMoreView{
    void success(ArrayList<FriendMap> list);
    void successAdd(int Id);
    void error();
}
