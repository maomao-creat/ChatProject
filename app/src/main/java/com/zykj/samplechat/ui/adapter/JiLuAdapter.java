package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.JiLuBean;
import com.zykj.samplechat.presenter.JiLuPresenter;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 徐学坤 on 2018/2/1.
 */
public class JiLuAdapter extends BaseAdapter<JiLuAdapter.VHolder, JiLuBean, JiLuPresenter> {
    public JiLuAdapter(Context context) {
        super(context);
        isAddFooter = false;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.user_add_item;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, final int position) {
        final JiLuBean model = data.get(position);

    }

    class VHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.ai_username)
        TextView ai_username;
        @Nullable
        @Bind(R.id.ai_userimg)
        ImageView ai_userimg;
        @Nullable
        @Bind(R.id.ai_usernumber)
        TextView ai_usernumber;
        @Nullable
        @Bind(R.id.ai_unagree)
        TextView ai_unagree;
        @Nullable
        @Bind(R.id.ai_agree)
        TextView ai_agree;
        @Nullable
        @Bind(R.id.ai_user)
        RelativeLayout ai_user;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
