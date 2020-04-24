package com.example.moriah.model;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
