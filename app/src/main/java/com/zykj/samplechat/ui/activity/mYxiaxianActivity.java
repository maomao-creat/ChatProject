package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.ui.adapter.MyXiaXianAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class mYxiaxianActivity extends AppCompatActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_action)
    TextView tvAction;
    @Bind(R.id.zhxxx)
    TextView zhxxx;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        fm = getSupportFragmentManager();
//        为viewpager设置适配器
        mViewPager.setAdapter(new MyXiaXianAdapter(mYxiaxianActivity.this, fm));

        mTabLayout.setupWithViewPager(mViewPager);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvTitle.setText("我的下线");
        tvAction.setVisibility(View.GONE);
        zhxxx.setText("当前账户信息:"+getIntent().getStringExtra("bs"));
    }


}
