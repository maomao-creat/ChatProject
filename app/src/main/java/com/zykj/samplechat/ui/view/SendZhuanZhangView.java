package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.presenter.base.HotWeiXinBean;
import com.zykj.samplechat.ui.view.base.BaseView;

/**
 * Created by ninos on 2017/8/22.
 */

public interface SendZhuanZhangView extends BaseView {
    void success(HotWeiXinBean qian);
    void zsuccess();

}
