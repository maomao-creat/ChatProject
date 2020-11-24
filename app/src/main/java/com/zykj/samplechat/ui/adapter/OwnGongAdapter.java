package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Gonggao;
import com.zykj.samplechat.presenter.OwnGongPresenter;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ninos on 2016/7/27.
 */
public class OwnGongAdapter extends BaseAdapter<OwnGongAdapter.VHolder, Gonggao, OwnGongPresenter> {

    public OwnGongAdapter(Context context, OwnGongPresenter ownGongPresenter) {
        super(context, ownGongPresenter);
        isAddFooter = false;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.activity_gonggao_item;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, int position) {

        Gonggao model = data.get(position);

        holder.gg_title.setText(model.Title.toString());

    }

    class VHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.gg_title)
        TextView gg_title;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
