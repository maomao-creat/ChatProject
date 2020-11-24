package com.zykj.samplechat.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.SearchChattingPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.adapter.SearchChattingAdapter;
import com.zykj.samplechat.ui.view.MessageView;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnTextChanged;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * Created by csh
 * Created date 2016/12/8.
 * Description 搜索聊天记录
 */

public class SearchChattingActivity extends ToolBarActivity<SearchChattingPresenter> implements MessageView {

    @Bind(R.id.f_phone)EditText f_phone;
    @Bind(R.id.lv_message)ListView lv_message;
    private SearchChattingAdapter mAdapter;
    private String filter = "";
    private Conversation conversation;
    private List<Message> data = new ArrayList<>();
    private TextView emptyView;

    @OnTextChanged(R.id.f_phone)
    public void f_phone(CharSequence s, int start, int before, int count){
        filter = f_phone.getText().toString();
        if(filter.length()==0) {
            lv_message.setVisibility(View.INVISIBLE);
        } else {
            lv_message.setVisibility(View.VISIBLE);
            presenter.searchMessage(conversation, filter);
        }
    }

    @Override
    protected CharSequence provideTitle() {
        return "";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_search_chatting;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        conversation = getIntent().getParcelableExtra("conversation");
        f_phone.setInputType(InputType.TYPE_CLASS_TEXT);
        mAdapter = new SearchChattingAdapter(this, data, conversation);
        lv_message.setAdapter(mAdapter);

        emptyView = new TextView(this);
        emptyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 200));
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup)lv_message.getParent()).addView(emptyView);
        lv_message.setEmptyView(emptyView);
    }

    @Override
    public void initListeners() {}

    @Override
    public SearchChattingPresenter createPresenter() {
        return new SearchChattingPresenter();
    }

    @Override
    public void success(List<Message> data) {
        if(data.size() == 0){
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append("没有搜到");
            SpannableStringBuilder colorFilterStr = new SpannableStringBuilder(filter);
            colorFilterStr.setSpan(new ForegroundColorSpan(Color.parseColor("#0099ff")), 0, filter.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.append(colorFilterStr);
            spannableStringBuilder.append("相关的信息");
            emptyView.setText(spannableStringBuilder);
        }
        mAdapter.setFilter(filter);
        this.data.clear();
        this.data.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}