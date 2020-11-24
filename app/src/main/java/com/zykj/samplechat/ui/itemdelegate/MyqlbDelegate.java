package com.zykj.samplechat.ui.itemdelegate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rey.material.widget.Button;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.FriendMap;
import com.zykj.samplechat.model.mqunlb;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.ui.activity.ChatActivity;
import com.zykj.samplechat.ui.view.lbhd;
import com.zykj.samplechat.utils.ToolsUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.davidsu.library.BaseViewHolder;
import cn.davidsu.library.ItemViewDelegate;


/**
 * Created by cxzheng on 2018/3/29.
 * process type1 item
 */

public class MyqlbDelegate implements ItemViewDelegate<mqunlb, MyqlbDelegate.Type1ViewHolder> {


    lbhd callback;
    public static final String CHAT_INFO = "chatInfo";
    public MyqlbDelegate(Context content, lbhd callback) {
        this.content = content;
        this.callback = callback;
    }

    Context content;

    @NonNull
    @Override
    public Type1ViewHolder createViewHolder(ViewGroup parent) {
        return new Type1ViewHolder(parent, R.layout.view_item_qun);
    }

    @Override
    public void bindViewHolder(Type1ViewHolder holder, mqunlb entity) {
        holder.bindData(entity);
    }

    class Type1ViewHolder extends BaseViewHolder {
        //
        @Bind(R.id.toux)
        ImageView toux;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.tjr)
        TextView tjr;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.l_login)
        Button lLogin;
        @Bind(R.id.zong)
        LinearLayout zong;

        public Type1ViewHolder(@NonNull ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void bindView(@NonNull View itemView) {
            ButterKnife.bind(this, itemView);
            // tv = itemView.findViewById(R.id.tv_type_1);
        }

        public void bindData(final mqunlb entity) {//ZhiFuActivity
//            PicassoUtil.loadPicassoARGB_8888(content, NR.url + entity.getImagePath(), toux, false);//加载网络图片
//            Glide.with(content)
//                    .load(Const.getbase(entity.getImagePath()))
//                    .centerCrop()
//                    .crossFade()
//                    .placeholder(R.drawable.userimg)
//                    .into(toux);
            Glide.with(content).
                    load(Const.getbase(entity.getImagePath())).
                    apply(new RequestOptions().placeholder(R.drawable.userimg)).
                    into(toux);
            name.setText("" + entity.getName());
            time.setText("" + entity.getTeamIntroduce());
            zong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IsInTeam(entity.getId(),entity.getName(),entity.TeamCode);
                }
            });
            lLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IsInTeam(entity.getId(),entity.getName(),entity.TeamCode);
                }
            });

        }

        public void IsInTeam(final String id, final String name, final String GroupId) {//是否在群里
            HttpUtils.IsInTeam(new SubscriberRes<FriendMap>() {
                @Override
                public void onSuccess(FriendMap res) {
                    if (res.State == 1) {
                        ChatInfo chatInfo = new ChatInfo();
                        chatInfo.setType( TIMConversationType.Group);
                        chatInfo.setId(GroupId);
                        chatInfo.setChatName(name);
                        Intent intent = new Intent(content, ChatActivity.class);
                        intent.putExtra(CHAT_INFO, chatInfo);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        content.startActivity(intent);
                    } else {
                        ToolsUtils.toast(content, "你还未加入该群，请联系管理员进群");
                    }
                }
                @Override
                public void completeDialog() {
                }
            },id);
        }
    }
}
