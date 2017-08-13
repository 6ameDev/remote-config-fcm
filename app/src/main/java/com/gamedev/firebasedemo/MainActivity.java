package com.gamedev.firebasedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

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
            public void onClick(final View view) {
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
            public void onClick(final View view) {
                remoteConfig.forceRequestUsername(new RemoteConfig.Callback() {
                    @Override
                    public void onFinished() {
                        usernameText.setText(remoteConfig.username());
                    }
                });
            }
        });
    }

}
