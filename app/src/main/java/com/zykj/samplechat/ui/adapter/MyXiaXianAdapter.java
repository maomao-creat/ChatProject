package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zykj.samplechat.ui.fragment.MyXiaXianFragment;


/**
 * 商品展示的适配器
 * Created by 11655 on 2016/10/23.
 */

public class MyXiaXianAdapter extends FragmentPagerAdapter {
    private static int PAGE_COUNT;//表示要展示的页面数量
    private Context mContext;

    public MyXiaXianAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
        PAGE_COUNT=4;

    }

    @Override
    public Fragment getItem(int position) {
        return MyXiaXianFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {//设置标题
        switch (position) {
            case 0:
                return "一级下线";
            case 1:
                return "二级下线";
            case 2:
                return "三级下线";
            case 3:
                return "其他";
            default:break;

        }
        return null;
    }
}
