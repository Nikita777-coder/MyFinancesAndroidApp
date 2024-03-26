package com.example.myfinances.httpreactions.forgotPassword;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.R;
import com.example.myfinances.httpreactions.HttpReactionInterface;

public class HttpStatusNotFoundReaction implements HttpReactionInterface {
    @Override
    public void handle(String message, AppCompatActivity activity) {
        EditText email = activity.findViewById(R.id.email);
        email.setError("User with email doesn't exist!");
    }
}
