package com.example.myfinances.settings.logger;

import android.app.Application;
import timber.log.Timber;

public class MyTimber extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // initialize timber in application class
        Timber.plant(new Timber.DebugTree());
    }
}