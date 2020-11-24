package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.sheqhyy;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.HotSellerListBean;
import com.zykj.samplechat.presenter.WalleJiLuPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.adapter.MainListAdapter;
import com.zykj.samplechat.ui.view.SendJiLuView;
import com.zykj.samplechat.ui.view.lbhd;
import com.zykj.samplechat.utils.RecyclerViewAbstract;
import com.zykj.samplechat.utils.RecyclerViewUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.davidsu.library.listener.Listener;
import cn.davidsu.library.loadMore.AbsLoadingMoreView;

public class MymoyouActivity extends ToolBarActivity<WalleJiLuPresenter> implements SendJiLuView, Listener.OnItemClickListener, Listener.LoadMoreListener {


    @Override
    public void success(List<HotSellerListBean> rs, final int i, final TwinklingRefreshLayout trs) {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ji_lu;
    }

    @Override
    public void initListeners() {//初始化
        tvAction.setText("");
        chushihualb();//初始化下拉列表
    }

    //下拉列表
    private MainListAdapter mMainListAdapter;
    private static int MAX_SIZE = 35;
    RecyclerViewUtil rvu;

    void chushihualb() {
        //---------------------------------listView 开始
        rvu = new RecyclerViewUtil(getWindow().getDecorView(), this, this, this, new MainListAdapter(MymoyouActivity.this, 2, this, new lbhd() {
            @Override
            public void zx(String by, int id) {
                rvu.loadData(true);
            }
        }));//new MainListAdapter();  这个是适配器
        rvu.setRecyclerViewAbstract(new RecyclerViewAbstract() {
            @Override
            public void shuaxin(MainListAdapter mMainListAdapter) {
                GetApplyList();
                //  Toast.makeText(getContext(), "刷新" , Toast.LENGTH_SHORT).show();
//                mMainListAdapter.setDataList(getPageData(10));
            }

            @Override
            public void shangla(MainListAdapter mMainListAdapter) {
                // Toast.makeText(getContext(), "加载更多" , Toast.LENGTH_SHORT).show();
//                mMainListAdapter.addData(getPageData(5));
                GetApplyList();
            }
        });
        //mMainListAdapter.addHeaderView(mHeaderView);添加头部

    }

//    void sendliebiao() {//转账记录
//        Map map = new HashMap();
//        map.put("function", "" + "GetApplyList");
//        map.put("userid", "" + new UserUtil(getContext()).getUserId2());
//        map.put("page", "" + rvu.getindex());
//        map.put("size", "10");
//        showDialog();
//        NR.posts("WebService/UserService.asmx/Entry", map, new StringCallback() {
//
//            @Override
//            public void onError(Request request, Exception e) {
//                dismissDialog();
//                //  LogUtils.i("xxxxx222  :", "" +e);  //输出测试
//                IsZH.getToast(MymoyouActivity.this, "服务器错误");  //吐司
//                rvu.getMainListAdapter().setLoadMoreState(AbsLoadingMoreView.STATE_END);
//            }
//
//            @Override
//            public void onResponse(String s) {
//                dismissDialog();
//                //  LogUtils.i("xxxxx", "" + s);  //输出测试
//                if (NRUtils.getYse(MymoyouActivity.this, s)) {
//                    sheqhyy bean2 = NRUtils.getData(s, sheqhyy.class);
//                    rvu.setDataList(bean2.getData());
//                }
//            }
//
//        });
//    }

    public void GetApplyList(){
        HttpUtils.GetApplyList(new SubscriberRes<sheqhyy>(rvu.getRecyclerView()) {
            @Override
            public void onSuccess(sheqhyy userBean) {
                rvu.setDataList(userBean.getData());
            }

            @Override
            public void completeDialog() {
            }
        },rvu.getindex(),"10");
    }

    @Override
    public WalleJiLuPresenter createPresenter() {
        return null;
    }


    @Override
    protected CharSequence provideTitle() {//    private String bs = "";//1转账 2提现
        return "新的朋友";
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
