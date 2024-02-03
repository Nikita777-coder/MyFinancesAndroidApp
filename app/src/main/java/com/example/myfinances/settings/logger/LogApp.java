package com.example.myfinances.settings.logger;

import android.app.Application;

public class LogApp extends Application {

    public static boolean checkDebug;
    @Override
    public void onCreate() {
        super.onCreate();
        checkDebug = true;
    }
}
