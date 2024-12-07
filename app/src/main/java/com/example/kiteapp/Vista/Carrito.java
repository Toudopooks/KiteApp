package com.example.kiteapp.Vista;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttException;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kiteapp.Modelo.carritoItem;
import com.example.kiteapp.R;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kiteapp.Modelo.dbManager;
import java.util.ArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ProgressBar;

public class Carrito extends AppCompatActivity {

    private RecyclerView recyclerViewCart;
    private carritoAdapter cartAdapter;
    private dbManager dbManager;
    Button btnClearCart, comprar;
    TextView preciototal;

    private MqttClient mqttClient;
    private ProgressBar progressBar;  // Declare the ProgressBar

    private String brokerUrl = "tcp://broker.hivemq.com:1883";  // Cambia esto por tu broker MQTT
    private String topic = "kiteapp/cart";

    private String calculatePrecioTotal(ArrayList<carritoItem> cartItems) {
        double total = 0.0; // Inicializar el total
        for (carritoItem item : cartItems) {
            try {
                // Convertir el precio de String a double
                double precio = Double.parseDouble(item.getPrecio());
                total += precio;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Manejar errores de conversión
            }
        }
        // Convertir el total a String
        return String.valueOf(total);
    }
    private String productosPH(ArrayList<carritoItem> cartItems) {
        StringBuilder productos = new StringBuilder();  // Usamos StringBuilder para mejorar la eficiencia

        for (carritoItem item : cartItems) {
            try {
                // Suponiendo que cada 'carritoItem' tiene un método 'getNombre' que devuelve el nombre del producto
                productos.append(item.getNombre());  // Agrega el nombre del producto al StringBuilder
                productos.append(" X ");
                productos.append(item.getCantidad());
                productos.append("\n");  // Agrega un salto de línea después de cada producto
            } catch (Exception e) {
                e.printStackTrace();  // Maneja cualquier error si es necesario
            }
        }

        // Convertir el StringBuilder a String y devolverlo
        return productos.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        // Initialize UI elements
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        btnClearCart = findViewById(R.id.btnBorrar);
        comprar = findViewById(R.id.comprar);  // Initialize comprar button
        progressBar = findViewById(R.id.progressBar);  // Initialize ProgressBar

        dbManager = new dbManager(this);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<carritoItem> cartItems = dbManager.getCartItems();

        if (cartItems == null || cartItems.isEmpty()) {
            Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
            cartItems = new ArrayList<>();
        }

        cartAdapter = new carritoAdapter(this, cartItems);
        recyclerViewCart.setAdapter(cartAdapter);

        // Clear Cart functionality
        ArrayList<carritoItem> finalCartItems = cartItems;
        btnClearCart.setOnClickListener(v -> {
            dbManager.clearCart(); // Llamada al método para borrar toda la tabla
            finalCartItems.clear(); // Limpia la lista local
            cartAdapter.notifyDataSetChanged(); // Actualiza el RecyclerView
            Toast.makeText(this, "El carrito ha sido vaciado", Toast.LENGTH_SHORT).show();
        });

        preciototal = findViewById(R.id.preciototal);
        String precioTotalString = calculatePrecioTotal(cartItems);
        String productosPh = productosPH(cartItems);



        // Mostrar el precio total en el TextView
        preciototal.setText("Total: $ " + precioTotalString);

        comprar.setOnClickListener(v -> {
            sendMessage("Compra realizada",finalCartItems,productosPh,precioTotalString);; // Enviar un mensaje indicando que se realizó una compra

        });
    }
    // Connect to MQTT broker only if not already connected
    private void connectToMqttBroker() {
        if (mqttClient == null || !mqttClient.isConnected()) {
            new Thread(() -> {
                try {
                    mqttClient = new MqttClient(brokerUrl, MqttClient.generateClientId(), null);
                    MqttConnectOptions options = new MqttConnectOptions();
                    options.setCleanSession(true);
                    mqttClient.connect(options);

                    // Update UI to indicate connection success
                    runOnUiThread(() -> Toast.makeText(this, "Conectado al broker MQTT", Toast.LENGTH_SHORT).show());
                } catch (MqttException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(this, "Error al conectar con el broker MQTT", Toast.LENGTH_SHORT).show());
                }
            }).start();
        }
    }

    // Send a message via MQTT
    private void sendMessage(String messageContent, ArrayList<carritoItem> finalCartItems,String productosPh,String precioTotalString) {
        // Show the ProgressBar
        runOnUiThread(() -> progressBar.setVisibility(ProgressBar.VISIBLE));

        // Perform the connection and message-sending operation
        new Thread(() -> {
            try {
                if (mqttClient == null || !mqttClient.isConnected()) {
                    // Connect to the MQTT broker
                    mqttClient = new MqttClient(brokerUrl, MqttClient.generateClientId(), null);
                    MqttConnectOptions options = new MqttConnectOptions();
                    options.setCleanSession(true);
                    mqttClient.connect(options);
                }

                // Ensure the connection is established before sending the message
                if (mqttClient.isConnected()) {
                    MqttMessage message = new MqttMessage(messageContent.getBytes());
                    mqttClient.publish(topic, message);

                    // Update UI after the message is sent
                    runOnUiThread(() -> {
                        dbManager dbMana = new dbManager(this);
                        dbMana.addProductsToHistory(productosPh,precioTotalString);
                        progressBar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(this, "Compra Realizada", Toast.LENGTH_SHORT).show();
                        dbManager.clearCart(); // Clear cart data in the database
                        finalCartItems.clear(); // Clear the local list
                        cartAdapter.notifyDataSetChanged(); // Refresh the RecyclerView
                    });
                }
            } catch (MqttException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    progressBar.setVisibility(ProgressBar.GONE);
                    Toast.makeText(this, "Error al realizar la compra", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mqttClient != null && mqttClient.isConnected()) {
            try {
                mqttClient.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
}