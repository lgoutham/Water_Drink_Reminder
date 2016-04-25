package com.example.gautham.dr.drinklog;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gautham.dr.R;
import com.example.gautham.dr.database.WaterBottlesData;
import com.example.gautham.dr.database.WaterDatabase;

/**
 * Created by GAUTHAM on 4/14/2016.
 */
public class OneDayLogAdapter extends BaseAdapter {
    Context context;
    String[] bposition;
    String[] gotdate;
    String[] gotime;
    Cursor one_day_adapter_cursor;
    int count;

    public OneDayLogAdapter(Context context, Cursor c) {
        this.context = context;
        this.one_day_adapter_cursor=c;
        if (one_day_adapter_cursor.getCount() > 0) {
            count = one_day_adapter_cursor.getCount();
            bposition = new String[count + 1];
            gotdate = new String[count + 1];
            gotime = new String[count + 1];
            one_day_adapter_cursor.moveToFirst();
            int i = 0;
            while (!one_day_adapter_cursor.isAfterLast()) {
                bposition[i] = one_day_adapter_cursor.getString(one_day_adapter_cursor.getColumnIndex(WaterDatabase.KEY_POS));
                gotdate[i] = one_day_adapter_cursor.getString(one_day_adapter_cursor.getColumnIndex(WaterDatabase.KEY_DATE));
                gotime[i] = one_day_adapter_cursor.getString(one_day_adapter_cursor.getColumnIndex(WaterDatabase.KEY_TIME));
                i++;
                one_day_adapter_cursor.moveToNext();
            }
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return WaterBottlesData.getData().get(Integer.parseInt(bposition[position]));
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
            row = inflater.inflate(R.layout.one_day_drink_log, parent, false);
        } else {
            row = convertView;
        }
        TextView one_day_log_bottle_size = (TextView) row.findViewById(R.id.one_day_log_bottle_size);
        TextView one_day_log_bottle_time = (TextView) row.findViewById(R.id.one_day_log_bottle_time);
        TextView one_day_log_bottle_date = (TextView) row.findViewById(R.id.one_day_log_bottle_date);
        ImageView one_day_log_bottle = (ImageView) row.findViewById(R.id.one_day_log_bottle);
        one_day_log_bottle.setImageResource(WaterBottlesData.getData().get(Integer.parseInt(bposition[position])).imageId);
        one_day_log_bottle_size.setText(WaterBottlesData.getData().get(Integer.parseInt(bposition[position])).title);
        one_day_log_bottle_date.setText(gotdate[position]);
        one_day_log_bottle_time.setText(gotime[position]);
        return row;
    }
}
