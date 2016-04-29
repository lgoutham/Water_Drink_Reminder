package com.example.gautham.dr.main;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gautham.dr.R;
import com.example.gautham.dr.database.Information;
import com.example.gautham.dr.database.WaterBottlesData;
import com.example.gautham.dr.database.WaterDatabase;
import com.example.gautham.dr.database.WaterDbProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by GAUTHAM on 4/12/2016.
 */

public class SelectGlass extends DialogFragment {

    RecyclerView recyclerView;
    SelectGlassAdapter adapter;
    Dialog dialog = null;
    static int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_glass, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.glasses);
        adapter = new SelectGlassAdapter(getActivity(), WaterBottlesData.getData());
        LinearLayoutManager linearLayoutManagerH = new LinearLayoutManager(getActivity());
        linearLayoutManagerH.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManagerH);
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.gravity = Gravity.BOTTOM | Gravity.CENTER;
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.getWindow().setAttributes(params);
        }
    }

    public class SelectGlassAdapter extends RecyclerView.Adapter<SelectGlassAdapter.MyViewHolder> {

        Context context;
        LayoutInflater inflater;
        ArrayList<Information> data;

        public SelectGlassAdapter(Context context, ArrayList<Information> data) {
            this.context = context;
            this.data = data;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.glass_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.imageView.setImageResource(data.get(position).imageId);
            holder.textView.setText(data.get(position).title+"ml");
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;

            public MyViewHolder(final View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), getPosition() + "", Toast.LENGTH_SHORT).show();

                        ContentMainFragment.mp.start();

                        //Database code
                        WaterDatabase wdb = new WaterDatabase(context);
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
                        String time = sdf.format(c.getTime());
                        String date = df.format(c.getTime());
                        position = getPosition();
                        ContentMainFragment.updateProgress(position);
                        ContentValues values = new ContentValues();
                        values.clear();
                        values.put(WaterDatabase.KEY_POS, position);
                        values.put(WaterDatabase.KEY_DATE, date);
                        values.put(WaterDatabase.KEY_TIME, time);
                        Uri uri = WaterDbProvider.CONTENT_URI;
                        Uri newUri = context.getContentResolver().insert(uri, values);
                        Log.e("DATA", newUri + "");
                        Fragment fragment = new BottleGrid();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.bottle_container, fragment).addToBackStack(null).commit();
                        dialog.dismiss();

                    }
                });
                imageView = (ImageView) itemView.findViewById(R.id.image_row);
                textView = (TextView) itemView.findViewById(R.id.title_row);
            }
        }
    }
}