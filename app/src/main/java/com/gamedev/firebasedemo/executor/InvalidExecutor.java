package com.gamedev.firebasedemo.executor;

import android.support.annotation.Nullable;
import android.util.Log;

import com.gamedev.firebasedemo.MySharedPreferences;

public class InvalidExecutor implements Executor {

    private static final String TAG = "InvalidExecutor";

    @Override
    public void execute(@Nullable MySharedPreferences mySharedPreferences) {
        Log.d(TAG, "execute");
    }
}
