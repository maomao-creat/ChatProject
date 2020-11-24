package com.zykj.samplechat.ui.itemdelegate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.ZhunZhangBean;
import com.zykj.samplechat.ui.activity.GetHongbaoListActivity;
import com.zykj.samplechat.ui.view.lbhd;
import com.zykj.samplechat.utils.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.davidsu.library.BaseViewHolder;
import cn.davidsu.library.ItemViewDelegate;


/**
 * Created by cxzheng on 2018/3/29.
 * process type1 item
 */

public class QiTaDelegate implements ItemViewDelegate<ZhunZhangBean.DataBean, QiTaDelegate.Type1ViewHolder> {


    lbhd callback;



    public QiTaDelegate(Context content, lbhd callback) {
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
        @Bind(R.id.ll_shouyi)
        LinearLayout ll_shouyi;
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

            bt1.setText(""+entity.getFiName());
            // sj.setText("审核中"+entity.getStatus());
            sj.setVisibility(View.INVISIBLE);
            bt.setText(entity.getTime());
            if(entity.isIsCompanyIncome()){
                qs.setText("+" + entity.getAmount());
            }else{
                qs.setText("-" + entity.getAmount());
            }
            ll_shouyi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!StringUtil.isEmpty(entity.getPackId())){
                        content.startActivity(new Intent(content, GetHongbaoListActivity.class)
                                .putExtra("hongbaoId",entity.getPackId())
                                .putExtra("from","boss"));
                    }
                }
            });

        }

    }
}
