package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Group;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.GroupFriendsPresenter;
import com.zykj.samplechat.ui.activity.GroupInfoActivity;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.TimeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ninos on 2016/7/13.
 */
public class GroupFriendsAdapter extends BaseAdapter<GroupFriendsAdapter.VHolder, Group, GroupFriendsPresenter> {

    public GroupFriendsAdapter(Context context, GroupFriendsPresenter groupFriendsPresenter, View header) {
        super(context, groupFriendsPresenter, header);
        isAddFooter = false;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.fragment_friends_group_item;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(final VHolder holder, final int position) {
        final Group group = data.get(position);

        String date = TimeUtil.timeFormat(group.PublishTime);

        String days = date.substring(date.indexOf("月")+1);
        String months = date.substring(0,date.indexOf("月")+1);

        holder.days.setText(days);
        holder.months.setText(months);

        if(!group.VideoPath.trim().equals("")){
            holder.gi_usercontent.setVisibility(View.GONE);
            holder.gi_content_img.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(Const.getbase(group.ImagePath))
                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
                    .into(holder.gi_photo);
            holder.gi_content.setText(group.Content);
            holder.gi_count.setText(context.getString(R.string.photo_num,group.PicList.size()));
        }else if (group.PicList.size()==0){
            holder.gi_usercontent.setVisibility(View.VISIBLE);
            holder.gi_content_img.setVisibility(View.GONE);
            holder.gi_usercontent.setText(group.Content);
        }else{
            holder.gi_usercontent.setVisibility(View.GONE);
            holder.gi_content_img.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(Const.getbase( group.PicList.get(0).ImagePath))
                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
                    .into(holder.gi_photo);
            holder.gi_content.setText(group.Content);
            holder.gi_count.setText(context.getString(R.string.photo_num,group.PicList.size()));
        }

        holder.gi_usercontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, GroupInfoActivity.class).putExtra("data", new Bun().putSerializable("group",group).ok()));
            }
        });

        holder.gi_content_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, GroupInfoActivity.class).putExtra("data", new Bun().putSerializable("group",group).ok()));
            }
        });

    }

    class VHolder extends RecyclerView.ViewHolder {

        @Nullable @Bind(R.id.gi_usercontent)TextView gi_usercontent;
        @Nullable @Bind(R.id.gi_content_img)RelativeLayout gi_content_img;
        @Nullable @Bind(R.id.gi_photo)ImageView gi_photo;
        @Nullable @Bind(R.id.gi_content)TextView gi_content;
        @Nullable @Bind(R.id.gi_count)TextView gi_count;
        @Nullable @Bind(R.id.days)TextView days;
        @Nullable @Bind(R.id.months)TextView months;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
