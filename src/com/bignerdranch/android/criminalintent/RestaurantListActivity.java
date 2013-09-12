package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class RestaurantListActivity extends SingleFragmentActivity
    implements RestaurantListFragment.Callbacks, RestaurantFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new RestaurantListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    public void onCrimeSelected(Restaurant restaurant) {
        if (findViewById(R.id.detailFragmentContainer) == null) {
            // start an instance of RestaurantPagerActivity
            Intent i = new Intent(this, RestaurantPagerActivity.class);
            i.putExtra(RestaurantFragment.EXTRA_CRIME_ID, restaurant.getId());
            startActivityForResult(i, 0);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = RestaurantFragment.newInstance(restaurant.getId());

            if (oldDetail != null) {
                ft.remove(oldDetail);
            } 

            ft.add(R.id.detailFragmentContainer, newDetail);
            ft.commit();
        }
    }

    public void onCrimeUpdated(Restaurant restaurant) {
        FragmentManager fm = getSupportFragmentManager();
        RestaurantListFragment listFragment = (RestaurantListFragment)
                fm.findFragmentById(R.id.fragmentContainer);
        listFragment.updateUI();
    }
}
