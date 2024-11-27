package com.example.kiteapp.Controlador;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class pfpManager {
    private static final String PROFILE_IMAGE_FILE_NAME = "profile_image.png";
    private Context context;

    public pfpManager(Context context) {
        this.context = context;
    }

    // Guardar imagen de perfil
    public void saveProfileImage(Bitmap bitmap) {
        try (FileOutputStream fos = context.openFileOutput(PROFILE_IMAGE_FILE_NAME, Context.MODE_PRIVATE)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cargar imagen de perfil
    public Bitmap loadProfileImage() {
        File file = new File(context.getFilesDir(), PROFILE_IMAGE_FILE_NAME);
        if (!file.exists()) {
            return null; // Retorna null si no existe la imagen
        }

        try (FileInputStream fis = context.openFileInput(PROFILE_IMAGE_FILE_NAME)) {
            return BitmapFactory.decodeStream(fis);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Eliminar imagen de perfil
    public void deleteProfileImage() {
        File file = new File(context.getFilesDir(), PROFILE_IMAGE_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }
}
