package com.example.gautham.dr.reminder;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gautham.dr.R;

/**
 * Created by GAUTHAM on 4/21/2016.
 */
public class CustomizeSound extends DialogFragment implements AdapterView.OnItemClickListener {

    CustomizeSoundAdapter customizeSoundAdapter;
    ListView listView;
    String[] mSoundTitles;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customize_sound, container, false);
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        listView = (ListView) view.findViewById(R.id.customize_sound_list);
        customizeSoundAdapter = new CustomizeSoundAdapter(getActivity());
        listView.setOnItemClickListener(this);
        listView.setAdapter(customizeSoundAdapter);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Context context=getActivity();
        getDialog().dismiss();
        Uri alarmSound = ReminderSound.getAlarmSound(context,position);
        MyAlarmService.mNotifyBuilder.setSound(alarmSound);
        Reminder.customize_sound_style.setText(mSoundTitles[position]);
    }

    private class CustomizeSoundAdapter extends BaseAdapter {

        Context context;


        public CustomizeSoundAdapter(Context context) {
            this.context = context;
            mSoundTitles = context.getResources().getStringArray(R.array.sounds_array);
        }

        @Override
        public int getCount() {
            return mSoundTitles.length;
        }

        @Override
        public Object getItem(int position) {
            return mSoundTitles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.customize_sound_row, parent, false);
            } else {
                row = convertView;
            }
            TextView tvTitle = (TextView) row.findViewById(R.id.customize_sound_row_text);
            tvTitle.setText(mSoundTitles[position]);
            return row;
        }
    }
}
