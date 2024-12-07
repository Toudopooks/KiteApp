package com.example.kiteapp.Vista;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kiteapp.Modelo.dbManager;
import com.example.kiteapp.Modelo.historialOrdenes;
import com.example.kiteapp.R;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;



public class historialActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private historialAdapter adapter;
    private ArrayList<historialOrdenes> historialList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);  // Make sure to set the correct layout for this activity

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewOrderHistory);
        dbManager dbManager =new dbManager(getApplicationContext());
        // Initialize the list
        historialList = dbManager.getHistory();

        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));  // Use a LinearLayoutManager or GridLayoutManager
        adapter = new historialAdapter(this, historialList);
        recyclerView.setAdapter(adapter);
    }

}
