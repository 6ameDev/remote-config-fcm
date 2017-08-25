package com.gamedev.firebasedemo;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

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
            executor.execute();
        }
    }
}