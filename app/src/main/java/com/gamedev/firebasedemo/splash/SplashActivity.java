package com.gamedev.firebasedemo.splash;

import static com.gamedev.firebasedemo.RemoteConfig.Callback;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gamedev.firebasedemo.FirebaseDemoApp;
import com.gamedev.firebasedemo.home.HomeActivity;
import com.gamedev.firebasedemo.R;
import com.gamedev.firebasedemo.RemoteConfig;

import javax.inject.Inject;

public class SplashActivity extends AppCompatActivity {

    @Inject
    RemoteConfig remoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ((FirebaseDemoApp) getApplicationContext()).deps().inject(this);

        remoteConfig.init(new Callback() {
            @Override
            public void onFinished() {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            }
        });
    }
}
