package com.zykj.samplechat.ui.activity;

import android.content.DialogInterface;
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
import com.zykj.samplechat.presenter.HotSellerListBean;
import com.zykj.samplechat.presenter.WalleJiLuPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.adapter.JingPinAdapter;
import com.zykj.samplechat.ui.view.SendJiLuView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.PicassoUtil;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class JiLuActivity extends ToolBarActivity<WalleJiLuPresenter> implements SendJiLuView {

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
    private String bs = "";//1转账 2提现
    List<SentRecords> shuju;

    @Override
    public void success(List<HotSellerListBean> rs, final int i, final TwinklingRefreshLayout trs) {
        //  shuju = qian;
        dismissRefresh(i, trs);
//                LogUtils.i("xxxxx", s+"");  //输出测试
//                IndexBean bean2 = NRUtils.getData(s,IndexBean.class);
        // List<HotSellerListBean> rs = new ArrayList<HotSellerListBean>();
        //  rs.add(new HotSellerListBean(shuju));
        int x = getPageIndex(recycler);
        setPageIndex(recycler, x+1);
        mAdapter.setbiaoshi(bs);
        mAdapter.addAllData(rs);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ji_lu;
    }

    @Override
    public void initListeners() {//初始化
        shuju = new ArrayList<SentRecords>();
        bs = getIntent().getStringExtra("bs");
        refresh();
        setMyAdapter();
    }

    //精品店铺部分
    private JingPinAdapter mAdapter;
    private Object obj = new Object();

    void setMyAdapter() {
        mAdapter = new JingPinAdapter(bs, this, new ArrayList<HotSellerListBean>(), new JingPinAdapter.OnMyAdapterLinstener() {

        });
        mAdapter.setClickListener(new JingPinAdapter.OnItemClickListener() {

            @Override
            public void onClick(View view, String code, View vbg, int position) {
                switch (bs) {//1发包 2中奖 3 中雷 4充值 5提现 6收包 7赔付 8转账记录 9红包返还记录
                    case "4":
                        //  title="充值记录";
                        return;
                    case "5":
                        //  title="提现记录";
                        return;
                        case "8":
                        //  title="提现记录";
                        return;
                        case "9":
                        //  title="提现记录";
                        return;
                }
                Intent intent = new Intent(JiLuActivity.this, GetHongbaoListActivity.class);
                Bun bun = new Bun();
                bun.putString("name","1");
                bun.putString("photo","2");
                bun.putString("content","3");
                bun.putString("amount","4");
                bun.putString("hongbaoId",""+code);
                bun.putString("state","1");
                bun.putString("open","0");
                intent.putExtra("data", bun.ok());
                startActivity(intent);
//                Intent intent = new Intent(SuoYouHuoYuanActivity.this,ShuoYouDActivity.class);
//                intent.putExtra("code",code);
//                startActivity(intent);
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
        switch (bs) {//1发包 2中奖 3 中雷 3充值 5提现 6收包 7赔付 8转账记录 9红包返还记录
            case "1":
                //title="发包记录";
                fabao(i, trs);
                break;
            case "2":
                //title="中奖记录";
                zhongjiang(i, trs);
                break;
            case "3":
                // title="中雷记录";
                zhonglei(i, trs);
                break;
            case "4":
                //  title="充值记录";
                chongzhi(i, trs);
                break;
            case "5":
                //  title="提现记录";
                tixian(i, trs);
                break;
            case "6":
                //  title="收包记录";
                shoubao(i, trs);
                break;
            case "7":
                //  title="赔付记录";
                peifu(i, trs);
                break;
                case "8":
                //  title="转账记录";
                zhaunzhang(i, trs);
                break;
                case "9":
                //  title="9红包返还记录";
                    tuikuan(i, trs);
                break;

        }
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
    public WalleJiLuPresenter createPresenter() {
        return new WalleJiLuPresenter();
    }
    @OnClick(R.id.tv_action)
    public void onViewClicked() {
        showListAlertDialog();
//        switch (bs) {//1发包 2中奖 3 中雷 3充值 5提现 6收包 7赔付 8转账记录 9红包返还记录
//            case "1":
//                bs="6";
//                tr.startRefresh();
//                tvTitle.setText("收包纪录");
//                tvAction.setText("发包纪录");
//                break;
//            case "2":
//                bs="7";
//                tvTitle.setText("赔付纪录");
//                tvAction.setText("中奖纪录");
//                tr.startRefresh();
//                break;
//            case "6":
//                bs="1";
//                tvTitle.setText("发包纪录");
//                tvAction.setText("收包纪录");
//                tr.startRefresh();
//                break;
//            case "7":
//                bs="2";
//                tvTitle.setText("中奖纪录");
//                tvAction.setText("赔付纪录");
//                tr.startRefresh();
//                break;
//        }
    }
    // 信息列表提示框
    private AlertDialog alertDialog1;
    public void showListAlertDialog(){//1发包 2中奖 3 中雷 3充值 5提现 6收包 7赔付 8转账记录 9红包返还记录
        final String[] items = {"发包纪录","中奖纪录","中雷纪录","充值纪录","提现纪录","收包纪录","赔付纪录","转账记录","红包返还记录"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
      //  alertBuilder.setTitle("交易记录");
        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int index) {
               // Toast.makeText(getContext(), ""+index, Toast.LENGTH_SHORT).show();
                tvTitle.setText(""+items[index]);
                bs=""+(index+1);
                tr.startRefresh();
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = alertBuilder.create();
        alertDialog1.show();
    }
    @Override
    protected CharSequence provideTitle() {//1发包 2中奖 3 中雷 3充值 5提现 6收包 7赔付 8转账记录 9红包返还记录
        bs = getIntent().getStringExtra("bs");
        String title = "";
        tvAction.setText("其他记录");
        tvAction.setVisibility(View.VISIBLE);
        switch (bs) {
            case "1":
                title = "发包记录";
                //tvAction.setText("收包纪录");
               // tvAction.setVisibility(View.VISIBLE);
                break;
            case "2":
                title = "中奖记录";
                //tvAction.setText("赔付纪录");
               // tvAction.setVisibility(View.VISIBLE);
                break;
            case "3":
                title = "中雷记录";
                break;
            case "4":
                title = "充值记录";
                break;
            case "5":
                title = "提现记录";
                break;
            case "6":
                title = "收包纪录";
                //tvAction.setText("发包记录");
               // tvAction.setVisibility(View.VISIBLE);
                break;
            case "7":
                title = "赔付纪录";
                break;
                case "8":
                title = "转账记录";
                break;
                case "9":
                title = "红包返还记录";
                break;

        }
        return "" + title;
    }

    void fabao(final int i, final TwinklingRefreshLayout trs) {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("index", getPageIndex(recycler));
        map.put("pageSize", "10");
        map.put("function", "getSentRecords");
        map.put("userId", new UserUtil(getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.getchongzhi(data, i, trs);
        } catch (Exception ex) {
            dismissDialog();
        }
    }

    void zhongjiang(final int i, final TwinklingRefreshLayout trs) {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("index", getPageIndex(recycler));
        map.put("pageSize", "10");
        map.put("function", "getZhongjiangRecords");
        map.put("userId", new UserUtil(getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.getchongzhi(data, i, trs);
        } catch (Exception ex) {
            dismissDialog();
        }
    }
    void zhonglei(final int i, final TwinklingRefreshLayout trs) {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("index", getPageIndex(recycler));
        map.put("pageSize", "10");
        map.put("function", "getLeiRecords");
        map.put("userId", new UserUtil(getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.getchongzhi(data, i, trs);
        } catch (Exception ex) {
            dismissDialog();
        }
    }
    void chongzhi(final int i, final TwinklingRefreshLayout trs) {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("index", getPageIndex(recycler));
        map.put("pageSize", "10");
        map.put("function", "getDepositList");
        map.put("userId", new UserUtil(getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.getchongzhi(data, i, trs);
        } catch (Exception ex) {
            dismissDialog();
        }
    }
    void tixian(final int i, final TwinklingRefreshLayout trs) {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("index", getPageIndex(recycler));
        map.put("pageSize", "10");
        map.put("function", "getWithdrawList");
        map.put("userId", new UserUtil(getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.getchongzhi(data, i, trs);
        } catch (Exception ex) {
            dismissDialog();
        }
    }
    void shoubao(final int i, final TwinklingRefreshLayout trs) {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("index", getPageIndex(recycler));
        map.put("pageSize", "10");
        map.put("function", "getReceivedRecords");
        map.put("userId", new UserUtil(getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.getchongzhi(data, i, trs);
        } catch (Exception ex) {
            dismissDialog();
        }
    }
    void peifu(final int i, final TwinklingRefreshLayout trs) {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("index", getPageIndex(recycler));
        map.put("pageSize", "10");
        map.put("function", "getPeifuRecords");
        map.put("userId", new UserUtil(getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.getchongzhi(data, i, trs);
        } catch (Exception ex) {
            dismissDialog();
        }
    }
    void zhaunzhang(final int i, final TwinklingRefreshLayout trs) {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("index", getPageIndex(recycler));
        map.put("pageSize", "10");
        map.put("function", "getTransferRecords");
        map.put("userId", new UserUtil(getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.getchongzhi(data, i, trs);
        } catch (Exception ex) {
            dismissDialog();
        }
    }
    void tuikuan(final int i, final TwinklingRefreshLayout trs) {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("index", getPageIndex(recycler));
        map.put("pageSize", "10");
        map.put("function", "getRecycleRecords");
        map.put("userId", new UserUtil(getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.getchongzhi(data, i, trs);
        } catch (Exception ex) {
            dismissDialog();
        }
    }
}
