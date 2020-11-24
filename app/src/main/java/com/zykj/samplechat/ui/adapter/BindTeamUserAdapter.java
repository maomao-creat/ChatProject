package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.activity.FriendsInfoActivity;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ninos on 2016/7/14.
 */
public class BindTeamUserAdapter extends BaseAdapter<BindTeamUserAdapter.VHolder, Friend, BasePresenterImp> {

    public BindTeamUserAdapter(Context context) {
        super(context);
        isAddFooter = false;
    }

    @Override
    public int getItemCount() {
        if(data.size()<51){
            return data.size();
        }else{
            return 50;
        }
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.activity_team_info_item;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, int position ) {
        final Friend model = data.get(position);

        holder.gi_usercontent.setText(model.RemarkName.equals("")?model.NicName:model.RemarkName);

        Glide.with(context)
                .load(Const.getbase(model.PhotoPath))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(holder.gi_userimg);

        holder.gi_userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(FriendsInfoActivity.class, new Bun().putString("id", id).ok());
                context.startActivity(new Intent(context,FriendsInfoActivity.class).putExtra("id", model.UserCode).
                        putExtra("type","0"));

            }
        });

    }

    class VHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.gi_userimg)
        ImageView gi_userimg;
        @Nullable
        @Bind(R.id.gi_usercontent)
        TextView gi_usercontent;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
