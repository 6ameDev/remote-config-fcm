package com.gamedev.firebasedemo;

import android.app.Application;

import com.gamedev.firebasedemo.deps.AppModule;
import com.gamedev.firebasedemo.deps.DaggerDeps;
import com.gamedev.firebasedemo.deps.Deps;

public class FirebaseDemoApp extends Application {

    private Deps deps;

    @Override
    public void onCreate() {
        super.onCreate();

        deps = DaggerDeps.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public Deps deps() {
        return deps;
    }
}
