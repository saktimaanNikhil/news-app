package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment(); // Replace with your actual fragment class
            case 1:
                return new EntertainmentFragment(); // Replace with your actual fragment class
            case 2:
                return new GameFragment(); // Replace with your actual fragment class
            case 3:
                return new HealthFragment(); // Replace with your actual fragment class
            default:
                return new IndianNewsFragment(); // Replace with your actual fragment class
        }
    }

    @Override
    public int getItemCount() {
        return 5; // Total number of tabs/pages
    }
}

//public class ViewPagerAdapter extends FragmentPagerAdapter {
//    public ViewPagerAdapter(@NonNull FragmentManager fm) {
//        super(fm);
//    }
//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        if (position == 0) {
//            return new HomeFragment();
//        } else if (position==1) {
//            return new EntertainmentFragment();
//        } else if (position == 2) {
//            return new GameFragment();
//        } else if (position == 3) {
//            return new HealthFragment();
//        }else{
//            return new IndianNewsFragment();
//        }
//    }
//    @Override
//    public int getCount() {
//        return 5;
//    }
//
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        if (position==0){
//            return "Home";
//        } else if (position == 1) {
//            return "Entertainment";
//        }else if (position == 2) {
//            return "Game";
//        }else if (position == 3){
//            return "Health";
//        }else {
//            return "Indian News";
//        }
//    }
//}
