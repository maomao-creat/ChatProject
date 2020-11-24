package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.MyHYLB;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ninos on 2016/7/14.
 */
public class BindUserAdapter extends BaseAdapter<BindUserAdapter.VHolder, MyHYLB, BasePresenterImp> {

    public BindUserAdapter(Context context) {
        super(context);
        isAddFooter = false;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.activity_bind_user_item;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, int position) {

        MyHYLB model = data.get(position);

        Glide.with(context)
                .load(Const.getbase(model.getHeadUrl()))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(holder.f_img);

    }

    class VHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.f_img)
        ImageView f_img;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
