package com.gamedev.firebasedemo.home;

import static android.view.View.OnClickListener;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gamedev.firebasedemo.executor.Executor;
import com.gamedev.firebasedemo.FirebaseDemoApp;
import com.gamedev.firebasedemo.MySharedPreferences;
import com.gamedev.firebasedemo.R;
import com.gamedev.firebasedemo.RemoteConfig;
import com.gamedev.firebasedemo.parser.RemoteMessageParser;
import com.google.firebase.messaging.FirebaseMessaging;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "FCMessaging";
    public static final String TOPIC = "test_topic";

    @Inject
    RemoteConfig remoteConfig;
    @Inject
    MySharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ((FirebaseDemoApp) getApplication()).deps().inject(this);
        subscribeToFcmTopic();

        final TextView usernameText = (TextView) findViewById(R.id.text_username);
        populateUsername(usernameText);

        Button refreshButton = (Button) findViewById(R.id.btn_refresh);
        refreshButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                populateUsername(usernameText);
            }
        });

        // Handle possible data accompanying notification message.
        Bundle bundle = getIntent().getExtras();
        Executor executor = RemoteMessageParser.parse(bundle);
        executor.execute(mySharedPreferences);
    }

    private void populateUsername(final TextView usernameText) {
        remoteConfig.fetch(new RemoteConfig.Callback() {
            @Override
            public void onFinished() {
                usernameText.setText(remoteConfig.username());
            }
        });
    }

    private void subscribeToFcmTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        String msg = String.format("[Subscribed] [Topic: %s]", TOPIC);
        Log.d(TAG, msg);
        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
