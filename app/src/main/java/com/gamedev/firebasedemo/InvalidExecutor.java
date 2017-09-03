package com.gamedev.firebasedemo;

import android.support.annotation.Nullable;
import android.util.Log;

public class InvalidExecutor implements Executor {

    private static final String TAG = "InvalidExecutor";

    @Override
    public void execute(@Nullable MySharedPreferences mySharedPreferences) {
        Log.d(TAG, "execute");
    }
}
