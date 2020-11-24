package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.TeamManagerPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.TeamInfoView;
import com.zykj.samplechat.ui.widget.RoundImageView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.Encoder;
import com.zykj.samplechat.utils.GlideLoader;
import com.zykj.samplechat.utils.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.Bind;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * Created by csh
 * Created date 2016/12/19.
 * Description 创建群组
 */

public class TeamManagerActivity extends ToolBarActivity<TeamManagerPresenter> implements TeamInfoView {

    @Bind(R.id.iv_group)
    RoundImageView iv_group;
    @Bind(R.id.et_name)
    EditText et_name;
    private String path = "";
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_team_manager;
    }

    @Override
    protected CharSequence provideTitle() {
        return "创建群组";
    }

    @Override
    public void initListeners() {}

    @OnClick(R.id.iv_group)
    public void upload(){
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(ContextCompat.getColor(this,R.color.colorPrimary))
                .titleBgColor(ContextCompat.getColor(this,R.color.colorPrimary))
                .titleSubmitTextColor(ContextCompat.getColor(this,R.color.white))
                .titleTextColor(ContextCompat.getColor(this,R.color.white))
                // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
                .crop()
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();
        ImageSelector.open(this, imageConfig);   // 开启图片选择器
    }

    @OnClick(R.id.create_ok)
    protected void createGroup(){
        String name = et_name.getText().toString();
        String idlist = getIntent().getStringExtra("ids");
        if(StringUtil.isEmpty(path)){
            snb("请上传群头像", et_name);
        }else if(StringUtil.isEmpty(name)){
            snb("群昵称不能为空", et_name);
        }else{
            showDialog();
            String fileStream = Encoder.bitmapToString(path);
            Map map = new HashMap();
            map.put("key", Const.KEY);
            map.put("uid",Const.UID);
            map.put("function","UpLoadImage");
            map.put("fileName",path);
            map.put("filestream",fileStream);
            String json = StringUtil.toJson(map);
            try {
                String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                presenter.UpLoadImage(data,name,idlist);
            }catch (Exception e){}
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            path = pathList.get(0);
            iv_group.setImageBitmap(BitmapFactory.decodeFile(pathList.get(0)));
        }
    }

    @Override
    public TeamManagerPresenter createPresenter() {
        return new TeamManagerPresenter();
    }

    @Override
    public void successTeam(Team team) {
        dismissDialog();
        RongIM.getInstance().startGroupChat(this, team.TeamId + "", team.Name);
        finish();
    }

    @Override
    public void successFriends(ArrayList<Friend> list) {}

    @Override
    public void other(int type, String data) {}

    @Override
    public void success() {}

    @Override
    public void successSendYue() {

    }

    @Override
    public void successJiesan() {}

    @Override
    public void error() {
        dismissDialog();
        snb("请求失败！", et_name);
    }
}