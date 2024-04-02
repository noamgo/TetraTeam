package com.example.tetrateam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// Main Fragment Hub (extends BaseMenuActivity to easily use the menu without using all of the menu functions again)
public class MainFragmentHub extends BaseMenuActivity {

    // variables
    ViewPager2 viewPager;
    MyViewPagerAdapter myAdapter;
    Intent intent;

    // Firebase Authentication
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment_hub);

        // set up the tab layout
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // define the Firebase Authentication from the instance
        mAuth = FirebaseAuth.getInstance();

        // set up the view pager
        viewPager = findViewById(R.id.viewPager2);
        myAdapter = new MyViewPagerAdapter(
                getSupportFragmentManager(),
                getLifecycle());
        myAdapter.addFragment(new SignUpFragment());
        myAdapter.addFragment(new SignInFragment());
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(myAdapter);

        // set up the tab layout
        new TabLayoutMediator(
                tabLayout,
                viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        // Set appropriate titles for the tabs
                        if (position == 0) {
                            tab.setText("Sign Up");
                        } else if (position == 1) {
                            tab.setText("Sign In");
                        }
                    }
                }
        ).attach();
    }

    // button that moves the user to the SignIn Fragment (used after signing up)
    public void goToSignIn(View view) {
        viewPager.setCurrentItem(1, true);
    }

    // function that moves the user to the Game Menu immediately (if signed in)
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            intent = new Intent(MainFragmentHub.this, GameMenuActivity.class);
            startActivity(intent);
        }
    }
}

