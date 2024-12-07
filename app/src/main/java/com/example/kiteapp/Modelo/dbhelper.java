package com.example.kiteapp.Modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbhelper extends SQLiteOpenHelper {

    // Database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cart.db";

    // Table and columns names
    public static final String TABLE_CART = "cart";
    public static final String COLUMN_PRODUCT_NAME = "nombre";
    public static final String COLUMN_PRICE = "precio";
    public static final String COLUMN_QUANTITY = "cantidad";
    public static final String COLUMN_URL = "url";

    public static final String TABLE_HISTORY = "history";
    public static final String COLUMN_PRODUCTOS = "productos";
    public static final String COLUMN_TOTALPRICE = "precioTotal";

    // Constructor
    public dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + COLUMN_PRODUCT_NAME + " TEXT,"
                + COLUMN_PRICE + " TEXT,"
                + COLUMN_QUANTITY + " TEXT,"
                + COLUMN_URL + " TEXT" + ")";

        String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + COLUMN_PRODUCTOS + " TEXT,"
                + COLUMN_TOTALPRICE + " TEXT" + ")";

        db.execSQL(CREATE_CART_TABLE);
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        // Create tables again
        onCreate(db);
    }
}
