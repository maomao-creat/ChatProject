package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.model.MyHYLB;
import com.zykj.samplechat.ui.view.base.LoadMoreView;

import java.util.ArrayList;

/**
 * Created by ninos on 2016/7/14.
 */
public interface TeamYaoqingView extends LoadMoreView {
    void successFound(ArrayList<MyHYLB> list);
    void errorFound();
    void refresh(boolean refreshing);
    void success();
}
