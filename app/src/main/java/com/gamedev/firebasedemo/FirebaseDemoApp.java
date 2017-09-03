package com.gamedev.firebasedemo;

import android.app.Application;

import com.gamedev.firebasedemo.deps.AppModule;
import com.gamedev.firebasedemo.deps.DaggerDeps;
import com.gamedev.firebasedemo.deps.Deps;

import javax.inject.Inject;

public class FirebaseDemoApp extends Application {

    @Inject
    RemoteConfig remoteConfig;

    private Deps deps;

    @Override
    public void onCreate() {
        super.onCreate();

        deps = DaggerDeps.builder()
                .appModule(new AppModule(this))
                .build();
        deps.inject(this);

        remoteConfig.init();
    }

    public Deps deps() {
        return deps;
    }
}
