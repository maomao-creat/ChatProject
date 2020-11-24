package com.zykj.samplechat.rongc.listener;

import android.content.Context;
import android.content.Intent;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;


public class NotificationReceiver extends PushMessageReceiver {

    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        Intent msgIntent = new Intent("ANNOUNCE_RECEIVED_ACTION");
        context.sendBroadcast(msgIntent);
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        return false;
    }
}
