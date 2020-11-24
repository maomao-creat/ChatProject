package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.presenter.base.HotWeiXinBean;
import com.zykj.samplechat.presenter.base.HotZhiFuBaoBean;
import com.zykj.samplechat.ui.view.base.BaseView;

/**
 * Created by ninos on 2017/8/22.
 */

public interface SendCongZhiView extends BaseView {
    void success(HotWeiXinBean qian);
    void zsuccess(HotZhiFuBaoBean qian);

}
