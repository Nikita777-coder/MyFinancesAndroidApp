package com.example.myfinances.httpreactions.signuppage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myfinances.R;
import com.example.myfinances.httpreactions.HttpReactionInterface;

public class HttpStatusOkReaction implements HttpReactionInterface {
    @Override
    public void handle(String message, AppCompatActivity activity) {
        AppCompatButton signUpBtn = activity.findViewById(R.id.signUpBtn);
        signUpBtn.setEnabled(true);
    }
}
