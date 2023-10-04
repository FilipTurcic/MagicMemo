package com.filipturcic.magicmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Create a new Handler to introduce a delay for the splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if there is a currently authenticated user
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser == null) {
                    // If there is no authenticated user, navigate to the LoginActivity
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    // If there is an authenticated user, navigate to the MainActivity
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                // Finish the current activity (splash screen)
                finish();
            }
        }, 1000); // Delay for 1000 milliseconds (1 second)
    }
}
