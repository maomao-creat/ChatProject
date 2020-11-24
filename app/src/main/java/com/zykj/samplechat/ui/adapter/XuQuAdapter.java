package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zykj.samplechat.ui.fragment.XuQiuFragment;


/**
 * 商品展示的适配器
 * Created by 11655 on 2016/10/23.
 */

public class XuQuAdapter extends FragmentPagerAdapter {
    private static int PAGE_COUNT;//表示要展示的页面数量
    private Context mContext;

    public XuQuAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
        PAGE_COUNT=5;

    }

    @Override
    public Fragment getItem(int position) {
        return XuQiuFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {//设置标题
        switch (position) {
            case 0:
                return "全    部";
            case 1:
                return "审 核 中";
            case 2:
                return "已 上 架";
            case 3:
                return "已 下 架";
                case 4:
                return "已 驳 回";
            default:break;

        }
        return null;
    }
}
