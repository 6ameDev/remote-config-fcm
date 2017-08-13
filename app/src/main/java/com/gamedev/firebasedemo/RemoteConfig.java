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
        public void onFetched() {

        }
    };

    private final FirebaseRemoteConfig firebaseRemoteConfig;
    private long cacheExpirationInSeconds = 1800;   // 30 minutes

    private RemoteConfig(FirebaseRemoteConfig firebaseRemoteConfig) {
        this.firebaseRemoteConfig = firebaseRemoteConfig;
    }

    public interface Callback {
        void onFetched();
    }

    public static RemoteConfig newInstance() {
        FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(
                BuildConfig.DEBUG).build();
        firebaseRemoteConfig.setConfigSettings(configSettings);
        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        return new RemoteConfig(firebaseRemoteConfig);
    }

    public void init() {
//        if (firebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
//            cacheExpirationInSeconds = 0;
//        }
        fetch(cacheExpirationInSeconds);
    }

    public String username() {
        return firebaseRemoteConfig.getString(KEY_USERNAME);
    }

    public void requestUsername(Callback callback) {
        fetch(cacheExpirationInSeconds, callback);
    }

    public void forceRequestUsername(Callback callback) {
        fetch(0, callback);
    }

    private void fetch(long cacheExpirationInSeconds) {
        fetch(cacheExpirationInSeconds, EMPTY_CALLBACK);
    }

    private void fetch(long cacheExpirationInSeconds, final Callback callback) {
        Log.i(TAG, String.format("[Fetching] [Expiry: %s]", cacheExpirationInSeconds));

        firebaseRemoteConfig.fetch(cacheExpirationInSeconds)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.i(TAG, "[Fetch successful]");
                                        firebaseRemoteConfig.activateFetched();
                                        callback.onFetched();
                                    } else {
                                        Log.i(TAG, "[Fetch not successful] [Exception: ]" + task.getException());
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull final Exception e) {
                                    Log.i(TAG, "[Fetch failed]");
                                }
                            });
    }
}
