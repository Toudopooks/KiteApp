package com.example.kiteapp.Modelo;

import org.json.JSONObject;


import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ajustesManager {
    private static final String SETTINGS_FILE_NAME = "user_settings.json";
    private Context context;

    public ajustesManager(Context context) {
        this.context = context;
    }

    // Guardar ajustes en un archivo JSON
    public void saveSettings(JSONObject settings) {
        try (FileOutputStream fos = context.openFileOutput(SETTINGS_FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(settings.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Leer ajustes desde el archivo JSON
    public JSONObject loadSettings() {
        File file = new File(context.getFilesDir(), SETTINGS_FILE_NAME);
        if (!file.exists()) {
            return new JSONObject(); // Retorna un objeto vacío si no existe el archivo
        }

        try (FileInputStream fis = context.openFileInput(SETTINGS_FILE_NAME)) {
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            String json = new String(buffer);
            return new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    // Eliminar ajustes (opcional)
    public void clearSettings() {
        File file = new File(context.getFilesDir(), SETTINGS_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }
}
