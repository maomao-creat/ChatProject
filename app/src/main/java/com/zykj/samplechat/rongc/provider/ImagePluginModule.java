package com.zykj.samplechat.rongc.provider;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import com.zykj.samplechat.ui.activity.PictureActivity;
import java.util.ArrayList;
import io.rong.imkit.RongExtension;
import io.rong.imkit.manager.SendImageManager;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.utilities.PermissionCheckUtil;
import io.rong.imlib.model.Conversation;

/**
 * Created by csh
 * Created date 2016/12/20.
 * Description
 */

public class ImagePluginModule implements IPluginModule {
    Conversation.ConversationType conversationType;
    String targetId;

    public ImagePluginModule() {}

    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, io.rong.imkit.R.drawable.rc_ext_plugin_image_selector);
    }

    public String obtainTitle(Context context) {
        return context.getString(io.rong.imkit.R.string.rc_plugin_image);
    }

    public void onClick(Fragment currentFragment, RongExtension extension) {
        String[] permissions = new String[]{"android.permission.READ_EXTERNAL_STORAGE"};
        if(PermissionCheckUtil.requestPermissions(currentFragment, permissions)) {
            this.conversationType = extension.getConversationType();
            this.targetId = extension.getTargetId();
            Intent intent = new Intent(currentFragment.getActivity(), PictureActivity.class);
            extension.startActivityForPluginResult(intent, 23, this);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == -1) {
            boolean sendOrigin = data.getBooleanExtra("sendOrigin", false);
            ArrayList list = data.getParcelableArrayListExtra("android.intent.extra.RETURN_RESULT");
            SendImageManager.getInstance().sendImages(conversationType, targetId, list, sendOrigin);
        }
    }
}