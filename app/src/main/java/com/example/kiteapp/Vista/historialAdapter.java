package com.example.kiteapp.Vista;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.kiteapp.Modelo.carritoItem;
import com.example.kiteapp.Modelo.dbManager;
import com.example.kiteapp.Modelo.historialOrdenes;
import com.example.kiteapp.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kiteapp.Modelo.Producto;
import com.example.kiteapp.R;
import java.util.List;

public class historialAdapter extends RecyclerView.Adapter<historialAdapter.OrderHistoryViewHolder> {
    private List<historialOrdenes> historialOrdenes;
    private Context context;

    public historialAdapter(Context context, List<historialOrdenes> historialOrdenes) {
        this.context = context;
        this.historialOrdenes = historialOrdenes;
    }


    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.historialitem, parent, false);
        return new historialAdapter.OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        historialOrdenes producto = historialOrdenes.get(position);

        if (producto == null) {
            throw new NullPointerException("El producto es nulo en la posición: " + position);
        }

        if (holder.textProductos == null || holder.textPrecioTotal == null) {
            throw new NullPointerException("Las vistas del ViewHolder no están inicializadas correctamente.");
        }

        holder.textProductos.setText("Productos: \n"+producto.getProductos());
        holder.textPrecioTotal.setText("$ "+producto.getPreciototal());


    }

    static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView textProductos,textPrecioTotal;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textProductos = itemView.findViewById(R.id.productos);
            textPrecioTotal = itemView.findViewById(R.id.preciototal);
        }
    }

    @Override
    public int getItemCount() {
        return historialOrdenes.size();
    }

}