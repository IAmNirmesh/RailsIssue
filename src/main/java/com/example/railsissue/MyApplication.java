package com.example.railsissue;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

public class MyApplication extends Application {

    private static MyApplication instance;
    private AppCompatActivity currentActivity;

    public static MyApplication getInstance() {
        return instance;
    }

    public AppCompatActivity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(AppCompatActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}

