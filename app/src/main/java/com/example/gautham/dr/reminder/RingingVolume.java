package com.example.gautham.dr.reminder;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.gautham.dr.R;

/**
 * Created by GAUTHAM on 4/22/2016.
 */
public class RingingVolume extends DialogFragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    SeekBar seekBar;
    Button cancel,ok;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ringing_volume, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        seekBar= (SeekBar) view.findViewById(R.id.volume_seekbar);
        cancel= (Button) view.findViewById(R.id.volume_cancel);
        ok= (Button) view.findViewById(R.id.volume_ok);
        setCancelable(true);
        seekBar.setOnSeekBarChangeListener(this);
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                getDialog().dismiss();
                break;
            case R.id.ok:

                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
