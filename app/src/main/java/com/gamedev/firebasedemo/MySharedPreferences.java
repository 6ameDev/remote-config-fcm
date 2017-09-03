package com.gamedev.firebasedemo;

import android.content.SharedPreferences;

public class MySharedPreferences {
    public static final String KEY_CONFIG_UPDATE = "com.gamedev.firebasedemo.CONFIG_UPDATE_FLAG";

    private SharedPreferences preferences;

    public MySharedPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public boolean configUpdateEnabled() {
        return preferences.getBoolean(KEY_CONFIG_UPDATE, false);
    }

    public void resetConfigUpdateFlag() {
        preferences.edit().putBoolean(KEY_CONFIG_UPDATE, false).apply();
    }

    public void enableConfigUpdate() {
        preferences.edit().putBoolean(KEY_CONFIG_UPDATE, true).apply();
    }
}
