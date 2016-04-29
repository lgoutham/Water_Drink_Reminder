package com.example.gautham.dr.reminder;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gautham.dr.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Reminder extends AppCompatActivity implements OnClickListener {

    TextView remind_description;
    public static TextView customize_sound_style;
    Button mode1, mode2, mode3, mode4, auto_btn, manual_btn;
    RadioButton auto_radio, manual_radio;
    LinearLayout otherSettingsLayout, customizeSoundLayout;
    RelativeLayout auto, manual;

    String whichRadio = "WHICHRADIO";
    public static String radio = "radio_state";
    SharedPreferences sharedData_Radio;
    SharedPreferences.Editor editor;

    String radioButtonState = "RADIO_BUTTON_STATE";
    boolean result;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedData_Radio = this.getSharedPreferences(radio, Context.MODE_PRIVATE);
        editor = sharedData_Radio.edit();

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        remind_description = (TextView) findViewById(R.id.reminder_mode_description);
        customize_sound_style = (TextView) findViewById(R.id.customize_sound_style);
        mode1 = (Button) findViewById(R.id.reminder_mode1);
        mode2 = (Button) findViewById(R.id.reminder_mode2);
        mode3 = (Button) findViewById(R.id.reminder_mode3);
        mode4 = (Button) findViewById(R.id.reminder_mode4);
        auto_btn = (Button) findViewById(R.id.auto_btn);
        manual_btn = (Button) findViewById(R.id.manual_btn);
        auto_radio = (RadioButton) findViewById(R.id.auto_radio);
        manual_radio = (RadioButton) findViewById(R.id.manual_radio);
        auto = (RelativeLayout) findViewById(R.id.auto_reminder_layout);
        manual = (RelativeLayout) findViewById(R.id.manual_reminder_layout);
        otherSettingsLayout = (LinearLayout) findViewById(R.id.other_settings_layout);
        customizeSoundLayout = (LinearLayout) findViewById(R.id.customize_sound_layout);

        result = sharedData_Radio.getBoolean(whichRadio, false);
        if (result) {
            auto_radio.setChecked(result);
            auto_btn.setVisibility(View.VISIBLE);
            startNotifications();
        } else {
            manual_radio.setChecked(!result);
            manual_btn.setVisibility(View.VISIBLE);
        }
        if (savedInstanceState != null) {
            result = savedInstanceState.getBoolean(radioButtonState);
            auto_radio.setChecked(result);
            manual_radio.setChecked(!result);
        }

        mode1.setOnClickListener(this);
        mode2.setOnClickListener(this);
        mode3.setOnClickListener(this);
        mode4.setOnClickListener(this);

        auto.setOnClickListener(this);
        manual.setOnClickListener(this);
        otherSettingsLayout.setOnClickListener(this);
        customizeSoundLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reminder_mode1:
                remind_description.setText("Turn off reminder");
                break;
            case R.id.reminder_mode2:
                remind_description.setText("No reminder when you are ahead of progress");
                break;
            case R.id.reminder_mode3:
                remind_description.setText("Mute when you are ahead of progress");
                break;
            case R.id.reminder_mode4:
                remind_description.setText("Auto reminders");
                break;
            case R.id.auto_reminder_layout:
                if (manual_radio.isChecked()) {
                    manual_radio.setChecked(false);
                    manual_btn.setVisibility(View.INVISIBLE);
                }
                auto_radio.setChecked(true);
                result = true;
                auto_btn.setVisibility(View.VISIBLE);
                editor.putBoolean(whichRadio, auto_radio.isChecked());
                editor.apply();
                startNotifications();
                auto_btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Reminder.this, "Auto Button", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.manual_reminder_layout:
                FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ManualReminder manualReminder = new ManualReminder();
                manualReminder.show(fragmentManager, "Manual Reminder");
                break;
            case R.id.other_settings_layout:
                Intent i = new Intent(this, OtherSettings.class);
                startActivity(i);
                break;
            case R.id.customize_sound_layout:
                CustomizeSound customizeSound = new CustomizeSound();
                FragmentManager manager = getFragmentManager();
                customizeSound.show(manager, "CUSTOMIZE_SOUND");
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean(radioButtonState, result);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        result = savedInstanceState.getBoolean(radioButtonState);
    }

    private void startNotifications() {

        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        final int NOTIFICATION_ID = 1;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(c.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // Set the alarm's trigger time to 8:00 a.m.
        calendar.set(Calendar.HOUR_OF_DAY, 8);

        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Reminder.this, AlarmReceiver.class);
        intent.putExtra("notificationId", NOTIFICATION_ID);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_HOUR, alarmIntent);
        if (time.equals("20:00"))
            alarmMgr.cancel(alarmIntent);

    }

    public class ManualReminder extends DialogFragment implements View.OnClickListener {
        Button cancel, apply;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            View view = inflater.inflate(R.layout.manual_reminder, container, false);
            cancel = (Button) view.findViewById(R.id.reminder_cancel);
            apply = (Button) view.findViewById(R.id.reminder_apply);
            cancel.setOnClickListener(this);
            apply.setOnClickListener(this);
            return view;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.reminder_cancel:
                    getDialog().dismiss();
                    break;
                case R.id.reminder_apply:
                    if (auto_radio.isChecked()) {
                        auto_radio.setChecked(false);
                        auto_btn.setVisibility(View.INVISIBLE);
                    }
                    manual_radio.setChecked(true);
                    manual_btn.setVisibility(View.VISIBLE);
                    editor.putBoolean(whichRadio, auto_radio.isChecked());
                    editor.commit();
                    manual_btn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(Reminder.this, "Manual Button", Toast.LENGTH_SHORT).show();
                        }
                    });
                    getDialog().dismiss();
                    break;
            }
        }
    }
}