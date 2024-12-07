package com.example.kiteapp.Vista;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.kiteapp.Modelo.Producto;
import com.example.kiteapp.R;

import java.util.List;

public class bebidaAdapter extends RecyclerView.Adapter<bebidaAdapter.ProductoViewHolder> {
    private List<Producto> productoList;
    private Context context;

    public bebidaAdapter(Context context, List<Producto> productoList) {
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
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, anyProduct.class);
            intent.putExtra("productoId", producto.getId());
            intent.putExtra("collection",producto.getCategoria());
            context.startActivity(intent);
        });

        Glide.with(context)
                .load(producto.getUrl()) // La URL obtenida de Firestore
                .placeholder(R.drawable.klogo) // Opcional: imagen de carga
                .error(R.drawable.ic_launcher_background)         // Opcional: imagen en caso de error
                .into(holder.productoImage); // El ImageView donde se mostrará la imagen

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
        TextView productoName,productoId;
        ImageView productoImage;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            productoName = itemView.findViewById(R.id.productoName);
            productoId = itemView.findViewById(R.id.productid);
            productoImage = itemView.findViewById(R.id.productoImage);
        }
    }
}

