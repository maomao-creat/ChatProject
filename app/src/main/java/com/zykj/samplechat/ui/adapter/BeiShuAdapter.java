package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.LeiBean;
import com.zykj.samplechat.presenter.SendMoneyPresenter;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BeiShuAdapter extends BaseAdapter<BeiShuAdapter.VHolder, LeiBean, SendMoneyPresenter> {




    public BeiShuAdapter(Context context) {
        super(context);
        setFooter(false);
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.ui_item_beishu;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(final VHolder holder, final int position) {
       LeiBean  model=data.get(position);
       holder.tvThree.setText(model.RightLowerText);



       holder.tvThree.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mOnItemClickListener.onItemClick(view,position,holder.tvThree);
           }
       });

    }

    class VHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.tv_three)
        TextView tvThree;
        public VHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
