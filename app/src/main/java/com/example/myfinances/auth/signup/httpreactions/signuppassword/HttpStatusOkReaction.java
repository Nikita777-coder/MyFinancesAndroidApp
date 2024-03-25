package com.example.myfinances.auth.signup.httpreactions.signuppassword;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.auth.HttpReactionInterface;
import com.example.myfinances.screens.MyStockPortfolioScreen;

public class HttpStatusOkReaction implements HttpReactionInterface {
    @Override
    public void handle(String message, AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, MyStockPortfolioScreen.class));
    }
}
