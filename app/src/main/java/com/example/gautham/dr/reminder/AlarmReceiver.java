package com.example.gautham.dr.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.gautham.dr.R;

/**
 * Created by GAUTHAM on 4/22/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra("notificationId", 0);
        Intent service = new Intent(context, MyAlarmService.class);
        service.putExtra("notificationId",notificationId);
        context.startService(service);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationId==1)
        manager.cancel(notificationId);
    }
}
