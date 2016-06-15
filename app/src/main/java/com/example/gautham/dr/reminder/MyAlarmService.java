package com.example.gautham.dr.reminder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.gautham.dr.R;
import com.example.gautham.dr.main.MainActivity;

/**
 * Created by GAUTHAM on 4/22/2016.
 */
public class MyAlarmService extends Service {

    NotificationCompat.Builder mNotifyBuilder;
    NotificationManager mNotificationManager;
    SharedPreferences got_notification;
    int position;
    Uri alarmSound;
    int counter;
    Vibrator vibrator;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        got_notification = getSharedPreferences(CustomizeSound.reminder, Context.MODE_PRIVATE);
        position = got_notification.getInt(CustomizeSound.whichPosition, 0);
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        switch (position) {
            case 0:
                alarmSound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.water);
                break;
            case 1:
                alarmSound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.water_cut);
                break;
            case 2:
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                break;
            default:
                break;
        }

        mNotifyBuilder = new NotificationCompat.Builder(this)
                .setTicker("Drink Water Reminder")
                .setContentTitle("Drink Water Reminder")
                .setContentText("Completed 1230ml, Drink 546ml water remaining")
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setSound(alarmSound)
                .setSmallIcon(R.drawable.notify_drink)
                .setNumber(++counter);

        int notificationId = resultIntent.getIntExtra("notificationId", 0);

        //Provide backstack for Action Button Drink
        Intent j = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder_settings = TaskStackBuilder.create(this);
        stackBuilder_settings.addParentStack(MainActivity.class);
        stackBuilder_settings.addNextIntent(j);
        PendingIntent pi_settings = stackBuilder_settings.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        TaskStackBuilder stackBuilder_help = TaskStackBuilder.create(this);
        stackBuilder_help.addNextIntent(new Intent(""));
        PendingIntent pi_help = stackBuilder_help.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mNotifyBuilder.setContentIntent(resultPendingIntent);
        mNotifyBuilder.addAction(R.drawable.notify_drink, "Drink", pi_settings);
        mNotifyBuilder.addAction(R.drawable.notify_snooze, "Snooze", pi_help);
        mNotifyBuilder.setAutoCancel(true);
        mNotificationManager.notify(notificationId, mNotifyBuilder.build());
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
