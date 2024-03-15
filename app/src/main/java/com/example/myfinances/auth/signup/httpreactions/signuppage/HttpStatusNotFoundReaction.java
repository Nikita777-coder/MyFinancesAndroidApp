package com.example.myfinances.auth.signup.httpreactions.signuppage;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.R;
import com.example.myfinances.auth.HttpReactionInterface;
import com.example.myfinances.auth.signup.ConstRoutine;

public class HttpStatusNotFoundReaction implements HttpReactionInterface {
    @Override
    public void handle(String message, AppCompatActivity activity) {
        ConstRoutine.setupVerificationCodeElementsOnError(activity.findViewById(R.id.verify_code), activity.findViewById(R.id.verify_error_message));
    }
}
