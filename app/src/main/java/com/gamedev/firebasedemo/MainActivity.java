package com.gamedev.firebasedemo;

import static android.view.View.OnClickListener;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "FCMessaging";
    public static final String TOPIC = "test_topic";

    @Inject
    RemoteConfig remoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((FirebaseDemoApp) getApplication()).deps().inject(this);
        subscribeToFcmTopic();
        remoteConfig.init();

        final TextView usernameText = (TextView) findViewById(R.id.text_username);

        Button refreshButton = (Button) findViewById(R.id.btn_refresh);
        refreshButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                remoteConfig.fetch(new RemoteConfig.Callback() {
                    @Override
                    public void onFinished() {
                        usernameText.setText(remoteConfig.username());
                    }
                });
            }
        });

        // Handle possible data accompanying notification message.
        Bundle bundle = getIntent().getExtras();
        Executor executor = RemoteMessageParser.parse(bundle);
        executor.execute(remoteConfig);
    }

    private void subscribeToFcmTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        String msg = String.format("[Subscribed] [Topic: %s]", TOPIC);
        Log.d(TAG, msg);
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
