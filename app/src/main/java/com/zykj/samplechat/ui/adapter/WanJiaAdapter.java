package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.WanJiaListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/17.
 */
public class WanJiaAdapter extends RecyclerView.Adapter<WanJiaAdapter.MyViewHolder> {



    //上下文
    private Context mContext;

    private List<WanJiaListBean> dataList = new ArrayList<>();

    public OnMyAdapterLinstener onMyAdapterLinstener;

    public static interface OnMyAdapterLinstener {
        //view，code，是否为默认地址，下标

    }

    private View mView = null;
    private int mPos = -1;
    private String biaoshi = "1";

    public void addAllData(List<WanJiaListBean> dataList) {
        if (dataList != null) {
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        if (dataList != null) {
            this.dataList.clear();
            notifyDataSetChanged();
        }
    }

    public WanJiaAdapter(String biaoshi, Context context, ArrayList<WanJiaListBean> list, OnMyAdapterLinstener onMyAdapterLinstener) {
        mContext = context;
        this.dataList = list;
        this.onMyAdapterLinstener = onMyAdapterLinstener;
        this.biaoshi = biaoshi;

    }

    public void setbiaoshi(String biaoshi) {
        this.biaoshi = biaoshi;
    }

    private OnItemClickListener clickListener;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public static interface OnItemClickListener {
        void onClick(View view,String name,String url, int code, View vbg, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.touxiang)
        ImageView touxiang;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.no3)
        TextView no3;
        private View itemView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                 clickListener.onClick(view,dataList.get(getAdapterPosition()).getNicName(),dataList.get(getAdapterPosition()).getPhotoPath(), dataList.get(getAdapterPosition()).getId(), itemView, getAdapterPosition());
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wanjia, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //空间内容
        // holder.ivImg.setVisibility(View.GONE);
        holder.name.setText(""+dataList.get(position).getNicName());
        holder.no3.setText(""+dataList.get(position).getRegTime());
        Glide.with(mContext)
                .load(Const.getbase(dataList.get(position).getPhotoPath()))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(holder.touxiang);
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

}
