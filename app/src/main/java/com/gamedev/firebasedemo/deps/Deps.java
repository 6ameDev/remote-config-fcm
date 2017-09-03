package com.gamedev.firebasedemo.deps;

import com.gamedev.firebasedemo.FirebaseDemoApp;
import com.gamedev.firebasedemo.MainActivity;
import com.gamedev.firebasedemo.MyFirebaseMessagingService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ConfigModule.class, StorageModule.class})
public interface Deps {
    void inject(FirebaseDemoApp app);

    void inject(MainActivity mainActivity);

    void inject(MyFirebaseMessagingService myFirebaseMessagingService);
}
