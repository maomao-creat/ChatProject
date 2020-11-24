package com.zykj.samplechat.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.ChaiHongbao;
import com.zykj.samplechat.model.HongbaoList;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.GetHongbaoListPresenter;
import com.zykj.samplechat.ui.activity.base.RecycleViewActivity;
import com.zykj.samplechat.ui.adapter.GetHongbaoListAdapter;
import com.zykj.samplechat.ui.view.GetHongbaoListView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.CommonUtil;
import com.zykj.samplechat.utils.IsZH;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ninos on 2017/8/23.
 */

public class GetHongbaoListActivity extends RecycleViewActivity<GetHongbaoListPresenter, GetHongbaoListAdapter, HongbaoList.HongbaoUser> implements GetHongbaoListView {

    public View header;
    ImageView gh_img;
    TextView gh_name;
    TextView gh_qian;
    LinearLayout gh_ll;
    TextView gh_content;
    TextView gh_type;

    private String hongbaoId;
    private String name;
    private String photo;
    private String content;
    private String amount;
    private String state;
    private String open;
    private String qid;//群ID
    private String bs123;//恭喜发财
    private String extra;//随机数
    private String from;

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        CommonUtil.setWindowStatusBarColor(GetHongbaoListActivity.this, R.color.bg_red);

        from = getIntent().getStringExtra("from");
        if(StringUtil.isEmpty(from)) {
            name = getIntent().getBundleExtra("data").getString("name");
            photo = getIntent().getBundleExtra("data").getString("photo");
            content = getIntent().getBundleExtra("data").getString("content");
            amount = getIntent().getBundleExtra("data").getString("amount");
            open = getIntent().getBundleExtra("data").getString("open");
            qid = getIntent().getBundleExtra("data").getString("qid");
            bs123 = getIntent().getBundleExtra("data").getString("content");
            extra = getIntent().getBundleExtra("data").getString("extra");
            hongbaoId = getIntent().getBundleExtra("data").getString("hongbaoId");
//            if (QuanJu.getQuanJu().getlei(qid)) {
                lei(extra);
//            } else {
//                grhb();
//            }
        }else {
            hongbaoId =getIntent().getStringExtra("hongbaoId");
            if(StringUtil.isEmpty(hongbaoId)){
                finish();
            }else {
                gethbxx();
            }
        }

    }

    void lei(String extra) {//带雷的 红包操作
        if (open.equals("1")) { //1是打开 0是不打开
            presenter.OpenSingleRedPacket1(qid,hongbaoId,extra);
        } else {
            gethbxx();
        }
    }

    void grhb() {//不带雷的 红包操作
        if (open.equals("1")) { //1是打开 0是不打开
            Map map = new HashMap();
            map.put("key", Const.KEY);
            map.put("uid", Const.UID);
            map.put("function", "GetRedPackageNormal");
            map.put("userid", new UserUtil(getContext()).getUserId2());
            map.put("packageid", hongbaoId);
            map.put("teamid", qid);
            String json = StringUtil.toJson(map);
            // System.out.print( new UserUtil(getContext()).getUserId()+"--"+hongbaoId);
            // showDialog();
            try {
                String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//                presenter.OpenSingleRedPacket1(data);
            } catch (Exception ex) {
                // dismissDialog();
            }
        } else {
            gethbxx();
        }
    }

    @Override
    public void onItemClick(View view, int pos, HongbaoList.HongbaoUser item) {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_get_hongbao_list;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public GetHongbaoListPresenter createPresenter() {
        return new GetHongbaoListPresenter();
    }

    @Override
    protected CharSequence provideTitle() {
        return "红包";
    }

    @Override
    protected GetHongbaoListAdapter provideAdapter() {//这个是抢红包人的子页面
        header = getLayoutInflater().inflate(R.layout.activity_get_hongbao_list_header, null);
        gh_img = (ImageView) header.findViewById(R.id.gh_img);
        gh_name = (TextView) header.findViewById(R.id.gh_name);
        gh_qian = (TextView) header.findViewById(R.id.gh_qian);
        gh_ll = (LinearLayout) header.findViewById(R.id.gh_ll);
        gh_content = (TextView) header.findViewById(R.id.gh_content);
        gh_type = (TextView) header.findViewById(R.id.gh_type);
        return new GetHongbaoListAdapter(getContext(), header);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void success(ChaiHongbao chaiHongbao) {//抢成功了
        //dismissDialog();
        gethbxx();
    }

    @Override
    public void success(HongbaoList list) {
        // dismissDialog();

    }

    @Override
    public void errorChai(String ecxx) {//抢红包失败
        IsZH.getToast(GetHongbaoListActivity.this, ecxx);  //吐司
        gethbxx();
    }

    void gethbxx() {//获取红包信息
        HttpUtils.GRedPackageDetail(new SubscriberRes<HongbaoList>() {
            @Override
            public void onSuccess(HongbaoList res) {
                setxx(res);
            }
            @Override
            public void completeDialog() {
            }
        },hongbaoId);
    }

    void setxx(HongbaoList list) {
        gh_ll.setVisibility(View.GONE);

        Glide.with(getContext())
                .load(Const.getbase(list.HeadUrl))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(gh_img);

        gh_name.setText(list.NickName);
//        gh_content.setText(list.Description);

        if(StringUtil.isEmpty(from)) {
//            if (QuanJu.getQuanJu().getlei(qid)) {//判断是不是中雷群 显示的内容不一样
//                gh_content.setText(list.TotalMoney + "-" + list.Lei);
//            } else {
//                String[] a = bs123.split("-");
//                if (a.length > 1) {
//                    gh_content.setText(a[1]);
//                }
//            }
            gh_content.setText(list.TotalMoney + "-" + list.Lei);
        }else {
            gh_content.setText(list.TotalMoney + "-" + list.Lei);
        }
        //mye.getTotalMoney()+"-"+mye.getLei()
        if ((list.UserList.size() + "").equals(list.TotalCount+""))
            gh_type.setText(list.TotalCount + "个红包共" + list.TotalMoney + "元，" + list.Time + "被抢光");
        else
            gh_type.setText(list.TotalCount + "个红包共" + list.TotalMoney + "元，" + "红包已被领" + list.UserList.size() + "个");
        page = 1;
        for (HongbaoList.HongbaoUser hbu : list.UserList) {
            if (hbu.userid .equals(new UserUtil(getContext()).getUserId2())) {
                gh_ll.setVisibility(View.VISIBLE);
                gh_qian.setText(hbu.Amount);
            }
        }
        for (int i = 0; i < list.UserList.size(); i++) {
            list.UserList.get(i).PhotoPath = list.UserList.get(i).HeadUrl;
            list.UserList.get(i).NicName = list.UserList.get(i).NickName;
            if (list.UserList.get(i).IsLuck==1) {//是否手气最佳 0不是1是
                list.UserList.get(i).Flag = "1";
            } else {
                list.UserList.get(i).Flag = "0";
            }
        }
        bd(list.UserList);//GetHongbaoListAdapter  这个是子页面
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
