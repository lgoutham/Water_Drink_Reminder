package com.example.gautham.dr.main;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gautham.dr.R;
import com.example.gautham.dr.database.WaterBottlesData;
import com.example.gautham.dr.database.WaterDatabase;

/**
 * Created by GAUTHAM on 4/26/2016.
 */
public class GridviewCustomAdapter extends SimpleCursorAdapter {

    Cursor dataCursor;
    LayoutInflater mInflater;
    Context context;

    public GridviewCustomAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        dataCursor = c;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CustomViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_grid_view, null);
            holder = new CustomViewHolder();
            holder.tvTime = (TextView) convertView.findViewById(R.id.bottle_time);
            holder.tvSize = (TextView) convertView.findViewById(R.id.bottle_quantity);
            holder.imageView = (ImageView) convertView.findViewById(R.id.bottle);
            convertView.setTag(holder);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }

        dataCursor.moveToPosition(position);
        int title = dataCursor.getColumnIndex(WaterDatabase.KEY_TIME);
        String task_title = dataCursor.getString(title);

        int description_index = dataCursor.getColumnIndex(WaterDatabase.KEY_POS);
        int priority = dataCursor.getInt(description_index);

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

    public static class CustomViewHolder {
        public TextView tvSize;
        public TextView tvTime;
        public ImageView imageView;
    }
}

