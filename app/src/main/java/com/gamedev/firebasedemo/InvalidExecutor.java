package com.gamedev.firebasedemo;

import android.util.Log;

public class InvalidExecutor implements Executor {

    private static final String TAG = "InvalidExecutor";

    @Override
    public void execute() {
        Log.d(TAG, "execute");
    }
}
