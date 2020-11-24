package com.zykj.samplechat.ui.view;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zykj.samplechat.presenter.WanJiaListBean;
import com.zykj.samplechat.ui.view.base.BaseView;

import java.util.List;

/**
 * Created by ninos on 2017/8/22.
 */

public interface SendMyWanView extends BaseView {
    void success(List<WanJiaListBean> rs, final int i, final TwinklingRefreshLayout trs);

}
