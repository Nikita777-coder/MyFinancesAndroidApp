package com.example.myfinances.auth.signup.httpreactions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myfinances.R;
import com.example.myfinances.auth.HttpReactionInterface;

public class HttpStatusOkReaction implements HttpReactionInterface {
    @Override
    public void handle(String message, AppCompatActivity activity) {
        AppCompatButton signUpBtn = activity.findViewById(R.id.signUpBtn);
        signUpBtn.setEnabled(true);
    }
}
