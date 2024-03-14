package com.example.myfinances.auth.signup.httpreactions;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.auth.HttpReactionInterface;
import com.example.myfinances.R;
import com.goodiebag.pinview.Pinview;

public class HttpStatusForbiddenReaction implements HttpReactionInterface {
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void handle(String message, AppCompatActivity activity) {
        Pinview verifyCode = activity.findViewById(R.id.verify_code);
        verifyCode.clearValue();
        verifyCode.setPinBackgroundRes(R.drawable.pin_error_background);

        TextView verifyErrorMessage = activity.findViewById(R.id.verify_error_message);
        verifyErrorMessage.setVisibility(View.VISIBLE);
    }
}
