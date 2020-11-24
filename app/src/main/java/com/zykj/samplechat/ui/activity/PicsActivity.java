package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Image;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.adapter.NavigateViewPagerAdapter;
import com.zykj.samplechat.ui.fragment.PicsFragment;
import com.zykj.samplechat.utils.Bun;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class PicsActivity extends ToolBarActivity {


    private int pos;
    private ArrayList<Image> pics;
    private String picsStr;

    public static boolean isLocal = false;


    @Bind(R.id.viewpager)
    ViewPager _ViewPage;
    private int picsCount;
    private boolean isHidePage = false;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "图片";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pics;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        pos = getIntentData().getInt("pos");
        picsStr = getIntentData().getString("pics");
        isHidePage = getIntentData().getBoolean("isHidePage");

//        Toast.makeText(getContext(),"shiwo",Toast.LENGTH_LONG).show();

        tvAction.setVisibility(isHidePage ? View.GONE : View.VISIBLE);

        pics = new Gson().fromJson(picsStr, new TypeToken<List<Image>>() {
        }.getType());

        picsCount = pics.size();

        tvAction.setText(pos+1+"/" + picsCount);

        NavigateViewPagerAdapter adapter = new NavigateViewPagerAdapter(getSupportFragmentManager());

        for (Image pic : pics) {
            adapter.addFragment(PicsFragment.newInstance(new Bun().putString("picUrl", pic.PictureUrl).putString("imagePath", pic.ImagePath).ok()));
        }
        _ViewPage.setAdapter(adapter);

        _ViewPage.setCurrentItem(pos);

        toolBar.setBackgroundResource(R.color.transparent);

        _ViewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position++;
                tvAction.setText(position + "/" + picsCount);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }


    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }
}
