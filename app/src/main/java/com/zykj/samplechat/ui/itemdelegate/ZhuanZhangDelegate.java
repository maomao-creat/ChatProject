package com.zykj.samplechat.ui.itemdelegate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.ZhunZhangBean;
import com.zykj.samplechat.ui.view.lbhd;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.davidsu.library.BaseViewHolder;
import cn.davidsu.library.ItemViewDelegate;


/**
 * Created by cxzheng on 2018/3/29.
 * process type1 item
 */

public class ZhuanZhangDelegate implements ItemViewDelegate<ZhunZhangBean.DataBean, ZhuanZhangDelegate.Type1ViewHolder> {


    lbhd callback;



    public ZhuanZhangDelegate(Context content, lbhd callback) {
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
    public void bindViewHolder(Type1ViewHolder holder, ZhunZhangBean.DataBean entity) {
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

        public void bindData(final ZhunZhangBean.DataBean entity) {//ZhiFuActivity


//            sj.setText(entity.getStatus()==1?);
            if(entity.isIsCompanyIncome()){//收入or支出 （true收入 false 支出）
                bt1.setText("转账收款");
                qs.setText("+" + entity.getAmount());
            }else{
                bt1.setText("转账付款");
                qs.setText("-" + entity.getAmount());
            }
            sj.setText(entity.getDescription());
            bt.setText(entity.getCreateDate());


        }

    }
}
