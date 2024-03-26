package com.example.myfinances.httpreactions.signin;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.httpreactions.HttpReactionInterface;
import com.example.myfinances.activities.MyStockPortfolioScreen;

public class HttpStatusOkReaction implements HttpReactionInterface {
    @Override
    public void handle(String message, AppCompatActivity activity) {
        // log+
        activity.startActivity(new Intent(activity, MyStockPortfolioScreen.class));
    }
}
