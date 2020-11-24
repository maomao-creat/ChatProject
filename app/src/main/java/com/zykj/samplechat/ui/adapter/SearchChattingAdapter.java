package com.zykj.samplechat.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;
import com.zykj.samplechat.ui.widget.CharacterParser;
import com.zykj.samplechat.ui.widget.RoundImageView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

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
 *               佛祖保佑         永无BUG               *
 *                                                    *
 *                                                    *
 ******************************************************
 *
 * Created by ninos on 2016/6/2.
 *
 */
public class SearchChattingAdapter extends BaseAdapter {

    private Context context;
    private List<Message> list;
    private Conversation conversation;
    private String filter;

    public SearchChattingAdapter(Context context, List<Message> list, Conversation conversation){
        this.context = context;
        this.list = list;
        this.conversation = conversation;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list == null)
            return null;

        if (position >= list.size())
            return null;

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final VHolder holder;
        Message message = (Message) getItem(position);
        if (convertView == null) {
            holder = new VHolder();
            convertView = View.inflate(context, R.layout.item_chatting_list, null);
            holder.iv_header = (RoundImageView) convertView.findViewById(R.id.iv_header);
            holder.ll_chat = (LinearLayout) convertView.findViewById(R.id.ll_chat);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_detail = (TextView) convertView.findViewById(R.id.tv_detail);
            holder.tv_date = (TextView)convertView.findViewById(R.id.tv_date);
            convertView.setTag(holder);
        } else {
            holder = (VHolder) convertView.getTag();
        }
        Message msg = list.get(position);
        holder.tv_name.setText("虎子");
        holder.tv_detail.setText(CharacterParser.getInstance().getColoredChattingRecord(filter, msg.getContent()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = simpleDateFormat.format(new Date(msg.getSentTime()));
        String formatDate = date.replace("-", "/");
        holder.tv_date.setText(formatDate);
        Glide.with(context)
                .load(conversation.getPortraitUrl())
                .apply(new RequestOptions().placeholder(R.drawable.group))
                .into(holder.iv_header);
        return convertView;
    }

    class VHolder {
        RoundImageView iv_header;
        LinearLayout ll_chat;
        TextView tv_name;
        TextView tv_detail;
        TextView tv_date;
    }
}