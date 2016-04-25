package com.example.gautham.dr.database;


import com.example.gautham.dr.R;

import java.util.ArrayList;

/**
 * Created by GAUTHAM on 3/31/2016.
 */
public class WaterBottlesData {
    public static ArrayList<Information> getData() {

        ArrayList<Information> data = new ArrayList<>();
         int[] images = {
                R.drawable.drink_1, R.drawable.drink_2, R.drawable.drink_3,
                R.drawable.drink_4, R.drawable.drink_5, R.drawable.drink_6,
                R.drawable.drink_7, R.drawable.drink_8
        };

         int[] ml = {100, 150,200, 400, 500, 600,700, 800};
        for (int i = 0; i < images.length; i++) {
            Information current = new Information();
            current.imageId = images[i];
            current.title = String.valueOf(ml[i]);
            data.add(current);
        }
        return data;
    }
}