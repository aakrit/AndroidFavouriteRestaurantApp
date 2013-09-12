package com.bignerdranch.android.criminalintent;

import java.util.UUID;

import android.support.v4.app.Fragment;

public class RestaurantActivity extends SingleFragmentActivity {
	@Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID)getIntent()
            .getSerializableExtra(RestaurantFragment.EXTRA_CRIME_ID);
        return RestaurantFragment.newInstance(crimeId);
    }
}
