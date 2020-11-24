package com.zykj.samplechat.ui.itemdelegate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.myxuqBean;
import com.zykj.samplechat.ui.view.lbhd;

import butterknife.ButterKnife;
import cn.davidsu.library.BaseViewHolder;
import cn.davidsu.library.ItemViewDelegate;


/**
 * Created by cxzheng on 2018/3/29.
 * process type1 item
 */

public class XuQiuDelegate implements ItemViewDelegate<myxuqBean.ListBean, XuQiuDelegate.Type1ViewHolder> {


    lbhd callback;


    public XuQiuDelegate(Context content, lbhd callback) {
        this.content = content;
        this.callback = callback;

    }

    Context content;

    @NonNull
    @Override
    public Type1ViewHolder createViewHolder(ViewGroup parent) {
        return new Type1ViewHolder(parent, R.layout.activity_erweima_layout);
    }

    @Override
    public void bindViewHolder(Type1ViewHolder holder, myxuqBean.ListBean entity) {
        holder.bindData(entity);
    }

    class Type1ViewHolder extends BaseViewHolder {
//
//        @BindView(R.id.zong)
//        LinearLayout zong;
        public Type1ViewHolder(@NonNull ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void bindView(@NonNull View itemView) {
            ButterKnife.bind(this, itemView);
            // tv = itemView.findViewById(R.id.tv_type_1);
        }

        public void bindData(final myxuqBean.ListBean entity) {//ZhiFuActivity


        }

    }
}
