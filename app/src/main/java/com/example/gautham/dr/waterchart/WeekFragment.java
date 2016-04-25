package com.example.gautham.dr.waterchart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gautham.dr.R;

/**
 * Created by GAUTHAM on 4/23/2016.
 */
public class WeekFragment extends android.support.v4.app.Fragment {

    public WeekFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.week_fragment, container, false);
        return view;
    }
}
