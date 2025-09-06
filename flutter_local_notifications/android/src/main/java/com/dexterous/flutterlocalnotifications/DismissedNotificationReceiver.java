package com.dexterous.flutterlocalnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dexterous.flutterlocalnotifications.models.NotificationDetails;
import com.dexterous.flutterlocalnotifications.utils.BooleanUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * A receiver that is triggered when a notification is dismissed by the user.
 *
 * This receiver is responsible for re-displaying ongoing (sticky) notifications that have been
 * dismissed by the user. This is a workaround for issues on some Android versions where ongoing
 * notifications can be dismissed.
 */
public class DismissedNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationDetailsJson = intent.getStringExtra(FlutterLocalNotificationsPlugin.NOTIFICATION_DETAILS);
        if (notificationDetailsJson != null) {
            Gson gson = FlutterLocalNotificationsPlugin.buildGson();
            Type type = new TypeToken<NotificationDetails>() {
            }.getType();
            NotificationDetails notificationDetails = gson.fromJson(notificationDetailsJson, type);

            if (BooleanUtils.getValue(notificationDetails.resendWhenDismissed) && BooleanUtils.getValue(notificationDetails.ongoing)) {
                FlutterLocalNotificationsPlugin.showNotification(context, notificationDetails);
            }
        }
    }
}
