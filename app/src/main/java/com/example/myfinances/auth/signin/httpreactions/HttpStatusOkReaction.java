package com.example.myfinances.auth.signin.httpreactions;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.auth.HttpReactionInterface;
import com.example.myfinances.screens.MainPage;

public class HttpStatusOkReaction implements HttpReactionInterface {
    @Override
    public void handle(String message, AppCompatActivity activity) {
        // log+
        activity.startActivity(new Intent(activity, MainPage.class));
    }
}
