package com.zykj.samplechat.ui.activity.base;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;
import com.zykj.samplechat.ui.view.base.LoadMoreView;
import com.zykj.samplechat.ui.view.base.OnItemClickListener;

import java.util.ArrayList;

/**
 * SwipeRecycleViewActivity
 * Created by xianguangjin on 15/12/23.
 */
public abstract class SwipeRecycleViewFragment<P extends RefreshAndLoadMorePresenter, A extends BaseAdapter, M>
        extends SwipeRefreshFragment<P> implements OnItemClickListener<M>, LoadMoreView {

    //@Bind(R.id.recycler_view)
    public RecyclerView recyclerView;
    public A adapter;
    public RecyclerView.LayoutManager layoutManager;

    public int page = 1;
    public int count = 10;

    @Override
    protected void initThings(View view) {
        super.initThings(view);
        this.layoutManager = provideLayoutManager();
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        this.adapter = provideAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(this);
        if (layoutManager instanceof LinearLayoutManager) {
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
//                    int lastPos = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
//                    if (lastPos >= adapter.data.size() - 1) {
//                        snb("加载更多", recyclerView);
//                    }

                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    onscroll(recyclerView, dx, dy);
                    if (canScrollDown(recyclerView)) {
                    } else {
                        loadMore();
                    }
                }
            });

        }
    }


    private boolean canScrollDown(RecyclerView recyclerView) {
        return ViewCompat.canScrollVertically(recyclerView, 1);
    }

    /**
     * RecycleView监听函数
     *
     * @param recyclerView
     * @param dx
     * @param dy
     */
    public void onscroll(RecyclerView recyclerView, int dx, int dy) {


    }

    /**
     * @return 提供Adapter
     */
    protected abstract A provideAdapter();

    /**
     * @return 提供LayoutManager
     */
    protected abstract RecyclerView.LayoutManager provideLayoutManager();


    /**
     * 基本的绑定数据
     *
     * @param data
     */
    public void bd(ArrayList<M> data) {
        if (page == 1) {
            adapter.data.clear();
            adapter.addDatas(data);
        } else {
            adapter.addMore(data);
        }

    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        page = 1;
        presenter.getData(page, count);
    }

    @Override
    public void loadMore() {
        page++;
        presenter.getData(page, count);
    }


    @Override
    public void hasMore() {
        adapter.hasMore();
    }

    @Override
    public void noMore() {
        adapter.noMore();
    }

    @Override
    public void loadMore(String tip) {
        if (adapter.getStatus() == BaseAdapter.STATUS_HASMORE) {
            page++;
            presenter.getData(page, count);
            adapter.loading(tip);
        }
    }

    @Override
    public void hasMore(String tip) {
        adapter.hasMore(tip);
    }

    @Override
    public void noMore(String tip) {
        adapter.noMore(tip);
    }


}
