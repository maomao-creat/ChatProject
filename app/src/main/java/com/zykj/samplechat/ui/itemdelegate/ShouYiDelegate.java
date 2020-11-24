package com.zykj.samplechat.ui.itemdelegate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.mYshouyi;
import com.zykj.samplechat.ui.activity.GetHongbaoListActivity;
import com.zykj.samplechat.ui.view.lbhd;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.davidsu.library.BaseViewHolder;
import cn.davidsu.library.ItemViewDelegate;


/**
 * Created by cxzheng on 2018/3/29.
 * process type1 item
 */

public class ShouYiDelegate implements ItemViewDelegate<mYshouyi.DataBean, ShouYiDelegate.Type1ViewHolder> {


    lbhd callback;



    public ShouYiDelegate(Context content, lbhd callback) {
        this.content = content;
        this.callback = callback;

    }

    Context content;

    @NonNull
    @Override
    public Type1ViewHolder createViewHolder(ViewGroup parent) {
        return new Type1ViewHolder(parent, R.layout.item_home_yq);
    }

    @Override
    public void bindViewHolder(Type1ViewHolder holder, mYshouyi.DataBean entity) {
        holder.bindData(entity);
    }

    class Type1ViewHolder extends BaseViewHolder {
        //
        @Bind(R.id.ll_shouyi)
        LinearLayout ll_shouyi;
        @Bind(R.id.sj)
        TextView sj;
        @Bind(R.id.bt)
        TextView bt;
        @Bind(R.id.qs)
        TextView qs;
        public Type1ViewHolder(@NonNull ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void bindView(@NonNull View itemView) {
            ButterKnife.bind(this, itemView);
            // tv = itemView.findViewById(R.id.tv_type_1);
        }

        public void bindData(final mYshouyi.DataBean entity) {//ZhiFuActivity
            sj.setText(entity.getCreateDate());
            bt.setText(entity.getDescription());
            qs.setText("+"+entity.getAmount());
            ll_shouyi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    content.startActivity(new Intent(content, GetHongbaoListActivity.class)
                    .putExtra("hongbaoId",entity.getPackId())
                    .putExtra("from","boss"));
                }
            });
        }

    }
}
