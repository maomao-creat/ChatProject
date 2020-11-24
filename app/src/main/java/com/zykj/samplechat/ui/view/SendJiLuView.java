package com.zykj.samplechat.ui.view;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zykj.samplechat.presenter.HotSellerListBean;
import com.zykj.samplechat.ui.view.base.BaseView;

import java.util.List;

/**
 * Created by ninos on 2017/8/22.
 */

public interface SendJiLuView extends BaseView {
    void success(List<HotSellerListBean> rs , final int i, final TwinklingRefreshLayout trs);

}
