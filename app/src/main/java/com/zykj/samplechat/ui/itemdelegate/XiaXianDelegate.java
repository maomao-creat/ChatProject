package com.zykj.samplechat.ui.itemdelegate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.MyxiaxianxinxBean;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.ui.view.lbhd;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.davidsu.library.BaseViewHolder;
import cn.davidsu.library.ItemViewDelegate;


/**
 * Created by cxzheng on 2018/3/29.
 * process type1 item
 */

public class XiaXianDelegate implements ItemViewDelegate<MyxiaxianxinxBean.DataBean, XiaXianDelegate.Type1ViewHolder> {


    lbhd callback;



    public XiaXianDelegate(Context content, lbhd callback) {
        this.content = content;
        this.callback = callback;

    }

    Context content;

    @NonNull
    @Override
    public Type1ViewHolder createViewHolder(ViewGroup parent) {
        return new Type1ViewHolder(parent, R.layout.view_item_yjrjl);
    }

    @Override
    public void bindViewHolder(Type1ViewHolder holder, MyxiaxianxinxBean.DataBean entity) {
        holder.bindData(entity);
    }

    class Type1ViewHolder extends BaseViewHolder {
        //
//        @BindView(R.id.zong)
//        LinearLayout zong;
        @Bind(R.id.toux)
        ImageView toux;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.tjr)
        TextView tjr;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.tv_money)
        TextView tv_money;
        public Type1ViewHolder(@NonNull ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void bindView(@NonNull View itemView) {
            ButterKnife.bind(this, itemView);
            // tv = itemView.findViewById(R.id.tv_type_1);
        }

        public void bindData(final MyxiaxianxinxBean.DataBean entity) {//ZhiFuActivity
//            PicassoUtil.loadPicassoARGB_8888(content, Const.getbase(entity.getHeadUrl()), toux, false);//加载网络图片
            Glide.with(content)
                    .load(Const.getbase(entity.getHeadUrl()))
                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
                    .into(toux);
            name.setText(""+entity.getNickName());
            time.setText(""+entity.getCreateDate());
            tv_money.setText(""+entity.getForMeAmount());
            tjr.setText("(推荐人:"+entity.getTJNickName()+")");

//            sj.setText(entity.getStatus()==1?);
//            if(entity.isIsCompanyIncome()){//收入or支出 （true收入 false 支出）
//                bt1.setText("转账收款");
//                qs.setText("+" + entity.getAmount());
//            }else{
//                bt1.setText("转账付款");
//                qs.setText("-" + entity.getAmount());
//            }
//            sj.setText("成功");
//            bt.setText(entity.getCreateDate());


        }

    }
}
