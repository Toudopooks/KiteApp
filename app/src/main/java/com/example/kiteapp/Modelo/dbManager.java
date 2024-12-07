package com.example.kiteapp.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
public class dbManager {
    private dbhelper dbHelper;

    // Constructor donde se inicializa la instancia de dbHelper
    public dbManager(Context context) {
        dbHelper = new dbhelper(context); // Se corrigió 'dbhelper' a 'dbHelper'
    }

    // Agregar producto al carrito
    public void addProductToCart(String nombre, String precio, String cantidad, String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(dbHelper.COLUMN_PRODUCT_NAME, nombre);
            values.put(dbHelper.COLUMN_PRICE, precio);
            values.put(dbHelper.COLUMN_QUANTITY, cantidad);
            values.put(dbHelper.COLUMN_URL, url);

            long rowId = db.insertOrThrow(dbHelper.TABLE_CART, null, values);
            Log.d("addProductToCart", "Row ID of inserted product: " + rowId);

        } catch (Exception e) {
            Log.e("addProductToCart", "Error adding product to cart", e);
        } finally {
            db.close();
        }
    }

    // Obtener los productos del carrito
    public ArrayList<carritoItem> getCartItems() {
        ArrayList<carritoItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(dbHelper.TABLE_CART, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Verificar si las columnas existen para evitar un error de índice negativo
                    int productNameIndex = cursor.getColumnIndex(dbHelper.COLUMN_PRODUCT_NAME);
                    int priceIndex = cursor.getColumnIndex(dbHelper.COLUMN_PRICE);
                    int quantityIndex = cursor.getColumnIndex(dbHelper.COLUMN_QUANTITY);
                    int urlIndex = cursor.getColumnIndex(dbHelper.COLUMN_URL);

                    if (productNameIndex != -1 && priceIndex != -1 && quantityIndex != -1 && urlIndex != -1) {
                        String nombre = cursor.getString(productNameIndex);
                        String precio = cursor.getString(priceIndex);
                        String cantidad = cursor.getString(quantityIndex);
                        String url = cursor.getString(urlIndex);

                        carritoItem item = new carritoItem(nombre, precio, cantidad, url);
                        cartItems.add(item);
                    } else {
                        Log.e("getCartItems", "One or more columns not found in the cursor.");
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("getCartItems", "Error fetching cart items", e);
        } finally {
            if (cursor != null) cursor.close();
        }

        return cartItems;
    }

    // Eliminar un producto del carrito
    public void deleteProductFromCart(String productName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.delete(dbHelper.TABLE_CART, dbHelper.COLUMN_PRODUCT_NAME + " = ?", new String[]{productName});
        } catch (Exception e) {
            Log.e("dbManager", "Error deleting product from cart", e);
        } finally {
            db.close();
        }
    }

    // Limpiar todos los productos del carrito
    public void clearCart() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.delete(dbHelper.TABLE_CART, null, null);
        } catch (Exception e) {
            Log.e("dbManager", "Error clearing cart", e);
        } finally {
            db.close();
        }
    }

    // Agregar productos al historial
    public void addProductsToHistory(String productos, String preciototal) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(dbHelper.COLUMN_PRODUCTOS, productos);
            values.put(dbHelper.COLUMN_TOTALPRICE, preciototal);

            long rowId = db.insertOrThrow(dbHelper.TABLE_HISTORY, null, values);
            Log.d("addProductsToHistory", "Row ID of inserted products: " + rowId);
        } catch (Exception e) {
            Log.e("addProductsToHistory", "Error adding products to history", e);
        } finally {
            db.close();
        }
    }

    // Obtener historial de productos
    public ArrayList<historialOrdenes> getHistory() {
        ArrayList<historialOrdenes> historialItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(dbHelper.TABLE_HISTORY, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Verificar si los índices de las columnas existen (no son -1)
                    int productosIndex = cursor.getColumnIndex(dbHelper.COLUMN_PRODUCTOS);
                    int precioTotalIndex = cursor.getColumnIndex(dbHelper.COLUMN_TOTALPRICE);

                    if (productosIndex != -1 && precioTotalIndex != -1) {
                        // Obtener los valores de las columnas solo si los índices son válidos
                        String productos = cursor.getString(productosIndex);
                        String preciototal = cursor.getString(precioTotalIndex);

                        historialOrdenes item = new historialOrdenes(productos, preciototal);
                        historialItems.add(item);
                    } else {
                        // Si alguna columna no existe, loggear el error
                        Log.e("getHistoryItems", "One or more columns not found in the cursor.");
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("getHistoryItems", "Error while fetching historial items", e);
        } finally {
            if (cursor != null) cursor.close();
        }

        return historialItems;
    }
}
