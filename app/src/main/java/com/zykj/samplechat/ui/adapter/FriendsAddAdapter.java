package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rey.material.app.Dialog;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.FriendMap;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.FriendsAddPresenter;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ninos on 2016/7/7.
 */
public class FriendsAddAdapter extends BaseAdapter<FriendsAddAdapter.VHolder, FriendMap, FriendsAddPresenter> {

    private Dialog dialog;
    private View view;
    private int fid;
    public FriendsAddAdapter(final Context context, FriendsAddPresenter friendsAddPresenter, View header) {
        super(context, friendsAddPresenter, header);
        isAddFooter = false;
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null);
        LinearLayout dd_delete = (LinearLayout) this.view.findViewById(R.id.dd_delete);
        dd_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _presenter.DeleteFriend(fid);
            }
        });
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
        final FriendMap model = data.get(position);
        holder.ai_username.setText(model.Value.NicName);
        holder.ai_usernumber.setText(model.JoinTime);
        holder.ai_usernumber.setVisibility(model.State==0?View.GONE:View.VISIBLE);
        holder.ai_unagree.setVisibility(model.State==0?View.VISIBLE:View.GONE);
        holder.ai_agree.setVisibility(model.State==0?View.GONE:View.VISIBLE);

        holder.ai_unagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map = new HashMap();
                map.put("key", Const.KEY);
                map.put("uid",Const.UID);
                map.put("function","AgreeFriendAndSaveFriend");
                map.put("applyid",model.Value.Id);
                String json = StringUtil.toJson(map);
                try {
                    String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                    _presenter.AgreeFriendAndSaveFriend(data,position,model.Key);
                }catch (Exception e){
                }
            }
        });
        Glide.with(context)
                .load(Const.getbase(model.Value.PhotoPath))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(holder.ai_userimg);
        holder.ai_user.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(model.State==0){
                    fid = model.Key;
                    if (dialog == null)
                        dialog = new Dialog(context).backgroundColor(Color.parseColor("#ffffff")).contentView(view);
                    dialog.show();
                }
                return true;
            }
        });
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