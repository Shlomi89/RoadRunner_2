package com.example.hw224a10357;

import android.app.Application;

import com.example.hw224a10357.Utility.SharedPreferencesManager;
import com.example.hw224a10357.Utility.SignalManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesManager.init(this);
        SignalManager.init(this);
    }
}
