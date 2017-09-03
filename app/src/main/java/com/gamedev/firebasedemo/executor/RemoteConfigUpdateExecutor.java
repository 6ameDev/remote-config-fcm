package com.gamedev.firebasedemo.executor;

import android.support.annotation.NonNull;
import android.util.Log;

import com.gamedev.firebasedemo.MySharedPreferences;

public class RemoteConfigUpdateExecutor implements Executor {

    public static final String TAG = "RemoteConfigUpdateExe";

    @Override
    public void execute(@NonNull MySharedPreferences mySharedPreferences) {
        mySharedPreferences.enableConfigUpdate();
        Log.d(TAG, "execute");
    }
}
