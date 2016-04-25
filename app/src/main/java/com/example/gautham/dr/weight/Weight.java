package com.example.gautham.dr.weight;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gautham.dr.R;
import com.example.gautham.dr.database.WaterDatabase;
import com.example.gautham.dr.database.WaterDbProvider;
import com.example.gautham.dr.main.ContentMainFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Weight extends DialogFragment implements OnClickListener {

    EditText enter_weight;
    TextView unit;
    Button choose_unit, cancel, ok;
    View v;
    String prefs_unit,weight_prefs;
    public static String weight_flie = "weight_unit";
    public static String weight = "weight";
    SharedPreferences sharedDataWeightUnit, sharedDataWeight;

    private void init() {
        // TODO Auto-generated method stub
        enter_weight = (EditText) v.findViewById(R.id.weight);
        unit = (TextView) v.findViewById(R.id.unit);
        choose_unit = (Button) v.findViewById(R.id.choose_unit);
        ok = (Button) v.findViewById(R.id.ok);
        cancel = (Button) v.findViewById(R.id.cancel);
        enter_weight.setTextColor(getResources().getColor(R.color.colorBlack));

        sharedDataWeightUnit = getActivity().getSharedPreferences(weight_flie, 0);
        sharedDataWeight = getActivity().getSharedPreferences(weight, 0);
        prefs_unit = sharedDataWeightUnit.getString("weight_unit", "Kg");
        weight_prefs = sharedDataWeight.getString("weight", 0 + "");
        unit.setText(prefs_unit);
        choose_unit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        v = inflater.inflate(R.layout.weight, container, false);
        TextView title = (TextView) v.findViewById(R.id.dialog_tite);
        title.setText("Update your weight");
        init();
        enter_weight.setContentDescription(weight_prefs);
        setCancelable(true);
        return v;
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.cancel:
                getDialog().dismiss();
                break;
            case R.id.choose_unit:
                FragmentManager manager = getFragmentManager();
                ChooseUnit unit = new ChooseUnit();
                unit.show(manager, "Dialog1");
                getDialog().dismiss();
                break;
            case R.id.ok:
                String data = enter_weight.getText().toString();
                int todaydate=Integer.parseInt(data);
                SharedPreferences.Editor editor = sharedDataWeight.edit();
                editor.putString("weight", data);
                editor.commit();
               /* Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String date = df.format(c.getTime());

                ContentValues values=new ContentValues();
                values.clear();
                values.put(WaterDatabase.KEY_WEIGHT,data);
                values.put(WaterDatabase.KEY_DATE, todaydate);
                Uri newUri=getActivity().getContentResolver().insert(WaterDbProvider.CONTENT_WEIGHT_URI, values);
                Log.e("WEIGHT",newUri+"");*/

                Fragment fragment = new ContentMainFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();

                getDialog().dismiss();

                Toast.makeText(getActivity(), "weight: " + weight_prefs, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
