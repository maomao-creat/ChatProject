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
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.TeamPresenter;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ninos on 2016/7/14.
 */
public class TeamAdapter extends BaseAdapter<TeamAdapter.VHolder, Team, TeamPresenter> {

    public TeamAdapter(Context context, TeamPresenter teamPresenter) {
        super(context, teamPresenter);
        isAddFooter = false;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.activity_team_item;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, int position) {

        Team model = data.get(position);

        holder.team_name.setText(model.Name.equals("")?"未命名":model.Name);

        Glide.with(context)
                .load(Const.getbase(model.ImagePath))
                .apply(new RequestOptions().placeholder(R.drawable.groupcreate))
                .into(holder.team_img);

    }

    class VHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.team_name)
        TextView team_name;
        @Nullable
        @Bind(R.id.team_img)
        ImageView team_img;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
