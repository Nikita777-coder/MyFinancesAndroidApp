package com.example.myfinances.auth.signin.reactions;

import androidx.appcompat.app.AppCompatActivity;

public interface Reaction {
    void handle(String message, AppCompatActivity activity);
}
