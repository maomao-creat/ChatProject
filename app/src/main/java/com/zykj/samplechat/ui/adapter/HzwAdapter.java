package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.HzwPresenter;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;
import com.zykj.samplechat.ui.view.ResizableImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2017/4/1 on 11:34
 * 描述:
 * 作者:张智超 Administrator
 */

public class HzwAdapter extends BaseAdapter<HzwAdapter.VHolder, Team, HzwPresenter> {

    private NoticeMessage message;
    public HzwAdapter(Context context, HzwPresenter hzwPresenter, NoticeMessage message) {
        super(context, hzwPresenter);
        this.message = message;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.activity_hzw_item;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(final VHolder holder, int position) {
        Log.d("xxxxxxxxx", "bindData: "+position);
        final Team model = data.get(position);
        Glide.with(context).load(Const.getbase(model.ImagePath))
                .apply(new RequestOptions().placeholder(R.drawable.group_top))
                .into(holder.team_img);
        holder.team_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _presenter.IsInTeam(model.Id+"", model.Name);
            }
        });
        holder.team_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.sendMsg(model.TeamIntroduce);
            }
        });
    }

    public interface NoticeMessage{
        void sendMsg(String msg);
    }

    class VHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.team_img)
        ResizableImageView team_img;
        @Nullable
        @Bind(R.id.team_intro)
        TextView team_intro;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
