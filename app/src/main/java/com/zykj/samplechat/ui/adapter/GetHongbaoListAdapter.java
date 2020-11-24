package com.zykj.samplechat.ui.adapter;

import android.content.Context;
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
import com.zykj.samplechat.model.HongbaoList;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;
import com.zykj.samplechat.utils.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ninos on 2017/8/23.
 */

public class GetHongbaoListAdapter extends BaseAdapter<GetHongbaoListAdapter.VHolder, HongbaoList.HongbaoUser, BasePresenterImp> {

    public GetHongbaoListAdapter(Context context, View header) {
        super(context, header);
        isAddFooter = false;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.activity_get_hongbao_list_item;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, int position) {
        HongbaoList.HongbaoUser hongbaoUser = data.get(position);
        if(StringUtil.isEmail(hongbaoUser.HeadUrl)){
            hongbaoUser.HeadUrl="";
        }
        Glide.with(context)
                .load(Const.getbase(hongbaoUser.PhotoPath))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(holder.aiUserimg);
        holder.aiUsername.setText(hongbaoUser.NicName);
        holder.aiLength.setText(hongbaoUser.Amount + "元");
        holder.aiUsernumber.setText(hongbaoUser.OpenTime);
        if (hongbaoUser.Flag.equals("1")){
            holder.ai_moreimg.setVisibility(View.VISIBLE);
            holder.ai_more.setVisibility(View.VISIBLE);
        }else{
            holder.ai_moreimg.setVisibility(View.GONE);
            holder.ai_more.setVisibility(View.GONE);
        }
    }

    class VHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.ai_userimg)
        ImageView aiUserimg;
        @Nullable
        @Bind(R.id.ai_username)
        TextView aiUsername;
        @Nullable
        @Bind(R.id.gender)
        ImageView gender;
        @Nullable
        @Bind(R.id.ai_usernumber)
        TextView aiUsernumber;
        @Nullable
        @Bind(R.id.ai_moreimg)
        ImageView ai_moreimg;
        @Nullable
        @Bind(R.id.ai_more)
        TextView ai_more;
        @Nullable
        @Bind(R.id.ai_length)
        TextView aiLength;
        @Nullable
        @Bind(R.id.ai_user)
        RelativeLayout aiUser;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}