package com.example.kiteapp.Vista;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kiteapp.R;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import com.example.kiteapp.Modelo.dbManager;
import com.example.kiteapp.Modelo.Producto;
import java.util.ArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Carrito extends AppCompatActivity {

    private RecyclerView recyclerViewCart;
    private Button buttonEmptyCart, buttonHistorial;
    private carritoAdapter cartAdapter;
    private dbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        buttonHistorial = findViewById(R.id.buttonHistorial);
        buttonEmptyCart = findViewById(R.id.buttonEmptyCart);

        dbManager = new dbManager(this);

        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Producto> cartItems = dbManager.getCartItems();
        cartAdapter = new carritoAdapter(cartItems);
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
        } else {
            cartAdapter = new carritoAdapter(cartItems);
            recyclerViewCart.setAdapter(cartAdapter);
        }
        buttonHistorial.setOnClickListener(view -> {
            Intent historialIntent = new Intent(Carrito.this, historialActivity.class);
            startActivity(historialIntent);
            finish();
        });

        buttonEmptyCart.setOnClickListener(view -> {
            dbManager.moveCartToOrderHistory();
            cartAdapter.clearItems();
            cartAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Carrito vacío. Orden guardada.", Toast.LENGTH_SHORT).show();
        });
    }
}
