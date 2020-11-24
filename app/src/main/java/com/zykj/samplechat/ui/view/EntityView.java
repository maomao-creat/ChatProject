package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.ui.view.base.BaseView;

/**
 * Created by 徐学坤 on 2018/2/1.
 */
public interface EntityView<M> extends BaseView{
    void model(M data);
    void error();
}
