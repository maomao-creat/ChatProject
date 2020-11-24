package com.zykj.samplechat.rongc.provider;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rey.material.app.Dialog;
import com.zykj.samplechat.R;
import com.zykj.samplechat.rongc.messge.ZhuanZhangMessage;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ninos on 2017/8/22.
 */
@ProviderTag(messageContent = ZhuanZhangMessage.class)
public class zhuanzhangProvider extends IContainerItemProvider.MessageProvider<ZhuanZhangMessage> {

    Context context;
    private Dialog dialog;
    TextView user_name, user_content;
    ImageView user_img,bj_img, user_open;
    View dialog_view, user_see, user_close;
    protected CompositeSubscription mCompositeSubscription;

    class ViewHolder {
        TextView title;
        LinearLayout content;
        LinearLayout left;
        LinearLayout right;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.rongc_zhuanzhang_item, null);
        ViewHolder holder = new ViewHolder();
        holder.title = (TextView) view.findViewById(R.id.title);
        holder.content = (LinearLayout) view.findViewById(R.id.content);
        holder.left = (LinearLayout) view.findViewById(R.id.left);
        holder.right = (LinearLayout) view.findViewById(R.id.right);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View v, int position, ZhuanZhangMessage content, UIMessage message) {
        ViewHolder holder = (ViewHolder) v.getTag();
        if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.left.setVisibility(View.VISIBLE);
            holder.right.setVisibility(View.GONE);
        } else {
            holder.left.setVisibility(View.GONE);
            holder.right.setVisibility(View.VISIBLE);
        }
        holder.content.setBackgroundColor(Color.parseColor("#f89d3a"));
        holder.title.setText(content.getContent());
    }

    @Override
    public Spannable getContentSummary(ZhuanZhangMessage data) {
        return new SpannableString("[转账消息]");
    }

    @Override
    public void onItemClick(final View view, int position, final ZhuanZhangMessage content, final UIMessage message) {

//        if (message.getConversationType().name().equals("PRIVATE"))
//            if (message.getMessage().getSenderUserId().equals(new UserUtil(context).getUserId() + "")) {
//                context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).ok()));
//                return;
//            }
//
//        if (message.getConversationType().name().equals("PRIVATE"))
//            context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).ok()));
//        else
//           context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).ok()));
//        Map map = new HashMap();
//        map.put("key", Const.KEY);
//        map.put("uid", Const.UID);
//        map.put("function", "IsOpenPacket");
//        map.put("userid", new UserUtil(context).getUserId());
//        map.put("packetid", content.getHongbaoId());
//        String json = StringUtil.toJson(map);
//        String data = "";
//        try {
//            data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//        } catch (Exception ex) {
//        }
//        Subscription subscription = Net.getService()
//                .IsOpenPacket(data)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Res<Xiangyu>>() {
//
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("xxxx", "onError: ");
//                    }
//
//                    @Override
//                    public void onNext(Res<Xiangyu> res) {
//                        if (res.code == Const.OK||res.code == 201) {
//                            if (App.targetId.equals("568")) {
//                                res.data.OverTime="1";
//                            }else  if (App.targetId.equals(" 567")) {
//                                res.data.OverTime="1";
//                            }
//                            if(res.code == 201){
//                                res.data.OverTime="1";
//                                res.data.Flag="0";
//                            }
//                            if (res.data.Flag.equals("0")) {   //我自己没抢
//                                dialog_view = LayoutInflater.from(context).inflate(R.layout.dialog_open_hongbao, null);
//                                user_name = (TextView) dialog_view.findViewById(R.id.user_name);
//                                user_content = (TextView) dialog_view.findViewById(R.id.user_content);
//                                user_img = (ImageView) dialog_view.findViewById(R.id.user_img);
//                                bj_img = (ImageView) dialog_view.findViewById(R.id.iv_bj);
//                                user_see = (View) dialog_view.findViewById(R.id.user_see);
//                                user_close = (View) dialog_view.findViewById(R.id.user_close);
//                                user_open = (ImageView) dialog_view.findViewById(R.id.user_open);
//                                user_name.setText(content.getName());
//                                user_content.setText(content.getDesc());
//
//                               if(res.data.OverTime.equals("1")){   //我自己没抢 包被抢完了
//                                    user_open.setVisibility(View.GONE);
//                                    user_see.setVisibility(View.VISIBLE);
//                                   if(res.code == 201){
//                                       user_content.setText("您的余额不足以支付赔付,请及时充值!");
//                                    }else{
//                                       user_content.setText("手慢了,红包派完了");
//                                   }
//                                    bj_img.setImageResource(R.drawable.qiangmei);
//                                }else{
//                                    bj_img.setImageResource(R.drawable.qiangmei);
//                                    user_open.setVisibility(View.VISIBLE);
//                                    user_see.setVisibility(View.VISIBLE);
//                                }
//                                if (!content.getPhoto().contains("http")) {
//                                    Glide.with(context)
//                                            .load(Const.BASE + content.getPhoto())
//                                            .centerCrop()
//                                            .crossFade()
//                                            .placeholder(R.drawable.userimg)
//                                            .into(user_img);
//                                } else {
//                                    Glide.with(context)
//                                            .load(content.getPhoto())
//                                            .centerCrop()
//                                            .crossFade()
//                                            .placeholder(R.drawable.userimg)
//                                            .into(user_img);
//                                }
//                                user_see.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        dialog.dismiss();
//                                        if (message.getConversationType().name().equals("PRIVATE"))
//                                            context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("open", "0").ok()));
//                                        else
//                                            context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).putString("open", "0").ok()));
//
//                                    }
//                                });
//                                user_close.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        dialog.dismiss();
//                                    }
//                                });
//                                user_open.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        ObjectAnimator animator = ObjectAnimator.ofFloat(user_open,"rotationY",0,180,0);
//                                        animator.setDuration(1000);
//                                        animator.start();
//                                        new Handler().postDelayed(new Runnable(){
//                                            public void run() {
//                                                dialog.dismiss();
//                                                ViewHolder holder = (ViewHolder) view.getTag();
//                                                holder.content.setBackgroundColor(Color.parseColor("#bbF97431"));
//                                                if (message.getConversationType().name().equals("PRIVATE"))
//                                                    context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("open", "1").ok()));
//                                                else
//                                                    context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).putString("open", "1").ok()));
//
//                                            }
//                                        }, 500);
//
//                                    }
//                                });
//                                dialog = new Dialog(context).backgroundColor(Color.parseColor("#ffffff")).contentView(dialog_view);
//                                dialog.show();
//                            }else{
//                                if (message.getConversationType().name().equals("PRIVATE"))
//                                    context.startActivity(new Intent(context, GetHongbaoActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("open", "1").ok()));
//                                else
//                                    context.startActivity(new Intent(context, GetHongbaoListActivity.class).putExtra("data", new Bun().putString("hongbaoId", content.getHongbaoId()).putString("photo", content.getPhoto()).putString("content", content.getDesc()).putString("name", content.getName()).putString("amount", content.getAmount()).putString("num", content.getNum()).putString("state", content.getExtra()).putString("open", "1").ok()));
//                            }
//
//                        } else {
//                        }
//                    }
//                });
//
//        if (this.mCompositeSubscription == null) {
//            this.mCompositeSubscription = new CompositeSubscription();
//        }

     //   this.mCompositeSubscription.add(subscription);
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
    public void onItemLongClick(View view, int position, ZhuanZhangMessage content, UIMessage message) {

    }

}