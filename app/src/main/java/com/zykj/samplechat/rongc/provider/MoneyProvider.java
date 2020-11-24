package com.zykj.samplechat.rongc.provider;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rey.material.app.Dialog;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.QuanJu;
import com.zykj.samplechat.model.Xiangyu;
import com.zykj.samplechat.model.mqunlb;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.rongc.messge.MoneyMessage;
import com.zykj.samplechat.ui.activity.GetHongbaoActivity;
import com.zykj.samplechat.ui.activity.GetHongbaoListActivity;
import com.zykj.samplechat.ui.widget.App;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.CommonUtil;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.StringUtils;

import java.util.List;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ninos on 2017/8/22.
 */
@ProviderTag(messageContent = MoneyMessage.class)
public class MoneyProvider extends IContainerItemProvider.MessageProvider<MoneyMessage> {

    Context context;
    private Dialog dialog;
    TextView user_name, user_content;
    ImageView user_img,bj_img, user_open;
    View dialog_view, user_see, user_close;
    boolean yesno=true;//红包是否可点击
    protected CompositeSubscription mCompositeSubscription;

    class ViewHolder {
        TextView title,lingqu;
        ImageView hb;
        LinearLayout content;
        LinearLayout left;
        LinearLayout right;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.rongc_hongbao_item, null);
        ViewHolder holder = new ViewHolder();
        holder.title = (TextView) view.findViewById(R.id.title);
        holder.lingqu = (TextView) view.findViewById(R.id.lingqu);
        holder.hb=(ImageView)view.findViewById(R.id.iv_hb);
        holder.content = (LinearLayout) view.findViewById(R.id.content);
        holder.left = (LinearLayout) view.findViewById(R.id.left);
        holder.right = (LinearLayout) view.findViewById(R.id.right);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View v, int position, MoneyMessage content, UIMessage message) {
        ViewHolder holder = (ViewHolder) v.getTag();
        if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.left.setVisibility(View.VISIBLE);
            holder.right.setVisibility(View.GONE);
        } else {
            holder.left.setVisibility(View.GONE);
            holder.right.setVisibility(View.VISIBLE);
        }
        holder.content.setBackgroundColor(Color.parseColor("#f89d3a"));
        holder.hb.setImageResource(R.drawable.ling_hongbao);
        holder.lingqu.setText("领取红包");
  //      LogUtils.i("xxxxx", "" +content.getDesc());  //输出测试
        holder.title.setText(content.getDesc());
//        if(!QuanJu.getQuanJu().getlei(App.targetId)){//这里判断是不是中雷红包  如果不是  提示的消息应该是留言内容
//            String[] a =content.getDesc().split("-");
//            if(a.length>1){
//                holder.title.setText(a[1]);
//            }
//        }

        String  shuju = CommonUtil.getshuju(context);
        if(!StringUtil.isEmpty(shuju)){//判断红包是否被打开过  改变红包颜色
            String shujus[] = shuju.split(",");
            for(String x:shujus){
                if(StringUtils.isEmpty(x)){
                    x="0";
                }
                if(StringUtils.isEmpty(content.getHongbaoId())){
                    content.setHongbaoId("");
                }
                if(content.getHongbaoId().equals(x)){
                    holder.content.setBackgroundColor(Color.parseColor("#fed09c"));
                    holder.hb.setImageResource(R.drawable.hongbaob);
                }
            }
        }
        String  shuju1 = CommonUtil.getshuju1(context);
        if(!StringUtil.isEmpty(shuju1)){//判断红包是否被打领取  改变红包颜色
            String shujus1[] = shuju1.split(",");
            for(String x:shujus1){
                if(content.getHongbaoId().equals(x)){
                    holder.content.setBackgroundColor(Color.parseColor("#fed09c"));
                    holder.hb.setImageResource(R.drawable.hongbaob);
                    holder.lingqu.setText("红包已领取");
                }
            }
        }
//
    }

    @Override
    public Spannable getContentSummary(MoneyMessage data) {
        return new SpannableString("[红包消息]");
    }

    @Override
    public void onItemClick(final View view, int position, final MoneyMessage content, final UIMessage message) {
//        if(!yesno){
//            return;
//        }
//        yesno=false;
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.content.setBackgroundColor(Color.parseColor("#fed09c"));
        holder.hb.setImageResource(R.drawable.hongbaob);
        String  shuju = CommonUtil.getshuju(context);
        if(StringUtil.isEmpty(shuju)){//标记红包是否被领取
            CommonUtil.setshuju(""+content.getHongbaoId(),context);
        }else{
            CommonUtil.setshuju(shuju+","+content.getHongbaoId(),context);
        }

        HttpUtils.IsOpenPackage(new SubscriberRes<Xiangyu>() {
            @Override
            public void onSuccess(final Xiangyu res) {
//                yesno=true;
                if (res.IsGet==0) {   //我自己没抢 IsGet 是否领取过 0未领取1已领取
                    dialog_view = LayoutInflater.from(context).inflate(R.layout.dialog_open_hongbao, null);
                    user_name = (TextView) dialog_view.findViewById(R.id.user_name);
                    user_content = (TextView) dialog_view.findViewById(R.id.user_content);
                    user_img = (ImageView) dialog_view.findViewById(R.id.user_img);
                    bj_img = (ImageView) dialog_view.findViewById(R.id.iv_bj);
                    user_see = (View) dialog_view.findViewById(R.id.user_see);
                    user_close = (View) dialog_view.findViewById(R.id.user_close);
                    user_open = (ImageView) dialog_view.findViewById(R.id.user_open);
                    user_name.setText(content.getName());
                    user_content.setText(content.getDesc());
//                    if(!QuanJu.getQuanJu().getlei(App.targetId)){//这里判断是不是中雷红包  如果不是  提示的消息应该是留言内容
    //                        String[] a =content.getDesc().split("-");
    //                        if(a.length>1){
    //                            user_content.setText(a[1]);
    //                        }
//                    }
                    if(res.IsOverTime==0){//IsOverTime 红包是否超时0不超时1超时
//                        if(res.data.Grab.equals("1")){   //我自己没抢 包被抢完了
                        if(res.IsHave==0){   //我自己没抢 包被抢完了  IsHave 红包是否还有剩余 0没有1有、
                            user_open.setVisibility(View.GONE);
                            user_see.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                            user_content.setText("手慢了,红包派完了");
                            bj_img.setImageResource(R.drawable.qiangmei);
                        }else{
                            bj_img.setImageResource(R.drawable.qiangmei);
                            user_open.setVisibility(View.VISIBLE);
                            user_see.setVisibility(View.VISIBLE);
                        }
                    }else{
                        user_open.setVisibility(View.GONE);
                        user_see.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                        user_content.setText("手慢了,红包已超时");
                        bj_img.setImageResource(R.drawable.qiangmei);
                    }

                    Glide.with(context)
                            .load(Const.getbase(content.getPhoto()))
                            .apply(new RequestOptions().placeholder(R.drawable.userimg))
                            .into(user_img);
                    user_see.setOnClickListener(new View.OnClickListener() {//查看详情
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();//详情
                            if(res.IsOverTime==0){//IsOverTime 红包是否超时0不超时1超时
//                        if(res.data.Grab.equals("1")){   //我自己没抢 包被抢完了
                                if(res.IsHave==0){   //我自己没抢 包被抢完了  IsHave 红包是否还有剩余 0没有1有、
                                    if (message.getConversationType().name().equals("PRIVATE")) {
                                        context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("open", "0").putString("qid", "" + App.targetId).putString("bs123", "" + content.getDesc()).ok()));
                                    } else {
                                        context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).putString("open", "0").putString("qid", "" + App.targetId).putString("bs123", "" + content.getDesc()).putString("extra", content.getExtra()).ok()));
                                    }
                                }
                            }else{
                                if (message.getConversationType().name().equals("PRIVATE")) {
                                    context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("open", "0").putString("qid", "" + App.targetId).putString("bs123", "" + content.getDesc()).ok()));
                                } else {
                                    context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).putString("open", "0").putString("qid", "" + App.targetId).putString("bs123", "" + content.getDesc()).putString("extra", content.getExtra()).ok()));
                                }
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
                            ObjectAnimator animator = ObjectAnimator.ofFloat(user_open,"rotationY",0,180,0);
                            animator.setDuration(1000);
                            animator.start();
                            //dialog.dismiss();
                            new Handler().postDelayed(new Runnable(){
                                public void run() {
                                    dialog.dismiss();
                                    ViewHolder holder = (ViewHolder) view.getTag();
                                    holder.content.setBackgroundColor(Color.parseColor("#fed09c"));
                                    holder.hb.setImageResource(R.drawable.hongbaob);
                                    String  shuju1 = CommonUtil.getshuju1(context);
                                    holder.lingqu.setText("红包已领取");
                                    if(StringUtil.isEmpty(shuju1)){//标记红包是否被领取
                                        CommonUtil.setshuju1(""+content.getHongbaoId(),context);
                                    }else{
                                        CommonUtil.setshuju1(shuju1+","+content.getHongbaoId(),context);
                                    }
                                    String  shuju2 = CommonUtil.getshuju1(context);
                                    if (message.getConversationType().name().equals("PRIVATE"))
                                        context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("open", "1").putString("qid", ""+App.targetId).putString("bs123", ""+content.getDesc()).ok()));
                                    else {
                                        context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).putString("open", "1").putString("qid", ""+App.targetId).putString("bs123", ""+content.getDesc()).putString("extra", content.getExtra()).ok()));
                                    }

                                }
                            }, 500);

                        }
                    });
                    dialog = new Dialog(context).backgroundColor(Color.parseColor("#ffffff")).contentView(dialog_view);
                    dialog.show();
                }else{
                    // dialog.dismiss();
                    if (message.getConversationType().name().equals("PRIVATE"))
                        context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("open", "0").putString("qid", ""+App.targetId).putString("bs123", ""+content.getDesc()).ok()));
                    else
                        context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).putString("open", "0").putString("qid", ""+App.targetId).putString("bs123", ""+content.getDesc()).ok()));
                }
            }
            @Override
            public void completeDialog() {
            }
        }, content.getHongbaoId());
//       Map map = new HashMap();
//        map.put("key", Const.KEY);
//        map.put("uid", Const.UID);
//        map.put("function", "IsOpenPackage");
//        map.put("userid", new UserUtil(context).getUserId2());
//        map.put("packid", content.getHongbaoId());
//        String json = StringUtil.toJson(map);
//        String data = "";
//        try {
//            data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//        } catch (Exception ex) {
//        }
//
//        Subscriber<Res<Xiangyu>> aa=  new Subscriber<Res<Xiangyu>>() {
//
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                //  Log.d("xxxx", "onError: ");
//                yesno=true;
//            }
//
//            @Override
//            public void onNext(Res<Xiangyu> res) {
//                yesno=true;
//                if (res.code != Const.OK) {
//                    IsZH.getToast(context, ""+res.error);  //吐司
//                }
//                if (res.code == Const.OK) {
////                    if (res.data.Flag.equals("0")) {   //我自己没抢
//                    if (res.data.IsGet==0) {   //我自己没抢 IsGet 是否领取过 0未领取1已领取
//                        dialog_view = LayoutInflater.from(context).inflate(R.layout.dialog_open_hongbao, null);
//                        user_name = (TextView) dialog_view.findViewById(R.id.user_name);
//                        user_content = (TextView) dialog_view.findViewById(R.id.user_content);
//                        user_img = (ImageView) dialog_view.findViewById(R.id.user_img);
//                        bj_img = (ImageView) dialog_view.findViewById(R.id.iv_bj);
//                        user_see = (View) dialog_view.findViewById(R.id.user_see);
//                        user_close = (View) dialog_view.findViewById(R.id.user_close);
//                        user_open = (ImageView) dialog_view.findViewById(R.id.user_open);
//                        user_name.setText(content.getName());
//                        user_content.setText(content.getDesc());
//                        if(!QuanJu.getQuanJu().getlei(App.targetId)){//这里判断是不是中雷红包  如果不是  提示的消息应该是留言内容
//                            String[] a =content.getDesc().split("-");
//                            if(a.length>1){
//                                user_content.setText(a[1]);
//                            }
//                        }
//                        if(res.data.IsOverTime==0){//IsOverTime 红包是否超时0不超时1超时
////                        if(res.data.Grab.equals("1")){   //我自己没抢 包被抢完了
//                            if(res.data.IsHave==0){   //我自己没抢 包被抢完了  IsHave 红包是否还有剩余 0没有1有、
//                                user_open.setVisibility(View.GONE);
//                                user_see.setVisibility(View.VISIBLE);
//                                user_content.setText("手慢了,红包派完了");
//                                bj_img.setImageResource(R.drawable.qiangmei);
//                            }else{
//                                bj_img.setImageResource(R.drawable.qiangmei);
//                                user_open.setVisibility(View.VISIBLE);
//                                user_see.setVisibility(View.VISIBLE);
//                            }
//                        }else{
//                            user_open.setVisibility(View.GONE);
//                            user_see.setVisibility(View.VISIBLE);
//                            user_content.setText("手慢了,红包已超时");
//                            bj_img.setImageResource(R.drawable.qiangmei);
//                        }
//
//                        Glide.with(context)
//                                .load(Const.getbase(content.getPhoto()))
//                                .centerCrop()
//                                .crossFade()
//                                .placeholder(R.drawable.userimg)
//                                .into(user_img);
//                        user_see.setOnClickListener(new View.OnClickListener() {//查看详情
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();//详情
//                                if (message.getConversationType().name().equals("PRIVATE")){
//                                    context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("open", "0").putString("qid", ""+App.targetId).putString("bs123", ""+content.getDesc()).ok()));
//                                } else{
//                                    context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).putString("open", "0").putString("qid", ""+App.targetId).putString("bs123", ""+content.getDesc()).putString("extra", content.getExtra()).ok()));
//                                }
//
//                            }
//                        });
//                        user_close.setOnClickListener(new View.OnClickListener() {//关闭
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//                        user_open.setOnClickListener(new View.OnClickListener() {//领取红包
//                            @Override
//                            public void onClick(View v) {
//                                //user_open.setOnClickListener(null);//点击过一次之后 不能在点击
//                                ObjectAnimator animator = ObjectAnimator.ofFloat(user_open,"rotationY",0,180,0);
//                                animator.setDuration(1000);
//                                animator.start();
//                                //dialog.dismiss();
//                                new Handler().postDelayed(new Runnable(){
//                                    public void run() {
//                                        dialog.dismiss();
//                                        ViewHolder holder = (ViewHolder) view.getTag();
//                                        holder.content.setBackgroundColor(Color.parseColor("#fed09c"));
//                                        holder.hb.setImageResource(R.drawable.hongbaob);
//                                        String  shuju1 = CommonUtil.getshuju1(context);
//                                        holder.lingqu.setText("红包已领取");
//                                        if(StringUtil.isEmpty(shuju1)){//标记红包是否被领取
//                                            CommonUtil.setshuju1(""+content.getHongbaoId(),context);
//                                        }else{
//                                            CommonUtil.setshuju1(shuju1+","+content.getHongbaoId(),context);
//                                        }
//                                        String  shuju2 = CommonUtil.getshuju1(context);
//                                        if (message.getConversationType().name().equals("PRIVATE"))
//                                            context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("open", "1").putString("qid", ""+App.targetId).putString("bs123", ""+content.getDesc()).ok()));
//                                        else
//                                        {
//                                            context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).putString("open", "1").putString("qid", ""+App.targetId).putString("bs123", ""+content.getDesc()).ok()));
//                                        }
//
//                                    }
//                                }, 500);
//
//                            }
//                        });
//                        dialog = new Dialog(context).backgroundColor(Color.parseColor("#ffffff")).contentView(dialog_view);
//                        dialog.show();
//                    }else{
//                       // dialog.dismiss();
//                        if (message.getConversationType().name().equals("PRIVATE"))
//                            context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("open", "0").putString("qid", ""+App.targetId).putString("bs123", ""+content.getDesc()).ok()));
//                        else
//                            context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).putString("open", "0").putString("qid", ""+App.targetId).putString("bs123", ""+content.getDesc()).ok()));
//                    }
//
//                } else {
//                }
//            }};
//        //根据不同群 判断具体走哪个方法
//        if (App.targetId.equals("571")) {
//            Subscription subscription = Net.getService()
//                    .IsOpenPacketj(data)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(aa);
//            if (this.mCompositeSubscription == null) {
//                this.mCompositeSubscription = new CompositeSubscription();
//            }
//            this.mCompositeSubscription.add(subscription);
//        }else if (App.targetId.equals("570")) {
//            Subscription subscription = Net.getService()
//                    .IsOpenPacketj(data)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(aa);
//            if (this.mCompositeSubscription == null) {
//                this.mCompositeSubscription = new CompositeSubscription();
//            }
//            this.mCompositeSubscription.add(subscription);
//        }else{
//            Subscription subscription = Net.getService()
//                    .IsOpenPacket(data)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(aa);
//            if (this.mCompositeSubscription == null) {
//                this.mCompositeSubscription = new CompositeSubscription();
//            }
//            this.mCompositeSubscription.add(subscription);
//        }

    }
    public static void FlipAnimatorXViewShow(final View oldView, final View newView, final long time) {

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(oldView, "rotationX", 0, 90);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(newView, "rotationX", -90, 0);
        animator2.setInterpolator(new OvershootInterpolator(2.0f));

        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                oldView.setVisibility(View.GONE);
                animator2.setDuration(time).start();
                newView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator1.setDuration(time).start();
    }
    @Override
    public void onItemLongClick(View view, int position, MoneyMessage content, UIMessage message) {

    }
    //是否走中雷红包
    private Boolean getlei(String fid) {
        List<mqunlb> aa = QuanJu.getQuanJu().getMye();
        for(mqunlb sad : aa){//
            if(fid.equals(sad.getId())){
                return true;
            }
        }
        return false;
    }
}