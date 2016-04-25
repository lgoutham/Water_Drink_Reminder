package com.example.gautham.dr.main;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gautham.dr.R;

/**
 * Created by GAUTHAM on 4/5/2016.
 */
public class WaterSettingsAdapter extends BaseAdapter {

    private Context context;
    String[] mWaterSettinsTitles;

    public WaterSettingsAdapter(Context context){
        this.context = context;
        mWaterSettinsTitles=context.getResources().getStringArray(R.array.water_settings_array);
    }
    @Override
    public int getCount() {
        return mWaterSettinsTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return mWaterSettinsTitles[position];
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
            row = inflater.inflate(R.layout.water_settings_view, parent, false);
        } else {
            row = convertView;
        }
        LinearLayout linearLayout=(LinearLayout)row.findViewById(R.id.settings_layout);
        CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkBox);
        TextView tvTitle = (TextView) row.findViewById(R.id.tvTitle);
        TextView tvValue = (TextView) row.findViewById(R.id.tvValue);

        if(position==0|position==1|position==4) {
            checkBox.setVisibility(View.INVISIBLE);
        }else {
            checkBox.setVisibility(View.VISIBLE);
        }
        tvValue.setText("2000ml");
        tvTitle.setText(mWaterSettinsTitles[position]);

        if(position==4){
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            tvTitle.setTextColor(Color.parseColor("#FFFFFF"));
            tvValue.setTextColor(Color.parseColor("#FFFFFF"));
        }
        return row;
    }
}
