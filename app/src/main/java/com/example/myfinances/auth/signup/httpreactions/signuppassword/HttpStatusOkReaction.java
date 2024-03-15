package com.example.myfinances.auth.signup.httpreactions.signuppassword;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myfinances.R;
import com.example.myfinances.auth.HttpReactionInterface;
import com.example.myfinances.screens.MainPage;

public class HttpStatusOkReaction implements HttpReactionInterface {
    @Override
    public void handle(String message, AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, MainPage.class));
    }
}
