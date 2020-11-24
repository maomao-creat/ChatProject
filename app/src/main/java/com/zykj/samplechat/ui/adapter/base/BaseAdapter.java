package com.zykj.samplechat.ui.adapter.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;
import com.zykj.samplechat.R;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.base.OnItemClickListener;

import java.util.ArrayList;

/**
 ******************************************************
 *                                                    *
 *                                                    *
 *                       _oo0oo_                      *
 *                      o8888888o                     *
 *                      88" . "88                     *
 *                      (| -_- |)                     *
 *                      0\  =  /0                     *
 *                    ___/`---'\___                   *
 *                  .' \\|     |# '.                  *
 *                 / \\|||  :  |||# \                 *
 *                / _||||| -:- |||||- \               *
 *               |   | \\\  -  #/ |   |               *
 *               | \_|  ''\---/''  |_/ |              *
 *               \  .-\__  '-'  ___/-. /              *
 *             ___'. .'  /--.--\  `. .'___            *
 *          ."" '<  `.___\_<|>_/___.' >' "".          *
 *         | | :  `- \`.;`\ _ /`;.`/ - ` : | |        *
 *         \  \ `_.   \_ __\ /__ _/   .-` /  /        *
 *     =====`-.____`.___ \_____/___.-`___.-'=====     *
 *                       `=---='                      *
 *                                                    *
 *                                                    *
 *     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    *
 *                                                    *
 *               佛祖保佑         永无BUG              *
 *                                                    *
 *                                                    *
 ******************************************************
 *
 * Created by ninos on 2016/6/2.
 *
 */
public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, M, P extends BasePresenterImp> extends RecyclerView.Adapter<VH> {

    public static final int STATUS_HASMORE = 0;
    public static final int STATUS_NOMORE = 1;
    public static final int STATUS_LOADING = 2;
    private static int STATUS = STATUS_HASMORE;
    private View header;
    public LayoutInflater _inflater;
    /**
     * 数据
     */
    public ArrayList<M> data = new ArrayList<>();

    public Context context;
    /**
     * Item点击事件
     */
    public OnItemClickListener mOnItemClickListener;
    public P _presenter;
    private View footer;
    @Nullable
    private TextView tvStatus;
    @Nullable
    public boolean isAddFooter = true;


    private void init() {
        if (isAddFooter) {
            footer = _inflater.inflate(R.layout.footer_recycleview, null);
            tvStatus = (TextView) footer.findViewById(R.id.tv_status);
        }

    }

    /**
     * RecycleView的头部
     */

    public BaseAdapter(Context context) {
        this.context = context;
        _inflater = LayoutInflater.from(context);
        init();
    }


    public BaseAdapter(Context context, P p) {
        this.context = context;
        _inflater = LayoutInflater.from(context);
        this._presenter = p;
        init();

    }


    public BaseAdapter(Context context, View header) {
        this.context = context;
        _inflater = LayoutInflater.from(context);
        this.header = header;
        init();

    }

    public BaseAdapter(Context context, P p, View header) {
        this.context = context;
        _inflater = LayoutInflater.from(context);
        this.header = header;
        this._presenter = p;
        init();

    }

    public BaseAdapter(Context context, boolean isAddFooter) {
        this.context = context;
        _inflater = LayoutInflater.from(context);
        this.isAddFooter = isAddFooter;
        init();

    }

    public BaseAdapter(Context context, P p, boolean isAddFooter) {
        this.context = context;
        _inflater = LayoutInflater.from(context);
        this._presenter = p;
        this.isAddFooter = isAddFooter;
        init();


    }


    public BaseAdapter(Context context, View header, boolean isAddFooter) {
        this.context = context;
        _inflater = LayoutInflater.from(context);
        this.header = header;
        this.isAddFooter = isAddFooter;
        init();


    }

    public BaseAdapter(Context context, P p, View header, boolean isAddFooter) {
        this.context = context;
        _inflater = LayoutInflater.from(context);
        this.header = header;
        this._presenter = p;
        this.isAddFooter = isAddFooter;
        init();

    }


    @Override
    public int getItemViewType(int position) {
        if (header != null) {
            if (position == Const.VIEW_TYPE_HEADER) {
                return Const.VIEW_TYPE_HEADER;
            }
        }

        if (isAddFooter) {
            if (position == getItemCount() - 1) {
                return Const.VIEW_TYPE_FOOTER;
            }
        }

        return Const.VIEW_TYPE_NORMAL;
    }


    /**
     * @return ItemView的LayoutId
     */
    public abstract int provideItemLayoutId();


    /**
     * @param parent
     * @param viewType
     * @param view
     * @return 创建ViewHolder
     */
    public abstract VH createVH(ViewGroup parent, int viewType, View view);


    /**
     * @param holder
     * @param position 绑定数据
     */
    public abstract void bindData(VH holder, int position);


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Const.VIEW_TYPE_HEADER) {
            AutoUtils.auto(header);
            return createVH(parent, viewType, header);
        } else if (viewType == Const.VIEW_TYPE_FOOTER) {
            AutoUtils.auto(footer);
            return createVH(parent, viewType, footer);
        } else {
            View view = LayoutInflater.from(context).inflate(provideItemLayoutId(), parent,false);
            AutoUtils.auto(view);
            return createVH(parent, viewType, view);
        }
    }


    @Override
    public void onBindViewHolder(VH holder, final int position) {
        if (holder.getItemViewType() != Const.VIEW_TYPE_HEADER && holder.getItemViewType() != Const.VIEW_TYPE_FOOTER) {
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(view, header == null ? position : position - 1, data.get(header == null ? position : position - 1));
                    }
                });
            } else {
                holder.itemView.setOnClickListener(null);
            }

            bindData(holder, header == null ? position : position - 1);
        }
    }

    @Override
    public int getItemCount() {
        if (header != null) {
            if (isAddFooter) {
                return data.size() + 2;
            } else {
                return data.size() + 1;
            }

        } else {
            if (isAddFooter) {
                return data.size() + 1;
            } else {
                return data.size();
            }
        }

    }

    /**
     * 设置监听者
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    /**
     * 先清除后添加
     *
     * @param data
     */
    public void addDatas(ArrayList<M> data) {
        this.data.clear();
        this.data.addAll(data);
        this.notifyDataSetChanged();
    }

    public void addLastData(M m) {
        this.data.add(m);
        this.notifyItemChanged(data.size() - 1);
    }

    public void addFirstData(M m) {
        this.data.add(0, m);
        this.notifyItemInserted(0);
    }

    public void removeFirstData() {
        this.data.remove(0);
        this.notifyItemRemoved(0);
    }

    public void removeLastData() {
        this.data.remove(data.size() - 1);
//        this.notifyItemChanged(data.size() - 1);
        notifyDataSetChanged();
    }


    /**
     * 先清除后添加(动画)
     *
     * @param data
     */
    public void addDataAnim(ArrayList<M> data) {
        this.data.clear();
        for (int i = 0; i < data.size(); i++) {
            this.data.add(data.get(i));
            this.notifyItemChanged(i);
        }
    }


    /**
     * 添加更多
     *
     * @param data
     */
    public void addMore(ArrayList<M> data) {
        this.data.addAll(data);
        this.notifyItemRangeChanged(this.data.size(), data.size());

    }

    public void loading() {
        STATUS = STATUS_LOADING;
        if (tvStatus!=null) {

            tvStatus.setText("正在加载...");
        }
    }

    public void hasMore() {
        STATUS = STATUS_HASMORE;
        if (tvStatus!=null) {

            tvStatus.setText("加载更多");
        }
    }

    public void noMore() {
        STATUS = STATUS_NOMORE;
        if (tvStatus!=null) {
            tvStatus.setText("已经全部加载完毕");
        }
    }


    public void loading(String tip) {
        STATUS = STATUS_LOADING;
        if (tvStatus!=null) {

            tvStatus.setText(tip);
        }
    }

    public void hasMore(String tip) {
        STATUS = STATUS_HASMORE;
        if (tvStatus!=null) {
            tvStatus.setText(tip);
        }
    }


    public void noMore(String tip) {
        STATUS = STATUS_NOMORE;
        if (tvStatus!=null) {
            tvStatus.setText(tip);
        }
    }


    /**
     * 获取当前状态
     *
     * @return
     */
    public int getStatus() {
        return this.STATUS;
    }

    public void setStatus(int state) {
        STATUS = state;
    }

    public void setFooter(boolean isAddFooter) {
        isAddFooter = isAddFooter;
    }

    public void removeItem(M m) {
        this.data.remove(m);
        this.notifyDataSetChanged();
    }
}
