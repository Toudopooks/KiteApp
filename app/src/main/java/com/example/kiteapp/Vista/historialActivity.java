package com.example.kiteapp.Vista;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kiteapp.R;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kiteapp.Modelo.Producto;
import com.example.kiteapp.Modelo.dbManager;

import java.util.ArrayList;

public class historialActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOrderHistory;
    private historialAdapter orderHistoryAdapter;
    private dbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        recyclerViewOrderHistory = findViewById(R.id.recyclerViewOrderHistory);
        dbManager = new dbManager(this);

        recyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Producto> orderHistory = dbManager.getOrderHistory();
        orderHistoryAdapter = new historialAdapter(orderHistory);
        recyclerViewOrderHistory.setAdapter(orderHistoryAdapter);
    }
}
