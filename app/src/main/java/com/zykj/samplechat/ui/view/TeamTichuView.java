package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.ui.view.base.LoadMoreView;

import java.util.ArrayList;

/**
 * Created by ninos on 2016/7/14.
 */
public interface TeamTichuView extends LoadMoreView {
    void successFound(ArrayList<Friend> list);
    void errorFound();
    void refresh(boolean refreshing);
    void success();
}
