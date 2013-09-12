//package teamyelp;
//
///**
// * Created by aakritprasad on 8/6/13.
// */
//
//import android.app.Activity;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//import edu.uchicago.teamyelp.yelpapi.APISearch;
//
//public class YelpAPIResultActivity extends Activity {
//    private static final String TAG = "YelpAPIResultActivity";
//
//    private TextView mRestaurantNameTextView, mRestaurantAddressTextView, mRestaurantPhoneTextView;
//    private Button mbackButton;
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreate() called");
//        setContentView(R.layout.activity_yelp_result);
//
//        mRestaurantNameTextView = (TextView) findViewById(R.id.yelp_list_item_restaurantNameTextView);
//        mRestaurantAddressTextView = (TextView) findViewById(R.id.yelp_list_item_restaurantAddressTextView);
//        mRestaurantPhoneTextView = (TextView) findViewById(R.id.yelp_list_item_restaurantPhoneTextView);
//        mbackButton = (Button) findViewById(R.id.backButton);
//
//        String name = (String) getIntent().getExtras().get("NAME");
//        String location = (String) getIntent().getExtras().get("LOCATION");
//        System.out.println(name + ", " + location);
//
//        new APISearchTask().execute(name, location);
//
//        mbackButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                back();
//            }
//        });
//
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//
//    }
//
//
//    private class APISearchTask extends AsyncTask<String, Void, ArrayList<String>> {
//
//        @Override
//        protected ArrayList<String> doInBackground(String... strings) {
//
//            String name = strings[0];
//            String location = strings[1];
//            ArrayList<String> result = new ArrayList<String>();
//
//            APISearch yelpApi = new APISearch();
//
//            try {
//                yelpApi.search(name, location);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            result.add(yelpApi.getResName());
//            result.add(yelpApi.getResAddress());
//            result.add(yelpApi.getResPhone());
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<String> strings) {
//            mRestaurantNameTextView.setText(strings.get(0));
//            mRestaurantAddressTextView.setText(strings.get(1));
//            mRestaurantPhoneTextView.setText(strings.get(2));
//        }
//    }
//
//    private void back() {
////        Intent intent = new Intent(this, StartSearchActivity.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
//        finish();
//    }
//
//}