package com.example.gautham.dr.reminder;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.view.View;

import com.example.gautham.dr.R;

/**
 * Created by GAUTHAM on 4/24/2016.
 */
public class ReminderSound {

    static Uri _uri = null;

    public static Uri getAlarmSound(Context view, int position) {
        Context context = view;
        switch (position) {
            case 0:
                _uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                break;
            case 1:
                _uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.water);
                break;
            case 2:
                _uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.water_cut);
                break;
            default:
                return null;
        }
        if (_uri == null)
            return null;
        else
            return _uri;
    }

}
