package com.gamedev.firebasedemo.services;

import android.util.Log;

import com.gamedev.firebasedemo.FirebaseDemoApp;
import com.gamedev.firebasedemo.MySharedPreferences;
import com.gamedev.firebasedemo.parser.RemoteMessageParser;
import com.gamedev.firebasedemo.executor.Executor;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import javax.inject.Inject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Inject
    MySharedPreferences mySharedPreferences;

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onCreate() {
        super.onCreate();
        ((FirebaseDemoApp) getApplication()).deps().inject(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // See: https://firebase.google.com/docs/cloud-messaging/concept-options
        // Troubleshooting: https://goo.gl/39bRNJ

        // tl;dr Firebase console will send as notification if app is in background
        // else it'll be received here. Tapping on that notification will take you to
        // Main activity

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        Map<String, String> remoteMessageData = remoteMessage.getData();
        if (remoteMessageData.size() > 0) {
            Executor executor = RemoteMessageParser.parse(remoteMessageData);
            executor.execute(mySharedPreferences);
        }
    }
}