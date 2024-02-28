package com.example.myfinances.auth.signin.reactions;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.R;

public class HttpStatusBadRequestReaction implements Reaction {
    @Override
    public void handle(String message, AppCompatActivity activity) {
        EditText password = activity.findViewById(R.id.password);
        password.setError("Passwords don't match! Try again");
        password.setTextColor(activity.getResources().getColor(R.color.error));
    }
}
