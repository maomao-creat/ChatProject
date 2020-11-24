package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.model.sshyBean;
import com.zykj.samplechat.ui.view.base.BaseView;

/**
 * Created by ninos on 2016/7/8.
 */
public interface FriendsInfoView extends BaseView {
    void successFound(sshyBean friend);
    void errorFound();
}
