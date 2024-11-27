package com.example.kiteapp.Modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "kiteapp.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_CART = "cart";
    public static final String TABLE_ORDER_HISTORY = "order_history";

    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_QUANTITY = "quantity";

    public dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CART + " (" +
                COLUMN_PRODUCT_ID + " TEXT PRIMARY KEY, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_ORDER_HISTORY + " (" +
                COLUMN_PRODUCT_ID + " TEXT PRIMARY KEY, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_HISTORY);
        onCreate(db);
    }
}
