package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.ui.view.base.BaseView;

import java.util.ArrayList;

/**
 * Created by ninos on 2016/7/14.
 */
public interface TeamInfoView extends BaseView {
    void successTeam(Team team);
    void successFriends(ArrayList<Friend> list);
    void other(int type, String data);
    void success();
    void successSendYue();
    void successJiesan();
    void error();
}