package com.example.gautham.dr.waterchart;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gautham.dr.R;

/**
 * Created by GAUTHAM on 4/23/2016.
 */
public class YearFragment extends android.support.v4.app.Fragment {

    public YearFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.year_fragment, container, false);
        return view;
    }
}
