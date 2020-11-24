package com.zykj.samplechat.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rey.material.app.Dialog;
import com.rey.material.widget.Button;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Hongbao;
import com.zykj.samplechat.model.QuanJu;
import com.zykj.samplechat.model.TeamBean;
import com.zykj.samplechat.model.mqunlb;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.LeiBean;
import com.zykj.samplechat.presenter.SendMoneyPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.adapter.BeiShuAdapter;
import com.zykj.samplechat.ui.view.SendMoneyView;
import com.zykj.samplechat.ui.view.base.OnItemClickListener;
import com.zykj.samplechat.ui.widget.App;
import com.zykj.samplechat.ui.widget.NumPswView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.CommonUtil;
import com.zykj.samplechat.utils.IsZH;
import com.zykj.samplechat.utils.NR;
import com.zykj.samplechat.utils.NRUtils;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import okhttp3.Request;

/**
 * Created by ninos on 2017/8/22.
 */

public class SendMoneyActivity extends ToolBarActivity<SendMoneyPresenter> implements SendMoneyView {

    @Bind(R.id.money_amount)
    EditText money_amount;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.money_content)
    EditText money_content;
    @Bind(R.id.money_count)
    TextView money_count;
    @Bind(R.id.money_show)
    TextView money_show;
    @Bind(R.id.money_state)
    TextView money_state;
    @Bind(R.id.tv_allpeople)
    TextView tv_allpeople;
    @Bind(R.id.money_h)
    TextView money_h;
    @Bind(R.id.send_money)
    Button send_money;
    @Bind(R.id.more_to_more)
    LinearLayout more_to_more;
    @Bind(R.id.money_state_ll)
    LinearLayout money_state_ll;
    @Bind(R.id.sm_scroll)
    ScrollView sm_scroll;

    String Tcode = "";
    String fid = "";
    String type = "";
    String state = "1";
    int countpeople;
    @Bind(R.id.tv_ts)
    TextView tvTs;
    private Dialog dialog;
    TextView title, cancel, confirm;
    NumPswView content;
    View dialog_view;
    Handler mHandler = new Handler();
    public LeiBean leiBean;
    public String  countid;
    public String  Tips;
    public PopupWindow window;
    public ArrayList<LeiBean> arrayList;
    mqunlb qimxx;//群信息
    private String mPackStyle;
    @Override
    protected int provideContentViewId() {
//        fid = getIntent().getStringExtra("fid");
        return R.layout.activity_send_money;
//        if (getlei()) {
//            return R.layout.activity_send_money;
//        } else {
//            return R.layout.activity_send_moneyx;
//        }

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        Tcode = getIntent().getStringExtra("fid");

        type = getIntent().getStringExtra("type");
        getTeam();
        countpeople = App.countpeople;
        more_to_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showerweimaPopwindow(arrayList);
            }
        });

        if (type.equals("group")) {
            more_to_more.setVisibility(View.VISIBLE);
            money_state_ll.setVisibility(View.VISIBLE);
            money_h.setText("总金额");
        } else {
            more_to_more.setVisibility(View.GONE);
            money_state_ll.setVisibility(View.GONE);
        }
        money_amount.requestFocus();

        CommonUtil.setWindowStatusBarColor(this, R.color.redad);
    }

    @Override
    public void initListeners() {//初始化

        send_money.setEnabled(true);
        send_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//发红包开始
                if (type.equals("group")) {
                    fhbgn();//发红包功能在这里
                } else {
                    //个人红包功能
//                    Map map = new HashMap();
//                    map.put("key", Const.KEY);
//                    map.put("uid", Const.UID);
//                    map.put("function", "SendSingleRedPacket");
//                    map.put("userid", new UserUtil(getContext()).getUserId());
//                    map.put("receiveid", fid);
//                    map.put("amout", money_show.getText().toString());
//                    map.put("message", money_content.getText().toString().trim().equals("") ? money_content.getHint().toString() : money_content.getText().toString());
//                    String json = StringUtil.toJson(map);
//                    try {
//                        String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//                        presenter.SendSingleRedPacket(data);
//                    } catch (Exception ex) {
//                        dismissDialog();
//                    }
                }
            }
        });
        money_state_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (money_state.getText().toString().endsWith("随机红包")) {
//                    money_state.setText("普通红包");
//                    money_h.setText("总金额");
//                    money_show.setText("0.00");
//                    money_amount.setText("0");
//                    state = "1";
//                } else {
//                    money_h.setText("单个红包");
//                    money_state.setText("随机红包");
//                    money_show.setText("0.00");
//                    money_amount.setText("0");
//                    state = "2";
//                }
            }
        });
        money_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    DecimalFormat df = new DecimalFormat("##########0.00");
                    if (type.equals("group")) {
                        if (money_state.getText().toString().equals("随机红包")) {
                            try {
                                double d = Integer.parseInt(money_count.getText().toString()) * Double.parseDouble(s.toString());
                                money_show.setText(df.format(d));
                            } catch (Exception ex) {
                                money_count.setText("0");
                                money_show.setText("0.00");
                            }
                        } else {
                            money_show.setText(df.format(Double.parseDouble(s.toString())));
                        }
                    } else {
                        money_show.setText(df.format(Double.parseDouble(s.toString())));
                    }
                } catch (Exception ex) {
                    money_show.setText("0");
                    money_amount.setText("0");
                }
            }
        });
    }

    //是否走中雷红包
    private Boolean getlei() {
        List<mqunlb> aa = QuanJu.getQuanJu().getMye();
        for (mqunlb sad : aa) {//
            if (fid.equals(sad.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public SendMoneyPresenter createPresenter() {
        return new SendMoneyPresenter();
    }

    @Override
    protected CharSequence provideTitle() {
        return "发红包";
    }

    @Override
    public void success(String hongbaoId) {
        if (!getlei()) {
            Map map = new HashMap();
            map.put("key", Const.KEY);
            map.put("uid", Const.UID);
            map.put("function", "SendMsg");
            map.put("userId", new UserUtil(getContext()).getUserId());
            map.put("packetid", hongbaoId);//hongbao
            map.put("teamid", fid);
            String json = StringUtil.toJson(map);
            try {
                String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                presenter.Sendxx(data);
            } catch (Exception ex) {
                dismissDialog();
            }
        }
        setResult(22, new Intent()
                .putExtra("hongbaoId", hongbaoId)
                .putExtra("amount", money_show.getText().toString())
                .putExtra("content", money_content.getText().toString().trim().equals("") ? money_content.getHint().toString() : money_content.getText().toString())
                .putExtra("photo", new UserUtil(getContext()).getUser().getHeadUrl())
                .putExtra("name", new UserUtil(getContext()).getUser().getNickName())
                .putExtra("num", money_count.getText().toString())
                .putExtra("state", state)
                .putExtra("name", new UserUtil(getContext()).getUser().getNickName()));
        finish();
    }

    @Override
    public void error(String msg) {
        toast(msg);
    }

    @Override
    public void successCheck() {
        dialog.dismiss();
        DecimalFormat df = new DecimalFormat("##########0.00");
//                df.format(Double.parseDouble(money_show.toString()));
        try {
            if (df.format(Double.parseDouble(money_show.getText().toString())).equals("0.00")) {
                toast("金额不能小于0.01");
                return;
            }
        } catch (Exception ex) {
            toast("金额不能小于0.01");
            return;
        }
        if (type.equals("group")) {
            if (money_state.getText().toString().equals("随机红包")) {
                Map map = new HashMap();
                map.put("key", Const.KEY);
                map.put("uid", Const.UID);
                map.put("function", "SendManyOrdinaryPacket");
                map.put("userid", new UserUtil(getContext()).getUserId());
                map.put("amount", money_amount.getText().toString());
                map.put("count", money_count.getText().toString());
                map.put("message", money_content.getText().toString().trim().equals("") ? money_content.getHint().toString() : money_content.getText().toString());
                String json = StringUtil.toJson(map);
                state = "1";
                try {
                    String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                    presenter.SendManyOrdinaryPacket(data);
                } catch (Exception ex) {
                    dismissDialog();
                }
            } else {
                presenter.SendManyRandomPacket(money_amount.getText().toString(), money_count.getText().toString(), money_content.getText().toString().trim().equals("") ? money_content.getHint().toString() : money_content.getText().toString());
            }
        } else {
            Map map = new HashMap();
            map.put("key", Const.KEY);
            map.put("uid", Const.UID);
            map.put("function", "SendSingleRedPacket");
            map.put("userid", new UserUtil(getContext()).getUserId());
            map.put("receiveid", fid);
            map.put("amout", money_show.getText().toString());
            map.put("message", money_content.getText().toString().trim().equals("") ? money_content.getHint().toString() : money_content.getText().toString());
            String json = StringUtil.toJson(map);
            try {
                String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                presenter.SendSingleRedPacket(data);
            } catch (Exception ex) {
                dismissDialog();
            }
        }
    }

    void fhbgn() {//发红包功能
        DecimalFormat df = new DecimalFormat("##########0.00");
        if (getlei()) {
            if (Double.parseDouble(money_show.getText().toString()) < qimxx.getMin()) {
                toast("金额不能小于" + qimxx.getMin());
                return;
            }
            if (Double.parseDouble(money_show.getText().toString()) > qimxx.getMax()) {
                toast("金额不能大于" + qimxx.getMax());
                return;
            }

            if (Double.parseDouble(money_show.getText().toString()) % 10 != 0) {
                toast("发送金额应为10元的整数倍");
                return;
            }

            if (money_content.getText().toString().trim().equals("")) {
                toast("请输入雷数");
                return;
            }
        }
        try {
            if (df.format(Double.parseDouble(money_show.getText().toString())).equals("0.00")) {
                toast("金额不能小于0.01");
                return;
            }
        } catch (Exception ex) {
            toast("金额不能小于0.01");
            return;
        }
        if (money_count.getText().toString().equals("")) {
            toast("请输入红包个数");
            return;
        }

        send_money.setEnabled(false);
        golei();
//        tc();

    }

    String zfmm = "";

    void tc() {
        final EditText et = new EditText(this);
        new AlertDialog.Builder(this).setTitle("请输入支付密码")
                //   .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        // Toast.makeText(getApplicationContext(), et.getText().toString(),Toast.LENGTH_LONG).show();
                        zfmm = et.getText().toString().trim();
                        if (getlei()) {
                            golei();
                        } else {
                            gozc();
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    public void getTeam() {
        HttpUtils.GetTeam(new SubscriberRes<TeamBean>() {
            @Override
            public void onSuccess(TeamBean teamBean) {
                fid = teamBean.Id;
                App.targetId = teamBean.Id;
                getNum();

                if (getlei()) {
                    List<mqunlb> aa = QuanJu.getQuanJu().getMye();
                    for (mqunlb sad : aa) {//获取群信息
                        if (fid.equals(sad.getId())) {
                            qimxx = sad;
                            money_count.setText("" + qimxx.getRedCount());
                            tv_allpeople.setText("游戏倍数:" + qimxx.getOdds() + "倍");
                            tvTs.setText("红包发布范围" + qimxx.getMin() + "元-" + qimxx.getMax() + "元");
                            break;
                        }
                    }
                } else {

                }
            }

            @Override
            public void completeDialog() {
                dismissDialog();
            }
        }, Tcode, "0");
    }

    public void getNum() {
        HttpUtils.GCountAndOddsByTeamId(new SubscriberRes<ArrayList<LeiBean>>() {
            @Override
            public void onSuccess(ArrayList<LeiBean> userBean) {
                arrayList = userBean;
                leiBean = userBean.get(0);
                countid=userBean.get(0).Id;
                Tips=userBean.get(0).Tips;
                money_count.setText(leiBean.Count);
                mPackStyle=userBean.get(0).RightLowerText;
                tv_allpeople.setText("游戏倍数：" + leiBean.Odds + "倍");
            }

            @Override
            public void completeDialog() {
                dismissDialog();
            }
        }, fid);
    }

    void golei() {//发送雷红包
        showDialog();
        HttpUtils.SendManyRandomPacket(new SubscriberRes<Hongbao>() {
            @Override
            public void onSuccess(Hongbao userBean) {
                dismissDialog();
                send_money.setEnabled(true);
                setResult(22, new Intent()
                        .putExtra("hongbaoId", userBean.Id)
                        .putExtra("amount", money_amount.getText().toString())//钱  // 雷数 // 红包个数
//                            .putExtra("content", money_content.getText().toString().trim().equals("") ? money_content.getHint().toString() : money_content.getText().toString())
                        .putExtra("photo", new UserUtil(getContext()).getUser().getHeadUrl())
                        .putExtra("name", new UserUtil(getContext()).getUser().getNickName())
                        .putExtra("num", money_content.getText().toString())
                        .putExtra("Usernum", mPackStyle)
                        .putExtra("desc", money_amount.getText().toString() + "-" + money_content.getText().toString()+Tips)
                        .putExtra("suijiStr", userBean.RandomNum));
                finish();
            }

            @Override
            public void completeDialog() {
                dismissDialog();
            }
        }, money_show.getText().toString(), countid, qimxx.getId(), money_content.getText().toString().trim());
    }

    void gozc() {//发送正常红包
        Map map = new HashMap();
        map.put("function", "" + "SendManyRandomPacketNormal");
        map.put("userid", "" + new UserUtil(getContext()).getUserId2());
        map.put("totalamount", "" + money_show.getText().toString());//金额
        map.put("teamid", "" + fid);
        map.put("count", "" + money_count.getText().toString().trim());//个数
        map.put("paypassword", "" + zfmm);
        showDialog();
        NR.posts("WebService/RedPackageService.asmx/Entry", map, new StringCallback() {

            @Override
            public void onError(Request request, Exception e) {
                dismissDialog();
                IsZH.getToast(SendMoneyActivity.this, "服务器错误");  //吐司
                send_money.setEnabled(true);
            }

            @Override
            public void onResponse(String s) {
                dismissDialog();
                send_money.setEnabled(true);
                if (NRUtils.getYse(SendMoneyActivity.this, s)) {
                    String mye = NRUtils.getData(s, String.class);
                    setResult(22, new Intent()
                            .putExtra("hongbaoId", mye)
                            .putExtra("amount", money_show.getText().toString())//钱  // 雷数 // 红包个数
                            .putExtra("content", "" + money_content.getText().toString())
                            .putExtra("photo", new UserUtil(getContext()).getUser().getHeadUrl())
                            .putExtra("name", new UserUtil(getContext()).getUser().getNickName())
                            .putExtra("num", money_count.getText().toString())
                            .putExtra("state", state));
                    finish();
                }
            }

        });
    }


    private void showerweimaPopwindow(final ArrayList<LeiBean> list) {

        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ui_pop_num, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x38000000);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.Animation_Popup);
        // 在底部显示
        window.showAtLocation(money_count, Gravity.BOTTOM, 0, 0);
//        TextView tv_seven = (TextView) view.findViewById(R.id.tv_seven);
//        TextView tv_three= (TextView) view.findViewById(R.id.tv_three);
//        TextView tv_ten = (TextView) view.findViewById(R.id.tv_ten);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
//        if (list.size() == 1) {
//            tv_three.setText(list.get(0).Count);
//            tv_seven.setVisibility(View.GONE);
//            tv_ten.setVisibility(View.GONE);
//        } else if (list.size() == 2) {
//            tv_three.setText(list.get(0).Count);
//            tv_seven.setText(list.get(1).Count);
////            v_ten.setText(list.get(2).Count);
//            tv_ten.setVisibility(View.GONE);
//        }else if (list.size() == 3){
//            tv_three.setText(list.get(0).Count);
//            tv_seven.setText(list.get(1).Count);
//            tv_ten.setText(list.get(2).Count);
//        }
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        final BeiShuAdapter beiShuAdapter=new BeiShuAdapter(getContext());
        beiShuAdapter.addDatas(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(beiShuAdapter);


        beiShuAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos, Object item) {
                countid=list.get(pos).Id;
                Tips=list.get(pos).Tips;
                money_count.setText(beiShuAdapter.data.get(pos).Count);
                mPackStyle=beiShuAdapter.data.get(pos).RightLowerText;
                tv_allpeople.setText("游戏倍数：" + beiShuAdapter.data.get(pos).Odds+ "倍");
                window.dismiss();
            }
        });




        LinearLayout ll_num = (LinearLayout) view.findViewById(R.id.ll_num);
        ll_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });



//        tv_three.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                leiBean = list.get(0);
//                money_count.setText(leiBean.Count);
//                tv_allpeople.setText("游戏倍数：" + leiBean.Odds + "倍");
//                window.dismiss();
//            }
//        });
//        tv_seven.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                leiBean = list.get(1);
//                money_count.setText(leiBean.Count);
//                tv_allpeople.setText("游戏倍数：" + leiBean.Odds + "倍");
//                window.dismiss();
//            }
//        });
//        tv_ten.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                leiBean = list.get(2);
//                money_count.setText(leiBean.Count);
//                tv_allpeople.setText("游戏倍数：" + leiBean.Odds + "倍");
//                window.dismiss();
//            }
//        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });
    }
}
