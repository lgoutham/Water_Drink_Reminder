package com.example.gautham.dr.waterchart;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gautham.dr.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

/**
 * Created by GAUTHAM on 4/23/2016.
 */
public class WeekFragment extends android.support.v4.app.Fragment {

    private LinearLayout linearLayout;
    private LineChart lineChart;

    public WeekFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.week_fragment, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.main_layout);
        lineChart = new LineChart(getContext());
        linearLayout.addView(lineChart);

        lineChart.setDescription("Line Chart");
        lineChart.setNoDataTextDescription("");

        lineChart.setHighlightEnabled(true);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);

        lineChart.setPinchZoom(true);
        lineChart.setBackgroundColor(Color.GRAY);

//        LineData data=new LineData();


        return view;
    }
}
