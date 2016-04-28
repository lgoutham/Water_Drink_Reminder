package com.example.gautham.dr.drinklog;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gautham.dr.R;
import com.example.gautham.dr.database.WaterBottlesData;
import com.example.gautham.dr.database.WaterDatabase;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by GAUTHAM on 4/20/2016.
 */
class CustomAdapter extends SimpleCursorAdapter {

    Cursor dataCursor;
    LayoutInflater mInflater;
    Context context;
    int sec_total;

    public CustomAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        dataCursor = c;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.drink_log_header, null);
            holder = new ViewHolder();
            holder.tvTime = (TextView) convertView.findViewById(R.id.one_day_log_bottle_time);
            holder.tvSize = (TextView) convertView.findViewById(R.id.one_day_log_bottle_size);
            holder.imageView = (ImageView) convertView.findViewById(R.id.one_day_log_bottle);
            holder.sec_total = (TextView) convertView.findViewById(R.id.one_day_log_total);
            holder.sec_hdr = (TextView) convertView.findViewById(R.id.one_day_log_bottle_date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        dataCursor.moveToPosition(position);
        int title = dataCursor.getColumnIndex(WaterDatabase.KEY_TIME);
        String task_title = dataCursor.getString(title);

        int title_date = dataCursor.getColumnIndex(WaterDatabase.KEY_DATE);
        String task_day = dataCursor.getString(title_date);

        int description_index = dataCursor.getColumnIndex(WaterDatabase.KEY_POS);
        int priority = dataCursor.getInt(description_index);

        String prevDate = null;

        if (dataCursor.getPosition() > 0 && dataCursor.moveToPrevious()) {
            prevDate = dataCursor.getString(title_date);
            dataCursor.moveToNext();
        }

        if (task_day.equals(prevDate)) {
            holder.sec_hdr.setVisibility(View.GONE);
            holder.sec_total.setVisibility(View.GONE);
            sec_total = getSectionTotal(priority);
            sec_total = 0;
        } else {
            holder.sec_hdr.setText(task_day);
            holder.sec_total.setText(String.valueOf(sec_total) + "ml");
            holder.sec_hdr.setVisibility(View.VISIBLE);
            holder.sec_total.setVisibility(View.VISIBLE);
        }

        holder.tvTime.setText(task_title);

        switch (priority) {
            case 0:
                holder.tvSize.setText(WaterBottlesData.getData().get(0).title + "ml");
                holder.imageView.setImageResource(WaterBottlesData.getData().get(0).imageId);
                break;
            case 1:
                holder.tvSize.setText(WaterBottlesData.getData().get(1).title + "ml");
                holder.imageView.setImageResource(WaterBottlesData.getData().get(1).imageId);
                break;
            case 2:
                holder.tvSize.setText(WaterBottlesData.getData().get(2).title + "ml");
                holder.imageView.setImageResource(WaterBottlesData.getData().get(2).imageId);
                break;
            case 3:
                holder.tvSize.setText(WaterBottlesData.getData().get(3).title + "ml");
                holder.imageView.setImageResource(WaterBottlesData.getData().get(3).imageId);
                break;
            case 4:
                holder.tvSize.setText(WaterBottlesData.getData().get(4).title + "ml");
                holder.imageView.setImageResource(WaterBottlesData.getData().get(4).imageId);
                break;
            case 5:
                holder.tvSize.setText(WaterBottlesData.getData().get(5).title + "ml");
                holder.imageView.setImageResource(WaterBottlesData.getData().get(5).imageId);
                break;
            case 6:
                holder.tvSize.setText(WaterBottlesData.getData().get(6).title + "ml");
                holder.imageView.setImageResource(WaterBottlesData.getData().get(6).imageId);
                break;
            case 7:
                holder.tvSize.setText(WaterBottlesData.getData().get(7).title + "ml");
                holder.imageView.setImageResource(WaterBottlesData.getData().get(7).imageId);
                break;
            default:
                Toast.makeText(context, "Wrong input", Toast.LENGTH_SHORT).show();
        }

        return convertView;
    }

    private int getSectionTotal(int priority) {

        switch (priority) {
            case 0:
                sec_total += 100;
                break;
            case 1:
                sec_total += 150;
                break;
            case 2:
                sec_total += 300;
                break;
            case 3:
                sec_total += 400;
                break;
            case 4:
                sec_total += 500;
                break;
            case 5:
                sec_total += 600;
                break;
            case 6:
                sec_total += 700;
                break;
            case 7:
                sec_total += 800;
                break;
        }
        return sec_total;
    }

    public static class ViewHolder {
        public TextView tvSize;
        public TextView tvTime;
        public TextView sec_hdr;
        public TextView sec_total;
        public ImageView imageView;
    }

}
