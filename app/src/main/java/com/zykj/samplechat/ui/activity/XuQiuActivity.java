package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.ui.adapter.XuQuAdapter;

import butterknife.ButterKnife;

public class XuQiuActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TextView zhongjian;
    private TextView youbian;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        zhongjian.setText("我的需求");
        youbian.setVisibility(View.INVISIBLE);
        fm = getSupportFragmentManager();
//        为viewpager设置适配器
        mViewPager.setAdapter(new XuQuAdapter(XuQiuActivity.this, fm));

        mTabLayout.setupWithViewPager(mViewPager);

    }


}
