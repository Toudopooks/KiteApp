package com.example.kiteapp.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kiteapp.Modelo.Producto;
import com.example.kiteapp.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class mainPage extends AppCompatActivity {

    private ShapeableImageView profilePicture;
    private Button accbtn;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        db = FirebaseFirestore.getInstance();
        db.collection("bebestible")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Producto> productos = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Convert each document to a Producto object
                            Producto producto = document.toObject(Producto.class);
                            productos.add(producto);  // Add product to the list
                        }

                        // Create and assign the adapter with the list of products
                        bebidaAdapter adapter = new bebidaAdapter(mainPage.this, productos);
                        RecyclerView recyclerView = findViewById(R.id.rvProductos);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.d("mainPage", "Error getting documents: ", task.getException());
                    }
                });
        db.collection("comestible")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Producto> productos = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Convert each document to a Producto object
                            Producto producto = document.toObject(Producto.class);
                            productos.add(producto);  // Add product to the list
                        }

                        // Create and assign the adapter with the list of products
                        bebidaAdapter adapter = new bebidaAdapter(mainPage.this, productos);
                        RecyclerView recyclerView = findViewById(R.id.rvComidas);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setAdapter(adapter);  // Set the adapter with the list
                    } else {
                        Log.d("mainPage", "Error getting documents: ", task.getException());
                    }
                });
        db.collection("comestible")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Producto> productos = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Convert each document to a Producto object
                            Producto producto = document.toObject(Producto.class);
                            productos.add(producto);  // Add product to the list
                        }

                        // Create and assign the adapter with the list of products
                        bebidaAdapter adapter = new bebidaAdapter(mainPage.this, productos);
                        RecyclerView recyclerView = findViewById(R.id.rvEvent);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setAdapter(adapter);  // Set the adapter with the list
                    } else {
                        Log.d("mainPage", "Error getting documents: ", task.getException());
                    }
                });

        Button goToCarritoButton = findViewById(R.id.cartButton);

        goToCarritoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para navegar a CarritoActivity
                Intent intent = new Intent(mainPage.this, Carrito.class);
                startActivity(intent);  // Iniciar la actividad
            }
        });

        profilePicture = findViewById(R.id.PFP);
        accbtn = findViewById(R.id.btnPFP);
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        RecyclerView recyclerView = findViewById(R.id.rvProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (currentUser != null) {
            Glide.with(this).load(currentUser.getPhotoUrl()).into(profilePicture);
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            redirectToLogin();
        }

        accbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainPage.this, Cuenta.class);
                startActivity(intent);
            }
        });
    }

    private void redirectToLogin() {
        Intent intent = new Intent(mainPage.this, Login.class);
        startActivity(intent);
        finish();  // Close Cuenta activity
    }

}