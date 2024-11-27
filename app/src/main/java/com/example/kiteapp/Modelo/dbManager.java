package com.example.kiteapp.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class dbManager {
    private dbhelper dbHelper;

    public dbManager(Context context) {
        dbHelper = new dbhelper(context);
    }

    public void addProductToCart(String productId, String productName, int quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(dbhelper.COLUMN_PRODUCT_ID, productId);
            values.put(dbhelper.COLUMN_PRODUCT_NAME, productName);
            values.put(dbhelper.COLUMN_QUANTITY, quantity);

            db.insertOrThrow(dbhelper.TABLE_CART, null, values);
        } catch (Exception e) {
            Log.e("dbManager", "Error adding product to cart", e);
        } finally {
            db.close();
        }
    }

    public ArrayList<Producto> getCartItems() {
        ArrayList<Producto> items = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(dbhelper.TABLE_CART, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            items.add(new Producto(
                    cursor.getString(cursor.getColumnIndexOrThrow(dbhelper.COLUMN_PRODUCT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(dbhelper.COLUMN_PRODUCT_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(dbhelper.COLUMN_QUANTITY))
            ));
        }
        cursor.close();
        db.close();
        return items;
    }

    public void removeProductFromCart(String productId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(dbhelper.TABLE_CART, dbhelper.COLUMN_PRODUCT_ID + "=?", new String[]{productId});
        db.close();
    }

    public void moveCartToOrderHistory() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            db.execSQL("INSERT INTO " + dbhelper.TABLE_ORDER_HISTORY +
                    " SELECT * FROM " + dbhelper.TABLE_CART);
            db.execSQL("DELETE FROM " + dbhelper.TABLE_CART);

        }catch(Exception e){
            Log.e("dbManager", "Error moving cart to order history ", e);
        }finally{
            db.close();
        }
    }

    public ArrayList<Producto> getOrderHistory() {
        ArrayList<Producto> history = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(dbhelper.TABLE_ORDER_HISTORY, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            history.add(new Producto(
                    cursor.getString(cursor.getColumnIndexOrThrow(dbhelper.COLUMN_PRODUCT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(dbhelper.COLUMN_PRODUCT_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(dbhelper.COLUMN_QUANTITY))
            ));
        }
        cursor.close();
        db.close();
        return history;
    }
}
