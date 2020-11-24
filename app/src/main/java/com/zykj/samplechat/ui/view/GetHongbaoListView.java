package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.model.ChaiHongbao;
import com.zykj.samplechat.model.HongbaoList;
import com.zykj.samplechat.ui.view.base.LoadMoreView;

/**
 * Created by ninos on 2017/8/23.
 */

public interface GetHongbaoListView extends LoadMoreView {
    void success(ChaiHongbao chaiHongbao);

    void success(HongbaoList list);

    void errorChai(String ecxx);
}
