package com.example.tetrateam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainFragmentHub extends BaseMenuActivity {

    ViewPager2 viewPager;
    MyViewPagerAdapter myAdapter;
    Intent intent;
    TabLayout tabLayout;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment_hub);

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        mAuth = FirebaseAuth.getInstance();

        viewPager = findViewById(R.id.viewPager2);
        myAdapter = new MyViewPagerAdapter(
                getSupportFragmentManager(),
                getLifecycle());
        myAdapter.addFragment(new SignUpFragment());
        myAdapter.addFragment(new SignInFragment());
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(myAdapter);

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

    public void goToSignIn(View view) {
        viewPager.setCurrentItem(1, true);
    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            intent = new Intent(MainFragmentHub.this, GameMenuActivity.class);
            startActivity(intent);
        }
    }
}

