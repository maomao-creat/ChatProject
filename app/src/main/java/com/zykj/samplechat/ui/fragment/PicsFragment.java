package com.zykj.samplechat.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.activity.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class PicsFragment extends BaseFragment {

//    @Bind(R.id.img)
//    ImageViewTouch _Img;

    public PicsFragment() {

        // Required empty public constructor
    }

    public static PicsFragment newInstance(Bundle args) {
        PicsFragment picsFragment = new PicsFragment();
        picsFragment.setArguments(args);
        return picsFragment;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    /**
     * 初始化一些事情
     *
     * @param view
     */
    @Override
    protected void initThings(View view) {
        String picUrl = getArguments().getString("picUrl");
        String imagePath = getArguments().getString("imagePath");
//        _Img.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
//
//        if (!StringUtil.isEmpty(picUrl)) {
//            Glide.with(getActivity())
//                    .load(Const.getbase( picUrl))
//                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
//                    .into(_Img);
//        } else {
//            Glide.with(getActivity())
//                    .load(imagePath)
//                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
//                    .into(_Img);
//        }
//        _Img.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() {
//            @Override
//            public void onSingleTapConfirmed() {
//                finishActivity();
//            }
//        });
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_pics;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }


}
