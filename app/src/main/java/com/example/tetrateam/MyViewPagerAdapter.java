package com.example.tetrateam;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

// My View Pager Adapter
public class MyViewPagerAdapter extends FragmentStateAdapter {

    // variables
    private ArrayList<Fragment> fragments = new ArrayList<>();

    // constructor
    public MyViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    // creates a fragment
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    // returns the number of fragments
    @Override
    public int getItemCount() {
        return fragments.size();
    }

    // adds a fragment
    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }
}
