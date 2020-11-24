package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.HotSellerListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/17.
 */
public class JingPinAdapter extends RecyclerView.Adapter<JingPinAdapter.MyViewHolder> {



    //上下文
    private Context mContext;

    private List<HotSellerListBean> dataList = new ArrayList<>();

    public OnMyAdapterLinstener onMyAdapterLinstener;

    public static interface OnMyAdapterLinstener {
        //view，code，是否为默认地址，下标

    }

    private View mView = null;
    private int mPos = -1;
    private String biaoshi = "1";

    public void addAllData(List<HotSellerListBean> dataList) {
        if (dataList != null) {
            if(this.dataList.size()>0){
                this.dataList.get(0).getShuju().addAll(dataList.get(0).getShuju());
                notifyDataSetChanged();
            }else{
                this.dataList.addAll(dataList);
                notifyDataSetChanged();
            }

        }
    }

    public void clearData() {
        if (dataList != null) {
            this.dataList.clear();
            notifyDataSetChanged();
        }
    }

    public JingPinAdapter(String biaoshi,Context context, ArrayList<HotSellerListBean> list, OnMyAdapterLinstener onMyAdapterLinstener) {
        mContext = context;
        this.dataList = list;
        this.onMyAdapterLinstener = onMyAdapterLinstener;
        this.biaoshi=biaoshi;

    }
public void setbiaoshi(String biaoshi){
    this.biaoshi=biaoshi;
}
    private OnItemClickListener clickListener;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public static interface OnItemClickListener {
        void onClick(View view, String code, View vbg, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.no1)
        TextView no1;
        @Bind(R.id.no2)
        TextView no2;
        @Bind(R.id.no3)
        TextView no3;
        @Bind(R.id.no4)
        TextView no4;
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
                clickListener.onClick(view, dataList.get(0).getShuju().get(getAdapterPosition()).getId()+"", itemView, getAdapterPosition());
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_jp, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //空间内容
        // holder.ivImg.setVisibility(View.GONE);
        Double money =0.00;
        if(dataList.get(0)!=null&&biaoshi.equals("1")){//1发包 2中奖 3 中雷 3充值 5提现 6收包 7赔付
            holder.no1.setText(dataList.get(0).getShuju().get(position).getTeamName()+"");
            holder.no2.setText(dataList.get(0).getShuju().get(position).getPayTime()+"");
            holder.no4.setText("");
            money = -Double.parseDouble(dataList.get(0).getShuju().get(position).getTotalmoney());
        }else if(dataList.get(0)!=null&&biaoshi.equals("2")){//1发包 2中奖 3 中雷 3充值 5提现 6收包 7赔付
            holder.no1.setText(dataList.get(0).getShuju().get(position).getNicName()+"");
            holder.no2.setText(dataList.get(0).getShuju().get(position).getPayTime()+"");
            holder.no4.setText("");
            money = Double.parseDouble(dataList.get(0).getShuju().get(position).getZhongjiangMoney());
        }else if(dataList.get(0)!=null&&biaoshi.equals("3")){
            holder.no1.setText(dataList.get(0).getShuju().get(position).getNicName()+"");
            holder.no2.setText(dataList.get(0).getShuju().get(position).getPayTime()+"");
            holder.no4.setText("");
            money = -Double.parseDouble(dataList.get(0).getShuju().get(position).getPeifuMoney());
        }else if(dataList.get(0)!=null&&biaoshi.equals("4")){
            holder.no1.setText("账户充值");
            holder.no2.setText(dataList.get(0).getShuju().get(position).getTimeStamp()+"");
            holder.no4.setText("");
            money = Double.parseDouble(dataList.get(0).getShuju().get(position).getValue());
        }else if(dataList.get(0)!=null&&biaoshi.equals("5")){
            holder.no1.setText("账户提现");
            holder.no2.setText(dataList.get(0).getShuju().get(position).getTimeStamp()+"");
            holder.no4.setText("");
            money = -Double.parseDouble(dataList.get(0).getShuju().get(position).getValue());
        }else if(dataList.get(0)!=null&&biaoshi.equals("6")){
            holder.no1.setText(dataList.get(0).getShuju().get(position).getTeamName()+"");
            holder.no2.setText(dataList.get(0).getShuju().get(position).getPayTime()+"");
            holder.no4.setText("");
            money = Double.parseDouble(dataList.get(0).getShuju().get(position).getAmount());
        }else if(dataList.get(0)!=null&&biaoshi.equals("7")){
            holder.no1.setText(dataList.get(0).getShuju().get(position).getNicName()+"");
            holder.no2.setText(dataList.get(0).getShuju().get(position).getPayTime()+"");
            holder.no4.setText("");
            money = Double.parseDouble(dataList.get(0).getShuju().get(position).getPeifuMoney());
        }else if(dataList.get(0)!=null&&biaoshi.equals("8")){
            holder.no1.setText(dataList.get(0).getShuju().get(position).getReceivedNicName()+"");
            holder.no2.setText(dataList.get(0).getShuju().get(position).getPayTime()+"");
            holder.no4.setText("");
            money = -Double.parseDouble(dataList.get(0).getShuju().get(position).getValue());
        }else if(dataList.get(0)!=null&&biaoshi.equals("9")){
            holder.no1.setText(dataList.get(0).getShuju().get(position).getReceivedNicName()+"");
            holder.no2.setText(dataList.get(0).getShuju().get(position).getPayTime()+"");
            holder.no4.setText("");
            money = Double.parseDouble(dataList.get(0).getShuju().get(position).getValue());
        }

        //钱统一设置
        if(money>0){
            holder.no3.setText("+"+money);
            holder.no3.setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.no3.setText(""+money);
            holder.no3.setTextColor(Color.parseColor("#18ad13"));
        }
    }

    @Override
    public int getItemCount() {
        if(dataList.size()<1){
            return 0;
        }else{
            return getintem();
        }
       // return dataList != null ?getintem():0 ;
    }
    public int getintem(){
//        int intem=0;
//        switch (biaoshi){
//            case "1":
//                intem=dataList.get(0)!=null?dataList.get(0).getShuju().size():0;
//                break;
//            case "2":
//                break;
//            case "3":
//                break;
//            case "4":
//                break;
//            case "5":
//                break;
//            default:
//                intem=0;
//                break;
//
//        }
        return dataList.get(0)!=null?dataList.get(0).getShuju().size():0;
    }
}
