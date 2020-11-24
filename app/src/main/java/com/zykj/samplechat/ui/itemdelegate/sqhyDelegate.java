package com.zykj.samplechat.ui.itemdelegate;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rey.material.widget.Button;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.sheqhyy;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.ui.view.lbhd;
import com.zykj.samplechat.utils.ToolsUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.davidsu.library.BaseViewHolder;
import cn.davidsu.library.ItemViewDelegate;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by cxzheng on 2018/3/29.
 * process type1 item
 */

public class sqhyDelegate implements ItemViewDelegate<sheqhyy.DataBean, sqhyDelegate.Type1ViewHolder> {


    lbhd callback;
    Activity act;

    public sqhyDelegate(Activity act, Context content, lbhd callback) {
        this.content = content;
        this.callback = callback;
        this.act = act;

    }

    Context content;

    @NonNull
    @Override
    public Type1ViewHolder createViewHolder(ViewGroup parent) {
        return new Type1ViewHolder(parent, R.layout.view_item_xyy);
    }

    @Override
    public void bindViewHolder(Type1ViewHolder holder, sheqhyy.DataBean entity) {
        holder.bindData(entity);
    }

    class Type1ViewHolder extends BaseViewHolder {
        //
//        @BindView(R.id.zong)
//        LinearLayout zong;
        @Bind(R.id.toux)
        CircleImageView toux;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.jj)
        Button jj;
        @Bind(R.id.ty)
        Button ty;
        @Bind(R.id.czlb)
        LinearLayout czlb;
        @Bind(R.id.zt)
        TextView zt;
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

        public void bindData(final sheqhyy.DataBean entity) {//ZhiFuActivity
//            PicassoUtil.loadPicassoARGB_8888(content, NR.url + entity.getFHeadUrl(), toux, false);//加载网络图片
            Glide.with(content)
                    .load(Const.getbase(entity.getFHeadUrl()))
                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
                    .into(toux);
            name.setText("" + entity.getFNickName());
            zt.setVisibility(View.GONE);
            ty.setVisibility(View.GONE);
            jj.setVisibility(View.GONE);
            zt.setText("" + entity.getStatusName());
            if (entity.getStatusName().equals("申请中")) {
                ty.setVisibility(View.VISIBLE);
                jj.setVisibility(View.VISIBLE);
            } else {
                zt.setVisibility(View.VISIBLE);
            }
            ty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    post(1, entity);
                }
            });
            jj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    post(2, entity);
                }
            });
            zong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent i = new Intent(content,FriendsInfoActivity.class);
//                    content.startActivity(i, new Bun().putString("id", entity.getFriendId()).ok());
                }
            });
        }

        void post(int bs, final sheqhyy.DataBean entity) {//操作类型1同意2拒绝
            HttpUtils.DealApplyFriend(new SubscriberRes<String>() {
                @Override
                public void onSuccess(String userBean) {
                    ToolsUtils.toast(content,"操作成功!");
                    callback.zx("", 0);
                }

                @Override
                public void completeDialog() {
                }
            }, entity.getFriendId(),bs);
        }
    }
}
