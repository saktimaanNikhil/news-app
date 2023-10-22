package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeFragment();
        } else if (position==1) {
            return new EntertainmentFragment();
        } else if (position == 2) {
            return new GameFragment();
        } else if (position == 3) {
            return new HealthFragment();
        }else{
            return new IndianNewsFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "Home";
        } else if (position == 1) {
            return "Entertainment";
        }else if (position == 2) {
            return "Game";
        }else if (position == 3){
            return "Health";
        }else {
            return "Indian News";
        }
    }
}
