package com.example.kiteapp.Vista;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kiteapp.Modelo.dbManager;
import com.example.kiteapp.R;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText editTextProductName, editTextProductQuantity;
    private Button buttonAddToCart, buttonGoToCart;
    private dbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductQuantity = findViewById(R.id.editTextProductQuantity);
        buttonAddToCart = findViewById(R.id.buttonAddToCart);
        buttonGoToCart = findViewById(R.id.buttonGoToCart);

        dbManager = new dbManager(this);

        buttonAddToCart.setOnClickListener(view -> {
            String name = editTextProductName.getText().toString().trim();
            String quantityStr = editTextProductQuantity.getText().toString().trim();

            if (name.isEmpty() || quantityStr.isEmpty()) {
                Toast.makeText(this, "Por favor, completa los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(quantityStr);
            dbManager.addProductToCart(String.valueOf(System.currentTimeMillis()), name, quantity);

            Toast.makeText(this, "Producto agregado al carrito.", Toast.LENGTH_SHORT).show();
            editTextProductName.setText("");
            editTextProductQuantity.setText("");
        });

        buttonGoToCart.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Carrito.class);
            startActivity(intent);
        });
    }
}
