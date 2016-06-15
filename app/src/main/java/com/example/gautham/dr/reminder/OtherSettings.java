package com.example.gautham.dr.reminder;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gautham.dr.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * Created by GAUTHAM on 4/21/2016.
 */
public class OtherSettings extends AppCompatActivity {

    private Toolbar toolbar;
    ListView listView;
    String[] motherSettingsTitles;
    OtherSettingsAdapter otherSettingsAdapter;
    public static String other_settings = "OTHER_SETTINGS";
    public static String vibration = "vibration";
    public static String pos = "POSITION";
    SharedPreferences otherSettings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        otherSettings = this.getSharedPreferences(other_settings, Context.MODE_PRIVATE);
        editor = otherSettings.edit();

        listView = (ListView) findViewById(R.id.other_settings_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        otherSettingsAdapter = new OtherSettingsAdapter(this);
        listView.setItemsCanFocus(true);
        listView.setAdapter(otherSettingsAdapter);
    }

    private class OtherSettingsAdapter extends BaseAdapter {

        private Context context;

        public OtherSettingsAdapter(Context context) {
            this.context = context;
            motherSettingsTitles = context.getResources().getStringArray(R.array.other_settings);
        }

        @Override
        public int getCount() {
            return motherSettingsTitles.length;
        }

        @Override
        public Object getItem(int position) {
            return motherSettingsTitles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            View row = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.other_settings_row, parent, false);
            } else {
                row = convertView;
            }
            TextView titletv = (TextView) row.findViewById(R.id.other_settings_row_text);
            final TextView textView = (TextView) row.findViewById(R.id.other_settings_row_text_status);
            final SwitchCompat mSwitch = (SwitchCompat) row.findViewById(R.id.other_settings_switch);
            if (position == 0 || position == 1 || position == 2) {
                mSwitch.setVisibility(View.VISIBLE);
            }
            titletv.setText(motherSettingsTitles[position]);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSwitch.isChecked() && position != 3) {
                        textView.setText("Off");
                        mSwitch.setChecked(false);
                    } else {
                        textView.setText("On");
                        mSwitch.setChecked(true);
                    }
                    if (position == 3) {
                        RingingVolume ringingVolume = new RingingVolume();
                        FragmentManager manager = getFragmentManager();
                        ringingVolume.show(manager, "RINGING_VOLUME");
                    }
                }
            });
//            mSwitch.
            return row;
        }
    }
}
