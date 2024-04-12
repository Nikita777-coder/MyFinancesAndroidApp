package com.example.myfinances.httpreactions.signin;

import android.os.Parcelable;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.R;
import com.example.myfinances.httpreactions.HttpReactionInterface;

public class HttpStatusBadRequestReaction implements HttpReactionInterface {
    @Override
    public void handle(Parcelable message, AppCompatActivity activity) {
        EditText password = activity.findViewById(R.id.password);
        password.setError("Passwords don't match! Try again");
        password.setTextColor(activity.getResources().getColor(R.color.error));
    }
}
