package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.model.ImageTemp;
import com.zykj.samplechat.ui.view.base.BaseView;

import java.util.ArrayList;

/**
 * Created by ninos on 2016/7/13.
 */
public interface PublishGroupView extends BaseView {
    void uploadImage(ArrayList<ImageTemp> list);
    void saveSuccess();
    void errorUpload();
}
