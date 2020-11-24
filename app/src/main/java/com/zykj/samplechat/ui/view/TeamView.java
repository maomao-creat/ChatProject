package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.ui.view.base.LoadMoreView;

import java.util.ArrayList;

/**
 * Created by ninos on 2016/7/14.
 */
public interface TeamView extends LoadMoreView {
    void success(ArrayList<Team> list);
    void errorFound();
    void refresh(boolean refreshing);
    void success(String str);
}
