package com.gamedev.firebasedemo;

import android.util.Log;

public class RemoteConfigRefreshExecutor implements Executor {

    public static final String TAG = "RemoteConfigRefreshExe";

    @Override
    public void execute(RemoteConfig remoteConfig) {
        remoteConfig.refresh();
        Log.d(TAG, "execute");
    }
}
