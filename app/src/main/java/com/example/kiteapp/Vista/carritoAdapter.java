package com.example.kiteapp.Vista;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kiteapp.Modelo.Producto;
import com.example.kiteapp.Modelo.carritoItem;
import com.example.kiteapp.Modelo.dbManager;
import com.example.kiteapp.R;
import java.util.List;

public class carritoAdapter extends RecyclerView.Adapter<carritoAdapter.CartViewHolder> {

    private List<carritoItem> cartItems;
    private Context context;
    private OnCartUpdatedListener cartUpdatedListener;

    public interface OnCartUpdatedListener {
        void onCartUpdated();
    }

    public carritoAdapter(Context context, List<carritoItem> cartItems, OnCartUpdatedListener cartUpdatedListener) {
        this.context = context;
        this.cartItems = cartItems;
        this.cartUpdatedListener = cartUpdatedListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.carritoitem, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        carritoItem producto = cartItems.get(position);
        holder.textNombre.setText(producto.getNombre());
        holder.textCantidad.setText(producto.getCantidad());
        holder.textPrecio.setText("$" + producto.getPrecio());

        if (producto == null) {
            throw new NullPointerException("El producto es nulo en la posición: " + position);
        }

        // Debug: Verifica las referencias del ViewHolder
        if (holder.textNombre == null || holder.textCantidad == null || holder.textPrecio == null) {
            throw new NullPointerException("Las vistas del ViewHolder no están inicializadas correctamente.");
        }

        // Configuración de los datos
        holder.textNombre.setText(producto.getNombre());
        holder.textCantidad.setText(producto.getCantidad());
        holder.textPrecio.setText("$" + producto.getPrecio());

        try {
            double precio = Double.parseDouble(producto.getPrecio());
            int cantidad = Integer.parseInt(producto.getCantidad());
            double precioTotal = precio * cantidad;

            holder.textPrecioTotal.setText(String.format("$ %.2f", precioTotal));
        } catch (NumberFormatException e) {
            holder.textPrecioTotal.setText("Error en datos: " + e.getMessage());
        }

        holder.btnDelete.setOnClickListener(v -> {
            dbManager dbManager = new dbManager(context);
            dbManager.deleteProductFromCart(producto.getNombre());
            cartItems.remove(position); // Remove from the list
            notifyItemRemoved(position); // Update RecyclerView
            notifyItemRangeChanged(position, cartItems.size());
            if (cartUpdatedListener != null) {
                cartUpdatedListener.onCartUpdated(); // Notify the activity
            }
        });

        String link= producto.getUrl();

        Glide.with(context)
                .load(link) // La URL obtenida de Firestore
                .placeholder(R.drawable.klogo) // Opcional: imagen de carga
                .error(R.drawable.ic_launcher_background)         // Opcional: imagen en caso de error
                .into(holder.imgURL); // El ImageView donde se mostrará la imagen

    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre,textPrecio,textCantidad, textPrecioTotal;
        ImageView imgURL;
        Button btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.nombre);
            textPrecio = itemView.findViewById(R.id.precio);
            textCantidad = itemView.findViewById(R.id.cantidad);
            textPrecioTotal = itemView.findViewById(R.id.preciototal);
            imgURL = itemView.findViewById(R.id.img);
            btnDelete = itemView.findViewById(R.id.elimbtn);
        }
    }



    @Override
    public int getItemCount() {
        return cartItems.size();
    }



}