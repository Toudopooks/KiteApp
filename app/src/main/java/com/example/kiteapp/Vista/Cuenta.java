package com.example.kiteapp.Vista;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kiteapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.kiteapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Cuenta extends AppCompatActivity {

    private ShapeableImageView profilePicture;
    private TextView username, email;
    private Button logoutButton,historialbtn,backbtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        // Initialize UI components
        profilePicture = findViewById(R.id.PFP);
        username = findViewById(R.id.username);
        email = findViewById(R.id.correo);
        logoutButton = findViewById(R.id.signout);
        historialbtn = findViewById(R.id.btnhistorial);
        backbtn = findViewById(R.id.backButton);

        // Check if user is logged in
        if (currentUser != null) {
            // Display user information
            Glide.with(this).load(currentUser.getPhotoUrl()).into(profilePicture);
            username.setText(currentUser.getDisplayName());
            email.setText(currentUser.getEmail());
        } else {
            // If the user is not logged in, redirect to Login
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            redirectToLogin();
        }
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        historialbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cuenta.this, historialActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Set logout button click listener
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        auth.signOut();
        GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
                .addOnCompleteListener(this, task -> {
                    // Redirect to login after clearing the account
                    redirectToLogin();
                });
    }

    private void redirectToLogin() {
        Intent intent = new Intent(Cuenta.this, Login.class);
        startActivity(intent);
        finish();  // Close Cuenta activity
    }
}
