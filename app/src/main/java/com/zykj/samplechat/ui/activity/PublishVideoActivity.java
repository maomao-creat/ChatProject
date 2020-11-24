package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Image;
import com.zykj.samplechat.model.ImageTemp;
import com.zykj.samplechat.model.Video;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.PublishVideoPresenter;
import com.zykj.samplechat.ui.activity.base.ToolBarActivity;
import com.zykj.samplechat.ui.view.PublishVideoView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.Bun;
import com.zykj.samplechat.utils.Encoder;
import com.zykj.samplechat.utils.GlideLoader;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ninos on 2016/7/13.
 */
public class PublishVideoActivity extends ToolBarActivity<PublishVideoPresenter> implements PublishVideoView {

    ArrayList<Image> list = new ArrayList<>();
    private String content;
    public int count = 3;
    public LayoutInflater inflater;
    private Bitmap bmp;
    private String path;
    ArrayList<String> paths;

    @Override
    protected void action() {
        super.action();
        if(edt_content.getText().toString().trim().equals("")){
            toast("内容不能为空");
            return;
        }
        content = edt_content.getText().toString().trim();

        showDialog();

        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "UpLoadVideo");
        map.put("fileName", path);
        map.put("filestream", Encoder.encodeBase64File(path));
        String json = StringUtil.toJson(map);
        try {
            String d = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.UpLoadVideo(d);
        } catch (Exception ex) {
            ex.toString();
        }
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Bind(R.id.gi_pics)LinearLayout gi_pics;
    @Bind(R.id.edt_content)EditText edt_content;
    @Bind(R.id.img_select_pic)ImageView img_select_pic;
    @Bind(R.id.img_take_pic)ImageView img_take_pic;
    @Bind(R.id.video_img)ImageView video_img;

    @OnClick(R.id.img_select_pic)
    public void Img_select_pic(){
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.colorPrimary))
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // 开启多选   （默认为多选）
                .mutiSelect()
                // 多选时的最大数量   （默认 9 张）
                .mutiSelectMaxSize(9)
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 已选择的图片路径
                .pathList(new ArrayList<String>())
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();


        ImageSelector.open(this, imageConfig);   // 开启图片选择器
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);

            for (int i = 0;i<pathList.size();i++){
                list.add(new Image(pathList.get(i)));
            }

            if(list.size()>9){
                toast("最多只能选择9张图片");
                return;
            }

            gi_pics.removeAllViews();

            if (list.size() > 0) {
                int pageCount = list.size() / count + 1;
                for (int i = 0; i < pageCount; i++) {
                    addImg(list, i);
                }
            }
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_publish_video;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        inflater = LayoutInflater.from(this);
        path = getIntent().getBundleExtra("data").getString("path");
        bmp = getIntent().getBundleExtra("data").getParcelable("bmp");

        video_img.setImageBitmap(bmp);

        tvAction.setText("发布");

    }

    @Override
    public void initListeners() {

    }

    @Override
    public PublishVideoPresenter createPresenter() {
        return new PublishVideoPresenter();
    }


    @Override
    protected CharSequence provideTitle() {
        return "发布动态";
    }

    /**
     * 添加图片
     * @param page
     */
    private void addImg(final ArrayList<Image> list, final int page) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.layout_pic, null);
        gi_pics.addView(linearLayout);

        /**
         * 要添加的图片
         **/
        List<Image> subList = list.subList(page * count, page * count + count >= list.size() ? list.size() : page * count + count);
        for (int i = 0; i < subList.size(); i++) {
            Image pic = subList.get(i);

            LinearLayout imgWraper = (LinearLayout) inflater.inflate(R.layout.singer_pic, null);
            linearLayout.addView(imgWraper);

            Glide.with(this)
                    .load(Uri.fromFile(new File(pic.FilePath)))
                    .apply(new RequestOptions().placeholder(R.drawable.userimg))
                    .into((ImageView)imgWraper.findViewById(R.id.img));

            final int finalI = i;
            imgWraper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /**
                     * 到图片集页
                     * */
                    for (Image pic1 : list) {
                        pic1.PictureUrl = pic1.ImagePath;
                    }
                    Intent intent = new Intent(PublishVideoActivity.this, PicsActivity.class);
                    intent.putExtra("data", new Bun().putInt("pos", finalI + (page * count)).
                            putString("pics", new Gson().toJson(list)).ok());
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public void uploadImage(ArrayList<ImageTemp> list) {
        Map map = new HashMap();
        String paths="";
        for (ImageTemp imageTemp : list){
            paths+=imageTemp.BigImagePath+"&";
        }
        try{paths = paths.substring(0,paths.lastIndexOf("&"));}catch (Exception ex){paths="";}

        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "SaveDongtai");
        map.put("userid", new UserUtil(this).getUserId());
        map.put("content", content);
        map.put("pathlist", "");
        map.put("videoImage", "");
        map.put("videopath", "");
        map.put("videoDuration", "");
        map.put("smallImage", "");
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.SaveDongtai(data);
        } catch (Exception ex) {
            ex.toString();
        }
    }

    @Override
    public void saveSuccess() {
        dismissDialog();
        setResult(1002);
        this.finish();
    }

    @Override
    public void errorUpload() {
        toast("上产失败");
        dismissDialog();
    }

    @Override
    public void successUpload(Video path) {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "SaveDongtai");
        map.put("userid", new UserUtil(this).getUserId());
        map.put("content", content);
        map.put("pathlist", "");
        map.put("videoImage", path.ImagePath);
        map.put("videopath", path.VideoPath);
        map.put("videoDuration", "");
        map.put("smallImage", "");
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            presenter.SaveDongtai(data);
        } catch (Exception ex) {
            ex.toString();
        }
    }
}
