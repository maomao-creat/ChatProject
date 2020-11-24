package com.zykj.samplechat.rongc.provider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.zykj.samplechat.R;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.presenter.UploadVideoPresenter;
import com.zykj.samplechat.ui.activity.NewRecordVideoActivity;
import com.zykj.samplechat.ui.view.UploadVideoView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.Encoder;
import com.zykj.samplechat.utils.StringUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.message.ImageMessage;

/**
 * Created by csh
 * Created date 2016/12/7.
 * Description 短视频
 */

public class VideoIPluginModule implements IPluginModule,UploadVideoView {
    private String picSrc="",type="",ider="";
    private Fragment fragment;
    private RongExtension rongExtension;
    private UploadVideoPresenter uploadVideoPresenter = new UploadVideoPresenter();
    private Dialog dialog;
    public VideoIPluginModule() {
        uploadVideoPresenter.attachView(this);
    }
    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, io.rong.imkit.R.drawable.rc_file_icon_video);
    }

    @Override
    public String obtainTitle(Context context) {
        return "小视频";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        this.fragment = fragment;
        this.rongExtension = rongExtension;
        ider = rongExtension.getTargetId();
        type = rongExtension.getConversationType().getName();
        boolean permission=ActivityCompat.checkSelfPermission(fragment.getContext(),Manifest.permission.CAMERA)
                ==PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT < 23 || permission) {
            rongExtension.startActivityForPluginResult(new Intent(fragment.getContext(),NewRecordVideoActivity.class),13,this);
        }else{
            //申请WRITE_EXTERNAL_STORAGE权限
            fragment.requestPermissions(new String[]{Manifest.permission.CAMERA},100);
        }
        //fragment.startActivityForResult(new Intent(fragment.getContext(),NewRecordVideoActivity.class),13);
    }

//    public void requestPermissions(){
//        rongExtension.startActivityForPluginResult(new Intent(fragment.getContext(),NewRecordVideoActivity.class),13,this);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==13 && resultCode == Activity.RESULT_OK){
            String path = data.getStringExtra("path");//"file://"+
            Bitmap bmp = getVideoThumbnail(path);
            if (bmp == null){
                Toast.makeText(fragment.getContext(),"视频过短，请重新拍摄",Toast.LENGTH_LONG).show();
            }else{
                dialog = new Dialog(fragment.getContext()).backgroundColor(Color.parseColor("#FFFFFF"))
                        .titleColor(Color.parseColor("#292A2F")).contentView(R.layout.dialog_circular).cancelable(false);
                dialog.show();
//                Bitmap bmp1 = BitmapFactory.decodeResource(fragment.getContext().getResources(), io.rong.imkit.R.drawable.video_play);
                String picName = path.substring(path.lastIndexOf("/")+1,path.lastIndexOf("."))+"_play.png";
                String picPath = path.substring(0,path.lastIndexOf("/")+1);
//                try {
////                    saveBitmap(mergeBitmap(bmp, bmp1),picName,picPath);
//                } catch (IOException e) {
//                    dialog.dismiss();
//                    Toast.makeText(fragment.getContext(),"上传失败，请稍后再试",Toast.LENGTH_LONG).show();
//                    return;
//                }
                String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
                Map<String,String> map = new HashMap<>();
                map.put("key", Const.KEY);
                map.put("uid", Const.UID);
                map.put("function", "UpLoadVideo");
                map.put("fileName", fileName);
                map.put("filestream", Encoder.encodeBase64File(path));
                String json = StringUtil.toJson(map);
                try {
                    String d = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                    uploadVideoPresenter.UpLoadVideo(d);
                } catch (Exception ex) {
                    dialog.dismiss();
                    ex.toString();
                }
            }
        }
    }

    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
            retriever.release();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //合并图片
    private Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(), firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, firstBitmap.getWidth()/2-39, firstBitmap.getHeight()/2-40, null);
        return bitmap;
    }

    private void saveBitmap(Bitmap bitmap,String bitName,String picPath) throws IOException{
        picSrc = picPath+bitName ;//"/storage/emulated/0/Android/data/com.hewuzhe/files/qupai_simple_workspace/"+bitName;
        File file = new File(picPath+bitName);
        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        out = new FileOutputStream(file);
        if(bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)){
            out.flush();
            out.close();
        }
    }

    @Override
    public void successUpload(String path) {
        dialog.dismiss();
        if(path.toLowerCase().startsWith("upload")){
            //Toast.makeText(fragment.getContext(),"视频上传成功，正在发送中...",Toast.LENGTH_LONG).show();
            sendVideoMessage(picSrc,Const.getbase(path), type.equals("group")? ConversationType.GROUP:
                            ConversationType.PRIVATE, ider);
        }else{
            Toast.makeText(fragment.getContext(), path, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 发送图片消息上传到服务器
     * @param firstImgPath  视频第一帧画面的本地 path
     * @param videoPath  视频存储的本地路径
     * @param mConversationType  会话类型
     * @param targetId  目标id
     */
    public void sendVideoMessage(String firstImgPath, String videoPath, ConversationType mConversationType, String targetId) {
        if (!new File(firstImgPath).exists()) {
            Toast.makeText(fragment.getContext(),"上传失败",Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(videoPath)) {
            Toast.makeText(fragment.getContext(),"上传失败",Toast.LENGTH_LONG).show();
        }else{
            ImageMessage imageMessage = ImageMessage.obtain(Uri.parse("file://" + firstImgPath), Uri.parse("file://" + firstImgPath));
            imageMessage.setExtra("123456;" + videoPath);
            RongIM.getInstance().sendImageMessage(mConversationType,targetId,imageMessage,null,null,null);
        }
    }
}