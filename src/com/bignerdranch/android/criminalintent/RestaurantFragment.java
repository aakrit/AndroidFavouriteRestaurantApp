package com.bignerdranch.android.criminalintent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bignerdranch.android.criminalintent.yelpapi.APISearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class RestaurantFragment extends Fragment implements Serializable{
    public static final String EXTRA_CRIME_ID = "criminalintent.CRIME_ID";
    private static final String TAG = "RestFrag";

    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_IMAGE = "image";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;
    private static final int REQUEST_CONTACT = 2;
    private static final int REQUEST_NOTES = 3;
    private static final int REQUEST_MAPS = 4;

    private static final String URL_YELP = "http://www.yelp.com";

    Restaurant mRestaurant;
    EditText mTitleField, mLocationField, mPhoneField, mAddressField;
    Button mDateButton, mYelpAPI, mYelpPage, mCancel, mSave, mSuspectButton, mDial, mMap;
    ImageButton mPhotoButton;
    ImageView mPhotoView;
    Callbacks mCallbacks;

    String mYelpPageView;
    boolean mYelpPageViewCheck = false;


    public interface Callbacks {
        void onCrimeUpdated(Restaurant restaurant);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public static RestaurantFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        RestaurantFragment fragment = new RestaurantFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        mRestaurant = RestaurantList.get(getActivity()).getCrime(crimeId);

        setHasOptionsMenu(true);
    }

    public void updateDate() {
        mDateButton.setText(mRestaurant.getDate().toString());
    }

    @Override
    @TargetApi(11)
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        if(mRestaurant.getTitle() == null)
            mTitleField.setText("thai 55");
        else
            mTitleField.setText(mRestaurant.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mRestaurant.setTitle(c.toString());
                mCallbacks.onCrimeUpdated(mRestaurant);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });
        mLocationField = (EditText)v.findViewById(R.id.crime_location);
        if(mRestaurant.getLocation() == null)
            mLocationField.setText("Chicago");
        else
            mLocationField.setText(mRestaurant.getLocation());
        mLocationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                mRestaurant.setLocation(charSequence.toString());
                mCallbacks.onCrimeUpdated(mRestaurant);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mPhoneField = (EditText)v.findViewById(R.id.rest_phone);
        mPhoneField.setText(mRestaurant.getPhone());
        mPhoneField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                mRestaurant.setPhone(charSequence.toString());
                mCallbacks.onCrimeUpdated(mRestaurant);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mAddressField = (EditText)v.findViewById(R.id.rest_address);
        mAddressField.setText(mRestaurant.getAddress());
        mAddressField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                mRestaurant.setAddress(charSequence.toString());
                mCallbacks.onCrimeUpdated(mRestaurant);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDial = (Button)v.findViewById(R.id.rest_phone_dialButton);
        mDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mPhone = mPhoneField.getText().toString();
                if(mPhone.matches(""))
                {
                    Toast.makeText(getActivity(), "No Phone number provided", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
//                    EndCallListener callListener = new EndCallListener();
//                    TelephonyManager mTM = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
//                    mTM.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);

                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel:"+mPhone));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }
                //else call the intent to dial
            }
        });
        mMap = (Button)v.findViewById(R.id.rest_mapButton);
        mMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mAdd = mAddressField.getText().toString();
                String mLocation = mLocationField.getText().toString();
                String name = mTitleField.getText().toString();
                String fullAdd = mAdd+", "+mLocation;

                if(mAdd.matches("") || mLocation.matches(""))
                {
                    Toast.makeText(getActivity(), "No Address provided", Toast.LENGTH_SHORT).show();
                    return;
                }
                //else call the intent to maps
                else
                {
//                    Toast.makeText(getActivity(), "going to map", Toast.LENGTH_SHORT).show();
//                    try {
//                        wait(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    Intent intent = new Intent(getActivity(), NavActivity.class);
                    intent.putExtra("NAME", name);
                    intent.putExtra("ADDRESS", fullAdd);
                    startActivity(intent);

                }
            }
        });
        mSave = (Button)v.findViewById(R.id.rest_save);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save and return to list view
                RestaurantList.get(getActivity()).saveCrimes();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                for(int j = 0; j < fm.getBackStackEntryCount(); ++j)
                    fm.popBackStack();
                getActivity().finish();
            }
        });
        mCancel = (Button)v.findViewById(R.id.rest_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //return to list view and clean start
                FragmentManager fm = getActivity().getSupportFragmentManager();
                for(int j = 0; j < fm.getBackStackEntryCount(); ++j)
                    fm.popBackStack();
                getActivity().finish();
            }
        });
        mDateButton = (Button)v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                    .newInstance(mRestaurant.getDate());
                dialog.setTargetFragment(RestaurantFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });
        
        mPhotoButton = (ImageButton)v.findViewById(R.id.crime_imageButton);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // launch the camera activity
                Intent i = new Intent(getActivity(), RestaurantCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
            }
        });
        
        // if camera is not available, disable camera functionality
        PackageManager pm = getActivity().getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) &&
                !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            mPhotoButton.setEnabled(false);
        }

        mPhotoView = (ImageView)v.findViewById(R.id.rest_imageView);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Photo p = mRestaurant.getPhoto();
                if (p == null) 
                    return;

                FragmentManager fm = getActivity().getSupportFragmentManager();
                String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
                ImageFragment.createInstance(path).show(fm, DIALOG_IMAGE);
            }
        });
        
        mSuspectButton = (Button)v.findViewById(R.id.crime_suspectButton);
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
            }
        });
        if (mRestaurant.getSuspect() != null) {
            mSuspectButton.setText(mRestaurant.getSuspect());
        }

        Button reportButton = (Button)v.findViewById(R.id.rest_exportButton);
        reportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            } 
        });
        
        return v; 
    }
    
    private void showPhoto() {
        // (re)set the image button's image based on our photo
        Photo p = mRestaurant.getPhoto();
        BitmapDrawable b = null;
        if (p != null) {
            String path = getActivity()
                .getFileStreamPath(p.getFilename()).getAbsolutePath();
            b = PictureUtils.getScaledDrawable(getActivity(), path);
        }
        mPhotoView.setImageDrawable(b);
    }

    @Override
    public void onStop() {
        super.onStop();
        PictureUtils.cleanImageView(mPhotoView);
    }

    @Override
    public void onStart() {
        super.onStart();
        showPhoto();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mRestaurant.setDate(date);
            mCallbacks.onCrimeUpdated(mRestaurant);
            updateDate();
        } else if (requestCode == REQUEST_PHOTO) {
            // create a new Photo object and attach it to the crime
            String filename = data
                .getStringExtra(RestaurantCameraFragment.EXTRA_PHOTO_FILENAME);
            if (filename != null) {
                Photo p = new Photo(filename);
                mRestaurant.setPhoto(p);
                mCallbacks.onCrimeUpdated(mRestaurant);
                showPhoto();
            }
        } else if (requestCode == REQUEST_CONTACT) {
            Uri contactUri = data.getData();
            String[] queryFields = new String[] { ContactsContract.Contacts.DISPLAY_NAME_PRIMARY };
            Cursor c = getActivity().getContentResolver()
                .query(contactUri, queryFields, null, null, null);

            if (c.getCount() == 0) {
                c.close();
                return; 
            }

            c.moveToFirst();
            String suspect = c.getString(0);
            mRestaurant.setSuspect(suspect);
            mCallbacks.onCrimeUpdated(mRestaurant);
            mSuspectButton.setText(suspect);
            c.close();
        }
//          else if(requestCode == REQUEST_NOTES)
//        {
//            String restNotes = data.getStringExtra(RestaurantNotesActivity.EXTRA_NOTES);
//            mRestaurant.setmNotes(restNotes);
//            mCallbacks.onCrimeUpdated(mRestaurant);
//        }
    }
    //check to see if the phone is online
    public boolean isOnline() {
        ConnectivityManager cm = null;
        NetworkInfo networkInfo = null;
        try{
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = cm.getActiveNetworkInfo();
        }catch (Exception e){e.printStackTrace();}
        if (networkInfo != null && networkInfo.isConnectedOrConnecting())
            return true;
        else
            return false;

    }
    private boolean launchBrowser(String strUri){
        if(isOnline())
        {
            Uri uri = Uri.parse(strUri);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }
        else
            return false;

    }
    private String getCrimeReport() {
        String solvedString = null;
        if (mRestaurant.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }
        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mRestaurant.getDate()).toString();
        String suspect = mRestaurant.getSuspect();
        if(suspect == null)
        {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }

        String report = getString(R.string.crime_report, mRestaurant.getTitle(), dateString, solvedString, suspect);

        return report;
    }

    @Override
    public void onPause() {
        super.onPause();
//        RestaurantList.get(getActivity()).saveCrimes();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.rest_frag_options_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;

            case R.id.item1://extract yelp data
                String mTitle = mTitleField.getText().toString();
                String mLocation = mLocationField.getText().toString();
                if(mTitle.matches("") || mLocation.matches(""))
                {
                    Toast.makeText(getActivity(), "Restaurant Name/ Address not provided", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else
                {
                    new APISearchTask().execute(mTitle, mLocation);
                    return true;
                }

            case R.id.item2://visit yelp page
                //need to pass in correct URL based on name and location
                if(true) //isOnline())
                {
                    String mTitleOption = mTitleField.getText().toString();
                    String mLocationOption = mLocationField.getText().toString();
                    if(mTitleOption.matches("") && mLocationOption.matches(""))
                    {
                        Toast.makeText(getActivity(), "Restaurant Name/ Address not provided", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else if(mYelpPageView == null || !mYelpPageViewCheck)
                    {
                        mYelpPageViewCheck = true;
                        Toast.makeText(getActivity(), "Extract Data from Yelp before Viewing page", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else//go to yelp page
                    {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(mYelpPageView));
                        startActivity(i);
                        return true;
                    }
                }
                else
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item3://add notes
                //add notes
                String mTitleOption = mTitleField.getText().toString();
                String mLocationOption = mLocationField.getText().toString();
                if(mTitleOption.matches("") && mLocationOption.matches(""))
                {
                    Toast.makeText(getActivity(), "Restaurant Name/ Address not provided", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else{
                Intent intent = new Intent(getActivity(), RestaurantNotesActivity.class);

                intent.putExtra("NAME", mTitleOption);
                intent.putExtra("OLDNOTES", mRestaurant.getmNotes());
                startActivityForResult(intent, REQUEST_NOTES);
                return true;
                }

            case R.id.item4://take a picture
                Intent i = new Intent(getActivity(), RestaurantCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
                return true;

            case R.id.item5://exit after clearing the stack
                FragmentManager fm = getActivity().getSupportFragmentManager();
                for(int j = 0; j < fm.getBackStackEntryCount(); ++j)
                    fm.popBackStack();
                getActivity().finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        } 
    }
    private class APISearchTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            String name = strings[0];
            String location = strings[1];
            ArrayList<String> result = new ArrayList<String>();

            APISearch yelpApi = new APISearch();

            try {
                yelpApi.search(name, location);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Unable to YELP data, check Internet Connection", Toast.LENGTH_SHORT).show();
            }

            result.add(yelpApi.getResName());
            result.add(yelpApi.getResStreet());
            result.add(yelpApi.getResPhone());
            result.add(yelpApi.getResURL());
            result.add(yelpApi.getResCity());
            result.add(yelpApi.getResState());
            result.add(yelpApi.getResZIP());
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            mTitleField.setText(strings.get(0));
            mAddressField.setText(strings.get(1));
            mPhoneField.setText(strings.get(2));
            mYelpPageView = strings.get(3);
            mLocationField.setText(strings.get(4)+", "+strings.get(5)+", "+strings.get(6));

        }
    }
    private class EndCallListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if(TelephonyManager.CALL_STATE_RINGING == state) {
                Log.i(TAG, "RINGING, number: " + incomingNumber);
            }
            if(TelephonyManager.CALL_STATE_OFFHOOK == state) {
                //wait for phone to go offhook (probably set a boolean flag) so you know your app initiated the call.
                Log.i(TAG, "OFFHOOK");
            }
            if(TelephonyManager.CALL_STATE_IDLE == state) {
                //when this state occurs, and your flag is set, restart your app
                Log.i(TAG, "IDLE");
            }
        }
    }

}
