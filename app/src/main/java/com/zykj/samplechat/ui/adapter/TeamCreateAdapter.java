package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.TeamCreatePresenter;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;
import com.zykj.samplechat.utils.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by ninos on 2016/7/14.
 */
public class TeamCreateAdapter extends BaseAdapter<TeamCreateAdapter.VHolder, Friend, TeamCreatePresenter> {

    private boolean flag = false;
    public TeamCreateAdapter(Context context, TeamCreatePresenter teamCreatePresenter, boolean flag) {
        super(context, teamCreatePresenter);
        this.flag = flag;
        isAddFooter = false;
    }

    public TeamCreateAdapter(Context context, TeamCreatePresenter teamCreatePresenter, View header) {
        super(context, teamCreatePresenter, header);
        isAddFooter = false;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.activity_team_create_item;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, int position) {
        Friend model = data.get(position);
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            holder.tv_item_indexview_catalog.setVisibility(View.VISIBLE);
            holder.tv_item_indexview_catalog.setText(model.topc);
        } else {
            holder.tv_item_indexview_catalog.setVisibility(View.GONE);
        }
        holder.f_name.setText(StringUtil.isEmpty(model.RemarkName)?model.NicName:model.RemarkName.toString());
        if(flag){
            holder.tci_select.setVisibility(View.GONE);
        }else{
            if(model.isSelected)
                holder.tci_select.setImageResource(R.drawable.ssdk_oks_auth_follow_cb_chd);
            else
                holder.tci_select.setImageResource(R.drawable.ssdk_oks_auth_follow_cb_unc);
        }
        Glide.with(context)
                .load(Const.getbase( model.PhotoPath))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(holder.f_img);
    }

    public int getSectionForPosition(int position) {
        return data.get(position).topc.charAt(0);
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount() - 1; i++) {
            String sortStr = data.get(i).topc;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    class VHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.tv_item_indexview_catalog)
        TextView tv_item_indexview_catalog;
        @Nullable
        @Bind(R.id.f_img)
        ImageView f_img;
        @Nullable
        @Bind(R.id.tci_select)
        ImageView tci_select;
        @Nullable
        @Bind(R.id.f_name)
        TextView f_name;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
