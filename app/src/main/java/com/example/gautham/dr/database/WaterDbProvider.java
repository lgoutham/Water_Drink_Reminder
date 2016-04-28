package com.example.gautham.dr.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

import static com.example.gautham.dr.database.WaterDatabase.WATER_TABLE;
import static com.example.gautham.dr.database.WaterDatabase.WEIGHT_TABLE;

/**
 * Created by GAUTHAM on 3/31/2016.
 */
public class WaterDbProvider extends ContentProvider {

    Context context;
    SQLiteDatabase sqLiteDatabase;
    WaterDatabase waterDatabase;

    private static HashMap<String, String> WaterMap;
//    private static HashMap<String, String> WeightMap;

    public static final String PROVIDER_NAME = "com.example.gautham.dr.database.WaterProvider";

    public static final String URL = "content://" + PROVIDER_NAME + "/WaterTable";
//    public static final String URL_WEIGHT = "content://" + PROVIDER_NAME + "/WeightTable";

    public static final Uri CONTENT_URI = Uri.parse(URL);
//    public static final Uri CONTENT_WEIGHT_URI = Uri.parse(URL_WEIGHT);

    private static final int Water = 1;
    private static final int Water_Id = 2;
//    private static final int Weight = 3;
//    private static final int Weight_Id = 4;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "WaterTable", Water);
        uriMatcher.addURI(PROVIDER_NAME, "WaterTable/#", Water);
//        uriMatcher.addURI(PROVIDER_NAME, "WeightTable", Weight);
//        uriMatcher.addURI(PROVIDER_NAME, "WeightTable/#", Weight);
    }

    public WaterDbProvider() {
    }


    @Override
    public boolean onCreate() {
        context = getContext();
        waterDatabase = new WaterDatabase(context);
        sqLiteDatabase = waterDatabase.getWritableDatabase();
        if (sqLiteDatabase == null)
            return false;
        else
            return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        int uriType = uriMatcher.match(uri);
        qb.setTables(WATER_TABLE);
//        qb.setTables(WEIGHT_TABLE);
        switch (uriType) {
            case Water:
                qb.setProjectionMap(WaterMap);
                break;
            case Water_Id:
                qb.appendWhere(WaterDatabase.KEY_ID + "=" + uri.getLastPathSegment());
                break;
           /* case Weight:
                qb.setProjectionMap(WeightMap);
                break;
            case Weight_Id:
                qb.appendWhere(WaterDatabase.KEY_ID + "=" + uri.getLastPathSegment());
                break;*/
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            // No sorting-> sort on names by default
            sortOrder = WaterDatabase.KEY_ID;
        }
        Cursor cursor = qb.query(sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case Water:
                return "vnd.android.cursor.dir/WaterTable";
            case Water_Id:
                return "vnd.android.cursor.item/WeightTable/#";
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = uriMatcher.match(uri);
        Uri _uri = null;
        switch (uriType) {
            case Water:
                long rowID = sqLiteDatabase.insert(WATER_TABLE, null, values);
                if (rowID > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            /*case Weight:
                long rowID1 = sqLiteDatabase.insert(WEIGHT_TABLE, null, values);
                if (rowID1 > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_WEIGHT_URI, rowID1);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;*/
            default:
                throw new SQLException("Error inserting into table: " + WATER_TABLE);
        }
        return _uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case Water:
                count = sqLiteDatabase.delete(WATER_TABLE, selection, selectionArgs);
                break;
            case Water_Id:
                String id = uri.getLastPathSegment();    //gets the id
                count = sqLiteDatabase.delete(WATER_TABLE, WaterDatabase.KEY_ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case Water:
                count = sqLiteDatabase.update(WATER_TABLE, values, selection, selectionArgs);
                break;
            case Water_Id:
                count = sqLiteDatabase.update(WATER_TABLE, values, WaterDatabase.KEY_ID +
                        " = " + uri.getLastPathSegment() +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
