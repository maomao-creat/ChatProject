package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.ui.view.base.BaseView;

/**
 * Created by ninos on 2017/8/22.
 */

public interface SendMoneyView extends BaseView {
    void success(String hongbaoId);

    void error(String msg);

    void successCheck();
}
