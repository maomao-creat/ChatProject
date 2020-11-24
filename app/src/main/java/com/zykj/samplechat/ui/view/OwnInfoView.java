package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.model.User;
import com.zykj.samplechat.ui.view.base.BaseView;

/**
 * Created by ninos on 2016/7/5.
 */
public interface OwnInfoView extends BaseView {
    void successUploadPhoto(User user);
    void successUpload();
    void errorUpload(String error);
    void upImage();
}
