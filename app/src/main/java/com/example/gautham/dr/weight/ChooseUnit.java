package com.example.gautham.dr.weight;

import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gautham.dr.R;

public class ChooseUnit extends DialogFragment implements OnCheckedChangeListener, OnClickListener {

    String unit1, unit2;
    String unit_lbs = "UNIT_LBS", unit_quantity = "UNIT_QUANTITY";
    View v;
    boolean lbs_state, quantity_state;
    RadioGroup rg_weight_unit, rg_capacity_unit;
    RadioButton lbs, kg, oz, ml;
    Button ok;
    public static String weight_flie = "weight_unit";
    public static String quantity_flie = "quantity_unit";
    SharedPreferences sharedData_weight, sharedDataQuantity;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        v = inflater.inflate(R.layout.chooseunit, container, false);
        TextView title = (TextView) v.findViewById(R.id.dialog_tite1);
        title.setText("Choose unit");
        sharedData_weight = getActivity().getSharedPreferences(weight_flie, Context.MODE_PRIVATE);
        sharedDataQuantity = getActivity().getSharedPreferences(quantity_flie, Context.MODE_PRIVATE);
        editor = sharedData_weight.edit();
        editor1 = sharedDataQuantity.edit();
        init();
        lbs_state = sharedData_weight.getBoolean(unit_lbs, false);
        Log.e(unit_lbs, lbs_state + "");
        lbs.setChecked(lbs_state);
        kg.setChecked(!lbs_state);
        quantity_state = sharedDataQuantity.getBoolean(unit_quantity, false);
        Log.e(unit_quantity, quantity_state + "");
        oz.setChecked(quantity_state);
        ml.setChecked(!quantity_state);

        setCancelable(true);
        return v;
    }

    private void init() {
        // TODO Auto-generated method stub
        lbs = (RadioButton) v.findViewById(R.id.lbs);
        kg = (RadioButton) v.findViewById(R.id.kg);
        oz = (RadioButton) v.findViewById(R.id.oz);
        ml = (RadioButton) v.findViewById(R.id.ml);
        rg_weight_unit = (RadioGroup) v.findViewById(R.id.rg_weight_unit);
        rg_weight_unit.setOnCheckedChangeListener(this);
        rg_capacity_unit = (RadioGroup) v.findViewById(R.id.rg_capacity_unit);
        rg_capacity_unit.setOnCheckedChangeListener(this);
        ok = (Button) v.findViewById(R.id.ok);
        ok.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.lbs:
                unit1 = lbs.getText().toString();
                editor.putString("weight_unit", unit1);
                editor.putBoolean(unit_lbs, lbs.isChecked());
                break;
            case R.id.kg:
                unit2 = kg.getText().toString();
                editor.putBoolean(unit_lbs, lbs.isChecked());
                editor.putString("weight_unit", unit2);
                break;
            case R.id.oz:
                unit1 = oz.getText().toString();
                editor1.putString("quantity_unit", unit1);
                editor1.putBoolean(unit_quantity, oz.isChecked());
                break;
            case R.id.ml:
                unit2 = ml.getText().toString();
                editor1.putString("quantity_unit", unit2);
                editor1.putBoolean(unit_quantity, oz.isChecked());
                break;
        }
    }

    @Override
    public void onClick(View v) {
        editor.commit();
        editor1.commit();
        getDialog().dismiss();
        Weight weight = new Weight();
        weight.show(getFragmentManager(), "");
        Toast.makeText(getActivity(), "Units Saved", Toast.LENGTH_SHORT).show();
    }

}
