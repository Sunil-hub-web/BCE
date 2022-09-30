package com.bceclub.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import com.bceclub.SessionManager;
import com.bceclub.app.databinding.FragmentHomeFragBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.crashlytics.internal.common.CrashlyticsCore;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Boolean exit = false;

    SessionManager sessionManager;
    BottomNavigationView bottomNavigationView;
    String userId;

    static String member_id;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_frag);
//        NavController navController = Navigation.findNavController(this, R.id.fragment);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);


//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.home_frag :
//                        setCurrentFragment
//                }
//
//                return false;
//            }
//        });
        sessionManager = new SessionManager(this);
        userId = sessionManager.getUSERID();

    }

    public String getUserId() {
        return userId;
    }

    @Override
    public void onBackPressed() {

        home_frag test = (home_frag) getSupportFragmentManager().findFragmentByTag("homefrag");

        if (test != null && test.isVisible()) {

            if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                        finish();
                    }
                }, 1 * 2000);
            }
        }
        else {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, home_frag,"homefrag").commit();

        }

    /*    Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/

    }


    home_frag home_frag = new home_frag();
    profile_frag profile_frag = new profile_frag();
    member_frag member_frag = new member_frag();
    more_frag more_frag = new more_frag();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_frag:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, home_frag,"homefrag").commit();
                return true;

            case R.id.profile_frag:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, profile_frag).commit();
                return true;

            case R.id.more_frag:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, more_frag).commit();
                return true;

            case R.id.member_frag:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, member_frag).commit();
                return true;
        }
        return false;
    }
}