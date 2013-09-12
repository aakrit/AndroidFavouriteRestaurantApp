package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import edu.uchicago.teamyelp.R;


//
//<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
public class NavActivity extends Activity {

    private Button mMapButton;
    private Button mNavButton;
    private Button mMapButtonAddress;
    private Button mNavButtonAddress;
    private EditText mLatEdit;
    private EditText mLongEdit;
    private EditText mAddressEdit;

    protected LocationManager locationManager;


    private double mLatitude = 41.789173;
    private double mLongitude = -87.600126;
    private double mLongCurrent;
    private double mLatCurrent;

    private String restName, completeAddress;
    //private Double mLatitude;
    //private Double mLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        //get info from previous
        restName = (String) getIntent().getExtras().get("NAME");
        completeAddress = (String ) getIntent().getExtras().get("ADDRESS");

        mAddressEdit = (EditText) findViewById(R.id.addressEditText);
        mAddressEdit.setText(completeAddress);


        mMapButtonAddress = (Button) findViewById(R.id.map_button_address);
        mMapButtonAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //not implemented.
                Toast.makeText(NavActivity.this, "not implemented", Toast.LENGTH_SHORT).show();

            }
        });

        mNavButtonAddress = (Button) findViewById(R.id.nav_button_address);
        mNavButtonAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String strNav = completeAddress;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + strNav));
                startActivity(intent);


            }
        });



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);

//        mNavButton = (Button) findViewById(R.id.nav_button);
//
//
//        mNavButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                String locationProvider = LocationManager.NETWORK_PROVIDER;
//                // Or use LocationManager.GPS_PROVIDER
//
//                Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
//
//                mLatCurrent = lastKnownLocation.getLatitude();
//                mLongCurrent = lastKnownLocation.getLongitude();
//
//                mLatitude = Double.parseDouble(mLatEdit.getText().toString());
//                mLongitude = Double.parseDouble(mLongEdit.getText().toString());
//
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" +
//                        "saddr=" +  mLatCurrent + "," + mLongCurrent +
//                        "&daddr=" + mLatitude + "," + mLongitude));
//                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//                startActivity(intent);
//            }
//        });

//        mLatEdit = (EditText) findViewById(R.id.editLat);
//        mLongEdit = (EditText) findViewById(R.id.editLong);

        //st. louis
        if(getResources().getBoolean(R.bool.debug)){
            mLatEdit.setText("38.629209");
            mLongEdit.setText("-90.192261");
            mAddressEdit.setText("6 East Monroe Chicago");
        }



    }

    private String generateNavString(String strOrig){
        strOrig = strOrig.replaceAll("  "," ");
        strOrig = strOrig.replaceAll(" ","+");
        return strOrig;

    }




}
