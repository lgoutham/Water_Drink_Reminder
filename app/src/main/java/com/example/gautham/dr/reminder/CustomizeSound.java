package com.example.gautham.dr.reminder;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gautham.dr.R;

/**
 * Created by GAUTHAM on 4/21/2016.
 */
public class CustomizeSound extends DialogFragment {

    CustomizeSoundAdapter customizeSoundAdapter;
    ListView listView;
    String[] mSoundTitles;
    Dialog dialog;
    public static String whichPosition = "WHICH_POSITION";
    public static String reminder = "reminder";
    SharedPreferences notificationSound;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customize_sound, container, false);
        listView = (ListView) view.findViewById(R.id.customize_sound_list);
        customizeSoundAdapter = new CustomizeSoundAdapter(getActivity());
        listView.setAdapter(customizeSoundAdapter);
        notificationSound = getActivity().getSharedPreferences(reminder, Context.MODE_PRIVATE);
        editor = notificationSound.edit();
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.customize_sound_row, parent, false);
            } else {
                row = convertView;
            }
            TextView tvTitle = (TextView) row.findViewById(R.id.customize_sound_row_text);
            tvTitle.setText(mSoundTitles[position]);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Reminder.customize_sound_style.setText(mSoundTitles[position]);
                    editor.putInt(whichPosition, position);
                    editor.commit();
                }
            });
            return row;
        }
    }
}
