package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.FriendsContactsPresenter;
import com.zykj.samplechat.ui.activity.FriendsContactsActivity;
import com.zykj.samplechat.ui.adapter.base.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ninos on 2016/7/8.
 */
public class FriendsContactsAdapter extends BaseAdapter<FriendsContactsAdapter.VHolder, Friend, FriendsContactsPresenter> {

    private FriendsContactsActivity friendsContactsActivity;

    public FriendsContactsAdapter(FriendsContactsActivity friendsContactsActivity,Context context, FriendsContactsPresenter friendsContactsPresenter, View header) {
        super(context, friendsContactsPresenter, header);
        this.friendsContactsActivity = friendsContactsActivity;
        isAddFooter = false;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.activity_friends_contacts_item;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, int position) {
        final Friend model = data.get(position);

        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            holder.tv_item_indexview_catalog.setVisibility(View.VISIBLE);
            holder.tv_item_indexview_catalog.setText(model.topc);
        } else {
            holder.tv_item_indexview_catalog.setVisibility(View.GONE);

        }

        holder.ai_username.setText(model.RealName);


        if (model.isShare){
            holder.ai_share.setVisibility(View.VISIBLE);
            holder.ai_agree.setVisibility(View.INVISIBLE);
            holder.ai_unagree.setVisibility(View.INVISIBLE);
            holder.ai_usernumber.setText("电话:"+model.Phone);
        }else {
            holder.ai_share.setVisibility(View.INVISIBLE);
            holder.ai_agree.setVisibility(model.IsFriend == 1 ? View.VISIBLE : View.INVISIBLE);
            holder.ai_unagree.setVisibility(model.IsFriend == 0 ? View.VISIBLE : View.INVISIBLE);
            holder.ai_usernumber.setText("IPIC:"+model.Phone);
        }

        holder.ai_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:" + model.Phone);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                sendIntent.putExtra("sms_body", "我现在在使用朋友，快来跟我一起体验吧，这是下载地址：http://www.pgyer.com");
                context.startActivity(sendIntent);
            }
        });


//        holder.ai_unagree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Map map = new HashMap();
//                map.put("key", Const.KEY);
//                map.put("uid",Const.UID);
//                map.put("function","SaveFriend");
//                map.put("userid",new UserUtil(context).getUserId());
//                map.put("friendid",model.Id);
//                String json = StringUtil.toJson(map);
//                friendsContactsActivity.showDialog();
//                try {
//                    String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//                    _presenter.SaveFriend(data,model.Id);
//                }catch (Exception e){
//                }
//            }
//        });


        Glide.with(context)
                .load(Const.getbase(model.PhotoPath))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(holder.ai_userimg);
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
        @Bind(R.id.ai_username)
        TextView ai_username;
        @Nullable
        @Bind(R.id.ai_userimg)
        ImageView ai_userimg;
        @Nullable
        @Bind(R.id.ai_usernumber)
        TextView ai_usernumber;
        @Nullable
        @Bind(R.id.ai_share)
        TextView ai_share;
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