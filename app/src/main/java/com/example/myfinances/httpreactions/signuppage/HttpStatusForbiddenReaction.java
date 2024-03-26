package com.example.myfinances.httpreactions.signuppage;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.httpreactions.HttpReactionInterface;
import com.example.myfinances.R;
import com.example.myfinances.utilities.ConstRoutine;

public class HttpStatusForbiddenReaction implements HttpReactionInterface {
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void handle(String message, AppCompatActivity activity) {
        ConstRoutine.setupVerificationCodeElementsOnError(activity.findViewById(R.id.verify_code), activity.findViewById(R.id.verify_error_message));
    }
}
