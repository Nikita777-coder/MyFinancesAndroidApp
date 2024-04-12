package com.example.myfinances.httpreactions;

import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;

public interface HttpReactionInterface {
    void handle(Parcelable content, AppCompatActivity activity);
}
