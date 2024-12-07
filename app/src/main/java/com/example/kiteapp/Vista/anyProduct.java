package com.example.kiteapp.Vista;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.kiteapp.Modelo.dbManager;
import com.example.kiteapp.Modelo.dbhelper;
import com.example.kiteapp.R;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class anyProduct extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView Titulo, Ingredientes, Precio, Cantidad;;
    private ImageView imagen;
    private String cURL, cNombre, cPrecio,cCantidad;
    EditText numberPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_any_product);

        db = FirebaseFirestore.getInstance();

        Titulo = findViewById(R.id.titulo);
        Ingredientes = findViewById(R.id.ingredientes);
        Precio = findViewById(R.id.precio);
        numberPicker = findViewById(R.id.cantidad);
        imagen = findViewById(R.id.imgg);
        cNombre="";
        cPrecio="";
        cCantidad="";
        cURL = "";

        String idProducto = getIntent().getStringExtra("productoId");
        String collection = getIntent().getStringExtra("collection");
        if (idProducto != null) {
            cargarDatosDesdeFirestore(idProducto,collection);
        } else {
            Toast.makeText(this, "Error: ID del producto no encontrado", Toast.LENGTH_SHORT).show();
        }

        Button btnAgregarCarrito = findViewById(R.id.miauButton);

        btnAgregarCarrito.setOnClickListener(v -> {
            dbManager dbMana = new dbManager(this);

            cCantidad = numberPicker.getText().toString();

            dbMana.addProductToCart(cNombre, cPrecio, cCantidad, cURL);

            Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show();

            finish();
        });

    }

    private void cargarDatosDesdeFirestore(String idProducto,String collection) {
            db.collection(collection)
                    .whereEqualTo("id", idProducto)  // Busca el campo 'id' que coincida con 'idProducto'
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Si se encuentra al menos un documento que coincida
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);  // Obtén el primer documento
                            String titulo = documentSnapshot.getString("nombre");
                            String descripcion = documentSnapshot.getString("ingredientes");
                            Object precioObject = documentSnapshot.get("precio");
                            String precio = "Precio no disponible";

                            if (precioObject != null) {
                                if (precioObject instanceof Double) {
                                    precio = String.format("%.2f", (Double) precioObject); // Si es un Double, conviértelo a String
                                } else if (precioObject instanceof Long) {
                                    precio = String.valueOf(precioObject); // Si es un Long, convierte directamente a String
                                } else if (precioObject instanceof String) {
                                    precio = (String) precioObject; // Si es un String, simplemente usa ese valor
                                }
                            }
                            String url = documentSnapshot.getString("url");


                            // Muestra los datos en el layout
                            Titulo.setText(titulo);
                            Ingredientes.setText(descripcion);
                            Precio.setText("Precio: "+precio);
                            Glide.with(this).load(url).into(imagen);

                            cNombre = titulo;
                            cPrecio = precio;
                            cURL = url;

                        } else {
                            Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error al cargar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
    }
}
