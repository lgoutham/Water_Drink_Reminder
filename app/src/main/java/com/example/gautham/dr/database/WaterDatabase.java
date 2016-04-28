package com.example.gautham.dr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by GAUTHAM on 4/1/2016.
 */
public class WaterDatabase extends SQLiteOpenHelper {


    // Database Version
    static final int DATABASE_VERSION = 1;

    // Database Name
    static final String DATABASE_NAME = "WATER_DATABASE";

    // Contacts table name
    static final String WATER_TABLE = "WaterTable";

    //weight table name
    static final String WEIGHT_TABLE = "WeightTable";

    // Contacts Table Columns names
    public static final String KEY_ID = "_id";
    public static final String KEY_POS = "position";
    public static final String KEY_DATE = "Current_date";
    public static final String KEY_TIME = "Current_time";

    //    Weight table columns
    public static final String KEY_WEIGHT = "weight";

    public WaterDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table " + WATER_TABLE + " ( "
                + KEY_ID + " integer primary key , "
                + KEY_POS + " text, "
                + KEY_DATE + " text, "
                + KEY_TIME + " text "
                + " ) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WATER_TABLE);
        onCreate(db);
    }
}
