package com.example.gautham.dr.drinklog;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.gautham.dr.R;
import com.example.gautham.dr.database.WaterDatabase;
import com.example.gautham.dr.database.WaterDbProvider;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

/**
 * Created by GAUTHAM on 4/7/2016.
 */
public class DrinkLog extends Fragment {

    ListView recyclerView;
    CustomAdapter customAdapter;
    Cursor drink_log_cursor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.drink_log, container, false);
        drink_log_cursor = getActivity().getContentResolver().query(WaterDbProvider.CONTENT_URI, null, null, null, null);
        String[] from = {WaterDatabase.KEY_POS, WaterDatabase.KEY_TIME, WaterDatabase.KEY_DATE};
        int[] to = {R.id.one_day_log_bottle_size, R.id.one_day_log_bottle_time, R.id.one_day_log_bottle_date};
        customAdapter = new CustomAdapter(getActivity(), R.layout.one_day_drink_log, drink_log_cursor, from, to, 0);

        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = (ListView) view.findViewById(R.id.drink_log_view);
        recyclerView.setAdapter(customAdapter);
        return view;
    }
}
