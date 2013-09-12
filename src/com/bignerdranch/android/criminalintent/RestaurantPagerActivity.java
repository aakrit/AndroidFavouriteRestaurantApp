package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.view.ViewPager;

public class RestaurantPagerActivity extends FragmentActivity implements RestaurantFragment.Callbacks {
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        final ArrayList<Restaurant> restaurants = RestaurantList.get(this).getCrimes();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return restaurants.size();
            }
            @Override
            public Fragment getItem(int pos) {
                UUID crimeId =  restaurants.get(pos).getId();
                return RestaurantFragment.newInstance(crimeId);
            }
        }); 

        UUID crimeId = (UUID)getIntent().getSerializableExtra(RestaurantFragment.EXTRA_CRIME_ID);
        for (int i = 0; i < restaurants.size(); i++) {
            if (restaurants.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            } 
        }
    }

    public void onCrimeUpdated(Restaurant restaurant) {
        // do nothing        
    }
}
