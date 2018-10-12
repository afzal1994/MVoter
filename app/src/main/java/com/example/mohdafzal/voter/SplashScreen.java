package com.example.mohdafzal.voter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                SplashScreen.this.finish();
                SharedPreferences sharedPreferences=getSharedPreferences("Loginstatus",MODE_PRIVATE);
                if (sharedPreferences.getBoolean("loginstatus",false)) {
                    Intent intent = new Intent(SplashScreen.this, MainPage.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent, ActivityOptions
                                .makeSceneTransitionAnimation(SplashScreen.this).toBundle());
                    }
                    else{
                        startActivity(intent);
                    }
                }
                else{
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent, ActivityOptions
                                .makeSceneTransitionAnimation(SplashScreen.this).toBundle());
                    }
                    else{
                        startActivity(intent);
                    }
                }




            }
        }, SPLASH_TIME_OUT);
    }}
