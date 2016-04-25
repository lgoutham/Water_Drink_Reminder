package com.example.gautham.dr.main;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.example.gautham.dr.R;

/**
 * Created by GAUTHAM on 3/18/2016.
 */
public class WaterSettings extends DialogFragment {

    ListView listView;
    Button cancel, save;
    WaterSettingsAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.water_settings, container, false);
        listView = (ListView) view.findViewById(R.id.water_settings_list);
        adapter = new WaterSettingsAdapter(getActivity());
        listView.setAdapter(adapter);
        cancel = (Button) view.findViewById(R.id.cancel);
        save = (Button) view.findViewById(R.id.save);
        cancel.setOnClickListener(new ButtonHandler());
        save.setOnClickListener(new ButtonHandler());
        setCancelable(true);
        return view;
    }

    private class ButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cancel:
                    getDialog().dismiss();
                    break;
                case R.id.save:
                    break;
            }

        }
    }
}

