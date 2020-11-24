package com.zykj.samplechat.helper;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.rey.material.app.Dialog;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.ICustomMessageViewGroup;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.IOnCustomMessageDrawListener;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.TeamBean;
import com.zykj.samplechat.model.Xiangyu;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.ui.activity.GetHongbaoListActivity;
import com.zykj.samplechat.ui.widget.App;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.CommonUtil;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomMessageDraw implements IOnCustomMessageDrawListener {
    private Context context;
    private MessageLayout messageLayout;
    private String UserIcon = "";
    private String UserName = "";

    public CustomMessageDraw(Context context, MessageLayout messageLayout) {
        this.context = context;
        this.messageLayout = messageLayout;
    }

    private Dialog dialog;
    TextView user_name, user_content;
    ImageView user_img, user_open;
    View dialog_view, user_see, user_close;
    LinearLayout bj_img;

    List<String> users = new ArrayList<String>();

    /**
     * 自定义消息渲染时，会调用该方法，本方法实现了自定义消息的创建，以及交互逻辑
     *
     * @param parent 自定义消息显示的父 View，需要把创建的自定义消息 View 添加到 parent 里
     * @param info   消息的具体信息
     */
    @Override
    public void onDraw(ICustomMessageViewGroup parent, final MessageInfo info) {
        View view = null;
        // 获取到自定义消息的 JSON 数据
        TIMCustomElem elem = (TIMCustomElem) info.getTIMMessage().getElement(0);
        UserName = "";
        UserIcon = "";
        //待获取用户资料的用户列表
//        users.clear();
//        users.add(info.getFromUser());

////获取用户资料
//        TIMFriendshipManager.getInstance().getUsersProfile(users, true, new TIMValueCallBack<List<TIMUserProfile>>() {
//            @Override
//            public void onError(int code, String desc) {
//                Log.e("getUsersProfile", "getUsersProfile failed: " + code + " desc");
//            }
//
//            @Override
//            public void onSuccess(List<TIMUserProfile> result) {
//                Log.e("getUsersProfile", "getUsersProfile succ");
//                for (TIMUserProfile res : result) {
//                    UserIcon = res.getFaceUrl();
//                    UserName = res.getNickName();
//                    Log.e("getUsersProfile", "identifier: " + res.getIdentifier() + " nickName: " + res.getNickName());
//                }
//            }
//        });


        // 自定义的 JSON 数据，需要解析成 bean 实例
        final CustomMessageData customMessageData = new Gson().fromJson(new String(elem.getData()), CustomMessageData.class);
        // 通过类型来创建不同的自定义消息展示 View
        if (customMessageData == null) {
            Log.e("No Custom Data: ", new String(elem.getData()));
            return;
        }
        // 通过类型来创建不同的自定义消息展示 View
        switch (customMessageData.msgType) {
            case CustomMessageData.TypeSend:
                view = View.inflate(context, R.layout.rongc_hongbao_item, null);
                // 把自定义消息 View 添加到 TUIKit 内部的父容器里
                parent.addMessageContentView(view);
                // 自定义消息 View 的实现，这里仅仅展示文本信息，并且实现超链接跳转

                final TextView title, lingqu, tvStyle,tvUserNum;
                final ImageView hb;
                final LinearLayout content;
                LinearLayout left;
                LinearLayout right;
                final LinearLayout llItem;
                title = (TextView) view.findViewById(R.id.title);
                tvStyle = (TextView) view.findViewById(R.id.tv_style);
                lingqu = (TextView) view.findViewById(R.id.lingqu);
                tvUserNum = (TextView) view.findViewById(R.id.tv_UserNum);
                hb = (ImageView) view.findViewById(R.id.iv_hb);
                content = (LinearLayout) view.findViewById(R.id.content);
                left = (LinearLayout) view.findViewById(R.id.left);
                right = (LinearLayout) view.findViewById(R.id.right);
                llItem = (LinearLayout) view.findViewById(R.id.ll_item);
//                if (info.isSelf()) {//消息方向，自己发送的
//                    left.setVisibility(View.VISIBLE);
//                    right.setVisibility(View.GONE);
//                } else {
//                    left.setVisibility(View.GONE);
//                    right.setVisibility(View.VISIBLE);
//                }
                content.setBackgroundColor(Color.parseColor("#f89d3a"));
                hb.setImageResource(R.drawable.ling_hongbao);
                lingqu.setText("领取红包");
                tvStyle.setText("游戏红包");
                title.setText(customMessageData.msgData.desc);
                tvUserNum.setText(customMessageData.msgData.UserNum);


                String shuju = CommonUtil.getshuju(context);
                if (!StringUtil.isEmpty(shuju)) {//判断红包是否被打开过  改变红包颜色
                    String shujus[] = shuju.split(",");
                    for (String x : shujus) {
                        if (StringUtils.isEmpty(x)) {
                            x = "0";
                        }
                        if (StringUtils.isEmpty(customMessageData.msgData.hongBaoId)) {
                            customMessageData.msgData.hongBaoId = "";
                        }
                        if (customMessageData.msgData.hongBaoId.equals(x)) {
                            content.setBackgroundColor(Color.parseColor("#fed09c"));
                            hb.setImageResource(R.drawable.hongbaob);
                        }
                    }
                }
                String shuju1 = CommonUtil.getshuju1(context);
                if (!StringUtil.isEmpty(shuju1)) {//判断红包是否被打领取  改变红包颜色
                    String shujus1[] = shuju1.split(",");
                    for (String x : shujus1) {
                        if (customMessageData.msgData.hongBaoId.equals(x)) {
                            content.setBackgroundColor(Color.parseColor("#fed09c"));
                            hb.setImageResource(R.drawable.hongbaob);
                            lingqu.setText("红包已领取");
                        }
                    }
                }


                llItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        content.setBackgroundColor(Color.parseColor("#fed09c"));
                        hb.setImageResource(R.drawable.hongbaob);
                        HttpUtils.GetTeam(new SubscriberRes<TeamBean>() {
                            @Override
                            public void onSuccess(TeamBean teamBean) {
                                UserIcon = teamBean.ImagePath;
                                UserName = teamBean.Name;
                            }

                            @Override
                            public void completeDialog() {
                            }
                        }, info.getFromUser(), "1");
                        String shuju = CommonUtil.getshuju(context);
                        if (StringUtil.isEmpty(shuju)) {//标记红包是否被领取
                            CommonUtil.setshuju("" + customMessageData.msgData.hongBaoId, context);
                        } else {
                            CommonUtil.setshuju(shuju + "," + customMessageData.msgData.hongBaoId, context);
                        }
                        HttpUtils.IsOpenPackage(new SubscriberRes<Xiangyu>() {
                            @Override
                            public void onSuccess(final Xiangyu res) {
                                if (res.IsGet == 0) {   //我自己没抢 IsGet 是否领取过 0未领取1已领取
                                    dialog_view = LayoutInflater.from(context).inflate(R.layout.dialog_open_hongbao, null);

                                    user_name = (TextView) dialog_view.findViewById(R.id.user_name);
                                    user_content = (TextView) dialog_view.findViewById(R.id.user_content);
                                    user_img = (ImageView) dialog_view.findViewById(R.id.user_img);
                                    bj_img = (LinearLayout) dialog_view.findViewById(R.id.iv_bj);
                                    user_see = (View) dialog_view.findViewById(R.id.user_see);
                                    user_close = (View) dialog_view.findViewById(R.id.user_close);
                                    user_open = (ImageView) dialog_view.findViewById(R.id.user_open);
                                    user_name.setText(UserName);
                                    user_content.setText(customMessageData.msgData.desc);
//
                                    if (res.IsOverTime == 0) {//IsOverTime 红包是否超时0不超时1超时
//                        if(res.data.Grab.equals("1")){   //我自己没抢 包被抢完了
                                        if (res.IsHave == 0) {   //我自己没抢 包被抢完了  IsHave 红包是否还有剩余 0没有1有、
                                            user_open.setVisibility(View.GONE);
                                            user_see.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                                            user_content.setText("手慢了,红包派完了");
                                            bj_img.setBackgroundResource(R.drawable.qiangmei);
                                        } else {
                                            bj_img.setBackgroundResource(R.drawable.qiangmei);
                                            user_open.setVisibility(View.VISIBLE);
                                            user_see.setVisibility(View.VISIBLE);
                                        }
                                    } else {
                                        user_open.setVisibility(View.GONE);
                                        user_see.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                                        user_content.setText("手慢了,红包已超时");
                                        bj_img.setBackgroundResource(R.drawable.qiangmei);
                                    }

                                    Glide.with(context)
                                            .load(Const.getbase(UserIcon))
                                            .apply(new RequestOptions().placeholder(R.drawable.userimg))
                                            .into(user_img);
                                    user_see.setOnClickListener(new View.OnClickListener() {//查看详情
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();//详情
                                            if (res.IsOverTime == 0) {//IsOverTime 红包是否超时0不超时1超时
//                        if(res.data.Grab.equals("1")){   //我自己没抢 包被抢完了
                                                if (res.IsHave == 0) {   //我自己没抢 包被抢完了  IsHave 红包是否还有剩余 0没有1有、
                                                    if (info.isGroup()) {
                                                        Intent intent = new Intent();
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        intent.putExtra("data", new Bun().
                                                                putString("hongbaoId", customMessageData.msgData.hongBaoId).putString("photo", customMessageData.msgData.photo).
                                                                putString("content", customMessageData.msgData.desc).putString("name", customMessageData.msgData.name).
                                                                putString("amount", customMessageData.msgData.money + "").putString("num", customMessageData.msgData.leiNum + "").
                                                                putString("open", "0").putString("qid", "" + App.targetId).
                                                                putString("extra", customMessageData.msgData.suijiStr).ok());
                                                        intent.setClass(App.getAppContext(), GetHongbaoListActivity.class);
                                                        context.startActivity(intent);


//                                                        App.getAppContext().startActivity(new Intent(App.getAppContext(), GetHongbaoListActivity.class).
//                                                                putExtra("data", new Bun().
//                                                                putString("hongbaoId", customMessageData.msgData.hongBaoId).putString("photo", customMessageData.msgData.photo).
//                                                                putString("content", customMessageData.msgData.desc).putString("name", customMessageData.msgData.name).
//                                                                putString("amount", customMessageData.msgData.money + "").putString("num", customMessageData.msgData.leiNum + "").
//                                                                putString("open", "0").putString("qid", "" + App.targetId).
//                                                                putString("extra", customMessageData.msgData.suijiStr).ok()));
                                                    }


//                                            if (message.getConversationType().name().equals("PRIVATE")) {
////                                                context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("open", "0").putString("qid", "" + App.targetId).putString("bs123", "" + content.getDesc()).ok()));
//                                            } else {
//                                                context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).putString("open", "0").putString("qid", "" + App.targetId).putString("bs123", "" + content.getDesc()).putString("extra", content.getExtra()).ok()));
//                                            }
                                                }else   {
                                                    if (info.isSelf()){
                                                        Intent intent = new Intent();
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        intent.putExtra("data", new Bun().
                                                                putString("hongbaoId", customMessageData.msgData.hongBaoId).putString("photo", customMessageData.msgData.photo).
                                                                putString("content", customMessageData.msgData.desc).putString("name", customMessageData.msgData.name).
                                                                putString("amount", customMessageData.msgData.money + "").putString("num", customMessageData.msgData.leiNum + "").
                                                                putString("open", "0").putString("qid", "" + App.targetId).
                                                                putString("extra", customMessageData.msgData.suijiStr).ok());
                                                        intent.setClass(App.getAppContext(), GetHongbaoListActivity.class);
                                                        context.startActivity(intent);
                                                    }
                                                }
                                            } else {

                                                if (info.isGroup()) {
                                                    Intent intent = new Intent();
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    intent.putExtra("data", new Bun().
                                                            putString("hongbaoId", customMessageData.msgData.hongBaoId).putString("photo", customMessageData.msgData.photo).
                                                            putString("content", customMessageData.msgData.desc).putString("name", customMessageData.msgData.name).
                                                            putString("amount", customMessageData.msgData.money + "").putString("num", customMessageData.msgData.leiNum + "").
                                                            putString("open", "0").putString("qid", "" + App.targetId).
                                                            putString("extra", customMessageData.msgData.suijiStr).ok());
                                                    intent.setClass(context, GetHongbaoListActivity.class);
                                                    context.startActivity(intent);
//                                                    App.getAppContext().startActivity(new Intent(App.getAppContext(), GetHongbaoListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
//                                                            .putExtra("data", new Bun().
//                                                            putString("hongbaoId", customMessageData.msgData.hongBaoId).putString("photo", customMessageData.msgData.photo).
//                                                            putString("content", customMessageData.msgData.desc).putString("name", customMessageData.msgData.name).
//                                                            putString("amount", customMessageData.msgData.money + "").putString("num", customMessageData.msgData.leiNum + "").
//                                                            putString("open", "0").putString("qid", "" + App.targetId).
//                                                            putString("extra", customMessageData.msgData.suijiStr).ok()));
                                                }
//                                        if (message.getConversationType().name().equals("PRIVATE")) {
//                                            context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("open", "0").putString("qid", "" + App.targetId).putString("bs123", "" + content.getDesc()).ok()));
//                                        } else {
//                                            context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).putString("open", "0").putString("qid", "" + App.targetId).putString("bs123", "" + content.getDesc()).putString("extra", content.getExtra()).ok()));
//                                        }
                                            }

                                        }
                                    });
                                    user_close.setOnClickListener(new View.OnClickListener() {//关闭
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    user_open.setOnClickListener(new View.OnClickListener() {//领取红包
                                        @Override
                                        public void onClick(View v) {
                                            //user_open.setOnClickListener(null);//点击过一次之后 不能在点击
                                            ObjectAnimator animator = ObjectAnimator.ofFloat(user_open, "rotationY", 0, 180, 0);
                                            animator.setDuration(1000);
                                            animator.start();
                                            //dialog.dismiss();
                                            new Handler().postDelayed(new Runnable() {
                                                public void run() {
                                                    dialog.dismiss();
                                                    content.setBackgroundColor(Color.parseColor("#fed09c"));
                                                    hb.setImageResource(R.drawable.hongbaob);
                                                    String shuju1 = CommonUtil.getshuju1(context);
                                                    lingqu.setText("红包已领取");
                                                    if (StringUtil.isEmpty(shuju1)) {//标记红包是否被领取
                                                        CommonUtil.setshuju1("" + customMessageData.msgData.hongBaoId, context);
                                                    } else {
                                                        CommonUtil.setshuju1(shuju1 + "," + customMessageData.msgData.hongBaoId, context);
                                                    }
                                                    String shuju2 = CommonUtil.getshuju1(context);
                                                    if (info.isGroup()) {
                                                        Intent intent = new Intent();
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        intent.putExtra("data", new Bun().
                                                                putString("hongbaoId", customMessageData.msgData.hongBaoId).putString("photo", customMessageData.msgData.photo).
                                                                putString("content", customMessageData.msgData.desc).putString("name", customMessageData.msgData.name).
                                                                putString("amount", customMessageData.msgData.money + "").putString("num", customMessageData.msgData.leiNum + "").
                                                                putString("open", "1").putString("qid", "" + App.targetId).
                                                                putString("extra", customMessageData.msgData.suijiStr).ok());
                                                        intent.setClass(App.getAppContext(), GetHongbaoListActivity.class);
                                                        context.startActivity(intent);
//                                                        App.getAppContext().startActivity(new Intent(App.getAppContext(), GetHongbaoListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
//                                                                    .putExtra("data", new Bun().
//                                                                    putString("hongbaoId", customMessageData.msgData.hongBaoId).putString("photo", customMessageData.msgData.photo).
//                                                                    putString("content", customMessageData.msgData.desc).putString("name", customMessageData.msgData.name).
//                                                                    putString("amount", customMessageData.msgData.money + "").putString("num", customMessageData.msgData.leiNum + "").
//                                                                    putString("open", "1").putString("qid", "" + App.targetId).
//                                                                    putString("extra", customMessageData.msgData.suijiStr).ok()));
                                                    }


//                                            if (message.getConversationType().name().equals("PRIVATE"))
//                                                context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("open", "1").putString("qid", "" + App.targetId).putString("bs123", "" + content.getDesc()).ok()));
//                                            else {
//                                                context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).putString("open", "1").putString("qid", "" + App.targetId).putString("bs123", "" + content.getDesc()).putString("extra", content.getExtra()).ok()));
//                                            }

                                                }
                                            }, 500);

                                        }
                                    });
                                    dialog = new Dialog(context).contentView(dialog_view);
                                    dialog.show();

                                } else {
                                    // dialog.dismiss();
                                    if (info.isGroup()) {
                                        Intent intent = new Intent();
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("data", new Bun().
                                                putString("hongbaoId", customMessageData.msgData.hongBaoId).putString("photo", customMessageData.msgData.photo).
                                                putString("content", customMessageData.msgData.desc).putString("name", customMessageData.msgData.name).
                                                putString("amount", customMessageData.msgData.money + "").putString("num", customMessageData.msgData.leiNum + "").
                                                putString("open", "0").putString("qid", "" + App.targetId).
                                                putString("extra", customMessageData.msgData.suijiStr).ok());
                                        intent.setClass(App.getAppContext(), GetHongbaoListActivity.class);
                                        context.startActivity(intent);
//                                        App.getAppContext().startActivity(new Intent(App.getAppContext(), GetHongbaoListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
//                                                .putExtra("data", new Bun().
//                                                putString("hongbaoId", customMessageData.msgData.hongBaoId).putString("photo", customMessageData.msgData.photo).
//                                                putString("content", customMessageData.msgData.desc).putString("name", customMessageData.msgData.name).
//                                                putString("amount", customMessageData.msgData.money + "").putString("num", customMessageData.msgData.leiNum + "").
//                                                putString("open", "0").putString("qid", "" + App.targetId).
//                                                putString("extra", customMessageData.msgData.suijiStr).ok()));
                                    }
//                            if (message.getConversationType().name().equals("PRIVATE"))
//                                context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("open", "0").putString("qid", "" + App.targetId).putString("bs123", "" + content.getDesc()).ok()));
//                            else
//                                context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).putString("open", "0").putString("qid", "" + App.targetId).putString("bs123", "" + content.getDesc()).ok()));
                                }
                            }

                            @Override
                            public void completeDialog() {
                            }
                        }, customMessageData.msgData.hongBaoId);
                    }
                });
                break;
            case CustomMessageData.TypeZhuan:
                // 自定义消息 View 的实现，这里仅仅展示文本信息，并且实现超链接跳转
                view = View.inflate(context, R.layout.rongc_zhuanzhang_item, null);
                // 把自定义消息 View 添加到 TUIKit 内部的父容器里
                parent.addMessageContentView(view);

                TextView title1;
                TextView style1;
                LinearLayout content1;
                LinearLayout left1;
                LinearLayout right1;

                title1 = (TextView) view.findViewById(R.id.title);
                style1 = (TextView) view.findViewById(R.id.tv_style);
                content1 = (LinearLayout) view.findViewById(R.id.content);
                left1 = (LinearLayout) view.findViewById(R.id.left);
                right1 = (LinearLayout) view.findViewById(R.id.right);

//                if (info.isSelf()) {//消息方向，自己发送的
//                    left1.setVisibility(View.VISIBLE);
//                    right1.setVisibility(View.GONE);
//                } else {
//                    left1.setVisibility(View.GONE);
//                    right1.setVisibility(View.VISIBLE);
//                }

                content1.setBackgroundColor(Color.parseColor("#f89d3a"));
                title1.setText(customMessageData.msgData.content);
                style1.setText("转账");
                break;
        }


    }


}
