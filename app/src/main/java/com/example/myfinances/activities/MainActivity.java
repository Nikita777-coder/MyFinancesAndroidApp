package com.example.myfinances.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.myfinances.R;

public class MainActivity extends AppCompatActivity {
    private static int TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(() -> {
            Intent i = new Intent(MainActivity.this, SignInScreen.class);
            startActivity(i);
            finish();
        }, TIME_OUT);
    }
}