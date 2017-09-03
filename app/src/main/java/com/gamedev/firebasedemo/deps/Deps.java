package com.gamedev.firebasedemo.deps;

import com.gamedev.firebasedemo.HomeActivity;
import com.gamedev.firebasedemo.MyFirebaseMessagingService;
import com.gamedev.firebasedemo.splash.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ConfigModule.class, StorageModule.class})
public interface Deps {
    void inject(SplashActivity splashActivity);

    void inject(HomeActivity homeActivity);

    void inject(MyFirebaseMessagingService myFirebaseMessagingService);
}
