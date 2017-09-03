package com.gamedev.firebasedemo;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class RemoteConfig {

    public static final String TAG = "RemoteConfig";
    public static final String KEY_USERNAME = "username";
    public static final Callback EMPTY_CALLBACK = new Callback() {
        @Override
        public void onFinished() {

        }
    };
    public static final int IMMEDIATE_CACHE_EXPIRY = 0;
    public static final long CACHE_EXPIRY_IN_SECONDS = 1800;   // 30 minutes

    private final FirebaseRemoteConfig firebaseRemoteConfig;
    private final MySharedPreferences mySharedPreferences;

    public RemoteConfig(FirebaseRemoteConfig firebaseRemoteConfig,
            MySharedPreferences mySharedPreferences) {
        this.firebaseRemoteConfig = firebaseRemoteConfig;
        this.mySharedPreferences = mySharedPreferences;
    }

    // ********
    // Configs
    // ********
    public String username() {
        return firebaseRemoteConfig.getString(KEY_USERNAME);
    }

    // ****************************
    // RemoteConfig helper methods
    // ****************************
    public static RemoteConfig newInstance(MySharedPreferences mySharedPreferences) {
        FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings =
                new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(
                        BuildConfig.DEBUG).build();
        firebaseRemoteConfig.setConfigSettings(configSettings);
        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        return new RemoteConfig(firebaseRemoteConfig, mySharedPreferences);
    }

    // TODO: 01/09/17 Show splash screen until configs are fetched
    public void init() {
//        if (firebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
//            cacheExpirationInSeconds = 0;
//        }
        if (mySharedPreferences.configUpdateEnabled()) {
            // TODO: 01/09/17 Disable config update after they are fetched successfully
            // TODO: mySharedPreferences.resetConfigUpdateFlag();
            fetch(IMMEDIATE_CACHE_EXPIRY);
        } else {
            fetch(CACHE_EXPIRY_IN_SECONDS);
        }
    }

    public void fetch(Callback callback) {
        fetch(CACHE_EXPIRY_IN_SECONDS, callback);
    }

    private void fetch(long cacheExpirationInSeconds) {
        fetch(cacheExpirationInSeconds, EMPTY_CALLBACK);
    }

    private void fetch(long cacheExpirationInSeconds, final Callback callback) {
        Log.d(TAG, String.format("[Fetching] [Expiry: %s]", cacheExpirationInSeconds));

        firebaseRemoteConfig.fetch(cacheExpirationInSeconds)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "[Fetch successful]");
                            firebaseRemoteConfig.activateFetched();
                            callback.onFinished();
                        } else {
                            callback.onFinished();
                            Log.d(TAG, String.format("[Fetch not successful] [Exception: %s]",
                                    task.getException()));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull final Exception e) {
                        callback.onFinished();
                        Log.d(TAG, "[Fetch failed]");
                    }
                });
    }

    public interface Callback {
        void onFinished();
    }
}
