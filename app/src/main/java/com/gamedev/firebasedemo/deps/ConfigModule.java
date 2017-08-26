package com.gamedev.firebasedemo.deps;

import com.gamedev.firebasedemo.RemoteConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ConfigModule {

    @Provides
    @Singleton
    public RemoteConfig providesRemoteConfig() {
        return RemoteConfig.newInstance();
    }
}
