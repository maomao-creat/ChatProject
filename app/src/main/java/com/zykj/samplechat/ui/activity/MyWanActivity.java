package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.Footer.LoadingView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.SentRecords;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.WalleMyWanPresenter;
import com.zykj.samplechat.presenter.WanJiaListBean;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.adapter.WanJiaAdapter;
import com.zykj.samplechat.ui.view.SendMyWanView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.PicassoUtil;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class MyWanActivity extends ToolBarActivity<WalleMyWanPresenter> implements SendMyWanView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_action)
    TextView tvAction;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.tr)
    TwinklingRefreshLayout tr;
    private String bs = "";//1发包 2中奖 3 中雷 3充值 5提现
    List<SentRecords> shuju;

    @Override
    public void success(List<WanJiaListBean> rs, final int i, final TwinklingRefreshLayout trs) {
        //  shuju = qian;
        dismissRefresh(i, trs);
        int x = getPageIndex(recycler);
        setPageIndex(recycler, x+1);
        mAdapter.setbiaoshi(bs);
        mAdapter.addAllData(rs);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_wabnjia;
    }

    @Override
    public void initListeners() {//初始化
        shuju = new ArrayList<SentRecords>();
        bs = getIntent().getStringExtra("bs");
        refresh();
        setMyAdapter();
    }

    //精品店铺部分
    private WanJiaAdapter mAdapter;
    private Object obj = new Object();

    void setMyAdapter() {
        mAdapter = new WanJiaAdapter(bs, this, new ArrayList<WanJiaListBean>(), new WanJiaAdapter.OnMyAdapterLinstener() {

        });
        mAdapter.setClickListener(new WanJiaAdapter.OnItemClickListener() {

            @Override
            public void onClick(View view,String name,String url, int code, View vbg, int position) {
                Intent intent = new Intent(MyWanActivity.this,WanJiadActivity.class);
                intent.putExtra("code",code);
                intent.putExtra("name",""+name);
                intent.putExtra("url",""+url);
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recycler.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置增加或删除条目的动画
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(mAdapter);
        //Picasso 图片加载
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    PicassoUtil.picassoResumeTag(getContext(), obj);   //开始加载
                } else {
                    PicassoUtil.picassoPauseTag(getContext(), obj);   //停止加载
                }
            }
        });
    }

    void loadData(final int i, final TwinklingRefreshLayout trs) {
        fabao(i, trs);
    }

    void refresh() {
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.mipmap.arrow);
        headerView.setTextColor(0xff745D5C);
        tr.setHeaderView(headerView);
        LoadingView loadingView = new LoadingView(getContext());
        tr.setBottomView(loadingView);
        //不加载刷新功能
        //refreshLayout.setPureScrollModeOn(true);
        tr.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setPageIndex(recycler, 1);
                        mAdapter.clearData();
                        loadData(1, refreshLayout);
                    }
                }, 100);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData(0, refreshLayout);
                    }
                }, 100);
            }
        });
        //自动执行一次刷新
        tr.startRefresh();
    }

    protected int getPageIndex(View refreshView) {
        Object tag = refreshView.getTag(R.id.page_index);
        if (tag == null)
            return 1;
        return Integer.parseInt(tag.toString());
    }

    protected void setPageIndex(View refreshView, int index) {
        refreshView.setTag(R.id.page_index, index);
    }

    //结束刷新
    void dismissRefresh(final int i, final TwinklingRefreshLayout refreshLayout) {
        if (i == 1) {
            //刷新
            refreshLayout.finishRefreshing();
        } else {
            //上提
            refreshLayout.finishLoadmore();
        }
    }


    @Override
    public WalleMyWanPresenter createPresenter() {
        return new WalleMyWanPresenter();
    }
    // 信息列表提示框
    private AlertDialog alertDialog1;
    @Override
    protected CharSequence provideTitle() {//1发包 2中奖 3 中雷 3充值 5提现 6收包 7赔付
        return "我的朋友" ;
    }

    void fabao(final int i, final TwinklingRefreshLayout trs) {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("index",getPageIndex(recycler));
        map.put("pageSize", "10");
        map.put("function", "getPlayerList");
        map.put("recommendCode", new UserUtil(getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.getchongzhi(data, i, trs);
        } catch (Exception ex) {
            dismissDialog();
        }
    }
}
