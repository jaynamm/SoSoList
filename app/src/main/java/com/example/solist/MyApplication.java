package com.example.solist;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("list.realm")
                .schemaVersion(0)
                .build();

        // Realm 에 세팅한 정보 값을 지정한다.
        Realm.setDefaultConfiguration(config);
    }
}