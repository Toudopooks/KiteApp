package com.example.kiteapp.Vista;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kiteapp.Modelo.Producto;
import com.example.kiteapp.R;
import java.util.List;

public class carritoAdapter extends RecyclerView.Adapter<carritoAdapter.CartViewHolder> {

    private final List<Producto> cartItems;

    public carritoAdapter(List<Producto> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carritoitem, parent, false); // Inflate the correct layout
        return new CartViewHolder(view);
    }

    public void clearItems() {
        cartItems.clear();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProductName, textViewQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Producto producto = cartItems.get(position);

        if (producto == null || holder.textViewProductName == null || holder.textViewQuantity == null) {
            throw new NullPointerException("Producto o Views no inicializados correctamente");
        }

        holder.textViewProductName.setText(producto.getProductName());
        holder.textViewQuantity.setText("Cantidad: " + producto.getQuantity());
    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }



}
