package com.zykj.samplechat.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.FriendMap;
import com.zykj.samplechat.presenter.FriendsAddPresenter;
import com.zykj.samplechat.ui.activity.base.SwipeRecycleViewActivity;
import com.zykj.samplechat.ui.adapter.FriendsAddAdapter;
import com.zykj.samplechat.ui.view.FriendsAddView;
import com.zykj.samplechat.utils.ActivityUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * Created by ninos on 2016/7/7.
 */
public class FriendsAddActivity extends SwipeRecycleViewActivity<FriendsAddPresenter, FriendsAddAdapter, FriendMap> implements FriendsAddView {

    private View header;
    private ArrayList<Friend> friends = new ArrayList<>();
    private LinearLayout add_for_phone;
    private LinearLayout add_for_contacts;

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        add_for_phone = (LinearLayout) header.findViewById(R.id.add_for_phone);
        add_for_contacts = (LinearLayout) header.findViewById(R.id.add_for_contacts);
        presenter.getData(page, count);
    }

    @Override
    protected FriendsAddAdapter provideAdapter() {
        header = getLayoutInflater().inflate(R.layout.activity_user_add_header, null);
        return new FriendsAddAdapter(this, presenter, header);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected CharSequence provideTitle() {
        return "新的朋友";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user_add;
    }

    @Override
    public void initListeners() {
        add_for_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//搜索按钮被点击
                startActivity(FriendsFoundActivity.class);
                ActivityUtil.addActivity(FriendsAddActivity.this);
            }
        });
        add_for_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean permission = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)
                        == PackageManager.PERMISSION_GRANTED;
                if (Build.VERSION.SDK_INT < 23 && permission) {
                    startActivity(FriendsContactsActivity.class);
                    ActivityUtil.addActivity(FriendsAddActivity.this);
                } else {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(FriendsAddActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(FriendsContactsActivity.class);
                ActivityUtil.addActivity(FriendsAddActivity.this);
            } else {
                toast("权限被拒绝");
            }
        }
    }

    @Override
    public FriendsAddPresenter createPresenter() {
        return new FriendsAddPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, FriendMap item) {
        RongIM.getInstance().startPrivateChat(getContext(), item.Key + "", item.Value.NicName);
    }

    @Override
    public void success(ArrayList<FriendMap> list) {
        bd(list);
        refresh(false);
    }

    @Override
    public void successAdd(int id) {
        if (id < 0) {
            presenter.getData(1, count);
            snb("删除成功", add_for_contacts);
        } else {
            snb("添加好友成功", add_for_contacts);
            adapter.data.remove(id);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void error() {
        refresh(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
