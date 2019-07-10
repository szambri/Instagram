package com.example.instagram;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.instagram.fragments.FeedFragment;
import com.example.instagram.fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define your fragments here
        final Fragment profileFragment = new ProfileFragment();
        final Fragment feedFragment = new FeedFragment();
   //     final Fragment postFragment = new PostFragment();

        // handle navigation selection
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                fragment = feedFragment;
                                break;
                            case R.id.action_post:
                                Intent intent = new Intent(HomeActivity.this, CameraActivity.class);
                                HomeActivity.this.startActivity(intent);
                                return true;
                            case R.id.action_profile:
                            default:
                                fragment = profileFragment;
                                break;
                        }
                        fragmentManager.beginTransaction().replace(R.id.default_frag, fragment).commit();
                        return true;
                    }
                });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}
