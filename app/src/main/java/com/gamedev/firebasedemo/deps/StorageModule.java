package com.gamedev.firebasedemo.deps;

import android.content.Context;
import android.content.SharedPreferences;

import com.gamedev.firebasedemo.MySharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {

    public static final String KEY_PREFERENCES = "com.gamedev.firebasedemo.SHARED_PREFERENCES";

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public SharedPreferences providesPreference(Context context) {
        return context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public MySharedPreferences providesPreferences(SharedPreferences sharedPreferences) {
        return new MySharedPreferences(sharedPreferences);
    }
}
