package com.database;

import android.app.Application;

import com.database.databaseclass.DataBaseManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataBaseManager.initDatabase(this);
    }
}
