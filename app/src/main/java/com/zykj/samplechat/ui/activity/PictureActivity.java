package com.zykj.samplechat.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import io.rong.imkit.plugin.image.PictureSelectorActivity;

/**
 * Created by csh
 * Created date 2016/12/20.
 * Description 拍照
 */

public class PictureActivity extends PictureSelectorActivity {
    @Override
    protected void requestCamera() {
        boolean permission= ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT < 23 || permission) {
            if(cameraIsCanUse()){
                super.requestCamera();
            }else{
                Toast.makeText(this, "无法开启摄像头，请检查朋友是否有访问摄像头的权限，或重启设备后重试", Toast.LENGTH_SHORT).show();
            }
            //startActivityForPluginResult(new Intent(this,NewRecordVideoActivity.class),13,this);
        }else{
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
//        if(Build.VERSION.SDK_INT < 23 && !cameraIsCanUse()){
//            dialog = new Dialog(this).title("无法开启摄像头，请检查朋友是否有访问摄像头的权限，或重启设备后重试")
//                    .positiveAction("确定").positiveActionClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    }).backgroundColor(Color.parseColor("#FFFFFF")).titleColor(Color.parseColor("#292A2F")).cancelable(false);
//            dialog.show();
//        }
//        super.requestCamera();
    }

    /**
     *  返回true 表示可以使用  返回false表示不可以使用
     */
    public boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters(); //针对魅族手机
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }
        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }
}