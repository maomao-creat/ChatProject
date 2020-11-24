package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.FriendsPresenter;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;
import com.zykj.samplechat.utils.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * *****************************************************
 * *
 * *
 * _oo0oo_                      *
 * o8888888o                     *
 * 88" . "88                     *
 * (| -_- |)                     *
 * 0\  =  /0                     *
 * ___/`---'\___                   *
 * .' \\|     |# '.                  *
 * / \\|||  :  |||# \                 *
 * / _||||| -:- |||||- \               *
 * |   | \\\  -  #/ |   |               *
 * | \_|  ''\---/''  |_/ |              *
 * \  .-\__  '-'  ___/-. /              *
 * ___'. .'  /--.--\  `. .'___            *
 * ."" '<  `.___\_<|>_/___.' >' "".          *
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |        *
 * \  \ `_.   \_ __\ /__ _/   .-` /  /        *
 * =====`-.____`.___ \_____/___.-`___.-'=====     *
 * `=---='                      *
 * *
 * *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    *
 * *
 * 佛祖保佑         永无BUG              *
 * *
 * *
 * *****************************************************
 * <p>
 * Created by ninos on 2016/6/2.
 */
public class FriendsAdapter extends BaseAdapter<FriendsAdapter.VHolder, Friend, FriendsPresenter> {

    public FriendsAdapter(Context context, FriendsPresenter friendsPresenter, View header) {
        super(context, friendsPresenter, header);
        isAddFooter = false;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.friends_item;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, int position) {

        Friend model = data.get(position);
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            holder.tv_item_indexview_catalog.setVisibility(View.VISIBLE);
            holder.tv_item_indexview_catalog.setText(model.topc);
        } else {
            holder.tv_item_indexview_catalog.setVisibility(View.GONE);

        }
        holder.f_name.setText(StringUtil.isEmpty(model.RemarkName) ? model.NicName : model.NicName.toString());

        Glide.with(context)
                .load(Const.getbase(model.PhotoPath))
                .apply(new RequestOptions().error(R.drawable.userimg))
                .into(holder.f_img);

    }

    public int getSectionForPosition(int position) {
        return data.get(position).topc.charAt(0);
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount() - 1; i++) {
            String sortStr = data.get(i).topc;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    class VHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.tv_item_indexview_catalog)
        TextView tv_item_indexview_catalog;
        @Nullable
        @Bind(R.id.f_img)
        ImageView f_img;
        @Nullable
        @Bind(R.id.f_name)
        TextView f_name;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
