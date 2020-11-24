package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.ui.view.base.BaseView;

/**
 * Created by ninos on 2017/8/22.
 */

public interface SendTiXianView extends BaseView {
    void success(String qian);
    void successUploadPhoto(String qian);
    void errorUpload(String qian);

}
