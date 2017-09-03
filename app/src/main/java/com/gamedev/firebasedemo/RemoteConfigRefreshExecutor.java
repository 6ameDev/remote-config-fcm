package com.gamedev.firebasedemo;

import android.support.annotation.NonNull;
import android.util.Log;

public class RemoteConfigRefreshExecutor implements Executor {

    public static final String TAG = "RemoteConfigRefreshExe";

    @Override
    public void execute(@NonNull MySharedPreferences mySharedPreferences) {
        mySharedPreferences.enableConfigUpdate();
        Log.d(TAG, "execute");
    }
}
