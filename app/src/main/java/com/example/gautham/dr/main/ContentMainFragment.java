package com.example.gautham.dr.main;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gautham.dr.R;
import com.example.gautham.dr.database.WaterBottlesData;
import com.example.gautham.dr.database.WaterDatabase;
import com.example.gautham.dr.database.WaterDbProvider;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by GAUTHAM on 3/28/2016.
 */
public class ContentMainFragment extends Fragment {

    public FloatingActionButton floatingActionButtonAdd, floatingActionButtonSelect;
    String time, date;
    TextView xx, yy;
    Button btn;
    public static MediaPlayer mp;

    int weight_array[];
    static ProgressBar progressBar;

    public static String weight = "weight";
    public static String weight_flie = "weight_unit";
    SharedPreferences sharedDataWeightUnit, sharedDataWeight;
    String total_weight, prefs_unit;

    Cursor weight_cursor;
    int drink_target;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        time = sdf.format(c.getTime());
        date = df.format(c.getTime());

        View view = inflater.inflate(R.layout.content_main, container, false);

        mp = MediaPlayer.create(getActivity(), R.raw.button_click);

        xx = (TextView) view.findViewById(R.id.target_xx);
        yy = (TextView) view.findViewById(R.id.target_yy);
        btn = (Button) view.findViewById(R.id.water_settings);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        final FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new BottleGrid();
        fragmentTransaction.replace(R.id.bottle_container, fragment).commit();

//        Cursor c1 = getActivity().getContentResolver().query(WaterDbProvider.CONTENT_WEIGHT_URI, null, null, null, null);
//        int weight_data = getWeight(c1);
//        Log.e("WEIGHT", weight_data + "");
//        drink_target= calTarget(weight_data);

        sharedDataWeight = getActivity().getSharedPreferences(weight, 0);
        String weight_prefs = sharedDataWeight.getString("weight", 0 + "");
        int dummy = Integer.parseInt(weight_prefs);
        drink_target = calTarget(dummy);
        yy.setText("/" + drink_target + "");

        progressBar.setMax(drink_target);

        sharedDataWeightUnit = getActivity().getSharedPreferences(weight_flie, 0);
        sharedDataWeight = getActivity().getSharedPreferences(weight, 0);
        prefs_unit = sharedDataWeightUnit.getString("weight_unit", "Kg");
        sharedDataWeight = getActivity().getSharedPreferences(weight, 0);
        total_weight = sharedDataWeight.getString("weight", "00");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaterSettings waterSettings = new WaterSettings();
                waterSettings.show(fragmentManager, "Water");
            }
        });
        floatingActionButtonSelect = (FloatingActionButton) view.findViewById(R.id.fab_select);
        floatingActionButtonAdd = (FloatingActionButton) view.findViewById(R.id.fab_add);
        floatingActionButtonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectGlass selectGlass = new SelectGlass();
                selectGlass.show(fragmentManager, "Glasses");
            }
        });
        floatingActionButtonSelect.setImageResource(WaterBottlesData.getData().get(SelectGlass.position).imageId);
        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                ContentValues values = new ContentValues();
                values.clear();
                int pos = SelectGlass.position;
                values.put(WaterDatabase.KEY_POS, pos);
                values.put(WaterDatabase.KEY_DATE, date);
                values.put(WaterDatabase.KEY_TIME, time);
                Uri uri = WaterDbProvider.CONTENT_URI;
                Uri newUri = getActivity().getContentResolver().insert(uri, values);
                updateProgress(pos);
                Fragment fragment = new BottleGrid();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.bottle_container, fragment).addToBackStack(null).commit();
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    public static void updateProgress(int position) {
        switch (position) {
            case 0:
                progressBar.incrementProgressBy(100);
                break;
            case 1:
                progressBar.incrementProgressBy(150);
                break;
            case 2:
                progressBar.incrementProgressBy(200);
                break;
            case 3:
                progressBar.incrementProgressBy(400);
                break;
            case 4:
                progressBar.incrementProgressBy(500);
                break;
            case 5:
                progressBar.incrementProgressBy(600);
                break;
            case 6:
                progressBar.incrementProgressBy(700);
                break;
            case 7:
                progressBar.incrementProgressBy(800);
                break;
        }
    }

    private int calTarget(int weight_data) {
        int data_y = weight_data * 33;
        Log.e("TARGET", data_y + "");
        return data_y;

    }

   /* public int getWeight(Cursor c1) {
        weight_cursor = c1;
        if (weight_cursor.getCount() > 0) {
            weight_array = new int[weight_cursor.getCount() + 1];
            weight_cursor.moveToFirst();
            int i = 0;
            while (!weight_cursor.isAfterLast()) {
                weight_array[i] = weight_cursor.getColumnIndex(WaterDatabase.KEY_WEIGHT);
                i++;
                weight_cursor.moveToNext();
            }
        }
        return weight_array[0];
    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }
}