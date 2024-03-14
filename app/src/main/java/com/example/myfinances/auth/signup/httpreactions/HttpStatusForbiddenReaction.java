package com.example.myfinances.auth.signup.httpreactions;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.auth.HttpReactionInterface;
import com.example.myfinances.R;
import com.example.myfinances.auth.signup.ConstRoutine;
import com.goodiebag.pinview.Pinview;

public class HttpStatusForbiddenReaction implements HttpReactionInterface {
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void handle(String message, AppCompatActivity activity) {
        ConstRoutine.setupVerificationCodeElementsOnError(activity.findViewById(R.id.verify_code), activity.findViewById(R.id.verify_error_message));
    }
}
