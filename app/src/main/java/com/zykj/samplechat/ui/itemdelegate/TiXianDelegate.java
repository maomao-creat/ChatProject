package com.zykj.samplechat.ui.itemdelegate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.TiXianBean;
import com.zykj.samplechat.ui.view.lbhd;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.davidsu.library.BaseViewHolder;
import cn.davidsu.library.ItemViewDelegate;


/**
 * Created by cxzheng on 2018/3/29.
 * process type1 item
 */

public class TiXianDelegate implements ItemViewDelegate<TiXianBean.DataBean, TiXianDelegate.Type1ViewHolder> {


    lbhd callback;



    public TiXianDelegate(Context content, lbhd callback) {
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
    public void bindViewHolder(Type1ViewHolder holder, TiXianBean.DataBean entity) {
        holder.bindData(entity);
    }

    class Type1ViewHolder extends BaseViewHolder {
        //
//        @BindView(R.id.zong)
//        LinearLayout zong;
        @Bind(R.id.sj)
        TextView sj;
        @Bind(R.id.bt)
        TextView bt;
        @Bind(R.id.qs)
        TextView qs;
        @Bind(R.id.bt1)
        TextView bt1;
        public Type1ViewHolder(@NonNull ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void bindView(@NonNull View itemView) {
            ButterKnife.bind(this, itemView);
            // tv = itemView.findViewById(R.id.tv_type_1);
        }

        public void bindData(final TiXianBean.DataBean entity) {//ZhiFuActivity

            bt1.setText("提现");
           sj.setText(entity.getStatus()==1?"待审核":"已完成");
           // sj.setText("审核中"+entity.getStatus());
            bt.setText(entity.getCreateDate());
            qs.setText("-" + entity.getAmount());

        }

    }
}
