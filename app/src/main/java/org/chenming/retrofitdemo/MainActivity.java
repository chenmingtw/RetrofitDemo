package org.chenming.retrofitdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    FragmentManager fragmentManager;
    Fragment showingFragment;
    GetFragment getFragment;
    PostFragment postFragment;
    DeleteFragment deleteFragment;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        showingFragment = new Fragment();
        getFragment = new GetFragment();
        postFragment = new PostFragment();
        deleteFragment = new DeleteFragment();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_get:
                        switchFragment(getFragment);
                        return true;
                    case R.id.navigation_post:
                        switchFragment(postFragment);
                        return true;
                    case R.id.navigation_delete:
                        switchFragment(deleteFragment);
                        return true;
                }
                return false;
            }
        });

        // Set GET fragment as default.
        bottomNavigationView.setSelectedItemId(R.id.navigation_get);
    }

    private void switchFragment(Fragment nextFragment) {
        if (showingFragment != nextFragment) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (!nextFragment.isAdded()) {
                fragmentTransaction.hide(showingFragment).add(R.id.layout_main, nextFragment).commit();
            } else {
                fragmentTransaction.hide(showingFragment).show(nextFragment).commit();
            }
            showingFragment = nextFragment;
        }
    }
}
