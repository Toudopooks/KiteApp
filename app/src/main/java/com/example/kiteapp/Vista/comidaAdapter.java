package com.example.kiteapp.Vista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kiteapp.Modelo.Producto;
import com.example.kiteapp.R;

import java.util.List;

public class comidaAdapter extends RecyclerView.Adapter<comidaAdapter.ProductoViewHolder> {
    private List<Producto> productoList;
    private Context context;

    public comidaAdapter(Context context, List<Producto> productoList) {
        this.context = context;
        this.productoList = productoList;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.productoitem, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productoList.get(position);  // Get the product at the current position

        // Bind the product data to the views
        holder.productoName.setText(producto.getNombre());

        // Load the image using Glide (if applicable)
        if (producto.getUrl() != null && !producto.getUrl().isEmpty()) {
            Glide.with(context).load(producto.getUrl()).into(holder.productoImage);
        } else {
            holder.productoImage.setImageResource(R.drawable.klogo);  // Set default image
        }
    }


    @Override
    public int getItemCount() {
        return productoList.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView productoName;
        ImageView productoImage;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            productoName = itemView.findViewById(R.id.productoName);
            productoImage = itemView.findViewById(R.id.productoImage);
        }
    }
}

