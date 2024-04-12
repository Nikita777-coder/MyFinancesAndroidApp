package com.example.myfinances.httpreactions.signin;

import android.os.Parcelable;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.R;
import com.example.myfinances.httpreactions.HttpReactionInterface;

public class HttpStatusNotFoundReaction implements HttpReactionInterface {
    @Override
    public void handle(Parcelable message, AppCompatActivity activity) {
        EditText publicData = activity.findViewById(R.id.publicData);
        publicData.setError("Data doesn't exist! Try again");
        publicData.setTextColor(activity.getResources().getColor(R.color.error));
    }
}
