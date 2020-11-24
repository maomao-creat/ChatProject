package com.zykj.samplechat.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.FriendsTuijianPresenter;
import com.zykj.samplechat.ui.activity.base.RecycleViewActivity;
import com.zykj.samplechat.ui.adapter.BindUserAdapter;
import com.zykj.samplechat.ui.adapter.FriendsTuijianAdapter;
import com.zykj.samplechat.ui.view.FriendsTuijianView;
import com.zykj.samplechat.ui.widget.CharacterParser;
import com.zykj.samplechat.ui.widget.IndexView;
import com.zykj.samplechat.ui.widget.PinyinFriendComparator;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;

/**
 * Created by ninos on 2016/7/14.
 */
public class FriendsTuijianActivity extends RecycleViewActivity<FriendsTuijianPresenter, FriendsTuijianAdapter, Friend> implements FriendsTuijianView {

    @Bind(R.id.tv_recyclerindexview_tip)TextView tv_recyclerindexview_tip;
    @Bind(R.id.ai_user)RelativeLayout ai_user;
    @Bind(R.id.ai_username)TextView ai_username;
    @Bind(R.id.ai_userimg)ImageView ai_userimg;
    @Bind(R.id.indexview)IndexView indexview;
    @Bind(R.id.recycler_showuser)RecyclerView recycler_showuser;

    LinearLayout f_add_friends;
    BindUserAdapter bindUserAdapter;
    private int id;
    private String photo;
    private String nicname;
    private View header;
    private ArrayList<Friend> friends = new ArrayList<>();
    private ArrayList<Friend> fs = new ArrayList<>();

    @Override
    protected void action() {
        super.action();
        if(friends.size() > 0){
            showDialog();
            for (Friend friend : friends){
                getUserInfoImage(friend.Id+"");
            }

        }else{
            toast("请至少选择一个人");
        }
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected FriendsTuijianAdapter provideAdapter() {
        return new FriendsTuijianAdapter(this, presenter);
    }

    public void getFriends(){
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","GetFriendForKeyValue");
        map.put("userid",new UserUtil(getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.GetFriendForKeyValue(data);
        }catch (Exception ex){
        }
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        indexview.setTipTv(tv_recyclerindexview_tip);

        fs = (ArrayList<Friend>) getIntent().getBundleExtra("data").getSerializable("fs");
        id = getIntent().getBundleExtra("data").getInt("id");
        photo = getIntent().getBundleExtra("data").getString("photo");
        nicname = getIntent().getBundleExtra("data").getString("nicname");

        ai_username.setText(nicname);

        Glide.with(this)
                .load(Const.getbase( photo))
                .apply(new RequestOptions().placeholder(R.drawable.userimg))
                .into(ai_userimg);
        getFriends();

        tvAction.setText("确定");
        recycler_showuser.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        bindUserAdapter = new BindUserAdapter(this);

        recycler_showuser.setAdapter(bindUserAdapter);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_team_create_test;
    }

    @Override
    public void initListeners() {
        indexview.setOnChangedListener(new IndexView.OnChangedListener() {
            @Override
            public void onChanged(String text) {
                int position = adapter.getPositionForSection(text.charAt(0));
                position++;
                if (position != -1) {
                    // position的item滑动到RecyclerView的可见区域，如果已经可见则不会滑动
                    layoutManager.scrollToPosition(position);
                }
            }
        });
    }

    @Override
    public FriendsTuijianPresenter createPresenter() {
        return new FriendsTuijianPresenter();
    }

    @Override
    public void onItemClick(View view, int pos, Friend item) {

        if(item.isAdd){
            return;
        }

        item.isSelected = item.isSelected?false:true;
        if (item.isSelected){
            friends.add(item);
        }else{
            friends.remove(item);
        }
        adapter.notifyDataSetChanged();

        bindUserAdapter.data.clear();
//        bindUserAdapter.data.addAll(friends);

        bindUserAdapter.notifyDataSetChanged();
    }

    @Override
    public void successFound(ArrayList<Friend> list) {
        PinyinFriendComparator mPinyinComparator = new PinyinFriendComparator();
        CharacterParser mCharacterParser = CharacterParser.getInstance();

        for (Friend friend : list) {
            if (StringUtil.isEmpty(friend.RemarkName)) {
                friend.topc = mCharacterParser.getSelling(friend.NicName).substring(0, 1).toUpperCase().matches("[A-Z]")?mCharacterParser.getSelling(friend.NicName).substring(0, 1).toUpperCase():"#";
            } else {
                friend.topc = mCharacterParser.getSelling(friend.RemarkName).substring(0, 1).toUpperCase().matches("[A-Z]")?mCharacterParser.getSelling(friend.RemarkName).substring(0, 1).toUpperCase():"#";
            }
        }

        Collections.sort(list, mPinyinComparator);


            for (Friend ff : list){
//                if(id==ff.Id){
//                    ff.isAdd = true;
//                }
            }


        bd(list);
    }

    @Override
    public void errorFound() {

    }

    @Override
    public void success() {
        toast("推荐发送成功");
        finishActivity();
    }

    @Override
    public void refresh(boolean refreshing) {

    }

    @Override
    protected CharSequence provideTitle() {
        return "选择联系人";
    }

    public void getUserInfoImage(final String id) {
        View view = findViewById(R.id.ai_user);
        view.setBackgroundColor(getResources().getColor(R.color.white));
        final Bitmap bmp = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bmp));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = dateFormat.format(new Date());

        File imageFileSource = new File(getCacheDir(), time+"source.jpg");
        File imageFileThumb = new File(getCacheDir(), time+"thumb.jpg");

        try {
            Bitmap bmpSource = bmp;

            imageFileSource.createNewFile();

            FileOutputStream fosSource = new FileOutputStream(imageFileSource);


            // 保存原图。
            bmpSource.compress(Bitmap.CompressFormat.JPEG, 100, fosSource);

            // 创建缩略图变换矩阵。
            Matrix m = new Matrix();
            m.setRectToRect(new RectF(0, 0, bmpSource.getWidth(), bmpSource.getHeight()), new RectF(0, 0, 160, 160), Matrix.ScaleToFit.CENTER);

            // 生成缩略图。
            Bitmap bmpThumb = bmpSource;//Bitmap.createBitmap(bmpSource, 0, 0, bmpSource.getWidth(), bmpSource.getHeight(), m, true);

            imageFileThumb.createNewFile();

            FileOutputStream fosThumb = new FileOutputStream(imageFileThumb);

            // 保存缩略图。
            bmpThumb.compress(Bitmap.CompressFormat.JPEG, 60, fosThumb);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageMessage imgMsg = ImageMessage.obtain(Uri.fromFile(imageFileThumb), Uri.fromFile(imageFileSource));
        imgMsg.setExtra("654321;" + this.id);

        /**
        * 发送图片消息。
        *
        * @param type     会话类型。
        * @param targetId 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
        * @param content  消息内容。
        * @param callback 发送消息的回调。
        */
        RongIM.getInstance().getRongIMClient().sendImageMessage(Conversation.ConversationType.PRIVATE, id, imgMsg, "", "", new RongIMClient.SendImageMessageCallback() {

            @Override
            public void onAttached(final Message message) {
                //保存数据库成功
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode code) {
                //发送失败
                dismissDialog();
                toast("推荐失败");
            }

            @Override
            public void onSuccess(Message message) {
                //发送成功
                toast("推荐成功");
                dismissDialog();
                finish();
            }

            @Override
            public void onProgress(Message message, int progress) {
                //发送进度
            }
        });
    }
}
