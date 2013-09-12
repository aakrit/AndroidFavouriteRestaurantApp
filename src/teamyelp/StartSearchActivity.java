//package teamyelp;
//
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Parcel;
//import android.os.Parcelable;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Toast;
//
//import java.io.Serializable;
//
//public class StartSearchActivity extends Activity implements Serializable {
//
//    private static final String TAG = "StartSearchActivity";
//    public static final String RADS = "RADS";
//
//    private EditText mRestaurantNameEditText, mLocationEditText;
//    private Button mSearchButton, mMappingButton;
//    private MyRadioButton[] mRadioButtons;
//    private RadioGroup mSearchOptionsRadioGroup;
//    private boolean mItemSelected = false;
//
//
//    @TargetApi(11)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreate() called");
//        setContentView(R.layout.activity_start_search);
//
//        mRestaurantNameEditText = (EditText) findViewById(R.id.editSearchRestaurant);
//        mLocationEditText = (EditText) findViewById(R.id.editSearchLocation);
//        if(getResources().getBoolean(R.bool.debug)){
//            mRestaurantNameEditText.setText("Pleasant House");
//            mLocationEditText.setText("Chicago");
//        }
//
//        mSearchButton = (Button) findViewById(R.id.searchButton);
//        mSearchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startSearch();
//            }
//        });
//
//
//        mMappingButton = (Button) findViewById(R.id.mappingButton);
//        mMappingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                startMyActivity(NavActivity.class);
//            }
//        });
//
//        mSearchOptionsRadioGroup = (RadioGroup) findViewById(R.id.radioSearchOptions);
//        //disallow submitting until an answer is selected
//        mSearchOptionsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                mItemSelected = true;
//            }
//        });
//
//        if (savedInstanceState != null) {
//            mRadioButtons = (MyRadioButton[]) savedInstanceState.getParcelableArray(StartSearchActivity.RADS);
//            for (int nC = 0; nC < mRadioButtons.length; nC++) {
//                mSearchOptionsRadioGroup.addView(mRadioButtons[nC]);
//            }
//        } else {
//            updateRadioOptions();
//        }
//
//
//    }
//
//    private void startSearch() {
//        if (mItemSelected) {
//            search();
//        } else {
//            Toast toast = Toast.makeText(this, getResources().getText(R.string.pleaseSelectASearchOption), Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }
//
//    private void search() {
//        Button checkedButton = (Button) findViewById(mSearchOptionsRadioGroup.getCheckedRadioButtonId());
//        String version = checkedButton.getText().toString();
//        if (version.equalsIgnoreCase("Yelp API Search")) {
//            startMyActivity(YelpAPIResultActivity.class);
//        }
//        if (version.equalsIgnoreCase("JSOUP Search")) {
//            startMyActivity(JsoupResultActivity.class);
//        }
//        if (version.equalsIgnoreCase("Webview Search")) {
//            startMyActivity(YelpWebViewScrapeResultActivity.class);
//        }
//    }
//
//    private void startMyActivity(Class<?> cls) {
//        Intent intent = new Intent(this, cls);
//        String name = mRestaurantNameEditText.getText().toString();
//        String location = mLocationEditText.getText().toString();
//        intent.putExtra("NAME", name);
//        intent.putExtra("LOCATION", location);
//        startActivity(intent);
//        //finish();
//    }
//
//
//
//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//
//        super.onSaveInstanceState(savedInstanceState);
//        //iterates over all children and sets pointers to null
//        savedInstanceState.putParcelableArray(StartSearchActivity.RADS, mRadioButtons);
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mSearchOptionsRadioGroup.removeAllViews();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_start_search, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_exit:
//                finish();
//                return true;
//            case R.id.menu_search:
//                startSearch();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    private void updateRadioOptions() {
//        mRadioButtons = new MyRadioButton[2];
//        mSearchOptionsRadioGroup.removeAllViews();
//        mRadioButtons[0] = addRadioButton(mSearchOptionsRadioGroup, "Yelp API Search");
//       // mRadioButtons[1] = addRadioButton(mSearchOptionsRadioGroup, "Webview Search");
//        mRadioButtons[1] = addRadioButton(mSearchOptionsRadioGroup, "JSOUP Search");
//
//    }
//
//    private MyRadioButton addRadioButton(RadioGroup optionGroup, String text) {
//        MyRadioButton button = new MyRadioButton(this);
//        button.setText(text);
//        button.setTextColor(Color.BLACK);
//        optionGroup.addView(button);
//        return button;
//    }
//
//    private class MyRadioButton extends RadioButton implements Parcelable {
//        public MyRadioButton(Context context){
//            super(context);
//        }
//
//        //contracted methods of Parcelable interface
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//
//        }
//    }
//}
