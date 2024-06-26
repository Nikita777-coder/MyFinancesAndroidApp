package com.example.myfinances.httpreactions.signuppage;

import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myfinances.R;
import com.example.myfinances.httpreactions.HttpReactionInterface;

public class HttpStatusOkReaction implements HttpReactionInterface {
    @Override
    public void handle(Parcelable message, AppCompatActivity activity) {
        AppCompatButton signUpBtn = activity.findViewById(R.id.signUpBtn);
        signUpBtn.setEnabled(true);
    }
}
