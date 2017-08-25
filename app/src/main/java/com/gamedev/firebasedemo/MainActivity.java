package com.gamedev.firebasedemo;

import static android.view.View.OnClickListener;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "FCMessaging";
    private TextView usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RemoteConfig remoteConfig = RemoteConfig.newInstance();
        remoteConfig.init();

        usernameText = (TextView) findViewById(R.id.text_username);

        Button refreshButton = (Button) findViewById(R.id.btn_refresh);
        refreshButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                remoteConfig.requestUsername(new RemoteConfig.Callback() {
                    @Override
                    public void onFinished() {
                        usernameText.setText(remoteConfig.username());
                    }
                });
            }
        });

        Button forceRefreshButton = (Button) findViewById(R.id.btn_force_refresh);
        forceRefreshButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                remoteConfig.forceRequestUsername(new RemoteConfig.Callback() {
                    @Override
                    public void onFinished() {
                        usernameText.setText(remoteConfig.username());
                    }
                });
            }
        });

        final EditText topicInput = (EditText) findViewById(R.id.input_topic);
        Button subscribeButton = (Button) findViewById(R.id.btn_subscribe);
        subscribeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String topic = topicInput.getText().toString();
                FirebaseMessaging.getInstance().subscribeToTopic(topic);

                String msg = String.format("[Subscribed] [Topic: %s]", topic);
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        // Handle possible data accompanying notification message.
        Bundle bundle = getIntent().getExtras();
        Executor executor = RemoteMessageParser.parse(bundle);
        executor.execute();
    }
}
