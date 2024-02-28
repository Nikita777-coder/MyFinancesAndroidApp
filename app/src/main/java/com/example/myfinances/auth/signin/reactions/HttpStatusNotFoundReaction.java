package com.example.myfinances.auth.signin.reactions;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.R;

public class HttpStatusNotFoundReaction implements Reaction {
    @Override
    public void handle(String message, AppCompatActivity activity) {
        EditText publicData = activity.findViewById(R.id.publicData);
        publicData.setError("Data doesn't exist! Try again");
        publicData.setTextColor(activity.getResources().getColor(R.color.error));
    }
}
