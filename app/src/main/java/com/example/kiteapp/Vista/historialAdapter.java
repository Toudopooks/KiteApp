package com.example.kiteapp.Vista;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kiteapp.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kiteapp.Modelo.Producto;
import com.example.kiteapp.R;
import java.util.List;

public class historialAdapter extends RecyclerView.Adapter<historialAdapter.OrderHistoryViewHolder> {

    private final List<Producto> orderHistory;

    public historialAdapter(List<Producto> orderHistory) {
        this.orderHistory = orderHistory;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historialitem, parent, false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        Producto producto = orderHistory.get(position);
        holder.textViewProductName.setText(producto.getProductName());
        holder.textViewQuantity.setText("Cantidad: " + producto.getQuantity());
    }

    @Override
    public int getItemCount() {
        return orderHistory.size();
    }

    static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProductName, textViewDate, textViewQuantity;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
        }
    }
}