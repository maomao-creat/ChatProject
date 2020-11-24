package com.zykj.samplechat.utils;

/**
 * Created by Administrator on 2018/5/9.
 */

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.zykj.samplechat.R;
import com.zykj.samplechat.ui.adapter.MainListAdapter;

import java.util.List;

import cn.davidsu.library.itemdecoration.SpaceItemDecoration;
import cn.davidsu.library.listener.Listener;
import cn.davidsu.library.loadMore.AbsLoadingMoreView;
import cn.davidsu.library.loadMore.DefaultLoadMoreView;
import cn.davidsu.library.util.ViewUtil;

/**
 * 清除缓存
 *RecyclerView 封装使用框架
 */
public class RecyclerViewUtil<T> {

    //RecyclerViewUtil rvu = new RecyclerViewUtil(getContent(),this,this,new MainListAdapter(2));//这个是不设置头部的初始化
    public RecyclerViewUtil(View view, Context content, Listener.OnItemClickListener listener, Listener.LoadMoreListener listener1, MainListAdapter mMainListAdapter){
        this.content=content;
        this.listener=listener;
        this.listener1=listener1;
        this.mMainListAdapter=mMainListAdapter;
        this.view=view;
        bindViews(view);
        initListView(view);
        loadData(true);
    }
    //这个是设置 头部的初始化
    public RecyclerViewUtil(int id, View view, Context content, Listener.OnItemClickListener listener, Listener.LoadMoreListener listener1, MainListAdapter mMainListAdapter){
        this.content=content;
        this.listener=listener;
        this.listener1=listener1;
        this.mMainListAdapter=mMainListAdapter;
        this.view=view;
        touID=id;
        bindViews(view);
        initListView(view);
        loadData(true);
    }
    public  void setRecyclerViewAbstract(RecyclerViewAbstract rv){
            this.rv=rv;
    }
    private Context content;
    private Listener.OnItemClickListener listener;
    private Listener.LoadMoreListener listener1;
    private RecyclerView rvMain;
    private MainListAdapter mMainListAdapter;
    private View mLoadingView;
    private View mHeaderView;
    private int touID=0;//头部ID
    private int index=1;//加载到了第几页

    private View view;
    private RecyclerViewAbstract rv;

    public void bindViews(View view) {
        rvMain = view.findViewById(R.id.recyclerView);

        mLoadingView = getView(R.layout.view_loading);
        if(touID==0){
            mHeaderView = getView(R.layout.view_header);
        }else{
            mHeaderView = getView(touID);
        }
    }
    SwipeRefreshLayout swipeRefreshLayout ;
    public void initListView(final View view) {
        int space = ViewUtil.dp2px(content, 1);
        rvMain.setLayoutManager(new LinearLayoutManager(content));
        rvMain.addItemDecoration(new SpaceItemDecoration(space, space, space, 0));
        rvMain.setVerticalScrollBarEnabled(false);
        mMainListAdapter = createAdapter();
        rvMain.setAdapter(mMainListAdapter);
        //----------------------下拉刷新
        swipeRefreshLayout =(SwipeRefreshLayout) view.findViewById(R.id.gank_swipe_refresh_layout);//添加监听 增加触发刷新时的操作(比如重新请求数据)
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //做一些事情，如从服务器或其他请求
                loadData(true);
            }
        });
        if(touID!=0){
            mMainListAdapter.addHeaderView(mHeaderView);//添加头部
        }

    }

    /**
     * 获取头部控件  进行操作
     * @return
     */
    public View getmHeaderView(){
        return mHeaderView;
    }
    /**
     * 获取RecyclerView
     * @return
     */
    public RecyclerView getRecyclerView(){
        return rvMain;
    }
    /**
     * 创建并配置Adapter
     * @return
     */
    private MainListAdapter createAdapter() {
        //mMainListAdapter = new MainListAdapter();
        mMainListAdapter.setLoadingView(mLoadingView)
                .setLoadingMoreView(new DefaultLoadMoreView(rvMain))
                //.preLoadMoreNum(5)//这个是上拉加载的数量
                .setOnItemClickListener(listener)
                .setOnLoadMoreListener(rvMain, false, listener1);
        return mMainListAdapter;
    }

    public void loadData(final boolean isFirstPage) {//true 刷新
//        if (isFirstPage) {
//            mMainListAdapter.showLoading();//显示自带的 刷新界面
//        }
        rvMain.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFirstPage) {//下拉+
                    index=1;
                    rv.shuaxin(mMainListAdapter);
                  //  Toast.makeText(content, "刷新" , Toast.LENGTH_SHORT).show();
                   // mMainListAdapter.setDataList(AnalogData.getPageData(10));

                } else {//上拉
                    index=index+1;
                    rv.shangla(mMainListAdapter);
//                    Toast.makeText(content, "加载更多" , Toast.LENGTH_SHORT).show();
//                    mMainListAdapter.addData(AnalogData.getPageData(5));
                }
               mMainListAdapter.loadMoreFinish();
                swipeRefreshLayout.setRefreshing(false);//加载完 执行停止动画
            }
        }, 1500);
    }
    //结束刷新
    public  void endshuaxin(){
      //  LogUtils.i("xxxxx1", "end");  //输出测试
        mMainListAdapter.loadMoreFinish();
        swipeRefreshLayout.setRefreshing(false);//加载完 执行停止动画
    }

    //获取 第几页
    public int getindex(){//获取加载到了第几页
        return index;
    }
    //设置 第几页
    public void setindex(int index){//设置加载到了第几页
        this.index = index;
    }
    //填充数据  判断数据是否没有了
     public void  setDataList(@Nullable List<T> data){
         if(data.size()>0){
             if(getindex()==1){
                 mMainListAdapter.setDataList(data);
             }else{
                 mMainListAdapter.addData(data);
             }
             if(data.size()<10){
                 getMainListAdapter().setLoadMoreState(AbsLoadingMoreView.STATE_END);
             }
         }else{
             mMainListAdapter.setDataList(data);
             getMainListAdapter().setLoadMoreState(AbsLoadingMoreView.STATE_END);
         }
     }

    public MainListAdapter getMainListAdapter(){
        return mMainListAdapter;
    }
    private View getView(@LayoutRes int layoutId) {
        return LayoutInflater.from(content).inflate(layoutId, null, false);
    }
}

