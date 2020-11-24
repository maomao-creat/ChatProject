package com.zykj.samplechat.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rey.material.app.Dialog;
import com.youth.banner.Banner;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.QuanJu;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.model.lbt;
import com.zykj.samplechat.model.mqunlb;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.HzwPresenter;
import com.zykj.samplechat.presenter.base.GlideImageLoader;
import com.zykj.samplechat.ui.activity.base.SwipeRecycleViewFragment;
import com.zykj.samplechat.ui.adapter.HzwAdapter;
import com.zykj.samplechat.ui.adapter.MainListAdapter;
import com.zykj.samplechat.ui.view.TeamView;
import com.zykj.samplechat.ui.widget.ScrollText;
import com.zykj.samplechat.utils.NR;
import com.zykj.samplechat.utils.RecyclerViewAbstract;
import com.zykj.samplechat.utils.RecyclerViewUtil;
import com.zykj.samplechat.utils.UserUtil;
import com.zykj.samplechat.utils.WindowUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.davidsu.library.listener.Listener;
import cn.davidsu.library.loadMore.AbsLoadingMoreView;

/**
 * *****************************************************
 * *
 * *
 * _oo0oo_                      *
 * o8888888o                     *
 * 88" . "88                     *
 * (| -_- |)                     *
 * 0\  =  /0                     *
 * ___/`---'\___                   *
 * .' \\|     |# '.                  *
 * / \\|||  :  |||# \                 *
 * / _||||| -:- |||||- \               *
 * |   | \\\  -  #/ |   |               *
 * | \_|  ''\---/''  |_/ |              *
 * \  .-\__  '-'  ___/-. /              *
 * ___'. .'  /--.--\  `. .'___            *
 * ."" '<  `.___\_<|>_/___.' >' "".          *
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |        *
 * \  \ `_.   \_ __\ /__ _/   .-` /  /        *
 * =====`-.____`.___ \_____/___.-`___.-'=====     *
 * `=---='                      *
 * *
 * *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    *
 * *
 * 佛祖保佑         永无BUG              *
 * *
 * *
 * *****************************************************
 * <p>
 * Created by csh on 2016/6/2.
 */
public class HzwFragment extends SwipeRecycleViewFragment<HzwPresenter, HzwAdapter, Team>
        implements TeamView, View.OnClickListener, HzwAdapter.NoticeMessage, Listener.OnItemClickListener, Listener.LoadMoreListener{

    @Bind(R.id.ll_notice)
    LinearLayout ll_notice;
    @Bind(R.id.news_statustxt)
    ScrollText scrollText;
    @Bind(R.id.tzxxnr)
    TextView tzxxnr;
    @Bind(R.id.banner)
    Banner banner;

    private Dialog dialog_notice;
    private LinearLayout linearLayout;
    private TextView view_notice;
    private String notice = "";


    @Override
    public void initListeners() {
        ll_notice.setOnClickListener(this);
        scrollText.setOnClickListener(this);
        String s = "正在加载...";
        scrollText.setStateList(s);
        scrollText.setText(s);
        scrollText.init(handler);
        scrollText.startScroll();
        scrollText.start();
    }

    @Override
    protected void initThings(View view) {
        super.initThings(view);
//        presenter.getVersion();
        User user = new UserUtil(getContext()).getUser();
        tzxxnr.setText(QuanJu.getQuanJu().getTzxx());
        View noticeView = getActivity().getLayoutInflater().inflate(R.layout.dialog_notice, null);
        ScrollView scrollView = (ScrollView) noticeView.findViewById(R.id.sl_content);
        this.view_notice = (TextView) noticeView.findViewById(R.id.tv_notice);
        view_notice.getLayoutParams().width = WindowUtil.getScreenWidth(getActivity()) / 5 * 4;
        scrollView.getLayoutParams().height = WindowUtil.getScreenWidth(getActivity());
        view_notice.setMinHeight(WindowUtil.getScreenWidth(getActivity()));
        dialog_notice = new Dialog(getContext()).backgroundColor(Color.parseColor("#ffffff")).contentView(noticeView);
        view_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_notice.dismiss();
            }
        });
        //presenter.getData(page, count);
        GetSystemNews();
//        cshlbt();//初始化轮播图

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == ScrollText.TEXT_TIMER && scrollText != null) {
                scrollText.scrollText();
            }
        }
    };


    @Override
    protected HzwAdapter provideAdapter() {
        return new HzwAdapter(getContext(), presenter, this);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_hzw;
    }

    @Override
    public HzwPresenter createPresenter() {
        return new HzwPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, Team item) {
    }

    @Override
    public void onClick(View v) {
        view_notice.setText(notice);
        dialog_notice.show();
    }

    @Override
    public void sendMsg(String msg) {
        view_notice.setText(msg);
        dialog_notice.show();
    }

    @Override
    public void success(ArrayList<Team> list) {
        bd(list);
    }

    @Override
    public void errorFound() {
    }

    @Override
    public void success(String res) {
        notice = res;
        scrollText.setStateList(notice);
        scrollText.setText(notice);
        scrollText.init(handler);
        //ll_notice.setVisibility(View.VISIBLE);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);

        rvu = new RecyclerViewUtil(view,getContext(),this,this,new  MainListAdapter(5, getContext()));//new MainListAdapter();  这个是适配器
        rvu.setRecyclerViewAbstract(new RecyclerViewAbstract() {
            @Override
            public void shuaxin(MainListAdapter mMainListAdapter) {
                GetSystemNews();
                //  Toast.makeText(getContext(), "刷新" , Toast.LENGTH_SHORT).show();
//                mMainListAdapter.setDataList(getPageData(10));
            }

            @Override
            public void shangla(MainListAdapter mMainListAdapter) {
                // Toast.makeText(getContext(), "加载更多" , Toast.LENGTH_SHORT).show();
//                mMainListAdapter.addData(getPageData(5));
                GetSystemNews();
            }
        });
        //mMainListAdapter.addHeaderView(mHeaderView);添加头部
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    void cshlbt(){//轮播图
        final List<String> image = new ArrayList<String>();
        HttpUtils.BannerList(new SubscriberRes<ArrayList<lbt>>() {
            @Override
            public void onSuccess(ArrayList<lbt> userBean) {
                for(lbt as:userBean){
                    image.add(NR.url+as.getImgPath());
                }
                //设置图片加载器
                banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                banner.setImages(image);
                //banner设置方法全部调用完毕时最后调用
                banner.start();
            }

            @Override
            public void completeDialog() {
            }
        });
//        image.add("http://218.192.170.132/BS80.jpg");
//        image.add("http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg");
//        image.add("http://img.zcool.cn/community/018fdb56e1428632f875520f7b67cb.jpg");


    }
    //下拉列表
    private MainListAdapter mMainListAdapter;
    private static int MAX_SIZE = 35;
    RecyclerViewUtil rvu;



    @Override
    public void onItemClick(Object o, View view, int i, int i1) {

    }
    private void GetSystemNews() {
        HttpUtils.GetSaoLeiGroupList(new SubscriberRes<ArrayList<mqunlb>>() {
            @Override
            public void onSuccess(ArrayList<mqunlb> mye) {
                QuanJu.getQuanJu().setMye(mye);
                success("#"+mye.get(0).getName()+"    ");
                for(mqunlb qq:mye){//更新群信息
//                    RongIM.getInstance().refreshGroupInfoCache(
//                            new io.rong.imlib.model.Group(qq.getId() + "",
//                                    qq.getName(),
//                                    Uri.parse(Const.getbase(qq.getImagePath()))));
                }
                rvu.setDataList(mye);
            }

            @Override
            public void completeDialog() {
            }
        });
//        Map<String, Object> map = new HashMap<>();
//        map.put("key", Const.KEY);
//        map.put("uid", Const.UID);
//        map.put("function", "NormalTeamList");
//        map.put("userid",""+new UserUtil(getContext()).getUserId2());
//        String json = StringUtil.toJson(map);
//        try {
//            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//            Log.e("TAG", data);
//            presenter.GetAllGonggao(data);
//        } catch (Exception ex) {
//        }
    }

    @Override
    public void loadMore() {
        if (rvu.getMainListAdapter().getDataList().size() > MAX_SIZE) {//无刷新结果
            rvu.getMainListAdapter().setLoadMoreState(AbsLoadingMoreView.STATE_END);
        } else {
            rvu.loadData(false);
        }
    }

}