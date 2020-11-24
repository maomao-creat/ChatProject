package com.zykj.samplechat.ui.view;

import com.zykj.samplechat.model.Picture;
import com.zykj.samplechat.ui.view.base.BaseView;

/**
 * Created by csh
 * Created date 2016/12/23.
 * Description 二维码
 */

public interface QRCodeView extends BaseView {
    void success(Picture pic);
    void error();
}