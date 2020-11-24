package com.zykj.samplechat.ui.itemdelegate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.myql;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.ui.view.lbhd;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.davidsu.library.BaseViewHolder;
import cn.davidsu.library.ItemViewDelegate;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;


/**
 * Created by cxzheng on 2018/3/29.
 * process type1 item
 */

public class MyqlDelegate implements ItemViewDelegate<myql, MyqlDelegate.Type1ViewHolder> {


    lbhd callback;


    public MyqlDelegate(Context content, lbhd callback) {
        this.content = content;
        this.callback = callback;

    }

    Context content;

    @NonNull
    @Override
    public Type1ViewHolder createViewHolder(ViewGroup parent) {
        return new Type1ViewHolder(parent, R.layout.view_item_myql);
    }

    @Override
    public void bindViewHolder(Type1ViewHolder holder, myql entity) {
        holder.bindData(entity);
    }

    class Type1ViewHolder extends BaseViewHolder {
        //
//        @BindView(R.id.zong)
//        LinearLayout zong;
        @Bind(R.id.toux)
        CircleImageView toux;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.zong)
        LinearLayout zong;

        public Type1ViewHolder(@NonNull ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void bindView(@NonNull View itemView) {
            ButterKnife.bind(this, itemView);
            // tv = itemView.findViewById(R.id.tv_type_1);
        }

        public void bindData(final myql entity) {//ZhiFuActivity
//            PicassoUtil.loadPicassoARGB_8888(content, NR.url + entity.getImagePath(), toux, false);//加载网络图片
            Glide.with(content)
                    .load(Const.getbase(entity.getImagePath()))
                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
                    .into(toux);
            name.setText("" + entity.getName());
            time.setText("");
            zong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RongIM.getInstance().startGroupChat(content, entity.getId(), entity.getName());//打开群聊天
                }
            });
        }

    }
}
