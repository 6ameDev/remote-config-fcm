package com.gamedev.firebasedemo.deps;

import com.gamedev.firebasedemo.MainActivity;
import com.gamedev.firebasedemo.MyFirebaseMessagingService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ConfigModule.class})
public interface Deps {
    void inject(MainActivity mainActivity);

    void inject(MyFirebaseMessagingService myFirebaseMessagingService);
}
