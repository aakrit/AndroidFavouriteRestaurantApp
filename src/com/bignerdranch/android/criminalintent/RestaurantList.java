package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

import android.util.Log;

public class RestaurantList {
    private static final String TAG = "RestaurantList";
    private static final String FILENAME = "crimes.json";

    private ArrayList<Restaurant> mRestaurants;
    private RestaurantListJSONSerializer mSerializer;

    private static RestaurantList sRestaurantList;
    private Context mAppContext;

    private RestaurantList(Context appContext) {
        mAppContext = appContext;
        mSerializer = new RestaurantListJSONSerializer(mAppContext, FILENAME);

        try {
            mRestaurants = mSerializer.loadCrimes();
        } catch (Exception e) {
            mRestaurants = new ArrayList<Restaurant>();
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    public static RestaurantList get(Context c) {
        if (sRestaurantList == null) {
            sRestaurantList = new RestaurantList(c.getApplicationContext());
        }
        return sRestaurantList;
    }

    public Restaurant getCrime(UUID id) {
        for (Restaurant c : mRestaurants) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }
    
    public void addCrime(Restaurant c) {
        mRestaurants.add(c);
        saveCrimes();
    }

    public ArrayList<Restaurant> getCrimes() {
        return mRestaurants;
    }

    public void deleteCrime(Restaurant c) {
        mRestaurants.remove(c);
        saveCrimes();
    }

    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mRestaurants);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving crimes: " + e);
            return false;
        }
    }
}

