package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.presenter.WanJiasBean;
import com.zykj.samplechat.ui.view.base.BaseView;

/**
 * Created by ninos on 2016/7/6.
 */
public interface OwnWanJiaView extends BaseView {
    void successUpload(WanJiasBean wjb);
    void errorUpload();
}
