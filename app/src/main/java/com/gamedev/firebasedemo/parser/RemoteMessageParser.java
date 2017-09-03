package com.gamedev.firebasedemo.parser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gamedev.firebasedemo.executor.Executor;
import com.gamedev.firebasedemo.executor.InvalidExecutor;
import com.gamedev.firebasedemo.executor.RemoteConfigUpdateExecutor;

import java.util.Map;

public class RemoteMessageParser {

    public static final String TAG = "RemoteMsgParser";
    public static final String KEY_COMMAND = "command";

    public static Executor parse(Map<String, String> map) {
        if (map.containsKey(KEY_COMMAND)) {
            String command = map.get(KEY_COMMAND);
            return parse(command);
        }
        return new InvalidExecutor();
    }

    public static Executor parse(@Nullable Bundle bundle) {
        if (bundle != null && bundle.containsKey(KEY_COMMAND)) {
            String command = ((String) bundle.get(KEY_COMMAND));
            return parse(command);
        }
        return new InvalidExecutor();
    }

    private static Executor parse(String command) {
        Log.d(TAG, "Command: " + command);

        switch (command) {
            case "REFRESH_REMOTE_CONFIG": return new RemoteConfigUpdateExecutor();
            default: return new InvalidExecutor();
        }
    }
}
