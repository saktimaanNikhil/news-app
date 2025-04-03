package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    TabLayout tab;
    ViewPager2 viewPager;
    FrameLayout container;
    BottomNavigationView bnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewPager);
        container = findViewById(R.id.container);
        bnView = findViewById(R.id.bottomNavigation);


//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(adapter);
//        tab.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tab, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Home");
                    break;
                case 1:
                    tab.setText("Entertainment");
                    break;
                case 2:
                    tab.setText("Game");
                    break;
                case 3:
                    tab.setText("Health");
                    break;
                default:
                    tab.setText("Other");
                    break;
            }
        }).attach();

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                container.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                container.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.GONE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                container.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
            }
        });

        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                container.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.GONE);
                tab.setVisibility(View.GONE);

                if (id==R.id.indianNews){
                    loadFragment(new IndianNewsFragment(),true);
                }else {
                    viewPager.setVisibility(View.VISIBLE);
                    container.setVisibility(View.GONE);
                    tab.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

    }

    public void loadFragment(Fragment fragment, boolean flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag) {
            ft.add(R.id.container, fragment);

        } else {
            ft.replace(R.id.container, fragment);
        }
        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        if (IndianNewsFragment.webView != null) {
            if (IndianNewsFragment.webView.canGoBack()) {
                IndianNewsFragment.webView.goBack();
            } else {
                super.onBackPressed();
            }
        }else {
            super.onBackPressed();
        }

    }
}