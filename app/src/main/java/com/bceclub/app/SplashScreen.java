package com.bceclub.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.UiModeManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class SplashScreen extends AppCompatActivity implements Animation.AnimationListener {
    private static int SPLASH_SCREEN_TIME_OUT = 2000;

    Handler handler;
    private UiModeManager uiModeManager;
    // Animation
    Animation animZoomOut;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.

        //this will bind your MainActivity.class file with activity_main.

        logo = findViewById(R.id.logo);

        uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);

        // load the animation
        animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);

        // set animation listener
        animZoomOut.setAnimationListener(SplashScreen.this);

        logo.startAnimation(animZoomOut);

        handler = new Handler();

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);*/

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if (animation == animZoomOut) {

            Intent i = new Intent(SplashScreen.this, Login.class);
            startActivity(i);
            Animatoo.animateSlideRight(SplashScreen.this);
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}