package com.example.gautham.dr.main;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gautham.dr.R;
import com.example.gautham.dr.database.WaterBottlesData;
import com.example.gautham.dr.database.WaterDatabase;
import com.example.gautham.dr.database.WaterDbProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by GAUTHAM on 4/25/2016.
 */
public class BottleGrid extends Fragment {

    String time, date;
    int pos;
    String[] bposition;
    private GridView gridView;
    GridViewAdapter gridViewAdapter;
    Uri uri = WaterDbProvider.CONTENT_URI;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottle_grid, container, false);
        gridView = (GridView) view.findViewById(R.id.scroll_view_content);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        time = sdf.format(c.getTime());
        date = df.format(c.getTime());

        String[] projection = {WaterDatabase.KEY_ID, WaterDatabase.KEY_POS, WaterDatabase.KEY_DATE, WaterDatabase.KEY_TIME};
        String selection = WaterDatabase.KEY_DATE + "=?";
        String[] selectionArgs = {date};
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);

        gridViewAdapter = new GridViewAdapter(getActivity(), cursor);
        gridViewAdapter.notifyDataSetChanged();
        gridView.setAdapter(gridViewAdapter);
        gridView.invalidateViews();
        gridView.setSelection(gridView.getAdapter().getCount() - 1);


        final FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                Log.e("POSITION", pos + "");
                Bottle bottle = new Bottle();
                bottle.show(fragmentManager, "Bottle");
                return true;
            }
        });

        Log.e("FORMATED_DATE", date + "");
        Log.e("FORMATED_DATE", time + "");

        return view;
    }

    private class GridViewAdapter extends BaseAdapter {

        Context context;
        int count;
        Cursor c;
        String[] gotdate;
        String[] gotime;

        public GridViewAdapter(Context context, Cursor cursor) {
            this.context = context;
            this.c = cursor;
            if (c.getCount() > 0) {
                count = c.getCount();
                bposition = new String[count + 1];
                gotdate = new String[count + 1];
                gotime = new String[count + 1];
                c.moveToFirst();
                int i = 0;
                while (!c.isAfterLast()) {
                    bposition[i] = c.getString(c.getColumnIndex(WaterDatabase.KEY_POS));
                    gotdate[i] = c.getString(c.getColumnIndex(WaterDatabase.KEY_DATE));
                    gotime[i] = c.getString(c.getColumnIndex(WaterDatabase.KEY_TIME));
                    i++;
                    c.moveToNext();
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
                row = inflater.inflate(R.layout.custom_grid_view, parent, false);
            } else {
                row = convertView;
            }
            TextView tvQuantity = (TextView) row.findViewById(R.id.bottle_quantity);
            ImageView bottle = (ImageView) row.findViewById(R.id.bottle);
            TextView tvTime = (TextView) row.findViewById(R.id.bottle_time);
            bottle.setImageResource(WaterBottlesData.getData().get(Integer.parseInt(bposition[position])).imageId);
            tvQuantity.setText(WaterBottlesData.getData().get(Integer.parseInt(bposition[position])).title + "ml");
            tvTime.setText(gotime[position]);
            return row;
        }
    }

    public class Bottle extends DialogFragment implements View.OnClickListener {

        Button cancel, delete, ok;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            setCancelable(false);
            View view = inflater.inflate(R.layout.bottle, container, false);
            ok = (Button) view.findViewById(R.id.button);
            cancel = (Button) view.findViewById(R.id.button2);
            delete = (Button) view.findViewById(R.id.delete);
            cancel.setOnClickListener(this);
            delete.setOnClickListener(this);
            ok.setOnClickListener(this);
            return view;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete:
                    String[] selectionArgs = {bposition[pos]};
                    Log.e("SELECTION_ARGS", bposition[pos]);
                    getActivity().getContentResolver().delete(WaterDbProvider.CONTENT_URI,
                            WaterDatabase.KEY_ID + " = ?", selectionArgs);
                    Fragment fragment = new BottleGrid();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.bottle_container, fragment).addToBackStack(null).commit();
                    getDialog().dismiss();
                    break;
                case R.id.button2:
                    getDialog().dismiss();
                    break;
                case R.id.button:

                    break;
            }
        }
    }
}
