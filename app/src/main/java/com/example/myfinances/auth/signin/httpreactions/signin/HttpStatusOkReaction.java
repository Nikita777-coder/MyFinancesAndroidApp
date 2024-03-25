package com.example.myfinances.auth.signin.httpreactions.signin;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.auth.HttpReactionInterface;
import com.example.myfinances.screens.MyStockPortfolioScreen;

public class HttpStatusOkReaction implements HttpReactionInterface {
    @Override
    public void handle(String message, AppCompatActivity activity) {
        // log+
        activity.startActivity(new Intent(activity, MyStockPortfolioScreen.class));
    }
}
