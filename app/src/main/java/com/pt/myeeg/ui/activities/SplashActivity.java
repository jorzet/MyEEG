package com.pt.myeeg.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.pt.myeeg.R;
import com.pt.myeeg.security.AccessToken;

//import com.facebook.AccessToken;
//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;

/**
 * Created by Jorge Zepeda Tinoco on 7/1/2017.
 */


public class SplashActivity extends BaseActivityLifecycle {
    private static final int TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inicializa api facebook
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_splash);
        launchSplash();
    }

    private void launchSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(AccessToken.getCurrentAccessToken(getApplicationContext())==null)
                    goSignInActivity();
                else
                    goHomeActivity();
            }
        }, TIME_OUT);

    }

    private void goSignInActivity(){
        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(i);
    }

    private void goHomeActivity(){
        //Intent intent = new Intent(SplashActivity.this, BluetoothConnectionActivity.class);
        Intent intent = new Intent(SplashActivity.this, ContentActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
