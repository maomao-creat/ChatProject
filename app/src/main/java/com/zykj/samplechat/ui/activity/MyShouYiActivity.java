package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.MyshouyiBean;
import com.zykj.samplechat.model.SentRecords;
import com.zykj.samplechat.model.mYshouyi;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.WalleMyWanPresenter;
import com.zykj.samplechat.presenter.WanJiaListBean;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.adapter.MainListAdapter;
import com.zykj.samplechat.ui.view.SendMyWanView;
import com.zykj.samplechat.utils.RecyclerViewAbstract;
import com.zykj.samplechat.utils.RecyclerViewUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.davidsu.library.listener.Listener;
import cn.davidsu.library.loadMore.AbsLoadingMoreView;

public class MyShouYiActivity extends ToolBarActivity<WalleMyWanPresenter> implements SendMyWanView, Listener.OnItemClickListener, Listener.LoadMoreListener {


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
    private String bs = "";//1发包 2中奖 3 中雷 3充值 5提现
    List<SentRecords> shuju;

    @Override
    public void success(List<WanJiaListBean> rs, final int i, final TwinklingRefreshLayout trs) {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_wodeshouyi;
    }

    @Override
    public void initListeners() {//初始化
        bs = getIntent().getStringExtra("bs");
        showDialog();
        getsy();
        chushihualb();//初始化下拉列表
    }

    void getsy() {//获取收益信息
        HttpUtils.GetFenHong(new SubscriberRes<MyshouyiBean>() {
            @Override
            public void onSuccess(MyshouyiBean mye) {
                jrsy.setText("" + mye.getTodayAmount());
                zsy.setText("" + mye.getAllAmount());
                zrsy.setText("" + mye.getYestodayAmount());
            }

            @Override
            public void completeDialog() {
            }
        });

//        Map map = new HashMap();
//        map.put("function", "" + "GetFenHong");
//        map.put("userid", "" + new UserUtil(getContext()).getUserId2());
//        showDialog();
//        NR.posts("WebService/UserService.asmx/Entry", map, new StringCallback() {
//
//            @Override
//            public void onError(Request request, Exception e) {
//                dismissDialog();
//                //  LogUtils.i("xxxxx222  :", "" +e);  //输出测试
//                IsZH.getToast(MyShouYiActivity.this, "服务器错误");  //吐司
//            }
//
//            @Override
//            public void onResponse(String s) {
//                dismissDialog();
//                // LogUtils.i("xxxxx", "" +s);  //输出测试
//                if (NRUtils.getYse(MyShouYiActivity.this, s)) {
//                    // IsZH.getToast(MyShouYiActivity.this, "成功");  //吐司
//                    MyshouyiBean mye = NRUtils.getData(s, MyshouyiBean.class);
//                    if (mye != null) {
//                        jrsy.setText("" + mye.getTodayAmount());
//                        zsy.setText("" + mye.getAllAmount());
//                        zrsy.setText("" + mye.getYestodayAmount());
//                    }
//                    //finish();
//                }
//            }
//
//        });
    }


    @Override
    public WalleMyWanPresenter createPresenter() {
        return new WalleMyWanPresenter();
    }


    @Override
    protected CharSequence provideTitle() {
        return "我的推荐";
    }

    //下拉列表
    private MainListAdapter mMainListAdapter;
    private static int MAX_SIZE = 35;
    RecyclerViewUtil rvu;

    void chushihualb() {
        //---------------------------------listView 开始

        rvu = new RecyclerViewUtil(R.layout.item_my_sy_tou, getWindow().getDecorView(), this, this, this, new MainListAdapter(1, this));//new MainListAdapter();  这个是适配器
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

        wdxx = rvu.getmHeaderView().findViewById(R.id.wdxx);//我的下线  需要点击事件
        jrsy = rvu.getmHeaderView().findViewById(R.id.jrsy);
        zrsy = rvu.getmHeaderView().findViewById(R.id.zrsy);
        zsy = rvu.getmHeaderView().findViewById(R.id.zsy);
    }

    TextView wdxx;
    TextView jrsy;
    TextView zrsy;
    TextView zsy;

    void sendliebiao() {

        HttpUtils.GetFenHongList(new SubscriberRes<mYshouyi>(rvu.getRecyclerView()) {
            @Override
            public void onSuccess(mYshouyi userBean) {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
