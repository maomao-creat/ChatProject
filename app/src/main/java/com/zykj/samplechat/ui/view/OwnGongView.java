package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.model.Gonggao;
import com.zykj.samplechat.ui.view.base.LoadMoreView;

import java.util.ArrayList;

/**
 * Created by ninos on 2016/7/27.
 */
public interface OwnGongView extends LoadMoreView {
    void success(ArrayList<Gonggao> list);
    String getJsonData();
}
