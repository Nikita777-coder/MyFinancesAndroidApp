package com.example.myfinances.httpreactions.signuppassword;

import android.content.Intent;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.R;
import com.example.myfinances.dto.UserOutData;
import com.example.myfinances.httpreactions.HttpReactionInterface;
import com.example.myfinances.activities.MyStockPortfolioScreen;

public class HttpStatusCreatedReaction implements HttpReactionInterface {
    @Override
    public void handle(Parcelable userData, AppCompatActivity activity) {
        Intent myPortfolioActivity = new Intent(activity, MyStockPortfolioScreen.class);
        myPortfolioActivity.putExtra(activity.getResources().getString(R.string.user_data), userData);
        activity.startActivity(myPortfolioActivity);
    }
}
