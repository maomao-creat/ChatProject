package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.ZhunZhangBean;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.HotSellerListBean;
import com.zykj.samplechat.presenter.WalleJiLuPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.adapter.MainListAdapter;
import com.zykj.samplechat.ui.view.SendJiLuView;
import com.zykj.samplechat.utils.RecyclerViewAbstract;
import com.zykj.samplechat.utils.RecyclerViewUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.davidsu.library.listener.Listener;
import cn.davidsu.library.loadMore.AbsLoadingMoreView;

public class JiLuTwoActivity extends ToolBarActivity<WalleJiLuPresenter> implements SendJiLuView, Listener.OnItemClickListener, Listener.LoadMoreListener {


    private String bs = "";//1转账 2提现


    @Override
    public void success(List<HotSellerListBean> rs, final int i, final TwinklingRefreshLayout trs) {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ji_lu;
    }

    @Override
    public void initListeners() {//初始化
        bs = getIntent().getStringExtra("bs");
        tvAction.setText("");
        showDialog();
        chushihualb();//初始化下拉列表
    }

    //下拉列表
    private MainListAdapter mMainListAdapter;
    private static int MAX_SIZE = 35;
    RecyclerViewUtil rvu;

    void chushihualb() {
        //---------------------------------listView 开始

        rvu = new RecyclerViewUtil(getWindow().getDecorView(), this, this, this, new MainListAdapter(3, this));//new MainListAdapter();  这个是适配器
        rvu.setRecyclerViewAbstract(new RecyclerViewAbstract() {
            @Override
            public void shuaxin(MainListAdapter mMainListAdapter) {
                sendliebiao();
                //  Toast.makeText(getContext(), "刷新" , Toast.LENGTH_SHORT).show();
//                mMainListAdapter.setDataList(getPageData(10));
            }

            @Override
            public void shangla(MainListAdapter mMainListAdapter) {
                // Toast.makeText(getContext(), "加载更多" , Toast.LENGTH_SHORT).show();
//                mMainListAdapter.addData(getPageData(5));
                sendliebiao();
            }
        });
        //mMainListAdapter.addHeaderView(mHeaderView);添加头部

    }

    void sendliebiao() {//转账记录
        HttpUtils.GetTransferList(new SubscriberRes<ZhunZhangBean>(rvu.getRecyclerView()) {
            @Override
            public void onSuccess(ZhunZhangBean userBean) {
                rvu.setDataList(userBean.getData());
                dismissDialog();
            }

            @Override
            public void completeDialog() {
                dismissDialog();
            }
        },rvu.getindex(),"10");
    }

    @Override
    public WalleJiLuPresenter createPresenter() {
        return null;
    }


    @Override
    protected CharSequence provideTitle() {//    private String bs = "";//1转账 2提现
        return "转账记录";
    }

    @Override
    public void loadMore() {
        if (rvu.getMainListAdapter().getDataList().size() > MAX_SIZE) {//无刷新结果
            rvu.getMainListAdapter().setLoadMoreState(AbsLoadingMoreView.STATE_END);
        } else {
            rvu.loadData(false);
        }
    }

    @Override
    public void onItemClick(Object o, View view, int i, int i1) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
